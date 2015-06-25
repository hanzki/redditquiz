package controllers

import models.imageQuizs
import play.api.db.slick._
import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Writes, _}
import play.api.mvc.{Action, BodyParsers, Controller}
import services.{UtilityService, RedditService}

import scala.slick.driver.MySQLDriver.simple._

/**
 * Created by hanzki on 20/06/15.
 */
object QuizCtrl extends Controller {

  //JSON API classes
  private case class ChoiceJson(id: Int, name: String)
  private case class QuizJson(id: Int, imageUrl: String, choices: List[ChoiceJson])
  private case class AnswerJson(quizId: Int, answerId: Int)

  //JSON implicits
  private implicit val choiceJsonWrites: Writes[ChoiceJson] = (
    (JsPath \ "id").write[Int] and
      (JsPath \ "name").write[String]
    )(unlift(ChoiceJson.unapply))
  private implicit val quizJsonWrites: Writes[QuizJson] = (
      (JsPath \ "id").write[Int] and
      (JsPath \ "imageUrl").write[String] and
      (JsPath \ "choices").write[List[ChoiceJson]]
    )(unlift(QuizJson.unapply))
  private implicit val answerJsonReads: Reads[AnswerJson] = (
      (JsPath \ "quizId").read[Int] and
      (JsPath \ "answerId").read[Int]
    )(AnswerJson.apply _)
  private implicit val answerJsonWrites: Writes[AnswerJson] = (
      (JsPath \ "quizId").write[Int] and
      (JsPath \ "answerId").write[Int]
    )(unlift(AnswerJson.unapply))

  //GET /quiz
  def view = Action { implicit r =>
    Ok(views.html.reddit2())
  }

  //GET /api/quiz/random
  def random = DBAction { implicit rs =>
    val quiz = RedditService.getQuizFromDB
    val quizJson = QuizJson(quiz.id.get,
      UtilityService.urlToImageUrl(quiz.image.src),
      quiz.choices.map(sr => ChoiceJson(sr.id.get, sr.name))
    )
    val json = Json.toJson(quizJson)
    Ok(json)
  }

  //POST /api/quiz/answer
  def answer = DBAction(BodyParsers.parse.json) { implicit rs =>
    val answerResult = rs.request.body.validate[AnswerJson]
    answerResult.fold(
      errors => {
        BadRequest(Json.obj("message" -> JsError.toFlatJson(errors)))
      },
      answer => {
        val quiz = imageQuizs.filter(_.id === answer.quizId).first
        val response = answer.copy(answerId = quiz.image.srdId)
        Ok(Json.toJson(response))
      }
    )

  }
}
