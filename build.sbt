import sbt._

name := "vergesense-client"

version := "1.0"

scalaVersion := "2.12.6"

lazy val core = Project("vergesense-client", file("."))
  .configs(IntegrationTest)
  .settings(Defaults.itSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      Library.config,
      Library.akkaActor,
      Library.akkaHTTP,
      Library.akkaCluster,
      Library.akkaClusterTools,
      Library.akkaTestKit,
      Library.json4sNative,
      Library.scalaTest
    )
  ).dependsOn(configuration % "compile->compile")

lazy val configuration = Project("configuration", file("configuration"))
  .settings(
    libraryDependencies ++= Seq(
      Library.config
    )
  )