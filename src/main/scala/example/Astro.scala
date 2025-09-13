//> using dep "dev.zio::zio-http:3.5.1"
//> using file ../astro/O.scala
//> using file ../astro/Util.scala

package example

import zio._
import zio.http._
import zio.http.template._
import scala.util.chaining._  //pipe, tap
import astro.O
import astro.Util

object Astro extends ZIOAppDefault
{

  val app =  Routes
    (
      Method.GET  / Root -> Handler.from(Response.text(O.elotet.toString).updateHeaders(_ => Headers("content-type", "text/html"))),
      Method.POST / "betet" -> 
        handler
        { (req: Request) =>
          var bStr = ""
          req.body.asString.map( s => bStr = s )
          //**/ println(bStr)
          Response.text(O.betet(Util.parseQueryString(bStr)).toString())
          .updateHeaders(_ => Headers("content-type", "text/html"))
        },
      Method.GET / "t"  -> Handler.from(Response.html(Html.fromString("<h1>ANYÁD</h1>"))), // <!DOCTYPE html>&lt;h1&gt;ANYÃD&lt;/h1&gt;
      Method.GET / "tt"  -> Handler.from(Response.text("<h1>ANYÁD</h1>").updateHeaders(_ => Headers("content-type", "text/html"))),
    )

  override val run = Server.serve(app).provide(Server.defaultWith(_.port(58080)))
}