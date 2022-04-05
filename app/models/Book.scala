package models

import play.api.libs.functional.syntax.toFunctionalBuilderOps
import play.api.libs.json.Reads.pure
import play.api.libs.json.{Json, Reads, Writes, __}
import reactivemongo.api.bson.{BSONDocumentHandler, BSONObjectID, Macros}
import org.joda.time.{DateTime, DateTimeZone}

case class Book (
  _id: BSONObjectID = BSONObjectID.generate(),
  title: String,
  description: Option[String],
  created: Int,
  update: Int
) {
  val id: String = _id.stringify

  def setUpdate(title: String, description: Option[String]): Book = copy(
    title = title,
    description = description,
    update = (new DateTime(DateTimeZone.UTC).getMillis / 1000).toInt
  )
}

trait BookJson {
  implicit val reads: Reads[Book] = (
    pure(BSONObjectID.generate()) and
    (__ \ "title").read[String] and
    (__ \ "description").readNullable[String] and
    pure((new DateTime(DateTimeZone.UTC).getMillis / 1000).toInt) and
    pure(0)
  ) (Book.apply _)

  implicit val writes: Writes[Book] = (r: Book) => {
    Json.obj(
      "id" -> r.id,
      "title" -> r.title,
      "description" -> r.description
    )
  }
}

trait BookBson {
  implicit val handler: BSONDocumentHandler[Book] = Macros.handler[Book]
}

object Book extends BookJson with BookBson
