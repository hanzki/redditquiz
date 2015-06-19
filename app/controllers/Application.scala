package controllers

import play.api.db.slick._
import play.api.mvc._
import services.RedditService

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("You suck ass!"))
  }

  def reddit = Action {
    val quiz = RedditService.getQuizFromReddit()
    Ok(views.html.reddit(quiz.image.src, quiz.choices))
  }

  def db = DBAction { implicit rs =>
    val quiz = RedditService.getQuizFromDB
    Ok(views.html.reddit(imgurled(quiz.image.src), quiz.choices.map(_.name)))
  }

  private def imgurled(url: String): String = {
    val simpleImgur = """http://imgur.com/(\w+)""".r
    url match {
      case jpg if jpg.endsWith("jpg") => jpg
      case png if png.endsWith("png") => png
      case gif if gif.endsWith("gif") => gif
      case simpleImgur(id) => s"http://imgur.com/$id.jpg"
      case other => other
    }
  }
}