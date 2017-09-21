package fpbasics

object Reduction {

  /**
    * Sum up these numbers in any way you like using reduction :)
    * List(2, 4, 8, 16, 32, 64, 128, 256)
    */
  def sumUp(numbers: List[Int]): Int = {
    ???
  }

  /**
    * Reduce these numbers using division, do not change the order of the data.
    * List(8, 512, 4, 32768)
    * The answer should be 2
    */
  def reduceRam(ram: List[Int]): Int = {
    ???
  }

  /**
    * Remove all vowels from this sentence below, try to do it in parallel.
    * "Seek is a fast and reliable job search"
    * The answer should be:
    * "Sk s  fst nd rlbl jb srch" :))
    */
  def removeVowels(line: String): String = {
    //List("a", "e", "i", "o", "u")
    ???
  }

  /**
    * Deduplicate the following data set:
    * firstName, lastName, dateOfBirth, processingDate, hash
    * where hash = firstName + lastName + dateOfBirth
    * List(
    *   Person(1L, "Nikola", "Tesla", "10-07-1856", 1505916000000L, 224817379L)
    * , Person(2L, "Nikola", "Tesla", "10-07-1856", 1506002400000L, 224817379L)
    * , Person(3L, "Albert", "Einstein", "14-04-1879", 1506002400000L, 1890783735L)
    * , Person(4L, "Albert", "Einstein", "14-04-1879", 1505916000000L, 1890783735L)
    * , Person(5L, "Isaac", "Newton", "25-31-1642", 1506002400000L, 1522665652L)
    * )
    * Records with most resent processingDate should stay in the data set.
    * Please consider Applicant as an example. Introduce functions, define your own operator(s),
    * create a Monoid or a Semi Group. Try to do deduplication in parallel.
    *
    *
    */
  case class Person(id: Long
                    , firstName: String
                    , lastName: String
                    , dateOfBirth: String
                    , processingDate: Long
                    , hash: Long) {
  }

  /**
    * The answer should be:
    * List(
    *   Person(2, "Nikola", "Tesla", "10-07-1856", 1506002400000L, 224817379)
    * , Person(5, "Isaac", "Newton", "25-31-1642", 1506002400000L, 1522665652)
    * , Person(3, "Albert", "Einstein", "14-04-1879", 1506002400000L, 1890783735)
    * )
    */
  def dedup(xs: List[Person]): List[Person] = {
    ???
  }
}
