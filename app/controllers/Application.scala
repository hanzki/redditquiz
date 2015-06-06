package controllers

import models.messages
import play.api.db.slick._
import play.api.mvc._
import services.RedditService

import scala.slick.driver.MySQLDriver.simple._

case class Post(title: String, url: String)
object Application extends Controller {

  def index = Action {
    Ok(views.html.index("You suck ass!"))
  }

  def reddit = Action {
    val quiz = RedditService.getNewQuiz()
    Ok(views.html.reddit(quiz.image.src, quiz.choices))
  }

  def slick = DBAction { implicit rs =>
    val ms = messages.list
    Ok(ms.map(_.content).mkString("\n"))
  }
}