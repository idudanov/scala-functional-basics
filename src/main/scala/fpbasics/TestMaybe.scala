package fpbasics

object TestMaybe extends App {

  val x = 1

  val m = Maybe(1)

  def f(x: Int): Maybe[Int] = Just(x)

  def g(x: Int): Maybe[Int] = Just(x)

  /**
    * left-identity law
    */
  println(
    Maybe.pure(x).flatMap(f) == f(x)
  )

  /**
    * right-identity law
    */
  println(
    m.flatMap(Maybe.pure) == m
  )

  /**
    * associativity law
    */
  println(
    m.flatMap(f).flatMap(g) == m.flatMap(x => f(x).flatMap(g))
  )
}
