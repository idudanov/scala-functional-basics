package fpbasics

import scala.collection.mutable.ListBuffer

object StateExample1 extends App {

  /**
    * In computer science, a program is described as stateful if it is designed to remember preceding events or user
    * interactions the remembered information is called the state of the system.
    */

  /**
    * Stack is an ordered list of similar data type. Stack is a LIFO structure. (Last in First out). push() function is
    * used to insert new elements into the Stack and pop() function is used to delete an element from the stack. Both
    * insertion and deletion are allowed at only one end of Stack called Top.
    *
    *
    * Dijkstra's Two-Stack Algorithm - The evaluation of arithmetic expressions
    * [[http://www.wisenheimerbrainstorm.com/archive/algorithms/dijkstra-s-two-stack-algorithm]]
    *
    */

  /**
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

  /**
    * Initial state
    */
  private val stack = ListBuffer(5, 8, 2, 1)

  /**
    * State manipulation function
    */
  def pop(): Int = {
    stack.remove(0)
  }

  /**
    * State manipulation function
    */
  def push(a: Int): Unit = {
    stack.prepend(a)
  }

  /**
    * Client calls
    */
  def stackManipulation(): Unit = {
    push(3)
    pop()
    pop()
  }

  stackManipulation()
  println(stack)

}