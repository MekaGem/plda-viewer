name := """plda-viewer"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

//libraryDependencies ++= Seq(
//  javaJdbc,
//  javaEbean,
//  cache,
//  javaWs
//)

libraryDependencies += "org.mongodb" % "mongo-java-driver" % "2.12.4" withSources() withJavadoc()

