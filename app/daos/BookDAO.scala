package daos

import models.Book
import play.modules.reactivemongo.{NamedDatabase, ReactiveMongoApi}
import reactivemongo.api.bson.{BSONDocument, BSONObjectID}
import reactivemongo.api.bson.collection.BSONCollection
import reactivemongo.api.commands.WriteResult
import reactivemongo.api.{Cursor, ReadPreference}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class BookDAO @Inject()(
  @NamedDatabase("library") val mongoApi: ReactiveMongoApi
)(implicit ec: ExecutionContext) {

  def collection: Future[BSONCollection] = mongoApi.database.map(db => db.collection("books"))

  def findAll(): Future[List[Book]] = {
    collection.flatMap(
      _.find(BSONDocument(), Option.empty[Book])
        .cursor[Book](ReadPreference.Primary)
        .collect[List](err = Cursor.FailOnError[List[Book]]())
    )
  }

  def findOne(id: BSONObjectID): Future[Option[Book]] = {
    collection.flatMap(
      _.find(
        BSONDocument("_id" -> id),
        Option.empty[Book]
      ).one[Book]
    )
  }

  def insert(book: Book): Future[WriteResult] = {
    collection.flatMap(
      _.insert(ordered = false).one(book)
    )
  }

  def update(book: Book): Future[WriteResult] = {
    collection.flatMap(
      _.update(ordered = false).one(
        BSONDocument("_id" -> book._id),
        book
      )
    )
  }

  def delete(id: BSONObjectID): Future[WriteResult] = {
    collection.flatMap(
      _.delete().one(
        BSONDocument("_id" -> id),
        Some(1)
      )
    )
  }

}
