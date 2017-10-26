name := "scala-functional-basics"

organization := "com.seek"

version := "0.1"

scalaVersion := "2.12.3"

crossScalaVersions := Seq("2.11.8", "2.12.3")


libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "1.0.0-MF",

  "com.twitter" %% "finagle-http" % "7.1.0",

  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "org.scalacheck" %% "scalacheck" % "1.13.4" % "test"
)


scalacOptions ++= Seq(
  "-target:jvm-1.8",
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-language:experimental.macros",
  "-unchecked",
  //"-Ywarn-unused-import",
  "-Ywarn-nullary-unit",
  "-Xfatal-warnings",
  "-Xlint",
  //"-Yinline-warnings",
  "-Ywarn-dead-code",
  "-Xfuture")