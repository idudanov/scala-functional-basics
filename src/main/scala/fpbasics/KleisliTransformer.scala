package fpbasics

import cats.data.Kleisli
import cats.implicits._


/**
  * Kleisli can be viewed as the monad transformer for functions. Recall that at its essence, Kleisli[F, A, B] is just a
  * function A => F[B], with niceties to make working with the value we actually care about, the B, easy. Kleisli allows
  * us to take the effects of functions and have them play nice with the effects of any other F[_].
  *
  * In other words lets transform UserA into UserB
  *
  * UserA => UserB
  *
  */

case class UserA(firstname: String, lastname: String)

case class UserB(givenname: String, surname: Int)

object KleisliTransformer extends App {

  type Result[A] = Either[Exception, A]

  def readFirst = Kleisli[Result, UserA, String] {
    userA => {
      try {
        Right(userA.firstname + "processed")
      } catch {
        case e: Exception => Left(e)
      }
    }
  }

  def readSecond = Kleisli[Result, UserA, Int] {
    userA => Right(userA.lastname.hashCode)
  }
  
  /**
    * To repeat:
    *
    * Kleisli[F[_], A, B] which is just A => F[B]
    *
    *             Monad(F[_]) In(A)  Out(B)
    *                   |       |      |
    *                   v       v      v
    */
  def mainFn: Kleisli[Result, UserA, UserB] = for {
    givenname <- readFirst
    surname   <- readSecond
  } yield UserB(givenname, surname)


  println(
    mainFn.run(UserA("Ivan", "Dudanov"))
  )

}
