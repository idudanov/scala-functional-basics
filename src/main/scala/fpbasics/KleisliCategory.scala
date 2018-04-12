package fpbasics

import cats.data.Kleisli
import cats.implicits._

object KleisliCategory extends App {

  def linear(x : Int): Int = ( 2 * x ) + 3

  def quadratic(x: Int) : Int = scala.math.pow(x, 2).toInt

  def reciprocal(x: Int): Double = 1.0 / x

  //linear o quadratic o reciprocal = 1.0 / [ { ( 2 * x ) + 3 } ^ 2 ]
  val result1 = linear _ andThen quadratic andThen reciprocal apply 3

  println(result1)

  //quadratic o linear o reciprocal = 1.0 / { ( 2 * x ^ 2 ) + 3 }
  val result2 = quadratic _ andThen linear andThen reciprocal apply 3

  println(result2)

  //do you want to check your math? ;)
  //quadratic o reciprocal o linear = ???

  /**
    * Lets sum it up!
    *
    * That is, given a function A => B and a function B => C, we can combine them to create a new function A => C
    *
    * A => |B|
    *      |B| => C
    *
    * So, our case:
    * Int => Int then Int => Int and then Int => Double we can combine that to Int => Double
    *
    */

  def linearM(x : Int): Option[Int] = Some(( 2 * x ) + 3)

  def quadraticM(x: Int): Option[Int] = Some(scala.math.pow(x, 2).toInt)

  def reciprocalM(x: Int): Option[Double] = if (x != 0) Some(1.0 / x) else None

  /**
    * linearM _ andThen quadraticM andThen reciprocalM apply 3
    *
    * So, why is this does not work???
    *
    * Because, our functions return monadic values.
    */

  val result3 = for {
    l <- linearM(3)
    q <- quadraticM(l)
    r <- reciprocalM(q)
  } yield r

  println(result3)

  /**
    * Kleisli enables composition of functions that return a monadic value, for instance an Option[Int] or
    * Either[String, Exception]
    *
    * Kleisli[F[_], A, B] which is just A => F[B]
    *
    *             Monad(F[_]) In(A) Out(B)
    *                   |       |    |
    *                   v       v    v
    */
  def linearK: Kleisli[Option, Int, Int] = Kleisli((x: Int) => Some(( 2 * x ) + 3))

  def quadraticK: Kleisli[Option, Int, Int] = Kleisli((x: Int) => Some(scala.math.pow(x, 2).toInt))

  def reciprocalK: Kleisli[Option, Int, Double] = Kleisli((x: Int) => if (x != 0) Some(1.0 / x) else None)

  val result4 = linearK andThen quadraticK andThen reciprocalK run 3

  println(result4)

  /**
    * So, at its core, Kleisli[F[_], A, B] is just a wrapper around the function A => F[B]. Depending on the properties
    * of the F[_], we can do different things with Kleislis. For instance, if F[_] has a FlatMap[F] instance
    * (we can call flatMap on F[A] values), we can compose two Kleislis much like we can two functions.
    */

}
