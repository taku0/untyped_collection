name := "untyped_collection"

version := "0.1"

scalaVersion := "2.10.1"

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-unchecked"
)

libraryDependencies ++= Seq(
  "org.json" % "json" % "20090211"
)
