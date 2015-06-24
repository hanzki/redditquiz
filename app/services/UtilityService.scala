package services

import daos.SubredditsDao
import daos.SubredditsDao.SubR
import daos.generated.Tables.SubredditsRow
import play.api.Play.current
import stuff.{BarDao, FooDao, Services}

import scala.slick.driver.MySQLDriver.simple._


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

  def eh = {
    play.api.db.slick.DB.withSession { implicit session =>
      val sr : SubR = SubredditsDao.q.first
      println(sr)
    }
  }

  def meh = {
    play.api.db.slick.DB.withSession { implicit session =>
      val foo = FooDao.q.sortBy(_.name.desc).first
      println(s"${FooDao.name}: ${foo.name}")
      val bar = BarDao.q.sortBy(_.name.asc).first
      println(s"${BarDao.name}: ${bar.name}")
      val meh = BarDao.withEvenId.first
      val lol = FooDao.withOddId.first
      println(s"even: ${meh.id} odd: ${lol.id}")
      var foobar = Seq(foo, bar)
      
      val s: SubredditsRow = SubredditsDao.q.first
    }
  }
}
