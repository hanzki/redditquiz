package services

import models._
import play.api.Play.current
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.libs.ws._

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.language.postfixOps
import scala.slick.driver.MySQLDriver.simple._

/**
 * Created by hanzki on 2.6.2015.
 */
object RedditService {

  def getNewQuiz(): LocalImageQuiz = {
    val futureResponse: Future[WSResponse] = WS.url("http://www.reddit.com/r/random/new.json").get()
    val response = Await.result(futureResponse, 1 second)

    case class Post(title: String, url: String, subreddit: String)

    implicit val postReads: Reads[Post] = (
      (JsPath \ "data" \ "title").read[String] and
        (JsPath \ "data" \ "url").read[String] and
        (JsPath \ "data" \ "subreddit").read[String]
      )(Post.apply _)

    val posts = (response.json \ "data" \ "children").as[List[Post]]

    val imagePost = posts.find(
                      p => p.url.endsWith(".jpg") ||
                      p.url.endsWith(".png")
                    ).getOrElse(Post("I'm sorry", "http://cdn.meme.am/instances/400x/56087995.jpg", "funny"))

    val choices = List("/r/funny", "/r/gaming", "/r/awwww", "/r/ftw", "/r/trees", s"/r/${posts.head.subreddit}")

    new LocalImageQuiz(new LocalRedditImage(imagePost.title, imagePost.url, s"/r/${imagePost.subreddit}"), choices)
  }

  def getFromDB(implicit s: Session): ImageQuiz = {
    imageQuizs.sortBy(_ => SimpleFunction[Double]("rand").apply(Seq.empty)).first
  }
}
