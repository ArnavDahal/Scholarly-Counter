name := "pdfParser"
version := "0.1"
organization := "org.myorganization"

crossPaths := false // drop off Scala suffix from artifact names.
autoScalaLibrary := false // exclude scala-library from dependencies


// project description
description := "Papers Parsing Project"

// Enables publishing to maven repo
publishMavenStyle := true

// Do not append Scala versions to the generated artifacts
crossPaths := false

// This forbids including Scala related libraries into the dependency
autoScalaLibrary := false

// library dependencies. (orginization name) % (project name) % (version)
libraryDependencies ++= Seq(
  "org.apache.commons" % "commons-math3" % "3.1.1",
  "org.apache.opennlp" % "opennlp-maxent" % "3.0.3",
  "org.apache.tika" % "tika-parsers" % "1.11",
  "org.scalatest" % "scalatest_2.10" % "2.1.0" %"test",
  "com.novocode" % "junit-interface" % "0.11" % "test",
  "junit" % "junit" % "4.11" % Test,
  "org.hamcrest" % "hamcrest-all" % "1.3"
)