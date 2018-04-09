package fpbasics

object Arithmetic extends App {

  def division(a: Int, b: Int): Int = a / b

  def multiplication(a: Int, b: Int): Int = a * b

  def addition(a: Int, b: Int): Int = a + b

  def subtraction(a: Int, b: Int): Int = a - b


  println(
    addition(
      division(
        subtraction(
          multiplication(5, 2), 2), 0), 5
    )
  )

}
