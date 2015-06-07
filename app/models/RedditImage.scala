package models

import slick.driver.MySQLDriver.simple._

/**
 * Created by hanzki on 5.6.2015.
 */
case class RedditImage (id: Int, title: String, src: String, srdId: Int) {
  def subReddit(implicit s: Session): SubReddit = subReddits.filter(_.id === srdId).first
}

class RedditImages(tag: Tag) extends Table[RedditImage](tag, "reddit_images")
{
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def title = column[String]("title")
  def src = column[String]("src")
  def srdId = column[Int]("srd_id")
  def * = (id, title, src, srdId) <> (RedditImage.tupled, RedditImage.unapply _)
}

object redditImages extends TableQuery(new RedditImages(_)) {
}

class LocalRedditImage (
  val title: String,
  val src: String,
  val subReddit: String
)


