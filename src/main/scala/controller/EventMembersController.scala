package controller

import skinny._
import skinny.validator._
import _root_.controller._
import model.EventMember

class EventMembersController extends SkinnyResource with ApplicationController {
  protectFromForgery()

  override def model = EventMember
  override def resourcesName = "eventMembers"
  override def resourceName = "eventMember"

  override def resourcesBasePath = s"/${toSnakeCase(resourcesName)}"
  override def useSnakeCasedParamKeys = true

  override def viewsDirectoryPath = s"/${resourcesName}"

  override def createParams = Params(params)
  override def createForm = validation(createParams,
    paramKey("event_id") is required & numeric & longValue,
    paramKey("member_id") is required & numeric & longValue
  )
  override def createFormStrongParameters = Seq(
    "event_id" -> ParamType.Long,
    "member_id" -> ParamType.Long
  )

  override def updateParams = Params(params)
  override def updateForm = validation(updateParams,
    paramKey("event_id") is required & numeric & longValue,
    paramKey("member_id") is required & numeric & longValue
  )
  override def updateFormStrongParameters = Seq(
    "event_id" -> ParamType.Long,
    "member_id" -> ParamType.Long
  )

}
