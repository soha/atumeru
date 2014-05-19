package controller

import skinny._
import skinny.controller.AssetsController

object Controllers {

  def mount(ctx: ServletContext): Unit = {
    eventMembers.mount(ctx)
    members.mount(ctx)
    events.mount(ctx)
    root.mount(ctx)
    AssetsController.mount(ctx)
  }

  object root extends RootController with Routes {
    val indexUrl = get("/?")(index).as('index)
  }
  object events extends _root_.controller.EventsController {
  }

  object members extends _root_.controller.MembersController {
  }

  object eventMembers extends _root_.controller.EventMembersController {
  }

}
