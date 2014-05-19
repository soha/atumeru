package controller

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import skinny._
import skinny.test._
import org.joda.time._
import model._

// NOTICE before/after filters won't be executed by default
class EventMembersControllerSpec extends FunSpec with ShouldMatchers with DBSettings {

  def createMockController = new EventMembersController with MockController
  def newEventMember = FactoryGirl(EventMember).create()

  describe("EventMembersController") {

    describe("shows event members") {
      it("shows HTML response") {
        val controller = createMockController
        controller.showResources()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/eventMembers/index"))
        controller.contentType should equal("text/html; charset=utf-8")
      }

      it("shows JSON response") {
        implicit val format = Format.JSON
        val controller = createMockController
        controller.showResources()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/eventMembers/index"))
        controller.contentType should equal("application/json; charset=utf-8")
      }
    }

    describe("shows a event member") {
      it("shows HTML response") {
        val eventMember = newEventMember
        val controller = createMockController
        controller.showResource(eventMember.id)
        controller.status should equal(200)
        controller.requestScope[EventMember]("item") should equal(Some(eventMember))
        controller.renderCall.map(_.path) should equal(Some("/eventMembers/show"))
      }
    }

    describe("shows new resource input form") {
      it("shows HTML response") {
        val controller = createMockController
        controller.newResource()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/eventMembers/new"))
      }
    }

    describe("creates a event member") {
      it("succeeds with valid parameters") {
        val controller = createMockController
        controller.prepareParams(
          "event_id" -> Long.MaxValue.toString(),
          "member_id" -> Long.MaxValue.toString())
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
      val eventMember = newEventMember
      val controller = createMockController
      controller.editResource(eventMember.id)
      controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/eventMembers/edit"))
    }

    it("updates a event member") {
      val eventMember = newEventMember
      val controller = createMockController
      controller.prepareParams(
        "event_id" -> Long.MaxValue.toString(),
        "member_id" -> Long.MaxValue.toString())
      controller.updateResource(eventMember.id)
      controller.status should equal(200)
    }

    it("destroys a event member") {
      val eventMember = newEventMember
      val controller = createMockController
      controller.destroyResource(eventMember.id)
      controller.status should equal(200)
    }

  }

}
