package fpbasics

import scala.util.{Failure, Success, Try}

/**
  * A bit less Java-like implementation :)))
  */

class JobProcessor2 {

  case class Job(jobId: Long, salary: Double)

  class JobNotFoundException(jobId: Long) extends RuntimeException

  case class SalaryComparator(private val jobs: Seq[Job]) {

    def salaryDifference(jobId1: Long, jobId2: Long): Try[Double] = {

      val jobOpt1 = findJob(jobId1)

      if(jobOpt1.isDefined) {

        val jobOpt2 = findJob(jobId2)

        if(jobOpt2.isDefined) {
          Success(difference(jobOpt1.get.salary, jobOpt2.get.salary))
        } else {
          Failure(new JobNotFoundException(jobId2))
        }
      } else {
        Failure(new JobNotFoundException(jobId1))
      }
    }

    private def findJob(jobId: Long) = jobs.find(_.jobId == jobId)

    private def difference(salary1: Double, salary2: Double) = salary1 - salary2
  }
}

/**
  * We have managed to eliminate the exception and potential null check issues, but the if-else branches are still making
  * our code verbose and fragile.
  *
  * To repeat, !!!The method signature does not say anything about the possible catastrophe!!!
  *
  * Fortunately, this type of solution is not common, but still can be seen time to time in our code base ;)
  */