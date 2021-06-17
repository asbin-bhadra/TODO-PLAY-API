package marshilling

import model.{Todo, TodoRow}
import play.api.libs.json.{Format, Json, Reads, Writes}

trait CustomJsonMarshaling {

  implicit val todoRowFormat: Format[TodoRow] = Json.format[TodoRow]
  implicit val todoFormat: Format[Todo] = Json.format[Todo]

  implicit val todoRowToJson: Writes[TodoRow] = Json.writes[TodoRow]
  implicit val todoRowFromJson: Reads[TodoRow] = Json.reads[TodoRow]

}
