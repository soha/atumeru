package controller

import skinny._
import skinny.controller.AssetsController

object Controllers {
  object join extends _root_.controller.JoinController with Routes {
    val indexUrl = get("/join")(index).as('index)
  }


  def mount(ctx: ServletContext): Unit = {
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


}
