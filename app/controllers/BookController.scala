package controllers

import models.Book
import play.api.libs.json.{JsValue, Json, Writes}
import play.api.mvc.{Action, AnyContent, InjectedController}
import reactivemongo.api.bson.BSONObjectID
import services.BookService

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class BookController @Inject()(
  bookService: BookService
)(implicit ec: ExecutionContext) extends InjectedController {

  def all(): Action[AnyContent] = Action.async {
    bookService.findAll().map { books =>
      Ok(Json.toJson(books)(Writes.list))
    }
  }

  def findOne(id:String): Action[AnyContent] = Action.async {
    val bookIdO = BSONObjectID.parse(id).toOption
    bookIdO.map { bookId =>
      bookService.findOne(bookId).map(bookO =>
        Ok(Json.toJson(bookO))
      )
    }.getOrElse(Future.successful(BadRequest(s"Invalid bookId")))
  }

  def addBook(): Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[Book].map { book =>
      bookService.insert(book).map( _ =>
        Ok(Json.toJson(book))
      )
    }.recoverTotal(error =>
      Future.successful(BadRequest(s"Json invalid, ${error.toString}"))
    )
  }

  def update(id:String): Action[JsValue] = Action.async(parse.json) { request =>
    import request.{body => json}

    val bookIdO = BSONObjectID.parse(id).toOption
    bookIdO.map { bookId =>
      bookService.findOne(bookId).flatMap { bookO =>
        bookO.map { book =>
          val title = (json \ "title").validate[String].getOrElse(book.title)
          val description = (json \ "description").asOpt[String].map(desc => Option(desc)).getOrElse(book.description)

          val newBook = book.setUpdate(title, description)
          bookService.update(newBook).map(_ => Ok(Json.toJson(newBook)))
        }.getOrElse(Future.successful(BadRequest(s"Book not found")))
      }
    }.getOrElse(Future.successful(BadRequest(s"Invalid bookId")))

  }

  def delete(id:String): Action[AnyContent] = Action.async {
    val bookIdO = BSONObjectID.parse(id).toOption
    bookIdO.map { bookId =>
      bookService.delete(bookId).map(_ => Ok)
    }.getOrElse(Future.successful(BadRequest(s"Invalid bookId")))
  }

}
