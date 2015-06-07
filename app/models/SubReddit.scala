package models

import slick.driver.MySQLDriver.simple._

/**
 * Created by hanzki on 7.6.2015.
 */
case class SubReddit(id: Int, name: String) {
  val fullName = s"r/$name"
}

class SubReddits(tag: Tag) extends Table[SubReddit](tag, "subreddits")
{
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def * = (id, name) <> (SubReddit.tupled, SubReddit.unapply _)
}

object subReddits extends TableQuery(new SubReddits(_)) {
}
