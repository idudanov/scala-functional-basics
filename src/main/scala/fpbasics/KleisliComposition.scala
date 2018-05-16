package fpbasics

import cats.data.Kleisli
import cats.implicits._

object KleisliComposition extends App {

  /**
    * Kleisli
    *
    * Composition, transformation, injection
    */

  /*
    Linear function

    f(x) = mx + b
    x - independent variable
    m - slope
    b - y intercept

   */

  // ( 2 * x ) + 3
  def linear(x : Int): Int = ( 2 * x ) + 3

  // x ^ 2
  def quadratic(x: Int) : Int = math.pow(x, 2).toInt

  // 1.0 / x
  def reciprocal(x: Int): Double = 1.0 / x

  //linear o quadratic o reciprocal = 1.0 / [ { ( 2 * x ) + 3 } ^ 2 ]

  val result0 = reciprocal(quadratic(linear(3)))

  println(result0)

  val result1 = linear _ andThen quadratic andThen reciprocal apply 3

  println(result1)

  //quadratic o linear o reciprocal = 1.0 / { ( 2 * x ^ 2 ) + 3 }

  def pipeline: Int => Double = quadratic _ andThen linear andThen reciprocal

  println ( pipeline apply 3 )

  //do you want to check your math? ;)
  //quadratic o reciprocal o linear = ???

  /**
    * Lets sum it up!
    *
    * That is, given a function A => B and a function B => C, we can combine them to create a new function A => C
    *
    * A => |B|
    *      |B| => C     this leeds to     A => C
    *
    * So, our case:
    *
    * Int => Int then Int => Int and then Int => Double
    *
    * We can combine that to Int => Double
    *
    */

  def linearM(x : Int): Option[Int] = Some(( 2 * x ) + 3)

  def quadraticM(x: Int): Option[Int] = Some(math.pow(x, 2).toInt)

  def reciprocalM(x: Int): Option[Double] = if (x != 0) Some(1.0 / x) else None

  /**
    * linearM _ andThen quadraticM andThen reciprocalM apply 3
    *
    * So, why this does not work???
    *
    * Because, our functions return monadic values.
    *
    * A => F[B]
    *        B => C
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
    *             Monad(F[_]) In(A) Out(B)            A         F[B]       B
    *                   |       |    |                |          |         |
    *                   v       v    v                v          v         v
    */
  def linearK: Kleisli[Option, Int, Int] = Kleisli((x: Int) => Some( ( 2 * x ) + 3 ) )

  def quadraticK: Kleisli[Option, Int, Int] = Kleisli((x: Int) => Some( math.pow(x, 2).toInt ) )

  def reciprocalK: Kleisli[Option, Int, Double] = Kleisli((x: Int) => if (x != 0) Some( 1.0 / x ) else None )

  val result4 = linearK andThen quadraticK andThen reciprocalK run 3

  println(result4)

  /**
    * So, at its core, Kleisli[F[_], A, B] is just a wrapper around the function A => F[B]. Depending on the properties
    * of the F[_], we can do different things with Kleislis. For instance, if F[_] has a FlatMap[F] instance
    * (we can call flatMap on F[A] values), we can compose two Kleislis much like we can two functions.
    */

  /**
    * Kleisli also has some interesting methods like lift, which allows you to lift a monadic function into another
    * applicative functor.
    */
  def square: Kleisli[Option, Int, Int] = Kleisli { x: Int => (x * x).some }

  val lft = square.lift[List]

  println( List(1, 2, 3) >>= lft.run )

  println( List(1, 2, 3).flatMap(lft.run) )

  println( List(1, 2, 3).flatMap(f => lft.run(f)) )

}
