import play.api.{Application, GlobalSettings, Logger}

/**
 * Created by hanzki on 8.6.2015.
 */
object Global extends GlobalSettings {

  override def onStart(app: Application): Unit = {
    Logger.info("Starting the application")
    //RedditImporter.startImport()


    //(1 to 100).foreach(i => RedditAPI.test(s"message #$i"))
//    case class SubredditWithPosts(subreddit: Subreddit, posts: List[Post])
//
//    val futureSubreddits: Future[List[Subreddit]] = RedditAPI.getSubreddits()
//
//    val subredditsWithPosts: Future[List[SubredditWithPosts]] = futureSubreddits.flatMap(subreddits =>
//      Future.sequence(subreddits.map(sr => RedditAPI.getPosts(sr).map(ps => SubredditWithPosts(sr, ps))))
//    )

//    futureSubreddits.onSuccess{
//      case sreddits => sreddits.foreach { sr =>
//        Logger.info(s"${sr.url} ${if (sr.nsfw) "+" else "-"} ${sr.subscribers}")
//        val futurePosts = RedditAPI.getPosts(sr)
//        val posts = Await.result(futurePosts, 5.seconds)
//        val imageCount = posts.count(p =>
//          p.url.endsWith(".png") ||
//          p.url.endsWith(".jpg") ||
//          p.url.endsWith(".gif") ||
//          p.url.contains("imgur")
//        )
//        Logger.info(s"Found $imageCount images")
//      }
//    }
//    subredditsWithPosts.onSuccess{
//      case sreddits => sreddits.foreach { srwp =>
//        val sr = srwp.subreddit
//        Logger.info(s"${sr.url} ${if (sr.nsfw) "+" else "-"} ${sr.subscribers}")
//        val posts = srwp.posts
//        val imageCount = posts.count(p =>
//          p.url.endsWith(".png") ||
//          p.url.endsWith(".jpg") ||
//          p.url.endsWith(".gif") ||
//          p.url.contains("imgur")
//        )
//        Logger.info(s"Found $imageCount images")
//      }
//    }
  }

  override def onStop(app: Application): Unit = {
    Logger.info("Shutting down the application")
  }
}
