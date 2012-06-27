package models

trait SalatContext {
  implicit val ctx = {
    import com.novus.salat._
    import play.api.Play
    import play.api.Play.current
    val context = new Context {
      val name = "play-salat-context"
    }
    context.registerClassLoader(Play.current.classloader)
    context.registerGlobalKeyOverride(remapThis = "id", toThisInstead = "_id")
    context
  }
}
