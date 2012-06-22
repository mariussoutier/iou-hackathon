package json

import scala.collection._

import play.api.libs.json._
import play.api.libs.json.Json._

import models._

object Formats {

  implicit def arrayWrites[T](implicit fmt: Writes[T], mf: Manifest[T]): Writes[Array[T]] = new Writes[Array[T]] {
    def writes(ts: Array[T]) = JsArray((ts.map(t => toJson(t)(fmt))).toList)
  }

  implicit def mapWrites[V](implicit fmtv: Writes[V]): Writes[collection.immutable.Map[String, V]] = new Writes[collection.immutable.Map[String, V]] {
    def writes(ts: collection.immutable.Map[String, V]) = JsObject(ts.map { case (k, v) => (k, toJson(v)(fmtv)) }.toList)
  }

  implicit def traversableWrites[A: Writes] = new Writes[Traversable[A]] {
    def writes(as: Traversable[A]) = JsArray(as.map(toJson(_)).toSeq)
  }

  /*implicit object JsValueWrites extends Writes[JsValue] {
    def writes(o: JsValue) = o
  }*/

  implicit def OptionWrites[T](implicit fmt: Writes[T]): Writes[Option[T]] = new Writes[Option[T]] {
    import scala.util.control.Exception._
    def writes(o: Option[T]) = o match {
      case Some(value) => fmt.writes(value)
      case None => JsNull
    }
  }

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



  /**
   * Deserializer for Map[String,V] types.
   */
  implicit def mapReads[V](implicit fmtv: Reads[V]): Reads[collection.immutable.Map[String, V]] = new Reads[collection.immutable.Map[String, V]] {
    def reads(json: JsValue) = json match {
      case JsObject(m) => m.map { case (k, v) => (k -> fromJson[V](v)(fmtv)) }.toMap
      case _ => throw new RuntimeException("Map expected")
    }
  }

  /**
   * Generic deserializer for collections types.
   */
  implicit def traversableReads[F[_], A](implicit bf: generic.CanBuildFrom[F[_], A, F[A]], ra: Reads[A]) = new Reads[F[A]] {
    def reads(json: JsValue) = json match {
      case JsArray(ts) => {
        val builder = bf()
        for (a <- ts.map(fromJson[A](_))) {
          builder += a
        }
        builder.result()
      }
      case _ => throw new RuntimeException("Collection expected")
    }
  }

  /**
   * Deserializer for Array[T] types.
   */
  implicit def arrayReads[T: Reads: Manifest]: Reads[Array[T]] = new Reads[Array[T]] {
    def reads(json: JsValue) = json.as[List[T]].toArray
  }

  /**
   * Deserializer for JsValue.
   */
  implicit object JsValueReads extends Reads[JsValue] {
    def reads(json: JsValue) = json
  }

  /**
   * Deserializer for JsObject.
   */
  implicit object JsObjectReads extends Reads[JsObject] {
    def reads(json: JsValue) = json match {
      case o: JsObject => o
      case _ => throw new RuntimeException("JsObject expected")
    }
  }

  implicit def OptionReads[T](implicit fmt: Reads[T]): Reads[Option[T]] = new Reads[Option[T]] {
    import scala.util.control.Exception._
    def reads(json: JsValue) = catching(classOf[RuntimeException]).opt(fmt.reads(json))
  }
}
