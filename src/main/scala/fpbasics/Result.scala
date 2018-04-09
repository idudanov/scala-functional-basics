package fpbasics

object Result {

  def ok[A](a: => A): Result[A] = Right(a)

  def error(e: Exception): Result[Nothing] = Left(e)

}
