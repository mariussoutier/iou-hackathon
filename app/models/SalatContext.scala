package models

trait SalatContext {
  implicit val ctx = {
    import com.novus.salat._
    import play.api.Play
    import play.api.Play.current
    val c = new Context {
      val name = "play-salat-context"
    }
    c.registerClassLoader(Play.current.classloader)
    c
  }
}
