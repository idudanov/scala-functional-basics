package fpbasics

import org.scalatest.{FunSuite, Matchers}

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

import scala.concurrent.ExecutionContext.Implicits.global

class FutureTest extends FunSuite with Matchers {

  test("convertToIntLiftToFuture() test 1") {
    val f = BrightFuture.convertToIntLiftToFuture("s")

    val r: Int = Await.result(f, 5.seconds)

    r shouldBe 0
  }

  test("convertToIntLiftToFuture() test 2") {
    val f = BrightFuture.convertToIntLiftToFuture("1")

    val r: Int = Await.result(f, 5.seconds)

    r shouldBe 1
  }

  test("cremoveVowels() test") {
    val f = BrightFuture.removeVowels(Future{"Sk s  fst nd rlbl jb srch"})

    val r: String = Await.result(f, 5.seconds)

    r shouldBe "Sk s  fst nd rlbl jb srch"
  }

  test("intersection() test") {
    val f = BrightFuture.intersection(Future{List(1, 4, 3)}, Future{List(1, 5, 3)})

    val r: List[Int] = Await.result(f, 5.seconds)

    r shouldBe List(1, 3)
  }

}
