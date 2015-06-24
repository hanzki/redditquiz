name := "RedditQuiz"

version := "1.0"

lazy val `redditquiz` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

scalacOptions ++= Seq("-feature", "-Xmax-classfile-name", "130")

libraryDependencies ++= Seq( jdbc , anorm , cache , ws )

libraryDependencies ++= Seq(
  "mysql" % "mysql-connector-java" % "5.1.35",
  "com.typesafe.play" %% "play-slick" % "0.8.0",
  "com.typesafe.akka" %% "akka-actor" % "2.3.11",
  "com.typesafe.akka" %% "akka-contrib" % "2.3.11",
  "com.typesafe.slick" %% "slick-codegen" % "2.1.0"
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

unmanagedSourceDirectories in Compile <+= baseDirectory ( _ /"target/scala-2.11/src_managed/main")

generateDaos := {
  val slickDriver = "scala.slick.driver.MySQLDriver"
  val jdbcDriver = "com.mysql.jdbc.Driver"
  val url = "jdbc:mysql://localhost/redditquiz"
  val outputFolder = "app"
  val pkg = "daos.generated"
  val user = "root"
  val password = ""
  scala.slick.codegen.SourceCodeGenerator.main(Array(slickDriver, jdbcDriver, url, outputFolder, pkg, user, password))
}