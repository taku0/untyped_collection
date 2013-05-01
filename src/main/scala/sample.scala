object Main extends App {
  val map: Any = Map(
    "foo" -> 1,
    "bar" -> Seq(
      0, 1, 2,
      Map(
        "buz" -> "hello"
      )
    )
  )

  val hello = {
    import UntypedCollectionImplicits._

    map("bar")(3)("buz").asString
  }

  val lessThanFoo = {
    import UntypedCollectionImplicits._

    map("bar").filter(_ < map("foo"))
  }

  println(hello)

  println(lessThanFoo)
}
