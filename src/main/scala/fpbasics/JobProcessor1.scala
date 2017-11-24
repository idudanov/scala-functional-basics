package fpbasics

/**
  * Salary Comparator
  *
  * Caller provides two job ids
  *
  * If both of the jobs are available then compute the salary difference
  *
  * If one of them is not available then return with a relevant error
  *
  * For demonstration purpose the salary can be expressed as negative value
  *
  * Java-like implementation :)
  */

class JobProcessor1 {

  case class Job(jobId: Long, salary: Double)

  class JobNotFoundException(jobId: Long) extends RuntimeException

  case class SalaryComparator(private val jobs: Seq[Job]) {

    def salaryDifference(jobId1: Long, jobId2: Long): Double = {

      val jobOpt1 = jobs.find(_.jobId == jobId1)

      if(jobOpt1.isDefined) {

        val jobOpt2 = jobs.find(_.jobId == jobId2)

        if(jobOpt2.isDefined) {
          difference(jobOpt1.get.salary, jobOpt2.get.salary)
        } else {
          throw new JobNotFoundException(jobId2)
        }
      } else {
        throw new JobNotFoundException(jobId1)
      }
    }
    private def difference(salary1: Double, salary2: Double) = salary1 - salary2
  }
}

/**
  * This is obviously java code dressed in scala syntax. ;)
  *
  * Very verbose, deep-nested if-else branches and throws exception.
  *
  * !!!The method signature does not say anything about the possible catastrophe!!!
  * If one of the jobs does not exist in the dataset an exception will be thrown.
  *
  * Another typical solution is to return with null instead of throwing exception. Both of the solutions forces the
  * caller to open the method to obtain more knowledge about the implementation.
  */