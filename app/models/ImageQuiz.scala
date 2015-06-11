package models

import slick.driver.MySQLDriver.simple._

/**
 * Created by hanzki on 5.6.2015.
 */
case class ImageQuiz
(
  id: Int,
  imgId: Int,
  choice1: Int,
  choice2: Int,
  choice3: Int,
  choice4: Int,
  choice5: Int,
  choice6: Int
){
  def choices(implicit s: Session): List[Subreddit] = subReddits.filter(sr =>
    sr.id === choice1 ||
    sr.id === choice2 ||
    sr.id === choice3 ||
    sr.id === choice4 ||
    sr.id === choice5 ||
    sr.id === choice6
  ).take(6).list

  def image(implicit s: Session): RedditImage = redditImages.filter(_.id === imgId).first
}

class ImageQuizs(tag: Tag) extends Table[ImageQuiz](tag, "image_quizs")
{
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def imgId = column[Int]("img_id")
  def choice1 = column[Int]("choice_1")
  def choice2 = column[Int]("choice_2")
  def choice3 = column[Int]("choice_3")
  def choice4 = column[Int]("choice_4")
  def choice5 = column[Int]("choice_5")
  def choice6 = column[Int]("choice_6")
  def * = (id, imgId, choice1, choice2, choice3, choice4, choice5, choice6) <> (ImageQuiz.tupled, ImageQuiz.unapply _)
}

object imageQuizs extends TableQuery(new ImageQuizs(_)) {
}

class LocalImageQuiz (
  val image: LocalRedditImage,
  val choices: List[String]
)
