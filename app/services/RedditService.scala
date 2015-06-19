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
import scala.util.Random

/**
 * Created by hanzki on 2.6.2015.
 */
object RedditService {

  def getQuizFromReddit(): LocalImageQuiz = {
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

  def getQuizFromDB(implicit s: Session): ImageQuiz = {
    val image = redditImages.random().get
    val subreddits: List[Subreddit] = subReddits.list
    val choices: List[Subreddit] = Random.shuffle(subreddits).filterNot(_.id.contains(image.srdId)).take(5) ++ List(subreddits.find(_.id.contains(image.srdId)).get)

    val quiz = ImageQuiz(None, image.id.get,
      choices(0).id.get,
      choices(1).id.get,
      choices(2).id.get,
      choices(3).id.get,
      choices(4).id.get,
      choices(5).id.get)

    imageQuizs.insert(quiz).get

  }
}
