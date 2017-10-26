package fpbasics

object StateExample3 extends App {

  /**
    * State is a datatype that encapsulates a stateful computation: S => (S, A). State forms a monad which passes along
    * the states represented by the type S.
    */

  /**
    * Creating a custom type Stack
    */
  type Stack = List[Int]

  import cats.data._

  /**
    * State manipulation function
    */
  def pop() = State[Stack, Int] (
    s => (s.slice(1, s.size), s.head)
  )

  /**
    * State manipulation function
    */
  def push(a: Int) = State[Stack, Unit](
    s => (a :: s, ())
  )

  /**
    * Client calls
    * Now we can construct compound programs by composing the monad.
    */
  def stackManipulation: State[Stack, Int] = for {
    _ <- push(3)
    a <- pop()
    b <- pop()
  } yield b

  /**
    * Initial state
    */
  println(
    stackManipulation.run(List(5, 8, 2, 1)).value
  )
  /**
    * Both push() and pop() are now purely functional, and we were able to eliminate explicitly passing the state object
    * (s1, s2, ...)
    */

}