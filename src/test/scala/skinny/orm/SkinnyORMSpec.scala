package skinny.orm

import scalikejdbc._, SQLInterpolation._
import org.joda.time.DateTime
import scalikejdbc.scalatest.AutoRollback
import org.scalatest.fixture
import org.scalatest.matchers.ShouldMatchers
import skinny._
import skinny.orm.exception.OptimisticLockException
import model._

class SkinnyORMSpec extends fixture.FunSpec with DBSettings with AutoRollback with ShouldMatchers {

  override def fixture(implicit session: DBSession) {
  }

  describe("EventMembers") {
    it("should Event has Members") { implicit session =>

      // #createWithNamedValues
      val eventId1 = Event.createWithAttributes('title -> "イベント１")
      val eventId2 = Event.createWithAttributes('title -> "イベント２")

      val memberId1 = Member.createWithAttributes('name -> "メンバー１", 'email -> "hoge")
      val memberId2 = Member.createWithAttributes('name -> "メンバー２", 'email -> "")

      EventMember.createWithAttributes('eventId -> eventId1, 'memberId -> memberId1, 'createdAt -> DateTime.now, 'updatedAt -> DateTime.now)
      EventMember.createWithAttributes('eventId -> eventId1, 'memberId -> memberId2, 'createdAt -> DateTime.now, 'updatedAt -> DateTime.now)
      EventMember.createWithAttributes('eventId -> eventId2, 'memberId -> memberId2, 'createdAt -> DateTime.now, 'updatedAt -> DateTime.now)


      val event1 = Event.findById(eventId1).get
      event1.title should equal("イベント１")
      event1.members.size should equal(2)

      val event2 = Event.findById(eventId2).get
      event2.title should equal("イベント２")
      event2.members.size should equal(1)

    }
  }
}
