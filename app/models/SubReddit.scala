package models

import slick.driver.MySQLDriver.simple._

/**
 * Created by hanzki on 7.6.2015.
 */
case class SubReddit(id: Int, name: String, subscribers: Int, nsfw: Boolean, redditName: String) {
  val fullName = s"r/$name"
}

class SubReddits(tag: Tag) extends Table[SubReddit](tag, "subreddits")
{
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def subscribers = column[Int]("subscribers")
  def nsfw = column[Boolean]("nsfw")
  def redditName = column[String]("reddit_name")
  def * = (id, name, subscribers, nsfw, redditName) <> (SubReddit.tupled, SubReddit.unapply)
}

object subReddits extends TableQuery(new SubReddits(_)) {
}
