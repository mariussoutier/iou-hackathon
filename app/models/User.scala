package models

import play.api.Play.current

import com.mongodb.casbah.Imports._
import se.radley.plugin.salat._
import com.novus.salat.dao._

import play.api.libs.json._

case class User(
  id: ObjectId = new ObjectId,
  email: Option[String],
  phone: Option[String],
  firstName: Option[String],
  lastName: Option[String]
) {
  def fullName =
    if (firstName.isDefined && lastName.isDefined) firstName.get + " " + lastName.get
    else firstName.orElse(lastName).getOrElse("")
}

object User extends ModelCompanion[User, ObjectId] {
  val collection = mongoCollection("users")
  val dao = new SalatDAO[User, ObjectId](collection = collection) {}

  def findByEMail(email: String): Option[User] = dao.findOne(MongoDBObject("email" -> email))
  def findByPhone(phone: String): Option[User] = dao.findOne(MongoDBObject("phone" -> phone))
}
