package model

import skinny.orm._, feature._
import scalikejdbc._, SQLInterpolation._
import org.joda.time._
import controller.Controllers.members

// If your model has +23 fields, switch this to normal class and mixin scalikejdbc.EntityEquality.
case class Event(
  id: Long,
  title: String,
  startDateTime: Option[DateTime] = None,
  endDateTime: Option[DateTime] = None,
  createdAt: DateTime,
  updatedAt: DateTime
  ,
  members: Seq[Member] = Nil
)

object Event extends SkinnyCRUDMapper[Event] with TimestampsFeature[Event] {
  override lazy val tableName = "events"
  override lazy val defaultAlias = createAlias("e")

  // members
  //val members = hasManyThrough[Member](
  //  EventMember, Member, (em, m) => em.copy(members = m)).byDefault
  hasManyThrough[Member](
    through = EventMember,
    many = Member,
    merge = (em, m) => em.copy(members = m)).byDefault


  override def extract(rs: WrappedResultSet, rn: ResultName[Event]): Event = new Event(
    id = rs.get(rn.id),
    title = rs.get(rn.title),
    startDateTime = rs.get(rn.startDateTime),
    endDateTime = rs.get(rn.endDateTime),
    createdAt = rs.get(rn.createdAt),
    updatedAt = rs.get(rn.updatedAt)
  )
}