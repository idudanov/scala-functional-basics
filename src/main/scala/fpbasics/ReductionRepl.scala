package fpbasics

object ReductionRepl {

  /**
    * reduce
    *
    * The reduce function takes an !!!associative!!! binary operator function as a parameter, this function will be applied to collapse
    * elements from the collection. The order for traversing the elements in the collection is called arbitrary tree
    * decompositions. The reduce function has no initial value (accumulator), so it completely relies
    * on the collection that was provided. The accumulator and the next value that will be stored in the accumulator should have
    * the same type.
    *
    * What is binary operator function?
    *
    * f(x,y) = z
    *
    * is a function that takes two inputs... :)
    *
    * What is associative?
    *
    * for multiplication
    *
    * (a * b) * c = a * (b * c)
    *
    * With identity element e = 1
    *
    * e * a = a * e = a
    *
    * for addition
    *
    * (a + b) + c = a + (b + c)
    *
    * With identity element e = 0
    *
    * e + a = a + e = a
    *
    */

  List(0, 1, 2, 3, 4, 5).reduce((bb, b) => bb + b)

  List(0, 1, 2, 3, 4, 5).reduce(_ + _)

  List(0, 1, 2, 3, 4, 5).sum


  /**0 + 1 = 1             3 + 4 = 7
             1 + 2 = 3             7 + 5 = 12
                     3         +           12 = 15 */

  //  Gives error
  List[Int]().reduce((bb, b) => bb + b)

  /**
    * reduceLeft
    *
    * The reduceLeft function takes a binary operator function as a parameter, this function !!!can be associative!!!
    * and will be applied to collapse elements from the collection. The order for traversing the elements in the collection
    * is from left to right and hence the name reduceLeft. The reduceLeft function has no initial value (accumulator),
    * so it completely relies on the collection that was provided. The accumulator and the next value that will be stored in
    * the accumulator should have the same type.
    */

  List(0, 1, 2, 3, 4, 5).reduceLeft((bb, b) => bb + b)

  /**
  0 + 1 = 1
          1 + 2 = 3
                  3 + 3 = 6
                          6 + 4 = 10
                                  10 + 5 = 15
                                           15 */

  //  Gives error
  List[Int]().reduceLeft((bb, b) => bb + b)

  /**
    * reduceRight
    *
    * The reduceRight function takes a binary operator function as a parameter, this function !!!can be associative!!!
    * and will be applied to collapse elements from the collection. The order for traversing the elements in the collection
    * is from right to left and hence the name reduceRight. The reduceRight function has no initial value (accumulator),
    * so it completely relies on the collection that was provided. The accumulator and the next value that will be stored in
    * the accumulator should have the same type.
    */

  List(0, 1, 2, 3, 4, 5).reduceRight((b, bb) => b + bb)

  /**                                 9 = 4 + 5
                             12 = 3 + 9
                    14 = 2 + 12
           15 = 1 + 14
  15 = 0 + 15
  15 */

  //  Gives error
  List[Int]().reduceRight((b, bb) => b + bb)

  /**
    * fold
    *
    * The fold function takes an !!!associative!!! binary operator function as parameter, and it will be applied to collapse
    * elements from the collection. The order for traversing the elements is called arbitrary tree decompositions.
    * The fold function also allows you to specify an initial value (accumulator). The accumulator and the next
    * value that will be stored in the accumulator should have the same type.
    */
  List(1, 2, 3, 4, 5).fold(0)((bb, b) => bb + b)

  /**0 + 1 = 1             0 + 3 = 3           0 + 5 = 5
             1 + 2 = 3             3 + 4 = 7           5
                     3         +           7 = 10      5
                                               10  +   5 = 15 */

  List("zero", "one", "two", "three", "four", "five").fold("")((bb, b) => bb concat b)

//  Uses initial value 0
  List[Int]().fold(0)((bb, b) => bb + b)
//  Uses initial value ""
  List[String]().fold("")((bb, b) => bb concat b)

  /**
    * foldLeft
    *
    * The foldLeft function takes a binary operator function as a parameter, this function !!!can be associative!!!
    * It will be applied to collapse elements from the collection. The order for traversing the elements in the collection
    * is from left to right and hence the name foldLeft. The foldLeft function also allows you to specify an initial value
    * (accumulator). The accumulator and the next value that will be stored in the accumulator can have different types.
    */

  List(1, 2, 3, 4, 5).foldLeft(0)((x, y) => x + y)

  /**0 + 1 = 1
             1 + 2 = 3
                     3 + 3 = 6
                             6 + 4 = 10
                                     10 + 5 = 15
                                              15 */

  //zeroonetwothreefourfive
  List("zero", "one", "two", "three", "four", "five").foldLeft("")((x, y) => x concat y)

  val ba = List(("aubergine", "eggplant"), ("aeroplane", "airplane"), ("beetroot", "beet"))
  val line = "I love eggplant and beet, however, they never serve them in an airplane."

  def strReplaceFoldLeft(line: String, tuples: List[(String, String)]): String = {
    tuples
      .foldLeft(line)(
        (str, tuple) =>
          tuple match {
            case (british, american) => str.replace(american, british)
          }
      )
  }
  //I love aubergine and beetroot, however, they never serve them in an aeroplane.

  /**
    * foldRight
    *
    * The foldRight function takes a binary operator function as a parameter, that function !!!can be associative!!!
    * It will be used to collapse elements from the collection. The order for traversing the elements in the collection
    * is from right to left and hence the name foldRight. The foldRight function also allows you to specify an initial value
    * (accumulator). The accumulator and the next value that will be stored in the accumulator can have different types.
    */

  List(1, 2, 3, 4, 5).foldRight(0)((x, y) => x + y)

  /**
                                     5 = 5 + 0
                             9 = 4 + 5
                    12 = 3 + 9
           14 = 2 + 12
  15 = 1 + 14
  15 */

  List("zero", "one", "two", "three", "four", "five").foldRight(0)((x, y) => y + x.length)

  /**                                                                      4 = (five)4 + 0
                                                             8 = (four)4 + 4
                                             13 = (three)5 + 8
                               16 = (two)3 + 13
                 19 = (one)3 + 16
  23 = (zero)4 + 19
  23 */
}