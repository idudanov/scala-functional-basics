package fpbasics

object Result {

  def success[A](a: => A): Result[A] = Right(a)

  def failure(e: Exception): Result[Nothing] = Left(e)

}
