// Set the version of Scala
ThisBuild / scalaVersion := "3.3.3"

// Set the scala compiler options
ThisBuild / scalacOptions ++= Seq(
  "-feature",
  "-deprecation",
  "-explain",
  "-explain-types",
  "-language:implicitConversions",
)

lazy val root = project
  .in(file("."))
  .settings(
    // Set the name of the project
    name := "scala-example",

    // Add the ScalaTest dependency
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15" % Test,

    // Enable the coverage plugin
    coverageEnabled := true,

    // Set the WartRemover classpaths
    wartremoverClasspaths += "file://" + baseDirectory.value + "/../lib/warts.jar",

    // Enable the WartRemover plugin
    wartremoverErrors ++= Seq(
      Wart.AsInstanceOf, Wart.IsInstanceOf, Wart.Null, Wart.Return, Wart.Throw,
      Wart.Var, Wart.While, Wart.MutableDataStructures,
      Wart.custom("kuplrg.warts.TryCatch"),
    ),

    // Exclude some files from the wartremover checks
    wartremoverExcluded ++= Seq(
      baseDirectory.value / "src" / "main" / "scala" / "kuplrg" / "error.scala",
      baseDirectory.value / "src" / "test" / "scala" / "kuplrg",
    )
  )

// Set the main class for 'sbt run'
Compile / mainClass := Some("kuplrg.App")

// Run the main class
run := (root / Compile / run).evaluated

// Run the tests in the 'test' configuration
test := (root / Test / test).value

// Automatically reload the project when source files change
Global / onChangedBuildSource := ReloadOnSourceChanges

// Enable the 'groups' feature for Scaladoc
Compile / doc / scalacOptions := Seq("-groups")
