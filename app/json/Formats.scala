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
}
