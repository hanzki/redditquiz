package daos.generated
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = scala.slick.driver.MySQLDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: scala.slick.driver.JdbcProfile
  import profile.simple._
  import scala.slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import scala.slick.jdbc.{GetResult => GR}
  
  /** DDL for all tables. Call .create to execute. */
  lazy val ddl = ImageQuizs.ddl ++ PlayEvolutions.ddl ++ RedditImages.ddl ++ Subreddits.ddl
  
  /** Entity class storing rows of table ImageQuizs
   *  @param id Database column id DBType(INT), AutoInc, PrimaryKey
   *  @param imgId Database column img_id DBType(INT)
   *  @param choice1 Database column choice_1 DBType(INT)
   *  @param choice2 Database column choice_2 DBType(INT)
   *  @param choice3 Database column choice_3 DBType(INT)
   *  @param choice4 Database column choice_4 DBType(INT)
   *  @param choice5 Database column choice_5 DBType(INT)
   *  @param choice6 Database column choice_6 DBType(INT) */
  case class ImageQuizsRow(id: Int, imgId: Int, choice1: Int, choice2: Int, choice3: Int, choice4: Int, choice5: Int, choice6: Int)
  /** GetResult implicit for fetching ImageQuizsRow objects using plain SQL queries */
  implicit def GetResultImageQuizsRow(implicit e0: GR[Int]): GR[ImageQuizsRow] = GR{
    prs => import prs._
    ImageQuizsRow.tupled((<<[Int], <<[Int], <<[Int], <<[Int], <<[Int], <<[Int], <<[Int], <<[Int]))
  }
  /** Table description of table image_quizs. Objects of this class serve as prototypes for rows in queries. */
  class ImageQuizs(_tableTag: Tag) extends Table[ImageQuizsRow](_tableTag, "image_quizs") {
    def * = (id, imgId, choice1, choice2, choice3, choice4, choice5, choice6) <> (ImageQuizsRow.tupled, ImageQuizsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (id.?, imgId.?, choice1.?, choice2.?, choice3.?, choice4.?, choice5.?, choice6.?).shaped.<>({r=>import r._; _1.map(_=> ImageQuizsRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column id DBType(INT), AutoInc, PrimaryKey */
    val id: Column[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column img_id DBType(INT) */
    val imgId: Column[Int] = column[Int]("img_id")
    /** Database column choice_1 DBType(INT) */
    val choice1: Column[Int] = column[Int]("choice_1")
    /** Database column choice_2 DBType(INT) */
    val choice2: Column[Int] = column[Int]("choice_2")
    /** Database column choice_3 DBType(INT) */
    val choice3: Column[Int] = column[Int]("choice_3")
    /** Database column choice_4 DBType(INT) */
    val choice4: Column[Int] = column[Int]("choice_4")
    /** Database column choice_5 DBType(INT) */
    val choice5: Column[Int] = column[Int]("choice_5")
    /** Database column choice_6 DBType(INT) */
    val choice6: Column[Int] = column[Int]("choice_6")
    
    /** Foreign key referencing RedditImages (database name image_quizs_ibfk_1) */
    lazy val redditImagesFk = foreignKey("image_quizs_ibfk_1", imgId, RedditImages)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Subreddits (database name image_quizs_ibfk_2) */
    lazy val subredditsFk2 = foreignKey("image_quizs_ibfk_2", choice1, Subreddits)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Subreddits (database name image_quizs_ibfk_3) */
    lazy val subredditsFk3 = foreignKey("image_quizs_ibfk_3", choice2, Subreddits)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Subreddits (database name image_quizs_ibfk_4) */
    lazy val subredditsFk4 = foreignKey("image_quizs_ibfk_4", choice3, Subreddits)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Subreddits (database name image_quizs_ibfk_5) */
    lazy val subredditsFk5 = foreignKey("image_quizs_ibfk_5", choice4, Subreddits)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Subreddits (database name image_quizs_ibfk_6) */
    lazy val subredditsFk6 = foreignKey("image_quizs_ibfk_6", choice5, Subreddits)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Subreddits (database name image_quizs_ibfk_7) */
    lazy val subredditsFk7 = foreignKey("image_quizs_ibfk_7", choice6, Subreddits)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table ImageQuizs */
  lazy val ImageQuizs = new TableQuery(tag => new ImageQuizs(tag))
  
  /** Entity class storing rows of table PlayEvolutions
   *  @param id Database column id DBType(INT), PrimaryKey
   *  @param hash Database column hash DBType(VARCHAR), Length(255,true)
   *  @param appliedAt Database column applied_at DBType(TIMESTAMP)
   *  @param applyScript Database column apply_script DBType(TEXT), Length(65535,true), Default(None)
   *  @param revertScript Database column revert_script DBType(TEXT), Length(65535,true), Default(None)
   *  @param state Database column state DBType(VARCHAR), Length(255,true), Default(None)
   *  @param lastProblem Database column last_problem DBType(TEXT), Length(65535,true), Default(None) */
  case class PlayEvolutionsRow(id: Int, hash: String, appliedAt: java.sql.Timestamp, applyScript: Option[String] = None, revertScript: Option[String] = None, state: Option[String] = None, lastProblem: Option[String] = None)
  /** GetResult implicit for fetching PlayEvolutionsRow objects using plain SQL queries */
  implicit def GetResultPlayEvolutionsRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Timestamp], e3: GR[Option[String]]): GR[PlayEvolutionsRow] = GR{
    prs => import prs._
    PlayEvolutionsRow.tupled((<<[Int], <<[String], <<[java.sql.Timestamp], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table play_evolutions. Objects of this class serve as prototypes for rows in queries. */
  class PlayEvolutions(_tableTag: Tag) extends Table[PlayEvolutionsRow](_tableTag, "play_evolutions") {
    def * = (id, hash, appliedAt, applyScript, revertScript, state, lastProblem) <> (PlayEvolutionsRow.tupled, PlayEvolutionsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (id.?, hash.?, appliedAt.?, applyScript, revertScript, state, lastProblem).shaped.<>({r=>import r._; _1.map(_=> PlayEvolutionsRow.tupled((_1.get, _2.get, _3.get, _4, _5, _6, _7)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column id DBType(INT), PrimaryKey */
    val id: Column[Int] = column[Int]("id", O.PrimaryKey)
    /** Database column hash DBType(VARCHAR), Length(255,true) */
    val hash: Column[String] = column[String]("hash", O.Length(255,varying=true))
    /** Database column applied_at DBType(TIMESTAMP) */
    val appliedAt: Column[java.sql.Timestamp] = column[java.sql.Timestamp]("applied_at")
    /** Database column apply_script DBType(TEXT), Length(65535,true), Default(None) */
    val applyScript: Column[Option[String]] = column[Option[String]]("apply_script", O.Length(65535,varying=true), O.Default(None))
    /** Database column revert_script DBType(TEXT), Length(65535,true), Default(None) */
    val revertScript: Column[Option[String]] = column[Option[String]]("revert_script", O.Length(65535,varying=true), O.Default(None))
    /** Database column state DBType(VARCHAR), Length(255,true), Default(None) */
    val state: Column[Option[String]] = column[Option[String]]("state", O.Length(255,varying=true), O.Default(None))
    /** Database column last_problem DBType(TEXT), Length(65535,true), Default(None) */
    val lastProblem: Column[Option[String]] = column[Option[String]]("last_problem", O.Length(65535,varying=true), O.Default(None))
  }
  /** Collection-like TableQuery object for table PlayEvolutions */
  lazy val PlayEvolutions = new TableQuery(tag => new PlayEvolutions(tag))
  
  /** Entity class storing rows of table RedditImages
   *  @param id Database column id DBType(INT), AutoInc, PrimaryKey
   *  @param srdId Database column srd_id DBType(INT)
   *  @param title Database column title DBType(VARCHAR), Length(255,true)
   *  @param src Database column src DBType(VARCHAR), Length(255,true)
   *  @param nsfw Database column nsfw DBType(BIT), Default(None)
   *  @param redditName Database column reddit_name DBType(VARCHAR), Length(255,true), Default(None) */
  case class RedditImagesRow(id: Int, srdId: Int, title: String, src: String, nsfw: Option[Boolean] = None, redditName: Option[String] = None)
  /** GetResult implicit for fetching RedditImagesRow objects using plain SQL queries */
  implicit def GetResultRedditImagesRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[Boolean]], e3: GR[Option[String]]): GR[RedditImagesRow] = GR{
    prs => import prs._
    RedditImagesRow.tupled((<<[Int], <<[Int], <<[String], <<[String], <<?[Boolean], <<?[String]))
  }
  /** Table description of table reddit_images. Objects of this class serve as prototypes for rows in queries. */
  class RedditImages(_tableTag: Tag) extends Table[RedditImagesRow](_tableTag, "reddit_images") {
    def * = (id, srdId, title, src, nsfw, redditName) <> (RedditImagesRow.tupled, RedditImagesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (id.?, srdId.?, title.?, src.?, nsfw, redditName).shaped.<>({r=>import r._; _1.map(_=> RedditImagesRow.tupled((_1.get, _2.get, _3.get, _4.get, _5, _6)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column id DBType(INT), AutoInc, PrimaryKey */
    val id: Column[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column srd_id DBType(INT) */
    val srdId: Column[Int] = column[Int]("srd_id")
    /** Database column title DBType(VARCHAR), Length(255,true) */
    val title: Column[String] = column[String]("title", O.Length(255,varying=true))
    /** Database column src DBType(VARCHAR), Length(255,true) */
    val src: Column[String] = column[String]("src", O.Length(255,varying=true))
    /** Database column nsfw DBType(BIT), Default(None) */
    val nsfw: Column[Option[Boolean]] = column[Option[Boolean]]("nsfw", O.Default(None))
    /** Database column reddit_name DBType(VARCHAR), Length(255,true), Default(None) */
    val redditName: Column[Option[String]] = column[Option[String]]("reddit_name", O.Length(255,varying=true), O.Default(None))
    
    /** Foreign key referencing Subreddits (database name reddit_images_ibfk_1) */
    lazy val subredditsFk = foreignKey("reddit_images_ibfk_1", srdId, Subreddits)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    
    /** Uniqueness Index over (srdId,src) (database name uc_SubredditSrc) */
    val index1 = index("uc_SubredditSrc", (srdId, src), unique=true)
  }
  /** Collection-like TableQuery object for table RedditImages */
  lazy val RedditImages = new TableQuery(tag => new RedditImages(tag))
  
  /** Entity class storing rows of table Subreddits
   *  @param id Database column id DBType(INT), AutoInc, PrimaryKey
   *  @param name Database column name DBType(VARCHAR), Length(255,true)
   *  @param subscribers Database column subscribers DBType(INT), Default(None)
   *  @param nsfw Database column nsfw DBType(BIT), Default(None)
   *  @param redditName Database column reddit_name DBType(VARCHAR), Length(255,true), Default(None)
   *  @param updated Database column updated DBType(DATETIME), Default(None) */
  case class SubredditsRow(id: Int, name: String, subscribers: Option[Int] = None, nsfw: Option[Boolean] = None, redditName: Option[String] = None, updated: Option[java.sql.Timestamp] = None)
  /** GetResult implicit for fetching SubredditsRow objects using plain SQL queries */
  implicit def GetResultSubredditsRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[Int]], e3: GR[Option[Boolean]], e4: GR[Option[String]], e5: GR[Option[java.sql.Timestamp]]): GR[SubredditsRow] = GR{
    prs => import prs._
    SubredditsRow.tupled((<<[Int], <<[String], <<?[Int], <<?[Boolean], <<?[String], <<?[java.sql.Timestamp]))
  }
  /** Table description of table subreddits. Objects of this class serve as prototypes for rows in queries. */
  class Subreddits(_tableTag: Tag) extends Table[SubredditsRow](_tableTag, "subreddits") {
    def * = (id, name, subscribers, nsfw, redditName, updated) <> (SubredditsRow.tupled, SubredditsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (id.?, name.?, subscribers, nsfw, redditName, updated).shaped.<>({r=>import r._; _1.map(_=> SubredditsRow.tupled((_1.get, _2.get, _3, _4, _5, _6)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column id DBType(INT), AutoInc, PrimaryKey */
    val id: Column[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name DBType(VARCHAR), Length(255,true) */
    val name: Column[String] = column[String]("name", O.Length(255,varying=true))
    /** Database column subscribers DBType(INT), Default(None) */
    val subscribers: Column[Option[Int]] = column[Option[Int]]("subscribers", O.Default(None))
    /** Database column nsfw DBType(BIT), Default(None) */
    val nsfw: Column[Option[Boolean]] = column[Option[Boolean]]("nsfw", O.Default(None))
    /** Database column reddit_name DBType(VARCHAR), Length(255,true), Default(None) */
    val redditName: Column[Option[String]] = column[Option[String]]("reddit_name", O.Length(255,varying=true), O.Default(None))
    /** Database column updated DBType(DATETIME), Default(None) */
    val updated: Column[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("updated", O.Default(None))
    
    /** Uniqueness Index over (name) (database name uc_Name) */
    val index1 = index("uc_Name", name, unique=true)
  }
  /** Collection-like TableQuery object for table Subreddits */
  lazy val Subreddits = new TableQuery(tag => new Subreddits(tag))
}