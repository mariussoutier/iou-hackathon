package controllers

import play.api._
import play.api.mvc._
import play.api.data.Forms._
import play.api.data._

import play.api.libs.json._

import com.mongodb.casbah.Imports._

import models._


object Users extends Controller {

  implicit val UserWrites = new Writes[User] {
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

  def dummyUser() = Action { implicit request =>
    val user = User(
      email = Some("mps.dev@googlemail.com"),
      firstName = Some("Marius"),
      lastName = Some("Soutier"),
      phone = Some("0124646")
    )
    Ok(Json.toJson(user))
  }

  def userById(id: ObjectId) = Action {
    User.findOneById(id).map { user =>
      Ok(Json.toJson(user))
    }.getOrElse(NotFound)
  }

}
