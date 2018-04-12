package fpbasics

import cats.data.Kleisli
import cats.implicits._

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

  def mainFn: Kleisli[Result, UserA, UserB] = for {
    givenname <- readFirst
    surname   <- readSecond
  } yield UserB(givenname, surname)


  println(
    mainFn.run(UserA("Ivan", "Dudanov"))
  )

}
