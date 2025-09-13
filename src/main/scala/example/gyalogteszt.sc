

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