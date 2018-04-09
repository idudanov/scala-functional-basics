package fpbasics

import scala.concurrent.Future
import scala.util.{Failure, Success}

import scala.concurrent.ExecutionContext.Implicits.global

object FutureRepl {

  /**
    * Future
    *
    * A Future is a placeholder object for a value that may not yet exist. Generally, the value of the Future is
    * supplied concurrently and can subsequently be used. Composing concurrent tasks in this way tends to result in faster,
    * asynchronous, non-blocking parallel code.
    */


  val fInt: Future[Int] = Future {
    6 / 3
  }

  /**
    * Execution Context
    *
    * An ExecutionContext is similar to an Executor: it is free to execute computations in a new thread, in a pooled
    * thread or in the current thread (although executing the computation in the current thread is discouraged).
    */

  /**
    * The Global Execution Context
    *
    * ExecutionContext.global is an ExecutionContext backed by a ForkJoinPool. It should be sufficient for most
    * situations but requires some care. A ForkJoinPool manages a limited amount of threads (the maximum amount of
    * thread being referred to as parallelism level).
    */

  //implicit val iec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global
  /**
    * or
    */
  //val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global

//  val fStr: Future[String] = Future {
//    "camou" concat "flage"
//  }(ec)

  /**
    * The scala.concurrent package comes out of the box with an ExecutionContext implementation, a global static thread
    * pool. It is also possible to convert an Executor into an ExecutionContext. Finally, users are free to extend the
    * ExecutionContext trait to implement their own execution contexts, although this should only be done in rare cases.
    */

//  val customEc: ExecutionContext = new ExecutionContext {
//    val threadPool: ExecutorService = Executors.newFixedThreadPool(1000)
//
//    def execute(runnable: Runnable) {
//      threadPool.submit(runnable)
//    }
//
//    def reportFailure(t: Throwable): Unit = {}
//  }
//
//  def square(x: Int): Future[Int] = Future {
//    x * x
//  }(customEc)
//
//  val fSquare: Future[Int] = square(2)

  /**
    *
    * Terminology is very important!!!
    *
    */

  /**
    * Futures
    *
    * 1) If the computation has not yet completed, we say that the Future is not completed.
    *
    * 2) If the computation has completed with a value or with an exception, we say that the Future is completed.
    *
    *
    * Completion can take one of two forms:
    *
    * 1) When a Future is completed with a value, we say that the future was successfully completed with that value.
    *
    * 2) When a Future is completed with an exception thrown by the computation, we say that the Future was failed with
    * that exception.
    *
    */

    val r = for {
      a <- Future{6 / 3}
      b <- Future {
        a / 0
      }
    } yield b

  val epicFail: Future[Int] = Future {
    Thread sleep 3000
    6 / 1
  }

  def fn(x: Int, y: Int, d: Long): Int = {
    Thread sleep d
    x / y
  }

  val f: Future[Int] = Future.apply(fn(6, 2, 3000))

  /**
    * A Future has an important property that it may only be assigned once. Once a Future object is given a value or an
    * exception, it becomes in effect immutable – it can never be overwritten. The simplest way to create a future object
    * is to invoke the Future.apply method which starts an asynchronous computation and returns a future holding the
    * result of that computation. The result becomes available once the future completes.
    *
    * Note that Future[T] is a type which denotes future objects, whereas Future.apply is a method which creates and
    * schedules an asynchronous computation, and then returns a future object which will be completed with the result
    * of that computation.
    */


  /**
    * Callbacks
    *
    * We now know how to start an asynchronous computation to create a new future value, but we have not shown how to
    * use the result once it becomes available, so that we can do something useful with it. We are often interested in
    * the result of the computation, not just its side-effects.
    *
    * In many future implementations, once the client of the future becomes interested in its result, it has to block its
    * own computation and wait until the future is completed - only then can it use the value of the future to continue
    * its own computation.
    *
    * Although, this is allowed by the Scala Future API as we will show later, from a performance
    * point of view a better way to do it is in a completely non-blocking way, by registering a callback on the future.
    */

  f onComplete {
    case Success(i) => println(i)
    case Failure(e) => println(e.getMessage)
  }

  /**
    * This callback is called asynchronously once the future is completed. If the future has already been completed when
    * registering the callback, then the callback may either be executed asynchronously, or sequentially on the same thread.
    */

  /**
    * The most general form of registering a callback is by using the onComplete method, which takes a callback function
    * of type
    *
    * Try[T] => U.
    *
    * The callback is applied to the value of type Success[T] if the future completes successfully,
    * or to a value of type Failure[T] otherwise.
    *
    * The Try[T] is similar to Option[T] or Either[T, S], in that it is a monad potentially holding a value of some type.
    * However, it has been specifically designed to either hold a value or some throwable object.
    */

  def optionDiv(x: Int, y: Int): Option[Int] = {
    try {
      Some(x / y)
    } catch {
      case _: Exception => None
    }
  }

  /**
    * Where an Option[T] could either be a value Some[T] or no value at all None, Try[T] is a Success[T] when it holds a
    * value and otherwise Failure[T], which holds an exception. Failure[T] holds more information than just a plain None
    * by saying why the value is not there. In the same time, you can think of Try[T] as a special version of
    * Either[Throwable, T], specialized for the case when the left value is a Throwable.
    */

  def eitherDiv(x: Int, y: Int): Either[Throwable, Int] = {
    try {
      Right(x / y)
    } catch {
      case e: Exception => Left(e)
    }
  }

  /**
    * The onComplete method has result type Unit, which means invocation of this method cannot be chained. Note that
    * this design is intentional, to avoid suggesting that chained invocations may imply an ordering on the execution
    * of the registered callbacks (callbacks registered on the same future are unordered).
    */

  val fut: Future[Int] = Future.apply(fn(6, 2, 1000))

  fut onComplete {
    case Success(i) => println(s"Success0: $i")
    case Failure(e) => println(s"Failure0: ${e.getMessage}")
  }

  fut onComplete {
    case Success(i) => println(s"Success1: $i")
    case Failure(e) => println(s"Failure1: ${e.getMessage}")
  }

  /**
    * For the sake of completeness the semantics of callbacks are listed here:
    *
    * 1) Registering an onComplete callback on the future ensures that the corresponding closure is invoked after the
    * future is completed, eventually.
    *
    * 2) Registering a callback on the future which is already completed will result in the callback being executed
    * eventually (as implied by 1).
    *
    * 3) In the event that multiple callbacks are registered on the future, the order in which they are executed is not
    * defined. In fact, the callbacks may be executed concurrently with one another. However, a particular
    * ExecutionContext implementation may result in a well-defined order.
    *
    * 4) In the event that some of the callbacks throw an exception, the other callbacks are executed regardless.
    *
    * 5) In the event that some of the callbacks never complete (e.g. the callback contains an infinite loop), the other
    * callbacks may not be executed at all. In these cases, a potentially blocking callback must use the blocking
    * construct.
    *
    * 6) Once executed, the callbacks are removed from the future object, thus being eligible for GC.
    */

  /**
    * Functional Composition and For-Comprehensions
    */

  /**
    * The callback mechanism we have shown is sufficient to chain future results with subsequent computations. However,
    * it is sometimes inconvenient and results in bulky code.
    */

  import scala.util.Random

  def rndNumber(): Int = {
    Thread sleep 500
    val n = Random.nextInt(100)
    if (n <= 50) n else throw new Exception(s"Sorry, but it seems that we ran out of numbers!")
  }

  def isEven(n: Int): Option[Int] = n % 2 match {
    case 0 => Some(n)
    case _ => None
  }

  Future {
    rndNumber()
  } onComplete {
    case Success(n) =>
      isEven(n)
        .fold(throw new Exception(s"$n is not even!!!")) { evenNumber => println(evenNumber) }
    case Failure(e) => println(s"Error, reason: ${ e.getMessage }")
  }

  /**
    *
    * Futures provide combinators which allow a more straightforward composition. One of the basic combinators is map,
    * which, given a future and a mapping function for the value of the future, produces a new future that is completed
    * with the mapped value once the original future is successfully completed.
    *
    * You can reason about mapping futures in the same way you reason about mapping collections.
    */

  Future {
    rndNumber()
  } map { n =>
    isEven(n)
      .fold(throw new Exception(s"$n is not even!!!")) { evenNumber => evenNumber }
  } onComplete {
    case Success(r) => println(r)
    case Failure(e) => println(s"Error, reason: ${ e.getMessage }")
  }

  /**
    * If the original future is completed successfully then the returned future is completed with a mapped value from
    * the original future. If the mapping function throws an exception the future is completed with that exception.
    * If the original future fails with an exception then the returned future also contains the same exception.
    * This exception propagating semantics is present in the rest of the combinators, as well.
    *
    *
    * Now, what if our conditional logic is also a part of some sort of remote API?
    * Then we should also handle it as a Future:
    */

  Future {
    rndNumber()
  } onComplete {
    case Success(n) => Future {
      isEven(n)
        .fold(throw new Exception(s"$n is not even!!!")) { evenNumber => evenNumber }
    } onComplete {
      case Success(res) => println(res)
      case Failure(err) => println(s"Error, reason: ${ err.getMessage }")
    }
    case Failure(e) => println(s"Error, reason: ${ e.getMessage }")
  }

  /**
    * This works, but is inconvenient for two reasons:
    *
    * First, we have to nest the second Future. Imagine that after the second Future is completed we want to do another
    * computation. We would have to repeat this callback pattern again, making the code overly indented,
    * bulky and hard to reason about.
    *
    * Second, the nested Future is not in the scope with the rest of the code - it can only be acted upon from within
    * the callback. This means that other parts of the application do not see the nested Future and cannot register
    * another callback to it.
    *
    * So, we can translate our code into:
    */

  Future {
    rndNumber()
  } flatMap { n =>
    Future {
      isEven(n)
        .fold(throw new Exception(s"$n is not even!!!")) { evenNumber => evenNumber }
    }
  } onComplete {
    case Success(r) => println(r)
    case Failure(e) => println(s"Error, reason: ${ e.getMessage }")
  }

  /**
    * Now, lets translate our code into for-comprehension:
    */

  val result: Future[Int] = for {
      num  <- Future { rndNumber() }
      even <- Future {
        isEven(num)
          .fold(throw new Exception(s"$num is not even!!!")) { evenNumber => evenNumber }
      }
    } yield even

  result onComplete {
    case Success(r) => println(r)
    case Failure(e) => println(s"Error, reason: ${ e.getMessage }")
  }

  /**
    * One of the design goals for futures was to enable their use in for-comprehensions. For this reason, futures also
    * have the flatMap, filter and foreach combinators. The flatMap method takes a function that maps the value to a new
    * future g, and then returns a future which is completed once g is completed.
    */

// throw new Exception(s"Arrrg!")
  val firstNumber: Future[Int] = Future { 2 }
  val secondNumber: Future[Int] = Future { 2 }
  def sum(x: Int, y: Int): Future[Int] = Future { x + y }

  val captainFuture: Future[Int] = for {
    first  <- firstNumber
    second <- secondNumber
    s      <- sum(first, second)
    if s % 2 == 0
  } yield s

  captainFuture onComplete {
    case Success(r) => println(r)
    case Failure(e) => println(s"Error, reason: ${ e.getMessage }")
  }

  /**
    * The code above translates to:
    */

  firstNumber
    .flatMap(
      first => secondNumber
        .flatMap(second => sum(first, second))
        .withFilter(s => s % 2 == 0 )
    ) onComplete {
    case Success(r) => println(r)
    case Failure(e) => println(s"Error, reason: ${ e.getMessage }")
  }

  /**
    * Recovering...
    */

  Future {
    throw new RuntimeException(s"Arrrg!")
  } recover { case _: Exception => 2 } map { n =>
    isEven(n)
      .fold(throw new Exception(s"$n is not even!!!")) { evenNumber => evenNumber }
  } onComplete {
    case Success(r) => println(r)
    case Failure(e) => println(s"Error, reason: ${ e.getMessage }")
  }

  /**
    * The recover combinator creates a new future which holds the same result as the original future if it completed
    * successfully. If it did not then the partial function argument is applied to the Throwable which failed the
    * original future. If it maps the Throwable to some value, then the new future is successfully completed with that
    * value. If the partial function is not defined on that Throwable, then the resulting future is failed with the same
    * Throwable.
    */

  /**
    * Blocking...
    */

  import scala.concurrent.duration._

  val block = Future {
    6 / 3
  }

  import scala.concurrent.Await

  val blockedRes: Int = Await.result(block, 5.seconds)

  println(s"blockedRes: $blockedRes")

  /**
    * this "style" is not recommended :)
    */
}
