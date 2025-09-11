//> using dep "dev.zio::zio-http:3.5.0"

package example

import zio._

import zio.http._

object HelloWorld extends ZIOAppDefault {
  // Responds with plain text
  val homeRoute =
    Method.GET / Root -> handler(Response.text("Hello World!"))

  // Responds with JSON
  val jsonRoute =
    Method.GET / "json" -> handler(Response.json("""{"greetings": "Hello World!"}"""))

  val óraRoute =
    Method.GET / "vekker" -> handler(Clock.currentDateTime.map(s => Response.text(s.toString)))  //mert nem utc

  // Create HTTP route
  val app = Routes(homeRoute, jsonRoute, óraRoute)

  // Run it like any simple app
  //override val run = Server.serve(app).provide(Server.default)
  override val run = Server.serve(app).provide(Server.defaultWith(_.port(58080)))
}
