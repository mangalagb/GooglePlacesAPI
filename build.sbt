name := """GoogleAPI"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "com.google.code.gson" % "gson" % "2.2.4"
)


fork in run := true

routesGenerator := InjectedRoutesGenerator