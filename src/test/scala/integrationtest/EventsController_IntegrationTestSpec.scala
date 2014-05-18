package integrationtest

import org.scalatra.test.scalatest._
import skinny._
import skinny.test._
import org.joda.time._
import _root_.controller.Controllers
import model._

class EventsController_IntegrationTestSpec extends ScalatraFlatSpec with SkinnyTestSupport with DBSettings {
  addFilter(Controllers.events, "/*")

  def newEvent = FactoryGirl(Event).create()

  it should "show events" in {
    get("/events") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/events/") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/events.json") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/events.xml") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "show a event in detail" in {
    get(s"/events/${newEvent.id}") {
      logBodyUnless(200)
      status should equal(200)
    }
    get(s"/events/${newEvent.id}.xml") {
      logBodyUnless(200)
      status should equal(200)
    }
    get(s"/events/${newEvent.id}.json") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "show new entry form" in {
    get(s"/events/new") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "create a event" in {
    post(s"/events",
      "title" -> "dummy",
      "start_date_time" -> skinny.util.DateTimeUtil.toString(new DateTime()),
      "end_date_time" -> skinny.util.DateTimeUtil.toString(new DateTime())) {
      logBodyUnless(403)
      status should equal(403)
    }

    withSession("csrf-token" -> "valid_token") {
      post(s"/events",
        "title" -> "dummy",
        "start_date_time" -> skinny.util.DateTimeUtil.toString(new DateTime()),
        "end_date_time" -> skinny.util.DateTimeUtil.toString(new DateTime()),
        "csrf-token" -> "valid_token") {
        logBodyUnless(302)
        status should equal(302)
        val id = header("Location").split("/").last.toLong
        Event.findById(id).isDefined should equal(true)
      }
    }
  }

  it should "show the edit form" in {
    get(s"/events/${newEvent.id}/edit") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "update a event" in {
    put(s"/events/${newEvent.id}",
      "title" -> "dummy",
      "start_date_time" -> skinny.util.DateTimeUtil.toString(new DateTime()),
      "end_date_time" -> skinny.util.DateTimeUtil.toString(new DateTime())) {
      logBodyUnless(403)
      status should equal(403)
    }

    withSession("csrf-token" -> "valid_token") {
      put(s"/events/${newEvent.id}",
        "title" -> "dummy",
        "start_date_time" -> skinny.util.DateTimeUtil.toString(new DateTime()),
        "end_date_time" -> skinny.util.DateTimeUtil.toString(new DateTime()),
        "csrf-token" -> "valid_token") {
        logBodyUnless(302)
        status should equal(302)
      }
    }
  }

  it should "delete a event" in {
    delete(s"/events/${newEvent.id}") {
      logBodyUnless(403)
      status should equal(403)
    }
    withSession("csrf-token" -> "valid_token") {
      delete(s"/events/${newEvent.id}?csrf-token=valid_token") {
        logBodyUnless(200)
        status should equal(200)
      }
    }
  }

}
