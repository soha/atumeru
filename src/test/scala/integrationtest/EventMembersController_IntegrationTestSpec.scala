package integrationtest

import org.scalatra.test.scalatest._
import skinny._
import skinny.test._
import org.joda.time._
import _root_.controller.Controllers
import model._

class EventMembersController_IntegrationTestSpec extends ScalatraFlatSpec with SkinnyTestSupport with DBSettings {
  addFilter(Controllers.eventMembers, "/*")

  def newEventMember = FactoryGirl(EventMember).create()

  it should "show event members" in {
    get("/event_members") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/event_members/") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/event_members.json") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/event_members.xml") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "show a event member in detail" in {
    get(s"/event_members/${newEventMember.id}") {
      logBodyUnless(200)
      status should equal(200)
    }
    get(s"/event_members/${newEventMember.id}.xml") {
      logBodyUnless(200)
      status should equal(200)
    }
    get(s"/event_members/${newEventMember.id}.json") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "show new entry form" in {
    get(s"/event_members/new") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "create a event member" in {
    post(s"/event_members",
      "event_id" -> Long.MaxValue.toString(),
      "member_id" -> Long.MaxValue.toString()) {
      logBodyUnless(403)
      status should equal(403)
    }

    withSession("csrf-token" -> "valid_token") {
      post(s"/event_members",
        "event_id" -> Long.MaxValue.toString(),
        "member_id" -> Long.MaxValue.toString(),
        "csrf-token" -> "valid_token") {
        logBodyUnless(302)
        status should equal(302)
        val id = header("Location").split("/").last.toLong
        EventMember.findById(id).isDefined should equal(true)
      }
    }
  }

  it should "show the edit form" in {
    get(s"/event_members/${newEventMember.id}/edit") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "update a event member" in {
    put(s"/event_members/${newEventMember.id}",
      "event_id" -> Long.MaxValue.toString(),
      "member_id" -> Long.MaxValue.toString()) {
      logBodyUnless(403)
      status should equal(403)
    }

    withSession("csrf-token" -> "valid_token") {
      put(s"/event_members/${newEventMember.id}",
        "event_id" -> Long.MaxValue.toString(),
        "member_id" -> Long.MaxValue.toString(),
        "csrf-token" -> "valid_token") {
        logBodyUnless(302)
        status should equal(302)
      }
    }
  }

  it should "delete a event member" in {
    delete(s"/event_members/${newEventMember.id}") {
      logBodyUnless(403)
      status should equal(403)
    }
    withSession("csrf-token" -> "valid_token") {
      delete(s"/event_members/${newEventMember.id}?csrf-token=valid_token") {
        logBodyUnless(200)
        status should equal(200)
      }
    }
  }

}
