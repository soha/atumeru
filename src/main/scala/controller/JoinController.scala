package controller

import skinny._
import skinny.validator._
import model.Event

class JoinController extends ApplicationController {
  protectFromForgery()

  protected def enablePagination = true

  def index = {
    if (enablePagination) {
      val pageNo: Int = params.getAs[Int]("page").getOrElse(1)
      val pageSize: Int = 3
      val totalCount: Long = Event.count()
      val totalPages: Int = (totalCount / pageSize).toInt + (if (totalCount % pageSize == 0) 0 else 1)
      set("items", Event.findAllWithPagination(Pagination.page(pageNo).per(pageSize)))
      set("totalPages", totalPages)
    } else {
      set("items", Event.findAll())
    }
    render(s"/join/index")
  }
}
