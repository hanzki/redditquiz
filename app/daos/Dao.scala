package daos


import scala.slick.lifted.{AbstractTable, TableQuery}
import scala.language.implicitConversions


/**
 * Created by hanzki on 25/06/15.
 */
trait Dao[T <: AbstractTable[_]] {

  trait DaoModel

  val q : TableQuery[T]

  implicit def row2Model(r: T#TableElementType) : DaoModel
}
