package stuff

import daos.Tables
import slick.driver.MySQLDriver.simple._

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
