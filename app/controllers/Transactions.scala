package controllers

import play.api._
import play.api.mvc._
import play.api.data.Forms._
import play.api.data._

import play.api.libs.json._
import play.api.libs.json.Json._
import json.Formats._

import se.radley.plugin.salat._

import com.mongodb.casbah.Imports._

import models._

object Transactions extends Controller {

  def summaries(id: ObjectId) = Action { implicit request =>
    val others = Transaction.transactedWith(id)
    val transactionsJs: Seq[JsValue] =
      others.toSeq.map(otherId =>
        JsObject(Seq(
          ("userId", JsString(otherId.toString)),
          ("userName", JsString("TODO")),
          ("totalAmount", JsNumber(Transaction.totalAmount(id, otherId))))
        )
      )
    Ok(JsObject(Seq("transactions" -> JsArray(transactionsJs))))
  }



}

//
