package fpbasics


object StateExample2 extends App {

  /**
    * Creating a custom type Stack
    */
  type Stack = List[Int]

  /**
    * State manipulation function
    */
  def pop(s: Stack): (Stack, Option[Int]) = {
    (s.slice(1, s.size), s.headOption) /* this equals [if (s.nonEmpty) Some(s.head) else None] */
  }

  /**
    * State manipulation function
    */
  def push(s: Stack, a: Int): (Stack, Unit) = (a :: s, ())

  /**
    * Client calls
    */
  def stackManipulation(s: Stack): (Stack, Option[Int]) = {
    val (s1, _) = push(s, 3)
    val (s2, a) = pop(s1)
    pop(s2)

  }

  /**
    * Initial state
    */
  println(
    stackManipulation(List(5, 8, 2, 1))
  )

  /**
    * The passing of the state objects (s1, s2, ...) becomes error-prone boilerplate. The goal is to automate the
    * explicit passing of the states.
    */

  /**
    * Is it possible to describe our state manipulation functions with:
    *
    * S => (S, A)
    *
    * Where S is List[Int] and A is an Int or a Unit ()
    *
    * ???
    */

}