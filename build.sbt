name := "getDataByCorpId"

version := "0.1"

scalaVersion := "2.13.3"



lazy val doobieVersion = "0.9.0"
lazy val http4sVersion = "0.21.7"
lazy val scalaLoggingVersion = "3.9.2"
lazy val logbackClassicVersion = "1.2.3"
lazy val catsCoreVersion = "2.1.1"
lazy val circeVersion = "0.13.0"
lazy val scoptVersion = "0.13.0"

libraryDependencies ++= Seq(
  "org.tpolecat" %% "doobie-core"     % doobieVersion,
  "org.tpolecat" %% "doobie-specs2"   % doobieVersion
)

libraryDependencies ++= Seq(
  "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
  "ch.qos.logback" % "logback-classic" % logbackClassicVersion
)


libraryDependencies += "org.typelevel" %% "cats-core" % catsCoreVersion

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % http4sVersion
)

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-circe" % http4sVersion,
  // Optional for auto-derivation of JSON codecs
  "io.circe" %% "circe-generic" % circeVersion,
  // Optional for string interpolation to JSON model
  "io.circe" %% "circe-literal" % circeVersion,
  "io.circe" %% "circe-parser"  % circeVersion,
  "io.circe" %% "circe-generic-extras" % circeVersion
)

libraryDependencies += "com.github.scopt" %% "scopt" % "4.0.0-RC2"

scalacOptions ++= Seq(
  "-encoding", "UTF-8",
  "-unchecked",
  "-deprecation",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Ywarn-unused",
  "-Ymacro-annotations"
)

assemblyJarName in assembly := "GetDataFromUlk.jar"
test in assembly := {}
mainClass in assembly := Some("ru.mos.it.udrvs.MainApp")