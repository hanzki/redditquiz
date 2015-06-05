package controllers

import play.api.Play.current
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.ws._
import play.api.mvc._

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

case class Post(title: String, url: String)
object Application extends Controller {

  def index = Action {
    Ok(views.html.index("You suck ass!"))
  }

  def reddit = Action {
    val holder: WSRequestHolder = WS.url("http://www.reddit.com/r/funny/new.json")
    val futureResponse: Future[WSResponse] = holder.get()
    val response = Await.result(futureResponse, 1 second)

    implicit val postReads: Reads[Post] = (
        (JsPath \ "data" \ "title").read[String] and
        (JsPath \ "data" \ "url").read[String]
      )(Post.apply _)

    val posts = (response.json \ "data" \ "children").as[List[Post]]
    Ok(views.html.reddit(posts))
  }
}