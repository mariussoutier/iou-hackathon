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

    if (Transaction.count() == 0) {
      val otherUser = User(
        email = Some("girlfriend@somewhere.com"),
        firstName = Some("Uschi"),
        lastName = Some("München"),
        phone = Some("0161-12123939123")
      )
      User.save(otherUser)

      User.findByEMail("dummy@somewhere.com").map { dummyUser =>
        // Add some dummy tx
        val tx1 = Transaction(
          subject = "Bier",
          amount = BigDecimal(3.00),
          from = dummyUser.id,
          to = otherUser.id
        )
        Transaction.save(tx1)
        val tx2 = Transaction(
          subject = "Restaurant",
          amount = BigDecimal(55.00),
          from = dummyUser.id,
          to = otherUser.id
        )
        Transaction.save(tx2)
      }
    }
  }

}
