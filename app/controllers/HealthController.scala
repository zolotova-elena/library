package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class HealthController extends InjectedController {
  def health(): Action[AnyContent] = Action(Ok("Hello USER"))
}