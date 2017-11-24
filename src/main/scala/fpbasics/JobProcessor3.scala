package fpbasics

import scala.util.{Failure, Success, Try}

/**
  * A Scala-like implementation :)))
  */

class JobProcessor3 {

  case class Job(jobId: Long, salary: Double)

  class JobNotFoundException(jobId: Long) extends RuntimeException

  case class SalaryComparator(private val jobs: Seq[Job]) {

    def salaryDifference(jobId1: Long, jobId2: Long): Try[Double] = {

      findJob(jobId1)
        .fold[Try[Double]](Failure(new JobNotFoundException(jobId1))) { job1 =>
        findJob(jobId2)
            .fold[Try[Double]](Failure(new JobNotFoundException(jobId2))) { job2 =>
            Success(difference(job1.salary, job2.salary))
          }
        }
    }

    private def findJob(jobId: Long) = jobs.find(_.jobId == jobId)

    private def difference(salary1: Double, salary2: Double) = salary1 - salary2
  }
}

/**
  * This solution can be seen more often. It is a bit more functional and scala-like, but this is just syntactically
  * hiding the if-else statements, and the method signature does not say anything about the possible outcome
  *
  * It is somehow shorter but the readability still can be improved.
  *
  * The benefit of the last 3 solutions (including this one) is that they make the decision tree pretty obvious:
  *
  * If the first job is not available then return with error
  *
  * If the first job is available then fetch the second job
  *
  * If the second job is not available then return with error
  *
  * If the first and the second job is available then calculate the final result
  *
  * Now we see the pattern, lets rephrase it!
  *
  * We need the calculated result at the end of the happy path, but we would like to terminate immediately when some
  * intermediate calculation fails.
  *
  * In FP this called a short-circuiting a computation.
  *
  *
  * Option[T] — A monad for maybe effect
  *
  * Try[T] — A monad for exception effect
  *
  * Future[T] — A monad for async effect
  *
  * Either[E, T] — A monad for error effect.
  *
  * State[S, A] — A monad for state effect (Cats)
  *
  * Task[A] — A monad for lazy & asynchronous computations, useful for controlling side-effects (Monix)
  *
  * Yes, they all Monads, but which one to use?
  *
  * There are many other more exotic monads like Free, Eff, but it goes beyond this session ;)
  *
  */