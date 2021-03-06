package fpbasics

import cats.implicits._

object KleisliInjection3 extends App {

  val contextService = new ContextServiceNaive

  val userService = new UserServiceNaive(contextService.getContext)

  def userPipeline: Either[RuntimeException, Double] = for {

    user1 <- Either.fromOption(userService.get(2L),
      new RuntimeException(s"Unable to get a user, in ${contextService.getContext.environment} environment"))
    user2 <- Either.fromOption(userService.find("gquagmire"),
      new RuntimeException(s"Unable to find a user, in ${contextService.getContext.environment} environment"))

  } yield difference(user1.salary, user2.salary)

  private def difference(salary1: Double, salary2: Double) = math.abs(salary1 - salary2)

  println(userPipeline)

  /**
    * The benefit of the last 3 solutions (including this one) is that they make the decision tree pretty obvious:
    *
    * If the first user is not available then return with error
    *
    * If the first user is available then fetch the second user
    *
    * If the second user is not available then return with error
    *
    * If the first and the second user is available then calculate the final result
    *
    * We need the calculated result at the end of the happy path, but we would like to terminate immediately when some
    * intermediate calculation fails.
    *
    *
    * This solution is functional, it is somehow shorter, and we are using a short-circuiting computation!
    *
    * However, the readability still can be improved, and unfortunately our solution still uses encapsulation as a
    * dependency injection mechanism.
    */

}