package json

import scala.collection._

import play.api.libs.json._
import play.api.libs.json.Json._

import models._

object Formats {
  implicit def UserWrites = new Writes[User] {
    def writes(user: User): JsValue = {
      JsObject(fields = Seq(
        ("id", JsString(user.id.toString)),
        ("email", JsString(user.email.getOrElse(""))),
        ("phone", JsString(user.phone.getOrElse(""))),
        ("firstName", JsString(user.firstName.getOrElse(""))),
        ("lastName", JsString(user.lastName.getOrElse(""))))
      )
    }
  }

  //implicit val UserReads = new Reads[User]

  implicit def TransactionWrites = new Writes[models.Transaction] {
    def writes(tx: models.Transaction): JsValue = {
      JsObject(Seq(
        ("id", JsString(tx.id.toString)),
        ("subject", JsString(tx.subject)),
        ("amount", JsNumber(tx.amount)),
        ("currency", JsString(tx.currency)),
        ("from", JsString(tx.from.toString)),
        ("to", JsString(tx.to.toString)),
        ("created", JsNumber(tx.created.getTime)),
        ("longitude", JsNumber(tx.longitude.map(BigDecimal(_)).getOrElse(BigDecimal(0.0)))),
        ("latitude", JsNumber(tx.latitude.map(BigDecimal(_)).getOrElse(BigDecimal(0.0)))),
        ("locationName", JsString(tx.locationName.getOrElse("")))
      ))
    }
  }

  //implicit val TransactionReads[Transaction]
}
