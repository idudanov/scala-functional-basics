package fpbasics

import cats.data.Kleisli
import cats.implicits._


/**
  * Kleisli can be viewed as the monad transformer for functions. Recall that at its essence, Kleisli[F, A, B] is just a
  * function A => F[B], with niceties to make working with the value we actually care about, the B, easy. Kleisli allows
  * us to take the effects of functions and have them play nice with the effects of any other F[_].
  *
  * In other words lets transform UserFromSystemA into UserFromSystemB
  *
  * UserFromSystemA => UserFromSystemB
  *
  * This is distinctly different to Kleisli composition where the output from one function was fed into the next.
  *
  * How would we go about combining these functions?
  *
  * Since Kleisli map over functions in a Monadic context:
  * Kleisli[F[_], A, B] where F has a Monad instance
  *
  * we can use a for-comprehension to solve our problem.
  *
  */

case class UserFromSystemA(firstname: String, lastname: String, age: String)

case class UserFromSystemB(givenname: String, surname: String, yearsOld: Int, hashValue: Int)

object KleisliTransformer extends App {

  /**
    * Our custom algebra
    */
  type Result[A] = Either[Exception, A]

  object Result {

    def apply[A](f: => A): Result[A] = pure(f)

    def pure[A](a: => A): Result[A] = try { success(a) } catch { case e: Exception => failure(e) }

    def success[A](a: => A): Result[A] = Right(a)

    /*
    Nothing is a subtype of every other type (including Null); there exist no instances of this type. Although type
    Nothing is uninhabited, it is nevertheless useful in several ways. For instance, the Scala library defines a value
    scala.collection.immutable.Nil of type List[Nothing]. Because lists are covariant in Scala, this makes
    scala.collection.immutable.Nil an instance of List[T], for any element of type T.

    Another usage for Nothing is the return type for methods which never return normally. One example is method error in
    sys, which always throws an exception.
    */
    def failure(e: => Exception): Result[Nothing] = Left(e)
  }

  def readFirstname = Kleisli[Result, UserFromSystemA, String] {
    userA =>
      if(userA.firstname.length > 170)
        Result.failure( new Exception("This firstname is too long!") )
      else Result( userA.firstname )
  }

  def readLastname = Kleisli[Result, UserFromSystemA, String] {
    userA =>
      if(userA.lastname.length > 35)
        Result.failure( new Exception("This lastname is too long!") )
      else Result( userA.lastname )
  }

  /**
    * Let's pretend that we are using some third party hashing function that potentially can crush :)
    */
  def generateUserHash = Kleisli[Result, UserFromSystemA, Int] {
    userA => Result( ( userA.firstname + userA.lastname ).hashCode )
  }

  /**
    * Kleisli with different inputs.
    *
    * One thing to note is that we keep aligning the input type, UserFromSystemA in this case, through all functions.
    *
    * How would we go about combining Kleisli with different input types?
    *
    * This is where the local function comes into play. It is defined as:
    *
    * def local[AA](f: AA => A): Kleisli[F, AA, B]
    *
    * Which is just:
    *
    * AA => A => B
    *
    * It basically converts a Kleisli[F, A, B] to a Kleisli[F, AA, B] as long as we supply it a function to convert an
    * AA to A.
    *
    * Some function f converts some other input type AA into our required input type of A. This allows us to combine
    * Kleisli with different input types as the local function, widens the input type to each Kleisli as AA.
    *
    */

  def readAge = Kleisli[Result, String, Int] {
    age => {
      Result(age.toInt).fold(_ => Result.failure(new Exception("Cannot convert to Int")),
        n => {
          if (n > 150 && n < 1)
            Result.failure(new Exception("Cannot convert to Int"))
          else Result.pure(n)
        })
    }
  }

  /**
    * To repeat:
    *
    * Kleisli[F[_], A, B] which is just A => F[B]
    *
    *                 Monad(F[_])    In(A)             Out(B)
    *                     |           |                  |
    *                     v           v                  v
    */
  def pipeline: Kleisli[Result, UserFromSystemA, UserFromSystemB] = for {
    givenname <- readFirstname
    surname   <- readLastname
    /**
      *                              AA        f() = AA => A
      *                              |               |
      *                              v               v
      */
    yearsOld  <- readAge.local[UserFromSystemA](userA => userA.age)
    userHash  <- generateUserHash
  } yield UserFromSystemB(givenname, surname, yearsOld, userHash)


   val result = pipeline run UserFromSystemA("Adolph Blaine Charles David Earl Frederick Gerald Hubert Irvin John" +
     "Kenneth Lloyd Martin Nero Oliver Paul Quincy Randolph Sherman Thomas Uncas Victor William Xerxes Yancy"
    , "Wolfeschlegelsteinhausenbergerdorff", "32")

  println(result)

}
