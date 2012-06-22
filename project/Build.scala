import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

  val appName         = "iou"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    "se.radley" %% "play-plugins-salat" % "1.0.4"
    //"se.radley" %% "play-plugins-enumeration" % "1.0"
  )

  val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
    routesImport += "se.radley.plugin.salat.Binders._",
    templatesImport += "org.bson.types.ObjectId"
  )

}
