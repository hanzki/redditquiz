logLevel := Level.Warn

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.3.6")

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick-codegen" % "2.1.0",
  "mysql" % "mysql-connector-java" % "5.1.35"
)