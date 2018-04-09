package fpbasics

import cats.data.State

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
  type Stack[A] = State[List[Int], A]

  import cats.data._

  /**
    * State manipulation function
    */
  def pop: Stack[Option[Int]] = for {
    s <- State.get
    _ <- State.set(s.slice(1, s.size))
  } yield s.headOption

  /**
    * State manipulation function
    */

  def push(a: Int): Stack[Unit] = State(s => (a :: s, ()))

  /**
    * Client calls
    */
  def stackManipulation: Stack[Option[Int]] = for {
    _ <- push(3)
    _ <- pop
    b <- pop
  } yield b

  /**
    * Initial state
    */
  println(
    stackManipulation.run(List(5, 8, 2, 1)).value
  )
}