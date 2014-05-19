package model

import skinny.orm._, feature._
import scalikejdbc._, SQLInterpolation._
import org.joda.time._

// If your model has +23 fields, switch this to normal class and mixin scalikejdbc.EntityEquality.
case class EventMember(
                        event_id: Long,
                        member_id: Long
                        )

object EventMember extends SkinnyJoinTable[EventMember] {
  override lazy val tableName = "event_members"
  override lazy val defaultAlias = createAlias("em")

}