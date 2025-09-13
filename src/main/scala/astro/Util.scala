package astro

import java.net.URLDecoder
import scala.collection.immutable.{Map, Seq}

object Util
{
  //chatgpt súgta
  // Function to parse URL query string into Map[String, Seq[String]]
  def parseQueryStringNemműx(qs: String, charset: String = "UTF-8"): Map[String, Seq[String]] = 
    { qs
      .split("&")
      .filter(_.nonEmpty)
      .map  { pair =>
              val Array(key, value) = pair.split("=", 2) ++ Array("")
              URLDecoder.decode(key, charset) -> URLDecoder.decode(value, charset)
            }
      .groupBy(_._1)
      .view
      .mapValues(_.map(_._2).toSeq)
      .toMap
    }

  // minden paraméterből csak egy lehet (nem is kell több), minden Seq egytagú
  // csak a %2F-et és a %3A-t dekódolja
  def parseQueryString(qs: String, charset: String = "UTF-8"): Map[String, Seq[String]] = 
    { qs
      .split("&|=")
      .grouped(2)   // Iterator[Array[String]]
      .map(a => (a(0), a(1))) // Iterator[(String, String)]
      .map { (k, v) => k -> v.replace("%2F", "/").replace("%3A", ":") }
      .map { (k, v) => k -> Seq(v) }
      .toMap      // eddig Iterator, innentől Map
    }

}