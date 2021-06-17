package db

import model.TodoRow
import slick.lifted.Tag
import slick.jdbc.MySQLProfile.api._

import java.util.UUID

class Schema(tag: Tag) extends Table[TodoRow](tag,"Todo_Details"){

  def id = column[UUID]("TODO_ID", O.PrimaryKey)
  def title = column[String]("TODO_TITLE")
  def description = column[String]("TODO_DESCRIPTION")
  def status = column[Boolean]("STATUS")

  def * = (id, title, description, status) <> (TodoRow.tupled, TodoRow.unapply)

}