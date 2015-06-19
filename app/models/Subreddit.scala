package models

import java.sql.Timestamp

import scala.slick.driver.MySQLDriver.simple._
import scala.util.Try

/**
 * Created by hanzki on 7.6.2015.
 */
case class Subreddit(id: Option[Int], name: String, subscribers: Int, nsfw: Boolean, redditName: String, updated: Timestamp) {
  val fullName = s"r/$name"
}

class SubRedditTable(tag: Tag) extends Table[Subreddit](tag, "subreddits")
{
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def subscribers = column[Int]("subscribers")
  def nsfw = column[Boolean]("nsfw")
  def redditName = column[String]("reddit_name")
  def updated = column[Timestamp]("updated")
  def * = (id.?, name, subscribers, nsfw, redditName, updated) <> (Subreddit.tupled, Subreddit.unapply)
}

object subReddits extends TableQuery(new SubRedditTable(_)) {

  def save(subreddit: Subreddit)(implicit session: Session): Try[Subreddit] = Try {
    val existing = this.filter(_.name === subreddit.name).firstOption
    existing match {
      case Some(row) => update(subreddit.copy(id = row.id))
      case None => insert(subreddit.copy(id = None))
    }
  }.flatten

  def update(subreddit: Subreddit)(implicit session: Session): Try[Subreddit] = Try {
    this.filter(_.id === subreddit.id).update(subreddit)
    subreddit
  }

  def insert(subreddit: Subreddit)(implicit session: Session): Try[Subreddit] = Try {
    val id = (subReddits returning subReddits.map(_.id)) += subreddit
    subreddit.copy(id = Some(id))
  }
}
