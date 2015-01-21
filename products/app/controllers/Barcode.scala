package controllers

import play.api._
import play.api.mvc._
import play.api.data.Forms._
import play.api.data._
/** Uncomment the following lines as needed **/
/**
import play.api.Play.current
import play.api.libs._
import play.api.libs.iteratee._
import play.api.libs.concurrent._
import java.util.concurrent._
import scala.concurrent.stm._
import akka.util.duration._
import play.api.cache._
import play.api.libs.json._
**/

object Barcode extends Controller {
	val ImageResolution = 144


	def barcode(ean: Long) = Action {
		import java.lang.IllegalArgumentException
		val MimeType = "image/png"
		try {
			val imageData = ean13BarCode(ean, MimeType)
			Ok(imageData).as(MimeType)
		}
		catch {
			case e: IllegalArgumentException =>
			BadRequest("Couldn’t generate bar code. Error: " + e.getMessage)
		}
	}
	// def barcode(ean: Long) = Action {
	// 	import java.lang.IllegalArgumentException

	// 	val Mimetype = "image/jpeg"

	// 	try{
	// 		val imageData = ean13BarCode(ean,Mimetype)
	// 		Ok(imageData).as(Mimetype)
	// 	}
	// 	catch{
	// 		case e: IllegalArgumentException =>
	// 		BadRequest("Couldn't generate bar code. Error " + e.getMessage)
	// 	}
	// }

	def ean13BarCode(ean: Long, mimeType: String): Array[Byte] = {
		import java.io.ByteArrayOutputStream
		import java.awt.image.BufferedImage
		import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider
		import org.krysalis.barcode4j.impl.upcean.EAN13Bean
		val output: ByteArrayOutputStream = new ByteArrayOutputStream
		val canvas: BitmapCanvasProvider =
		new BitmapCanvasProvider(output, mimeType, ImageResolution,
			BufferedImage.TYPE_BYTE_BINARY, false, 0)
		val barcode = new EAN13Bean()
		barcode.generateBarcode(canvas, String valueOf ean)
		canvas.finish
		output.toByteArray
	}

}
