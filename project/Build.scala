import sbt._

object HelloBuild extends Build {

  val generateDaos = taskKey[Unit]("demo key D")

}
