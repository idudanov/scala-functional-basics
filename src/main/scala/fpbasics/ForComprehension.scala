package fpbasics

object ForComprehension {

  val xs = List(1, 2, 3)
  val ys = List(4, 5, 6)
  val zs = List(7, 8, 9)

  /**
    * translate this to an appropriate for-comprehension
    * for(x <- xs; y <- ys; z <- zs) {
    *   println(s"$x, $y, $z")
    * }
    */
  def nestedLoop(): Unit = {

  }

  /**
    * translate this to an appropriate for-comprehension
    * for(x <- xs; y <- ys; z <- zs) yield z
    * the result should be List(7, 8, 9, 7, 8, 9, 7, 8, 9, 7, 8, 9, 7, 8, 9, 7, 8, 9, 7, 8, 9, 7, 8, 9, 7, 8, 9)
    *
    */
  def nestedLoopYield(): List[Int] = {
    ???
  }

  /**
    * translate this to an appropriate for-comprehension
    * for(x <- xs; y <- ys; z <- zs; if z != 9) yield z
    * the result should be List(7, 8, 7, 8, 7, 8, 7, 8, 7, 8, 7, 8, 7, 8, 7, 8, 7, 8)
    *
    */
  def nestedLoopYieldCond(): List[Int] = {
    ???
  }

  val ns = List(1, 2, 3, 4, 5, 6, 7, 8, 9)

  def isEven(n: Int): Option[Int] = n % 2 match {
    case 0 => Some(n) case _ => None
  }

  /**
    * Using syntactic sugar for composition isEven filter out all odd numbers
    * the result should be List(2, 4, 6, 8)
    */
def syntacticSugar(): List[Int] =
  ???
}
