package fpbasics

import cats.implicits._

/**
  * A functional implementation.
  */

class JobProcessor4 {

  case class Job(jobId: Long, salary: Double)

  class JobNotFoundException(jobId: Long) extends RuntimeException

  case class SalaryComparator(private val jobs: Seq[Job]) {

    def salaryDifference(jobId1: Long, jobId2: Long): Either[JobNotFoundException, Double] = for {

        job1 <- Either.fromOption(findJob(jobId1), new JobNotFoundException(jobId1))
        job2 <- Either.fromOption(findJob(jobId2), new JobNotFoundException(jobId2))

    } yield difference(job1.salary, job2.salary)

    private def findJob(jobId: Long) = jobs.find(_.jobId == jobId)

    private def difference(salary1: Double, salary2: Double) = math.abs(salary1 - salary2)
  }
}

/**
  * Just to sum up what we have now:
  *
  * If the first job is not available then return with error
  *
  * If the first job is available then fetch the second job
  *
  * If the second job is not available then return with error
  *
  * If the first and the second job is available then calculate the final result
  *
  *
  * No exception and null handling
  *
  * Clear method signature
  *
  * Short and concise solution
  *
  * The first thing comes into my mind about Monad is for-comprehension since it expresses the plumbing and
  * short-circuiting in a neat and readable way
  *
  * I am using Cats to boost the default Either to be able to create from Option
  *
  *
  *
  * Either Monad
  *
  * Right projection:
  *
  * Binds the given function across `Right`.
  *
  * def flatMap[A1 >: A, B1](f: B => Either[A1, B1]): Either[A1, B1] = e match {
  *   case Right(b) => f(b)
  *   case _        => e.asInstanceOf[Either[A1, B1]]
  * }
  *
  * Maps the function argument through `Left`.
  *
  * def map[B1](f: B => B1): Either[A, B1] = e match {
  *   case Right(b) => Right(f(b))
  *   case _        => e.asInstanceOf[Either[A, B1]]
  * }
  *
  * Left projection:
  *
  * Binds the given function across `Left`.
  *
  * def flatMap[A1, B1 >: B](f: A => Either[A1, B1]): Either[A1, B1] = e match {
  *   case Left(a) => f(a)
  *   case _       => e.asInstanceOf[Either[A1, B1]]
  * }
  *
  * Maps the function argument through `Left`.
  *
  * def map[A1](f: A => A1): Either[A1, B] = e match {
  *   case Left(a) => Left(f(a))
  *   case _       => e.asInstanceOf[Either[A1, B]]
  * }
  */