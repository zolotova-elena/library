lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := """library""",
    version := "0.1",
    scalaVersion := "2.13.6",
    libraryDependencies ++= Seq(
      guice,
      "joda-time" % "joda-time" % "2.10.10",
      "org.reactivemongo" %% "play2-reactivemongo" % s"1.0.5-play27",
      "org.reactivemongo" %% "reactivemongo" % "1.0.5",
      "com.typesafe.play" %% "play-json" % "2.9.1",
      "org.typelevel" %% "cats-core" % "2.2.0"
    )
  )