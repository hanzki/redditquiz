package daos

import daos.generated.Tables
import daos.generated.Tables.{SubredditsRow, Subreddits}
import scala.language.implicitConversions

/**
 * Created by hanzki on 25/06/15.
 */
object SubredditsDao extends Dao[Subreddits] {

  case class SubR(id: Int, name: String) extends DaoModel {
    override def toString = s"$id: $name"
  }

  val q = Tables.Subreddits

  implicit def row2Model(r: SubredditsRow) : SubR = SubR(r.id, r.name)
}
