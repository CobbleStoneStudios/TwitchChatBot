name := "TwitchChatBot"

organization := "com.css.cobble"

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.12.1"

libraryDependencies ++= Seq(
    "io.netty" % "netty" % "3.10.6.Final",
    "org.scalatest" %% "scalatest" % "3.0.1" % "test",
    "org.pegdown" % "pegdown" % "1.4.2"
)

isSnapshot := version.value.toLowerCase.contains("snapshot")

crossPaths := false

target in Compile in doc := baseDirectory.value / "docs"

apiURL := Some(url("https://cobbleopolis.github.io/TwitchChatBot/"))

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
    