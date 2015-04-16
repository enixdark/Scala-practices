package controllers

import play.api.mvc._
import models._

object Products extends Controller {
	def list = Action {
		implicit request =>
		val products = Product.findAll

		Ok(views.html.list(products))
	}

	// def show(ean: Long) = Action {
	// 	implicit request =>

	// 	Product.findByEan(ean).map {
	// 		product =>
	// 		Ok(views.html.details(product))
	// 	}
	// }
	def show(ean: Long) = Action {
		implicit request =>
		Product.findByEan(ean).map {
			product =>
			Ok(views.html.details(product))
			}.getOrElse(NotFound)
	}
}


