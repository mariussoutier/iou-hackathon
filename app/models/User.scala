package models

import play.api.Play.current

import com.novus.salat.dao._
import com.mongodb.casbah.Imports._
import se.radley.plugin.salat._

import play.api.libs.json._

case class User(
  id: ObjectId = new ObjectId,
  email: Option[String],
  phone: Option[String],
  firstName: Option[String],
  lastName: Option[String]
)

object User extends ModelCompanion[User, ObjectId] {
  val collection = mongoCollection("users")
  val dao = new SalatDAO[User, ObjectId](collection = collection) {}

  def findByEMail(email: String): Option[User] = dao.findOne(MongoDBObject("email" -> email))
  def findByPhone(phone: String): Option[User] = dao.findOne(MongoDBObject("phone" -> phone))
}



/*implicit object UserFormat extends Format[User] {
  def read(json: JsValue): User = {

  }

  def writes(o: User)
}*/
