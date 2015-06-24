package stuff


import daos.generated.Tables

import scala.slick.driver.MySQLDriver.simple._

/**
 * Created by hanzki on 23/06/15.
 */
trait SubredditServiceComponent {
  def subredditService: SubredditService

  case class Subreddit(id: Int, name: String)

  trait SubredditService {
    def findAll(implicit session: Session): Seq[Subreddit]
  }
}

trait DefaultSubredditServiceComponent extends SubredditServiceComponent {
  this: Tables =>

  def subredditService = new DefaultSubredditService

  class DefaultSubredditService extends SubredditService {
    def findAll(implicit session: Session) = Subreddits.list.map(s => Subreddit(s.id, s.name))
  }

}

object Services {
  val subredditServiceComponent = new DefaultSubredditServiceComponent with Tables {
    val profile = scala.slick.driver.MySQLDriver
  }

  val subredditService = subredditServiceComponent.subredditService
}

object FooDao extends {
  val profile = scala.slick.driver.MySQLDriver
} with Tables {
  val name = "Foo"
  val q = Subreddits

  def withOddId = q.filter(_.id % 2 === 1)

}

object BarDao extends {
  val profile = scala.slick.driver.MySQLDriver
} with Tables {
  val name = "bar"
  val q = Subreddits

  def withEvenId = q.filter(_.id % 2 === 0)

}
