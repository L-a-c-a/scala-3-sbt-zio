//> using dep com.kosherjava:zmanim:2.5.0
//> using dep org.scala-lang.modules::scala-xml:2.4.0

package astro

//innentől változatlan

import com.kosherjava.zmanim._
import com.kosherjava.zmanim.util._
import java.util.TimeZone
import scala.util.chaining._  //pipe, tap
import scala.xml._

object O
{
  val elotet:NodeSeq =
    <html>
      <meta charset="UTF-8" />
      <body>
        <script src="https://cdn.jsdelivr.net/gh/alpinejs/alpine@v2.x.x/dist/alpine.min.js" defer="defer"></script>
        <script src="https://unpkg.com/htmx.org@1.5.0"></script>
        <form hx-post="betettt" hx-target="#valasz"
              x-data="{ma: new Date(), tolN: 0, igN: 0, tol: 't', ig: 'i'}"
              x-init="tolN=ma.setHours(0,0,0,0); tol=ma.toLocaleString('sv'); ig=tol; igN=tolN"
        >
          <label>tz id: <input name="tzid" value="Europe/Budapest" /></label> <br/>
          <label>lokáció név: <input name="loknev" value="BP" /></label> <br/>
          <label>szélesség: <input name="szel" value="47.5" /></label> <br/>
          <label>hosszúság: <input name="hossz" value="19" /></label> <br/>
          <label>magasság: <input name="mag" value="100" /></label> <br/>
          <label>algoritmus: <input type="checkbox" name="suntimes" /> SunTimesCalculator</label> - ha nem, NOAACalculator
                  <a href="https://kosherjava.com/zmanim/docs/api/com/kosherjava/zmanim/util/SunTimesCalculator.html">(ld.)</a> <br/>
          <label>naptól:
            <input type="hidden" name="naptolN" x-bind:value="tolN" />
            <input type="datetime-local" name="naptol" x-model="tol" x-on:change="tolN=new Date(tol).getTime()"/>
          </label> <br/>
          <label>napig:
            <input type="hidden" name="napigN" x-bind:value="igN" />
            <input type="datetime-local" name="napig" x-model="ig" x-on:change="igN=new Date(ig).getTime()" />
          </label> <br/>
          <input type="submit" value="nyomjad" />
        </form>
        <pre style="white-space: pre-wrap; word-break: keep-all;"></pre>
        <div id="valasz"></div>
      </body>
    </html>
  
  def betet(p:Map[String, Seq[String]]):NodeSeq =
  {
    try
    {
      val tz = TimeZone.getTimeZone(p("tzid")(0))
      val geo = new GeoLocation(p("loknev")(0), p("szel")(0).toDouble, p("hossz")(0).toDouble, p("mag")(0).toDouble, tz)
      val ac = new AstronomicalCalendar(geo)

      val idoFormat = new java.text.SimpleDateFormat("HH:mm:ss")
      val datumFormat = new java.text.SimpleDateFormat("YYYY-MM-dd")

      //**/println (ac.getCalendar().getTime())
      //**/println (ac.getCalendar().getTime().pipe(idoFormat.format(_)))

      p.get("suntimes").foreach({_ => ac.setAstronomicalCalculator(new SunTimesCalculator())})  // p.get("suntimes") vagy None, vagy Some(ArraySeq("on"))

      def sor(dl:Long):Elem =
      {
        //val d = new Date(dl)
        ac.getCalendar().setTimeInMillis(dl)
        <tr>
          <td>{ac.getCalendar().getTime().pipe(datumFormat.format(_))}</td>
          <td>{ac.getSunrise().pipe(idoFormat.format(_))}</td>
          <td>{ac.getSunTransit().pipe(idoFormat.format(_))}</td>
          <td>{ac.getSunset().pipe(idoFormat.format(_))}</td>
        </tr>
      }

      val tablaBele =
      (p("naptolN")(0).toLong to p("napigN")(0).toLong by 86_400_000L) //millimásodperces dátumok, naponként
      .map(sor)
      .foldLeft(NodeSeq.Empty){(eddigi,uj)=> eddigi ++ uj}

      <div>algoritmus: {ac.getAstronomicalCalculator().getCalculatorName()}</div>
      <table>
        <tr>
          <th>Nap</th>
          <th>Napkelte</th>
          <th>Delelés</th>
          <th>Napnyugta</th>
        </tr>
        {tablaBele}
      </table>
    } catch
    {
      case e : Throwable => { /*e.printStackTrace();*/ <div>{s"tessék rendesen kitölteni a paramétereket! ${e.getMessage}"}</div>}
    }

  }

}