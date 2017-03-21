name := "TwitchChatBot"

organization := "com.css.cobble"

version := "1.0.0-SNAPSHOT"

val scalaVer: String = "2.11.7"

scalaVersion := scalaVer

val logbackVersion: String = "1.2.1"

libraryDependencies ++= Seq(
    "io.netty" % "netty-all" % "4.1.9.Final",
    "org.scalatest" %% "scalatest" % "3.0.1" % "test",
    "org.pegdown" % "pegdown" % "1.4.2",
    "org.slf4j" % "slf4j-api" % "1.7.25"
)

isSnapshot := version.value.toLowerCase.contains("snapshot")

crossPaths := false

target in Compile in doc := baseDirectory.value / "docs"

apiURL := Some(url("https://cobblestonestudios.github.io/TwitchChatBot/"))

autoAPIMappings := true

apiMappings += (scalaInstance.value.libraryJar -> url(s"http://www.scala-lang.org/api/${scalaVersion.value}/"))

publishTo := {
    val nexus = "http://mvn.random.haus/repository/"
    if (isSnapshot.value)
        Some("CobbleStoneStudios" at nexus + "maven-snapshots/")
    else
        Some("CobbleStoneStudios" at nexus + "maven-releases/")
}

credentials += Credentials(Path.userHome / ".m3" / ".credentials")

testOptions in Test ++= Seq(
    Tests.Argument(TestFrameworks.ScalaTest, "-u", "target/test-reports"),
    Tests.Argument(TestFrameworks.ScalaTest, "-h", "target/test-reports")
)

javaOptions in Test += "-Dconfig.file=conf/application.test.conf"

lazy val `twitchchatbot` = project in file(".")

lazy val `example` = (project in file("example")).settings(
    name := "ExampleBot",
    scalaVersion := scalaVer,
    libraryDependencies ++= Seq(
        "ch.qos.logback" % "logback-core" % logbackVersion,
        "ch.qos.logback" % "logback-classic" % logbackVersion,
        "com.typesafe" % "config" % "1.3.1"
    ),
    crossPaths := false
).dependsOn(`twitchchatbot`)