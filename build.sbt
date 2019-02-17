ThisBuild / scalaVersion      := "2.12.8"
ThisBuild / version           := "0.1.0-SNAPSHOT"
ThisBuild / organization      := "com.github.gigurra"
ThisBuild / organizationName  := "com.github.gigurra"
ThisBuild / resolvers         += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
ThisBuild / resolvers         += "jitpack" at "https://jitpack.io"


lazy val root = (project in file("."))
  .settings(
    name := "upp2",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5",
    libraryDependencies += "com.github.strikerx3" % "jxinput" % "v1.0.0",
    libraryDependencies += "net.java.jinput" % "jinput" % "2.0.9",
    libraryDependencies += "net.java.jinput" % "jinput-platform" % "2.0.7",
    libraryDependencies += "net.java.jinput" % "windows-plugin" % "2.0.9",
    libraryDependencies += "commons-io" % "commons-io" % "2.6",

      /*libraryDependencies += scalaTest % Test,
      libraryDependencies += jxinput,
      libraryDependencies += jinput,
      libraryDependencies += jinputNatives,*/
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
