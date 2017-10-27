package fpbasics

import fpbasics.FunctionalState.Job
import org.scalatest.{FunSuite, Matchers}


class StateTest extends FunSuite with Matchers {

  test("Add a job record into a state monad") {
    val result = (for {
        s <- FunctionalState.add(Job(1L, 1L)).get
      } yield s).runA(List()).value

    result shouldBe List(Job(1L, 1L))
  }

  test("Get a job record from a state monad") {
    val result = (for {
      _ <- FunctionalState.add(Job(1L, 1L))
      _ <- FunctionalState.add(Job(2L, 2L))
      j <- FunctionalState.get(Job(1L, 1L))
    } yield j).runA(List()).value

    result shouldBe Some(Job(1L, 1L))
  }

  test("Remove a job record from a state monad") {
    val result = (for {
      _ <- FunctionalState.add(Job(1L, 1L))
      _ <- FunctionalState.add(Job(2L, 2L))
      s <- FunctionalState.remove(Job(1L, 1L)).get
    } yield s).runA(List()).value

    result shouldBe List(Job(2L, 2L))
  }

  test("Add a job record into a state monad, only if the date is the most recent") {
    val result = (for {
      _ <- FunctionalState.addUnique(Job(1L, 500L))
      _ <- FunctionalState.addUnique(Job(2L, 2L))
      s <- FunctionalState.addUnique(Job(1L, 250L)).get
    } yield s).runA(List()).value

    result.sorted shouldBe List(Job(1L, 500L), Job(2L, 2L)).sorted
  }

  test("State manipulation") {

    val result = FunctionalState.stateResult

    result._1.sorted shouldBe List(Job(1,50), Job(2,100), Job(3,150)).sorted
    result._2 shouldBe Some(Job(1, 50))

  }

}
