val scala3Version = "3.7.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "Scala 3 sbt zio",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies += "dev.zio" %% "zio-http"     % "3.5.1",
    libraryDependencies += "org.scalameta" %% "munit" % "1.0.0" % Test
    /* astro kedvéért */ , libraryDependencies += "com.kosherjava" % "zmanim" % "2.5.0"
    , libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "2.4.0"
  )
