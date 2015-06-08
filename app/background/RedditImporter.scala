package background

import play.Logger
import play.api.Play.current
import play.api.libs.concurrent.Akka
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._
import play.api.libs.ws.{WS, WSResponse}

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.language.postfixOps

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
      val srjson = get("http://www.reddit.com/subreddits.json")

      case class Post(title: String, url: String, subreddit: String)
      case class Subreddit(url: String)

      implicit val postReads: Reads[Post] = (
        (JsPath \ "data" \ "title").read[String] and
          (JsPath \ "data" \ "url").read[String] and
          (JsPath \ "data" \ "subreddit").read[String]
        )(Post.apply _)

      implicit val subredditReads: Reads[Subreddit] =(JsPath \ "data" \ "url").read[String].map(Subreddit.apply _)

      val subreddits = (srjson \ "data" \ "children").as[List[Subreddit]]

      subreddits.foreach{ sr =>
        Logger.info(sr.url)
        val postjson = get(s"http://www.reddit.com${sr.url}.json")
        val posts = (postjson \ "data" \ "children").as[List[Post]]
        posts.map(p => s"${p.title} ${p.url}").foreach(Logger.info)
      }
//      val imagePost = posts.find(
//        p => p.url.endsWith(".jpg") ||
//          p.url.endsWith(".png")
//      ).getOrElse(Post("I'm sorry", "http://cdn.meme.am/instances/400x/56087995.jpg", "funny"))
//
//      val choices = List("/r/funny", "/r/gaming", "/r/awwww", "/r/ftw", "/r/trees", s"/r/${posts.head.subreddit}")
//
//      new LocalImageQuiz(new LocalRedditImage(imagePost.title, imagePost.url, s"/r/${imagePost.subreddit}"), choices)
    }
  }
}
