package fpbasics

import org.scalatest.{FunSuite, Matchers}


class WriterTest extends FunSuite with Matchers {

  test("Implement flatMap for Writer monad") {
    val result = for {
      a <- Writer(2, "")
      b <- Writer(a * 5, "times five")
      c <- Writer(b + 3, "plus three")
    } yield c

    result shouldBe Writer(13, "times fiveplus three")
  }

}
