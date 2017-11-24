package fpbasics

object StaterExample extends App {

  /**
    * States is a datatype that encapsulates a stateful computation: S => (S, A). State forms a monad which passes along
    * the states represented by the type S.
    */

  /**
    * Creating a custom type Stack
    */
  type Stack = List[Int]

  /**
    * State manipulation function
    */
  def pop() = Stater[Stack, Int] (
    s => (s.slice(1, s.size), s.head)
  )

  /**
    * State manipulation function
    */
  def push(a: Int) = Stater[Stack, Unit](
    s => (a :: s, ())
  )

  /**
    * Client calls
    * Now we can construct compound programs by composing the monad.
    *
    * 5 - Top
    * 8
    * 2
    * 1
    * push(3)
    * =
    * 3 - Top
    * 5
    * 8
    * 2
    * 1
    * pop() 3
    * =
    * 5 - Top
    * 8
    * 2
    * 1
    * pop() 5
    * =
    * 8 - Top
    * 2
    * 1
    */

  def stackManipulation: Stater[Stack, Int] = for {
    _ <- push(3)
    a <- pop()
    b <- pop()
  } yield b

  /**
    * Initial state
    */
  println(
    stackManipulation.run(List(5, 8, 2, 1))
  )
  /**
    * Both push() and pop() are now purely functional, and we were able to eliminate explicitly passing the state object
    * (s1, s2, ...)
    */

}