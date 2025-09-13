

final class gyalogteszt$_ {
def args = gyalogteszt_sc.args$
def scriptPath = """src/main/scala/example/gyalogteszt.sc"""
/*<script>*/


  def parseQueryString(qs: String, charset: String = "UTF-8"): Map[String, Seq[String]] = 
    { qs
      .split("&|=")
      .grouped(2)
      .map(a => (a(0), a(1)))
      .toMap
      .map { (k, v) => k -> v.replace("%2F", "/").replace("%3A", ":") }
      .map { (k, v) => k -> Seq(v) }
    }

val qs = "tzid=Europe%2FBudapest&loknev=BP&szel=47.5&hossz=19&mag=100&naptolN=1757628000000&naptol=2025-09-12T00%3A00&napigN=1757628000000&napig=2025-09-12T00%3A00"
val result = parseQueryString(qs)
println(result)
/*</script>*/ /*<generated>*//*</generated>*/
}

object gyalogteszt_sc {
  private var args$opt0 = Option.empty[Array[String]]
  def args$set(args: Array[String]): Unit = {
    args$opt0 = Some(args)
  }
  def args$opt: Option[Array[String]] = args$opt0
  def args$: Array[String] = args$opt.getOrElse {
    sys.error("No arguments passed to this script")
  }

  lazy val script = new gyalogteszt$_

  def main(args: Array[String]): Unit = {
    args$set(args)
    val _ = script.hashCode() // hashCode to clear scalac warning about pure expression in statement position
  }
}

export gyalogteszt_sc.script as `gyalogteszt`

