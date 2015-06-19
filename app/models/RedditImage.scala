package models

import scala.util.{Random, Try}
import slick.driver.MySQLDriver.simple._

/**
 * Created by hanzki on 5.6.2015.
 */
case class RedditImage (id: Option[Int], title: String, src: String, srdId: Int, nsfw: Boolean, redditName: String) {
  def subReddit(implicit s: Session): Subreddit = subReddits.filter(_.id === srdId).first
}

class RedditImageTable(tag: Tag) extends Table[RedditImage](tag, "reddit_images")
{
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def title = column[String]("title")
  def src = column[String]("src")
  def srdId = column[Int]("srd_id")
  def nsfw = column[Boolean]("nsfw")
  def redditName = column[String]("reddit_name")
  def * = (id.?, title, src, srdId, nsfw, redditName) <> (RedditImage.tupled, RedditImage.unapply)
}

object redditImages extends TableQuery(new RedditImageTable(_)) {

  def insert(image: RedditImage)(implicit session: Session): Try[RedditImage] = Try {
    val id = (redditImages returning redditImages.map(_.id)) += image
    image.copy(id = Some(id))
  }

  def random()(implicit session: Session): Try[RedditImage] = Try {
    val all = redditImages.list
    all(Random.nextInt(all.size))
  }
}

class LocalRedditImage (
  val title: String,
  val src: String,
  val subReddit: String
)


