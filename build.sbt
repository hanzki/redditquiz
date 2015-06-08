name := "RedditQuiz"

version := "1.0"

lazy val `redditquiz` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

scalacOptions ++= Seq("-feature")

libraryDependencies ++= Seq( jdbc , anorm , cache , ws )

libraryDependencies ++= Seq(
  "mysql" % "mysql-connector-java" % "5.1.6",
  "com.typesafe.play" %% "play-slick" % "0.8.0",
  "com.typesafe.akka" %% "akka-actor" % "2.3.11",
  "com.typesafe.akka" %% "akka-contrib" % "2.3.11"
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  