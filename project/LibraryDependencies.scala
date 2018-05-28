import sbt._

object LibraryVersions {
  val akka = "2.5.8"
  val akkaHTTP = "10.0.11"
  val ignite = "2.0.0"
  val scalactic = "3.0.1"
  val json4s = "3.5.0"
  val akkaClusterManagement = "0.5"
}

object LibraryRepos {
  val akka = "com.typesafe.akka"
}

object Library {
  val config = "com.typesafe" % "config" % "1.3.1"
  val scalactic = "org.scalactic" %% "scalactic" % LibraryVersions.scalactic
  val akkaActor = LibraryRepos.akka %% "akka-actor" % LibraryVersions.akka
  val akkaAgent = LibraryRepos.akka %% "akka-agent" % LibraryVersions.akka
  val akkaClusterTools = LibraryRepos.akka %% "akka-cluster-tools" % LibraryVersions.akka
  val json4sNative = "org.json4s" %% "json4s-native" % LibraryVersions.json4s
  val akkaCluster = LibraryRepos.akka %% "akka-cluster" % LibraryVersions.akka
  val akkaClusterMetrics = LibraryRepos.akka %% "akka-cluster-metrics" % LibraryVersions.akka
  val akkaMultiNodeTestKit = LibraryRepos.akka %% "akka-multi-node-testkit" % LibraryVersions.akka % "test"
  val akkaTestKit = LibraryRepos.akka %% "akka-testkit" % LibraryVersions.akka % "test"
  val akkaHTTP = LibraryRepos.akka %% "akka-http" % LibraryVersions.akkaHTTP
  val scalaTest = "org.scalatest" %% "scalatest" % LibraryVersions.scalactic % "test"
}
