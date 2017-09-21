package fpbasics

import fpbasics.Reduction.Person
import org.scalatest.{FunSuite, Matchers}

class ReductionTest extends FunSuite with Matchers {

  test("sumUp() test") {
    Reduction.sumUp(List(2, 4, 8, 16, 32, 64, 128, 256)) shouldBe 510
  }

  test("reduceRam() test") {
    Reduction.reduceRam(List(8, 512, 4, 32768)) shouldBe 2
  }

  test("removeVowels() test") {
    Reduction.removeVowels("Seek is a fast and reliable job search") shouldBe "Sk s  fst nd rlbl jb srch"
  }

  test("dedup() test") {

    val data = List(
      Person(1L, "Nikola", "Tesla", "10-07-1856", 1505916000000L, 224817379L)
      , Person(2L, "Nikola", "Tesla", "10-07-1856", 1506002400000L, 224817379L)
      , Person(3L, "Albert", "Einstein", "14-04-1879", 1506002400000L, 1890783735L)
      , Person(4L, "Albert", "Einstein", "14-04-1879", 1505916000000L, 1890783735L)
      , Person(5L, "Isaac", "Newton", "25-31-1642", 1506002400000L, 1522665652L)
    )

    val result = List(Person(2, "Nikola", "Tesla", "10-07-1856", 1506002400000L, 224817379)
      , Person(5, "Isaac", "Newton", "25-31-1642", 1506002400000L, 1522665652)
      , Person(3, "Albert", "Einstein", "14-04-1879", 1506002400000L, 1890783735)
    )

    Reduction.dedup(data) shouldBe result
  }
}
