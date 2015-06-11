package background

import models.{RedditImage, Subreddit, redditImages, subReddits}
import play.api.Play.current
import play.api.libs.concurrent.Akka
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json._
import play.api.libs.ws.{WS, WSResponse}
import reddit.RedditAPI

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.language.postfixOps
import scala.slick.driver.MySQLDriver.simple._
import scala.util.{Failure, Success}

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
          val existingSubreddits: List[Subreddit] = subReddits.list
          subreddits.foreach(subreddit => {
            val existing = existingSubreddits.find(_.name == subreddit.name)
            existing match {
              case Some(sr) => {
                updateSubreddit(subreddit)
              }
              case None => {
                updateSubreddit(subreddit)
              }
            }
          })
        }
      }
    }
  }

  private def updateSubreddit(subreddit: Subreddit)(implicit session: Session) = {
    val updatedSubreddit = subReddits.save(subreddit).get
    val futurePosts = RedditAPI.getPosts(updatedSubreddit)

    futurePosts.onSuccess {
      case posts => play.api.db.slick.DB.withSession { implicit session =>
        posts.withFilter(imageFilter).foreach(post =>
          redditImages.insert(post) match {
            case Success(post) => Unit
            case Failure(ex) => println(s"insert error: ${ex.getMessage}")
          }

          )
      }
    }
  }

  private def imageFilter(post: RedditImage): Boolean = {
      post.src.endsWith(".png") ||
      post.src.endsWith(".jpg") ||
      post.src.endsWith(".gif") ||
      post.src.contains("imgur")
  }
}
