package reddit

import akka.actor.{Actor, Props}
import akka.contrib.throttle.Throttler._
import akka.contrib.throttle.TimerBasedThrottler
import akka.pattern.ask
import akka.util.Timeout
import play.api.Play.current
import play.api.libs.concurrent.Akka
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.libs.ws.{WS, WSResponse}

import scala.concurrent.Future
import scala.concurrent.duration._

/**
 * Created by hanzki on 8.6.2015.
 */
object RedditAPI {
  implicit val timeout = Timeout(5.seconds)
  private val reddit = Akka.system.actorOf(Props[RedditActor], name = "redditactor")
  private val throttler = Akka.system.actorOf(Props(classOf[TimerBasedThrottler], 30 msgsPer 1.second))

  throttler ! SetTarget(Some(reddit))

  def test(msg: String) = throttler ! msg

  def getSubreddits(after: String = ""): Future[List[Subreddit]] = {
    val futureResponse = (throttler ?
      RedditRequest(s"http://www.reddit.com/subreddits.json?limit=100&after=$after")
      ).mapTo[RedditResponse]
    futureResponse.flatMap(_.futureJson).map {json =>

      implicit val subredditReads: Reads[Subreddit] = (
        (__ \ "data" \ "url").read[String] and
        (__ \ "data" \ "over18").read[Boolean] and
        (__ \ "data" \ "subscribers").read[Int] and
        (__ \ "data" \ "name").read[String])(Subreddit.apply _)

      (json \ "data" \ "children").as[List[Subreddit]]
    }
  }

  def getPosts(subreddit: Subreddit, after: String = ""): Future[List[Post]] = {
    val futureResponse = (throttler ?
      RedditRequest(s"http://www.reddit.com${subreddit.url}.json?limit=100&after=$after")
      ).mapTo[RedditResponse]
    futureResponse.flatMap(_.futureJson).map {json =>

      implicit val PostReads: Reads[Post] = (
        (__ \ "data" \ "title").read[String] and
        (__ \ "data" \ "url").read[String] and
        (__ \ "data" \ "over_18").read[Boolean] and
        (__ \ "data" \ "permalink").read[String] and
        (__ \ "data" \ "name").read[String])(Post.apply _)

      (json \ "data" \ "children").as[List[Post]]
    }
  }
}

case class Subreddit(url: String, nsfw: Boolean, subscribers: Int, redditName: String)
case class Post(title: String, url: String, nsfw: Boolean, selfUrl: String, redditName: String)

private case class RedditRequest(url: String)
private case class RedditResponse(futureJson: Future[JsValue])

private class RedditActor extends Actor {
  def receive = {
    case RedditRequest(url) => {
      val futureResponse: Future[WSResponse] = WS.url(url).get()
      sender() ! RedditResponse(futureResponse.map(_.json))
    }
  }
}