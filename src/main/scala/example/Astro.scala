//> using dep "dev.zio::zio-http:3.5.0"
//> using file ../astro/O.scala

package example

import zio._
import zio.http._
import zio.http.template._
import scala.util.chaining._  //pipe, tap
import astro.O

// Copilot (GPT-5 mini) súgta
object RequestSyntax {
  extension (b: Body) {

    /** Extract URL-encoded form as Map[String, List[String]] */
    def formDataList: ZIO[Any, Throwable, Map[String, List[String]]] =
      b.asURLEncodedForm.map { form =>
        // groupMap on a Chunk yields Chunk values; convert each Chunk to List
        form.formData
          .collect { case FormField.Text(k, v, _, _) => (k, v) }
          .groupMap(_._1)(_._2)                // Map[String, zio.Chunk[String]]
          .map { case (k, chunk) => k -> chunk.toList } // Map[String, List[String]]
      }

    /** Safe variant: non-failing, returns empty map if not form data */
    def formDataListSafe: ZIO[Any, Nothing, Map[String, List[String]]] =
      formDataList.orElseSucceed(Map.empty)
  }
}

object Astro extends ZIOAppDefault
{
  import example.RequestSyntax.* // Import extension methods for Body

  /**
    * Body-ból előállít Map[String, Seq[String]] formájú (scalatrás) paramétert O.betet-nek
    */
  def betetParam (b:Body): Map[String, Seq[String]] =
  {
    /**/ println(b)
    /**/ println(b.asURLEncodedForm)
    /**/ println(b.asString)
    /* */ b.asString.map(println(_))//.orElseSucceed  // a .map a Success(valami)-ből valami-t csinál
    /**/ println("---")
    /* */b.asURLEncodedForm.map( f => println(f.toQueryParams) )

    Map("" -> Seq(""))
  }

  val app =  Routes
    (
      Method.GET  / Root -> Handler.from(Response.text(O.elotet.toString).updateHeaders(_ => Headers("content-type", "text/html"))),
      Method.POST / "betettt" -> //Handler.from(Response.text("betét")),
        handler
          { (req: Request) =>
            betetParam(req.body)
            .pipe(p => O.betet(p).toString)
            .pipe(Response.text(_))
          },
      Method.POST / "betet" -> 
        Handler.fromFunctionZIO { (req: Request) =>
          req.body.formDataListSafe.map { paramMap =>
            // paramMap: Map[String, List[String]]
            // O.betet-nek Map[String, Seq[String]] kell
            val p: Map[String, Seq[String]] = paramMap.view.mapValues(_.toSeq).toMap
            O.betet(p).toString
          }.map(Response.text(_).updateHeaders(_ => Headers("content-type", "text/html")))
        },
      Method.GET / "t"  -> Handler.from(Response.html(Html.fromString("<h1>ANYÁD</h1>"))), // <!DOCTYPE html>&lt;h1&gt;ANYÃD&lt;/h1&gt;
      Method.GET / "tt"  -> Handler.from(Response.text("<h1>ANYÁD</h1>").updateHeaders(_ => Headers("content-type", "text/html"))),
    )

  override val run = Server.serve(app).provide(Server.defaultWith(_.port(58080)))
}