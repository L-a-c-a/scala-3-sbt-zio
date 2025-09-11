Így jött létre:

```
$ sbt new scala/scala3.g8 
A template to demonstrate a minimal Scala 3 application 

name [Scala 3 Project Template]: Scala 3 sbt zio

Template applied in /home/laca/fejlesztés/scala/g8/./scala-3-sbt-zio
```

build.sbt-hez hozzáadtam:
```
    libraryDependencies += "dev.zio" %% "zio-http"     % "3.4.0",
```
src/main/scala alá másoltam a cli alól pl. a `HelloWorldAdvanced.scala`-t
```
$ sbt
> run
```
és MŰX!

...csak kilépni nem lehet utána a terminálból.  
A terminált ki kell lőni a kuka-ikonnal (Kill Terminal), és új terminált indítani.  
De úgy is műxik, hogy
```
$ cd src/main/scala/
$ scala-cli run HelloWorldAdvanced.scala 
```
meg úgy is, hogy 
```
scala-cli run src/main/scala/HelloWorldAdvanced.scala
``` 
(legalábbis amíg csak egy fájl van)  
...meg úgy is, hogy lejjebb rakom a `example/` alá és
```
scala-cli run src/main/scala/example/HelloWorldAdvanced.scala
``` 
(és akkor a `package example` marad)

A `HelloWorld.scala`-t is odamásoltam (`example/` alá) Az is műx. Default port `:8080`.  
(A `//> using`-ot mindig át kell írni 3.5.0-ra, mert egyrészt ajánlja, másrészt az sbt-ben is az van.)  
Az sbt is műx továbbra is. Megkérdezi:
```
Multiple main classes detected. Select one to run:
 [1] example.HelloWorld
 [2] example.HelloWorldAdvanced
```
Még a forrásban megjelenő `run | debug` is műxik.

A scala.xml-t be kell függőségelni!

Most, hogy van az `import astro.O`, most már csak a `run` működik, a scala-cli nem. Meg az sbt is műx.

Na, a hülye anyád. Kész html-t úgy nem lehet kiküldeni, hogy `Response.html(O.elotet.toString)`, mert eszképeli (pl. `&lt;`).  
Át kell vágni a fejét: `Response.text(O.elotet.toString).updateHeaders(_ => Headers("content-type", "text/html"))`

A scala-cli működéséhez `//> using file` kell.

---
[Megkérdeztem a chatgpt-t](chatgpt.form.md)  
Az extenziós megoldás tetszik.

