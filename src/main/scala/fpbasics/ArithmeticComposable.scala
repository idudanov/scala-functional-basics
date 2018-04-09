package fpbasics

object ArithmeticComposable extends App {

  def division(a: Int, b: Int): Option[Int] = if (b == 0) None else Some(a / b)

  def multiplication(a: Int, b: Int): Option[Int] = Some(a * b)

  def addition(a: Int, b: Int): Option[Int] = Some(a + b)

  def subtraction(a: Int, b: Int): Option[Int] = Some(a - b)

  println(
    for {
      m <- multiplication(5, 2)
      s <- subtraction(m, 2)
      d <- division(s, 0)
      a <- addition(d, 5)
    } yield a
  )

}
