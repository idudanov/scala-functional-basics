package fpbasics

import cats.implicits._

object KleisliExample4 extends App with UserService with ContextService {

  def userPipeline: Action[Double] = for {
    _     <- log("retrieving first user1")
    user1 <- get(27L)
    _     <- log("retrieving second user2")
    user2 <- find("gquagmire")
  } yield difference(user1.salary, user2.salary)

  private def difference(salary1: Double, salary2: Double) = math.abs(salary1 - salary2)

  def log(message: String): Action[Unit] = Action( ctx =>
    println(s"${ctx.environment}: $message")
  )

  println(userPipeline.run(getContext))
}