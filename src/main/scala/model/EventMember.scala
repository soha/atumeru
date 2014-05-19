package model

import skinny.orm._, feature._
import scalikejdbc._, SQLInterpolation._
import org.joda.time._

// If your model has +23 fields, switch this to normal class and mixin scalikejdbc.EntityEquality.
case class EventMember(
  id: Long,
  event_id: Long,
  member_id: Long,
  createdAt: DateTime,
  updatedAt: DateTime
)

object EventMember extends SkinnyCRUDMapper[EventMember] with TimestampsFeature[EventMember] {
  override lazy val tableName = "event_members"
  override lazy val defaultAlias = createAlias("em")

  override def extract(rs: WrappedResultSet, rn: ResultName[EventMember]): EventMember = new EventMember(
    id = rs.get(rn.id),
    event_id = rs.get(rn.event_id),
    member_id = rs.get(rn.member_id),
    createdAt = rs.get(rn.createdAt),
    updatedAt = rs.get(rn.updatedAt)
  )
}
