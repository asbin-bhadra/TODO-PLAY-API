package model


import play.api.libs.json.{Json, OWrites, Reads, Writes}

import java.util.UUID

case class TodoRow(id:UUID, title:String, description:String, status:Boolean)



