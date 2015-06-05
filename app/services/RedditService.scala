package services

import models.ImageQuiz
import play.api.Play.current
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.libs.ws._

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

/**
 * Created by hanzki on 2.6.2015.
 */
object RedditService {

  def getNewQuiz(): ImageQuiz = {
    val holder: WSRequestHolder = WS.url("http://www.reddit.com/r/funny/new.json")
    val futureResponse: Future[WSResponse] = holder.get()
    val response = Await.result(futureResponse, 1 second)

    case class Post(title: String, url: String)

    implicit val postReads: Reads[Post] = (
      (JsPath \ "data" \ "title").read[String] and
        (JsPath \ "data" \ "url").read[String]
      )(Post.apply _)

    val posts = (response.json \ "data" \ "children").as[List[Post]]
  }
}
