package fpbasics

object FirstMonoid {

  implicit object IntMonoid extends Monoid[Int] {
    def combine(x: Int, y: Int): Int = x + y
    def empty: Int = 0
  }

  val ln = List(0, 1, 2, 3, 4, 5)

  def sum[A](xs: List[A])(implicit m: Monoid[A]): A = {
    xs.fold(m.empty)(m.combine)
  }

}
