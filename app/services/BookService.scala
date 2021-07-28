package services

import daos.BookDAO
import models.Book
import reactivemongo.api.bson.BSONObjectID
import reactivemongo.api.commands.WriteResult

import javax.inject.{Inject, Singleton}
import scala.concurrent.Future

@Singleton
class BookService @Inject()(
  bookDAO: BookDAO
){
  def findAll(): Future[List[Book]] = bookDAO.findAll()

  def findOne(id: BSONObjectID): Future[Option[Book]] = bookDAO.findOne(id)

  def insert(book: Book): Future[WriteResult] = bookDAO.insert(book)

  def update(book: Book): Future[WriteResult] = bookDAO.update(book)

  def delete(id: BSONObjectID): Future[WriteResult] = bookDAO.delete(id)
}
