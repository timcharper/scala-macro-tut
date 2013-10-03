import sbt._
import Keys._

object MyBuild extends Build {
  val buildSettings = Defaults.defaultSettings ++ Seq (
    organization := "com.superrad",
    version := "0.0.1",
    scalaVersion := "2.11.0-M5"
  )

  val macros = Project(
    "macros",
    file("macros"),
    settings = buildSettings ++ (Seq(
      libraryDependencies <+= (scalaVersion)("org.scala-lang" % "scala-compiler" % _))
    )
  )

  val examples = Project(
    "examples",
    file("."),
    settings = buildSettings
  ) dependsOn(macros)
}
