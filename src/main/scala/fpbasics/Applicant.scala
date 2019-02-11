package fpbasics

case class Applicant(id: Long
                     , firstName: String
                     , lastName: String
                     , successes: Int
                     , failures: Int
                     , declines: Int
                     , date: Long) {

  def +(other: Applicant): Applicant = {
    Applicant(
      other.id
      , other.firstName
      , other.lastName
      , successes + other.successes
      , failures + other.failures
      , declines + other.declines
      , if (date > other.date) date else other.date
    )
  }

}

object Applicant {

  val xs = List(
      Applicant(1, "Glenn", "Quagmire", 1, 0, 0, 333208800000L) //Thu Jul 24 1980 00:00:00
    , Applicant(1, "Glenn", "Quagmire", 1, 0, 1, 648741600000L) //Tue Jul 24 1990 00:00:00
    , Applicant(1, "Glenn", "Quagmire", 1, 1, 0, 964360800000L) //Mon Jul 24 2000 00:00:00
    , Applicant(2, "Zapp", "Brannigan", 0, 1, 0, 333208800000L) //Thu Jul 24 1980 00:00:00
    , Applicant(2, "Zapp", "Brannigan", 0, 1, 0, 648741600000L) //Tue Jul 24 1990 00:00:00
  )

  implicit object ApplicantMonoid extends Monoid[Applicant] {
    def combine(x: Applicant, y: Applicant): Applicant = x + y
    def empty: Applicant = Applicant(0, "", "", 0, 0, 0, 0L)
  }

  def sum[A](xs: List[A])(implicit m: Monoid[A]): A = xs.fold(m.empty)(m.combine)

  def main(args: Array[String]): Unit = {
    val res = xs
      .groupBy(_.id)
      .map { case (_, list) => sum(list) }.toList

    println(res)
  }

  /**
    * try running this in REPL:
    * xs.groupBy(_.id)
    */

}
