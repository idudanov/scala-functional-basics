package fpbasics

/**
  * This will act as a pure function A => M[A], so you do not have to implement it
  */
case class Writer(value: Int, diary: String) {

  /**
    * Implement flatMap function for the Writer monad
    *
    * This code will be called from a test with the data below:
    *
    * Writer(2, "")
    *  .flatMap(a => Writer(a * 5, "times five"))
    *  .flatMap(b => Writer(b + 3, "plus three"))
    *
    *  OR
    *
    * for {
    *   a <- Writer(2, "")
    *   b <- Writer(a * 5, "times five")
    *   c <- Writer(b + 3, "plus three")
    * } yield c
    *
    * Should result in Writer(13, "times fiveplus three")
    *
    */
  def flatMap(f: Int => Writer): Writer = {
    ???
  }

  /**
    * This should serve as a clue of how flatMap function should be implemented
    */
  def map(f: Int => Int) = Writer(f(value), diary)
}
