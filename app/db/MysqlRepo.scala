package db

import model.{Todo, TodoRow}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import java.util.UUID
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class MysqlRepo @Inject()(dbConfigProvider : DatabaseConfigProvider)(implicit  ec : ExecutionContext){

  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  private val todoQuery = TableQuery[Schema]

  def create(todo:Todo):Future[Int] ={
    db.run(todoQuery +=TodoRow(UUID.randomUUID(), todo.title,todo.description,status = false))
  }

  def getAll():Future[Seq[TodoRow]] ={
    db.run(todoQuery.result)
  }

  def getById(id:UUID):Future[Seq[TodoRow]]={
    db.run(todoQuery.filter(_.id === id).result)
  }

  def dropAll():Future[Int] ={
    db.run(todoQuery.delete)
  }

  def dropById(id:UUID): Future[Int] ={
    db.run(todoQuery.filter(_.id === id).delete)
  }

  def update(id:UUID, updatedTodo:Todo):Future[Int] ={
    db.run(todoQuery.filter(_.id === id).map(a => (a.title,a.description)).update(updatedTodo.title,updatedTodo.description))
  }

}
