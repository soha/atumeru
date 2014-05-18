package controller

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import skinny._
import skinny.test._
import org.joda.time._
import model._

// NOTICE before/after filters won't be executed by default
class EventsControllerSpec extends FunSpec with ShouldMatchers with DBSettings {

  def createMockController = new EventsController with MockController
  def newEvent = FactoryGirl(Event).create()

  describe("EventsController") {

    describe("shows events") {
      it("shows HTML response") {
        val controller = createMockController
        controller.showResources()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/events/index"))
        controller.contentType should equal("text/html; charset=utf-8")
      }

      it("shows JSON response") {
        implicit val format = Format.JSON
        val controller = createMockController
        controller.showResources()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/events/index"))
        controller.contentType should equal("application/json; charset=utf-8")
      }
    }

    describe("shows a event") {
      it("shows HTML response") {
        val event = newEvent
        val controller = createMockController
        controller.showResource(event.id)
        controller.status should equal(200)
        controller.requestScope[Event]("item") should equal(Some(event))
        controller.renderCall.map(_.path) should equal(Some("/events/show"))
      }
    }

    describe("shows new resource input form") {
      it("shows HTML response") {
        val controller = createMockController
        controller.newResource()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/events/new"))
      }
    }

    describe("creates a event") {
      it("succeeds with valid parameters") {
        val controller = createMockController
        controller.prepareParams(
          "title" -> "dummy",
          "start_date_time" -> skinny.util.DateTimeUtil.toString(new DateTime()),
          "end_date_time" -> skinny.util.DateTimeUtil.toString(new DateTime()))
        controller.createResource()
        controller.status should equal(200)
      }

      it("fails with invalid parameters") {
        val controller = createMockController
        controller.prepareParams() // no parameters
        controller.createResource()
        controller.status should equal(400)
        controller.errorMessages.size should be >(0)
      }
    }

    it("shows a resource edit input form") {
      val event = newEvent
      val controller = createMockController
      controller.editResource(event.id)
      controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/events/edit"))
    }

    it("updates a event") {
      val event = newEvent
      val controller = createMockController
      controller.prepareParams(
        "title" -> "dummy",
        "start_date_time" -> skinny.util.DateTimeUtil.toString(new DateTime()),
        "end_date_time" -> skinny.util.DateTimeUtil.toString(new DateTime()))
      controller.updateResource(event.id)
      controller.status should equal(200)
    }

    it("destroys a event") {
      val event = newEvent
      val controller = createMockController
      controller.destroyResource(event.id)
      controller.status should equal(200)
    }

  }

}
