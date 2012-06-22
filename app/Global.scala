import play.api._
import play.api.mvc._

import com.mongodb.casbah.Imports._
import com.novus.salat.dao._
import com.novus.salat.annotations._
import se.radley.plugin.salat._

import models._

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    if (User.findByEMail("dummy@somewhere.com").isEmpty) {
      User.save(User(
        email = Some("dummy@somewhere.com"),
        firstName = Some("Max"),
        lastName = Some("Musterbro"),
        phone = Some("0180-1234567")
      ))
    }
  }

}
