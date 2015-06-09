package background

import java.sql.Timestamp

import models.{RedditImage, redditImages, subReddits}
import play.api.Play.current
import play.api.libs.concurrent.Akka
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json._
import play.api.libs.ws.{WS, WSResponse}
import reddit.{RedditAPI, Subreddit}

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.language.postfixOps
import scala.slick.driver.MySQLDriver.simple._

/**
 * Created by hanzki on 8.6.2015.
 */
object RedditImporter {

  def get(url: String): JsValue = {
    val futureResponse: Future[WSResponse] = WS.url(url).get()
    val response = Await.result(futureResponse, 5 second)
    response.json
  }

  def startImport() = {
    Akka.system.scheduler.scheduleOnce(1 second) {

      val futureSubreddits: Future[List[Subreddit]] = RedditAPI.getSubreddits()

      futureSubreddits.onSuccess{
        case subreddits => play.api.db.slick.DB.withSession { implicit session =>
          subreddits.foreach(subreddit => {
            val subredditId = (subReddits returning subReddits.map(_.id)) +=
              models.SubReddit(None, subreddit.url, subreddit.subscribers,
                subreddit.nsfw, subreddit.redditName, new Timestamp(System.currentTimeMillis()))

            val futurePosts = RedditAPI.getPosts(subreddit)

            futurePosts.onSuccess {
              case posts => play.api.db.slick.DB.withSession { implicit session =>
                posts.filter(p =>
                  p.url.endsWith(".png") ||
                    p.url.endsWith(".jpg") ||
                    p.url.endsWith(".gif") ||
                    p.url.contains("imgur")
                ).foreach(post =>
                  redditImages += RedditImage(None, post.title, post.url, subredditId, post.nsfw, post.redditName)
                  )
              }
            }
          })
        }
      }
    }
  }
}
