package controller

import skinny._
import skinny.validator._
import _root_.controller._
import model.Event

class EventsController extends SkinnyResource with ApplicationController {
  protectFromForgery()

  override def model = Event
  override def resourcesName = "events"
  override def resourceName = "event"

  override def resourcesBasePath = s"/${toSnakeCase(resourcesName)}"
  override def useSnakeCasedParamKeys = true

  override def viewsDirectoryPath = s"/${resourcesName}"

  override def createParams = Params(params).withDateTime("start_date_time").withDateTime("end_date_time")
  override def createForm = validation(createParams,
    paramKey("title") is required & maxLength(512),
    paramKey("start_date_time") is dateTimeFormat,
    paramKey("end_date_time") is dateTimeFormat
  )
  override def createFormStrongParameters = Seq(
    "title" -> ParamType.String,
    "start_date_time" -> ParamType.DateTime,
    "end_date_time" -> ParamType.DateTime
  )

  override def updateParams = Params(params).withDateTime("start_date_time").withDateTime("end_date_time")
  override def updateForm = validation(updateParams,
    paramKey("title") is required & maxLength(512),
    paramKey("start_date_time") is dateTimeFormat,
    paramKey("end_date_time") is dateTimeFormat
  )
  override def updateFormStrongParameters = Seq(
    "title" -> ParamType.String,
    "start_date_time" -> ParamType.DateTime,
    "end_date_time" -> ParamType.DateTime
  )

}
