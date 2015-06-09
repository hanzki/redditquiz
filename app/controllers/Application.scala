package controllers

import play.api.db.slick._
import play.api.mvc._
import services.RedditService

case class Post(title: String, url: String)
object Application extends Controller {

  def index = Action {
    Ok(views.html.index("You suck ass!"))
  }

  def reddit = Action {
    val quiz = RedditService.getNewQuiz()
    Ok(views.html.reddit(quiz.image.src, quiz.choices))
  }

  def db = DBAction { implicit rs =>
    val quiz = RedditService.getFromDB
    Ok(views.html.reddit(quiz.image.src, quiz.choices.map(_.name)))
  }
}