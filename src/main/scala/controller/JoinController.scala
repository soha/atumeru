package controller

import skinny._
import skinny.validator._

class JoinController extends ApplicationController {
  protectFromForgery()

  def index = render("/join/index")

}
