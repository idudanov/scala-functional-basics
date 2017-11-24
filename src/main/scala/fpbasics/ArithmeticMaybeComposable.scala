package fpbasics

object ArithmeticMaybeComposable extends App {

  def division(a: Int, b: Int): Maybe[Int] = if (b == 0) Nothing else Just(a / b)

  def multiplication(a: Int, b: Int): Maybe[Int] = Just(a * b)

  def addition(a: Int, b: Int): Maybe[Int] = Just(a + b)

  def subtraction(a: Int, b: Int): Maybe[Int] = Just(a - b)

  println(
      multiplication(5, 2)
        .flatMap(m => subtraction(m, 2))
        .flatMap(s => division(s, 4))
        .flatMap(d => addition(d, 5))
  )

  println(
    for {
      m <- multiplication(5, 2)
      s <- subtraction(m, 2)
      d <- division(s, 4)
      a <- addition(d, 5)
    } yield a
  )

}
