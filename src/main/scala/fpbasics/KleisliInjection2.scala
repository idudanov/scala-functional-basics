package fpbasics

object KleisliInjection2 extends App {

  val contextService = new ContextServiceNaive

  val userService = new UserServiceNaive(contextService.getContext)

  /**
    * To repeat, contextService and userService are injected into the code using encapsulation technique.
    *
    * However, we have managed to eliminate the exception and potential null check issues, but the if-else branches are
    * still making our code verbose and fragile.
    */

  def userPipeline: Either[RuntimeException, Double] = {

    val user1 = userService.get(2L)

    if(user1.isDefined) {
      val user2 = userService.find("gquagmire")

      if(user2.isDefined) {
        Right(difference(user1.get.salary, user2.get.salary))
      } else {
        Left(new RuntimeException(s"Unable to find a user"))
      }
    } else {
      Left(new RuntimeException(s"Unable to get a user"))
    }
  }
  private def difference(salary1: Double, salary2: Double) = math.abs(salary1 - salary2)


  println(userPipeline)
}