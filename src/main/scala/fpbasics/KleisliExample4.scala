package fpbasics

import cats.data.Kleisli
import fpbasics.ValueObject._
import cats.implicits._

object KleisliExample4 extends App with UserService with ContextService {

  def userPipeline: Kleisli[Result, Context, Double] = for {
    _       <- log("retrieving first user")
    result1 <- get(27L)
    _       <- log("retrieving second user")
    result2 <- find("gquagmire")
  } yield difference(result1.salary, result2.salary)

  private def difference(salary1: Double, salary2: Double) = math.abs(salary1 - salary2)

  def log(message: String): Action[Unit] = Action { ctx =>
    println(s"${ctx.environment}: $message")
  }

  println(userPipeline.run(getContext))
}