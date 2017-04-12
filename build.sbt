import sbtrelease.ReleasePlugin.autoImport.ReleaseTransformations._

name := "TwitchChatBot"

organization := "com.github.cobblestonestudios"

version := "1.0.0-SNAPSHOT"

val scalaVer: String = "2.11.7"

val logbackVersion: String = "1.2.1"

lazy val `twitchchatbot` = project in file(".")

scalaVersion := scalaVer

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

libraryDependencies ++= Seq(
    "io.netty" % "netty-all" % "4.1.9.Final",
    "org.scalatest" %% "scalatest" % "3.0.1" % "test",
    "org.pegdown" % "pegdown" % "1.4.2",
    "org.slf4j" % "slf4j-api" % "1.7.25"
)

isSnapshot := version.value.toLowerCase.contains("snapshot")

crossPaths := false

run in `twitchchatbot` := run in `example`

target in Compile in doc := baseDirectory.value / "docs"

apiURL := Some(url("https://cobblestonestudios.github.io/TwitchChatBot/"))

autoAPIMappings := true

apiMappings += (scalaInstance.value.libraryJar -> url(s"http://www.scala-lang.org/api/${scalaVersion.value}/"))

publishMavenStyle := true

publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
        Some("snapshots" at nexus + "content/repositories/snapshots")
    else
        Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

testOptions in Test ++= Seq(
    Tests.Argument(TestFrameworks.ScalaTest, "-u", "target/test-reports"),
    Tests.Argument(TestFrameworks.ScalaTest, "-h", "target/test-reports")
)

pomIncludeRepository := { _ => false }

homepage := Some(url("https://cobblestonestudios.github.io/TwitchChatBot/"))

licenses := Seq("MIT" -> url("https://opensource.org/licenses/MIT"))

pomExtra :=
    <scm>
        <url>git@github.com:CobbleStoneStudios/TwitchChatBot.git</url>
        <connection>scm:git@github.com:CobbleStoneStudios/TwitchChatBot.git</connection>
    </scm>
        <developers>
            <developer>
                <id>Cobbleopolis</id>
            </developer>
        </developers>

ghreleaseNotes := { tagName =>
    IO.read(baseDirectory.value / "notes" / s"${tagName.stripPrefix("v")}.md")
}

ghreleaseRepoOrg := "CobbleStoneStudios"

javaOptions in Test += "-Dconfig.file=conf/application.test.conf"

releaseTagName := "v" + version.value

releaseProcess := Seq[ReleaseStep](
    checkSnapshotDependencies,
    runClean,
    runTest,
    ReleaseStep(action = Command.process("publishSigned", _)),
    ReleaseStep(action = Command.process("sonatypeReleaseAll", _))
)

releaseProcess ++= {
    if (!isSnapshot.value)
        Seq[ReleaseStep](
            tagRelease,
            pushChanges,
            ReleaseStep(action = Command.process("githubRelease", _))
        )
    else
        Seq()
}