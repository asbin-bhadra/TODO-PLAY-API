package services

import db.MysqlRepo
import model.{Failed, FailedWithMessage, Response, Successful, SuccessfulWithMessage, Todo, TodoRow}

import java.util.UUID
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class TodoService @Inject()(repo : MysqlRepo)(implicit ec : ExecutionContext) {

  def add(todo:Todo):Future[Response] ={
    repo.create(todo).map{
      case 1 => SuccessfulWithMessage("Todo has been added")
      case 0 => FailedWithMessage("Todo couldn't be added")
    }
  }

  def getAll: Future[Seq[TodoRow]] ={
    repo.getAll()
  }

  def getUserById(id:UUID): Future[Seq[TodoRow]] ={
    repo.getById(id)
  }
  def updateUser(id:UUID, updatedUser:Todo): Future[Response] ={
    repo.update(id,updatedUser).map{
      case 1 => SuccessfulWithMessage("Todo has been updated")
      case 0 => FailedWithMessage("Todo couldn't be updated")
    }
  }

  def deleteById(id:UUID): Future[Response] ={
    repo.dropById(id).map{
      case 1 => SuccessfulWithMessage("Todo has been deleted")
      case 0 => FailedWithMessage("Todo couldn't be deleted")
    }
  }

  def deleteAll(): Future[Response] ={
    repo.dropAll().map{
      case 0 => FailedWithMessage("Todo couldn't be deleted")
      case _ => SuccessfulWithMessage("Todo has been deleted")
    }
  }

}
