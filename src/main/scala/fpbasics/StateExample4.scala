package fpbasics

object StateExample4 extends App {

  /**
    * Getting, setting and modifying the state.
    *
    * These are confusing at first. But remember that the State monad encapsulates a pair of; a state transition function
    * and a return value.
    *
    * So State.get keeps the state as is, and returns it.
    *
    * Similarly, State.set(s) in this context means to overwrite the state with s and return ().
    *
    * In its turn, State.modify is a combination of State.get and State.set(s), since it has (f: S => S) as a parameter
    * function.
    *
    * Get
    *
    * def get[S]: State[S, S] = {...}
    *
    *
    * Set
    *
    * def set[S](s: S): State[S, Unit] = {...}
    *
    *
    * Modify
    *
    * def modify[S](f: S => S): State[S, Unit] = {...}
    */

  /**
    * Creating a custom type Stack
    */
  type Stack = List[Int]

  import cats.data._

  /**
    * State manipulation function
    */
  def pop(): State[Stack, Int] = for {
    s <- State.get[Stack]
    _ <- State.set[Stack](s.slice(1, s.size))
  } yield s.head

  /**
    * State manipulation function
    */

  def push(a: Int): State[Stack, Unit] = for {
    _ <- State.modify[Stack](s => a :: s)
  } yield ()

  /**
    * Client calls
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
}