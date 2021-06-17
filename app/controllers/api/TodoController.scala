package controllers.api

import db.MysqlRepo
import marshilling.CustomJsonMarshaling
import model.{Failed, FailedWithMessage, Response, Successful, SuccessfulWithMessage, Todo, TodoRow}
import play.api.libs.json.Format.GenericFormat
import play.api.libs.json.{JsError, JsValue, Json}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, Request}
import services.TodoService

import java.util.UUID
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class TodoController @Inject()(val controllerComponents: ControllerComponents, todoService:TodoService, repo : MysqlRepo)(implicit ec : ExecutionContext) extends BaseController with CustomJsonMarshaling with Response{


  def add(): Action[JsValue] = Action(parse.json) async { implicit request:Request[JsValue]=>
    val entity = request.body.validate[Todo]
    entity.fold(
      errors => {
        Future.successful(BadRequest(Json.obj("status" -> "error", "message" -> JsError.toJson(errors))))
      },
      todo =>
        todoService.add(todo).map{
          case SuccessfulWithMessage(message:String) => Ok(message)
          case FailedWithMessage(message:String) => BadRequest(message)
        }
    )
  }

  def getAllUser(): Action[AnyContent] = Action async { implicit request:Request[AnyContent]=>
    todoService.getAll map{ item =>
      Ok(Json.toJson(item))
    }
  }

  def getUser(id:UUID): Action[AnyContent] = Action async { implicit request:Request[AnyContent]=>
    todoService.getUserById(id).map{ item =>
      Ok(Json.toJson(item))
    }
  }

  def update(id:UUID): Action[JsValue] = Action(parse.json) async { implicit request:Request[JsValue]=>
    val entity = request.body.validate[Todo]
    entity.fold(
      errors => {
        Future.successful(BadRequest(Json.obj("status" -> "error", "message" -> JsError.toJson(errors))))
      },
      todo =>
        todoService.updateUser(id,todo).map{
          case SuccessfulWithMessage(message:String) => Ok(message)
          case FailedWithMessage(message:String) => BadRequest(message)
        }
    )
  }

  def deleteUser(id:UUID): Action[AnyContent] = Action async { implicit request:Request[AnyContent] =>
    todoService.deleteById(id).map{
      case SuccessfulWithMessage(message:String) => Ok(message)
      case FailedWithMessage(message:String) => BadRequest(message)
    }
  }

  def deleteAll(): Action[AnyContent] = Action async { implicit request:Request[AnyContent]=>
    todoService.deleteAll().map{
      case SuccessfulWithMessage(message:String) => Ok(message)
      case FailedWithMessage(message:String) => BadRequest(message)
    }
  }
}
