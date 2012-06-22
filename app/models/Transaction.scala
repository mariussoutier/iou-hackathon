package models

import play.api.Play.current

import com.novus.salat.dao._
import com.novus.salat.annotations._
import com.mongodb.casbah.Imports._
import se.radley.plugin.salat._

import scala.math._

import play.api.libs.json._

// 'from' gives money to 'to'
case class Transaction(
  id: ObjectId = new ObjectId,
  subject: String,
  amount: BigDecimal,
  currency: String = "€",
  from: ObjectId,
  to: ObjectId,
  created: java.util.Date = new java.util.Date,
  longitude: Option[Double],
  latitude: Option[Double],
  locationName: Option[String]
) {
  def fromUser: Option[User] = User.findOneById(from)
  def toUser: Option[User] = User.findOneById(to)
}

object Transaction extends ModelCompanion[Transaction, ObjectId] {
  val collection = mongoCollection("users")
  val dao = new SalatDAO[Transaction, ObjectId](collection = collection) {}

  def findByUser(userId: ObjectId) =
    dao.find(MongoDBObject("from" -> userId)).toSeq ++ dao.find(MongoDBObject("to" -> userId)).toSeq

  def transactedWith(userId: ObjectId): Set[ObjectId] = {
    val usersTransactions = Transaction.findByUser(userId)
    val froms = usersTransactions.map(_.from).filter(_ != userId).toSet
    val tos = usersTransactions.map(_.to).filter(_ != userId).toSet
    froms ++ tos
  }

  def totalAmount(from: ObjectId, to: ObjectId) = {
    val iGaveHim: Seq[Transaction] = dao.find(MongoDBObject("from" -> from, "to" -> to)).toSeq
    val heGaveMe: Seq[Transaction] = dao.find(MongoDBObject("from" -> to, "to" -> from)).toSeq
    val iGaveHimAmount = iGaveHim.map(_.amount).reduce((tx1, tx2) => tx1 + tx2)
    val heGaveMeAmount = heGaveMe.map(_.amount).reduce((tx1, tx2) => tx1 + tx2)
    max(iGaveHimAmount.toDouble, heGaveMeAmount.toDouble) - min(iGaveHimAmount.toDouble, heGaveMeAmount.toDouble)
  }
}