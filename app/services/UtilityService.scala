package services

import stuff.Services
import play.api.Play.current

/**
 * Created by hanzki on 22/06/15.
 */
object UtilityService {

  private val simpleImgurUrl = """http://imgur.com/(\w+)""".r

  /**
   * Takes url and tries to parse it into an image url.
   *
   * @param url pointing to a image file or an image hosting site
   * @return url pointing to an image file or the input param if url couldn't be resolved.
   */
  def urlToImageUrl(url: String): String = {
    url match {
      case jpg if jpg.endsWith("jpg") => jpg
      case png if png.endsWith("png") => png
      case gif if gif.endsWith("gif") => gif
      case simpleImgurUrl(id) => s"http://imgur.com/$id.jpg"
      case other => other
    }
  }

  def lol = {
    play.api.db.slick.DB.withSession { implicit session =>
      val subreddits = Services.subredditService.findAll
      subreddits.foreach(s => println(s"${s.id}#${s.name}"))
    }
  }
}
