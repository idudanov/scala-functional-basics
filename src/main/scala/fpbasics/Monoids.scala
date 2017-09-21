package fpbasics

object Monoids {

  val ls = List("zero","one", "two", "three", "four", "five")
  val ln = List(0, 1, 2, 3, 4, 5)

  implicit object StringMonoid extends Monoid[String] {
    def combine(x: String, y: String): String = { println(s"x=$x, y=$y") ; x concat y }
    def empty: String = ""
  }

  implicit object IntMonoid extends Monoid[Int] {
    def combine(x: Int, y: Int): Int = { println(s"x=$x, y=$y") ; x + y }
    def empty: Int = 0
  }

  def sumFold[A](xs: List[A])(implicit m: Monoid[A]): A = {
    xs.fold(m.empty)(m.combine)
  }

  def sumFoldLeft[A](xs: List[A])(implicit m: Monoid[A]): A = {
    xs.foldLeft(m.empty)(m.combine)
  }

  def sumFoldRight[A](xs: List[A])(implicit m: Monoid[A]): A = {
    xs.foldRight(m.empty)(m.combine)
  }

  def sumReduce[A](xs: List[A])(implicit m: Monoid[A]): A = {
    if(xs.nonEmpty) xs.reduce(m.combine) else m.empty
  }

  def sumReduceLeft[A](xs: List[A])(implicit m: Monoid[A]): A = {
    if(xs.nonEmpty) xs.reduceLeft(m.combine) else m.empty
  }

  def sumReduceRight[A](xs: List[A])(implicit m: Monoid[A]): A = {
    if(xs.nonEmpty) xs.reduceRight(m.combine) else m.empty
  }
}