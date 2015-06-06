package models

import slick.driver.MySQLDriver.simple._

/**
 * Created by hanzki on 7.6.2015.
 */
case class Message(id: Int, content: String)

class Messages(tag: Tag) extends Table[Message](tag, "MESSAGES")
{
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def content = column[String]("CONTENT")
  def * = (id, content) <> (Message.tupled, Message.unapply _)
}

object messages extends TableQuery(new Messages(_)) {
}
