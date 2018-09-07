package fpbasics

object ForComprehensionRepl {

  val xs = List("accurate", "fast", "reliable", "rich")

  /**
    * foreach
    * filter and filterNot
    * map
    * flatMap
    */


  /**
    * for-comprehension: foreach
    *
    * def foreach(f: (A) ⇒ Unit): Unit
    *
    * The foreach method takes a function as parameter and applies it to every element in the collection(monad).
    * As an example, you can use foreach method to loop through all elements in a collection.
    */

  //final override def foreach[U](f: String => U): Unit
  xs.foreach(x => println(s"$x search"))
  //translates to
  for(x <- xs) {
    println(s"$x search")
  }

  def f(x: String): Unit = println(s"$x search")

  xs.foreach(x => f(x))
  //or
  xs.foreach(f(_))
  //or
  xs.foreach(f)
  //or
  xs foreach f

  //final def foreach[U](f: Nothing => U): Unit
  //None.foreach(x => println(s"$x search"))

  //final def foreach[U](f: String => U): Unit
  Some("reliable").foreach(x => println(s"$x search"))

  //Lets create a function
  def safeDiv(x: Int, y: Int): Option[Int] = try {
    Some(x / y)
  } catch { case _: Exception => None }

  safeDiv(6, 3).foreach(println)

  //translates to, that is x <- Some(2)
  for(x <- safeDiv(6, 3)) {
    println(x)
  }

  safeDiv(6, 0).foreach(println)

  //translates to, that is x <- None
  for(x <- safeDiv(6, 0)) {
    println(x)
  }

  /**
    * for-comprehension: filter and filterNot
    *
    * def filter(p: (A) ⇒ Boolean): F[B]
    *
    * def filterNot(p: (A) ⇒ Boolean): F[B]
    *
    * The filter method takes a predicate function as its parameter and uses it to select all the elements in the
    * collection(monad) which matches the predicate. It will return a new collection with elements that matched the predicate.
    *
    * The filterNot method is similar to the filter method except that it will create a new collection with elements
    * that do not match the predicate function.
    *
    */

  xs.filter(_.eq("rich")).foreach(f)

  //translates to
  for(x <- xs; if x.eq("rich")) {
    f(x)
  }

  xs.filterNot(_.eq("rich")).foreach(f)

  //translates to
  for(x <- xs; if x.ne("rich")) {
    f(x)
  }

  /**
    * Now with Option!
    */

  Option("poor").filter(_.eq("rich")).foreach(f)

  Option("rich").filterNot(x => x.eq("poor")).foreach(f)

  Option(null).filter(_.eq("rich")).foreach(f)

  //translates to
  //for(int i = 0; i <= something.length; i++)
  for(x <- Option(null); if x.eq("rich")) {
    f(x)
  }

  /**
    * for-comprehension: map
    *
    * def map[B](f: (A) ⇒ B): F[B]
    *
    * Map is applying a function on each element of a collection(monad) similar to foreach, with the difference that map
    * returns a new collection as a result. So we can think of the function supplied to map as a
    * transformation function, which we can use to transform a collection of N elements into a new collection of N
    * transformed elements.
    */

  xs.map(x => s"$x search")

  //translates to
  for(x <- xs; y = s"$x search") yield y

  def fUpper(x: String): String = s"$x search".toUpperCase()

  xs.map(fUpper)
  //translates to
  for(x <- xs; y = fUpper(x)) yield y

  //final def map[B](f: Int => B): Option[B]
  safeDiv(6, 0).map(x => x)

  safeDiv(6, -3).map( x => math.sqrt( math.pow(x, 2.0) ) )

  /**
    * for-comprehension: flatMap
    *
    * def flatMap[B](f: (A) ⇒ F[B]): F[B]
    *
    * The flatMap method takes a predicate function, applies it to every element in the collection. It then returns a
    * new collection by using the elements returned by the predicate function.
    *
    * The flatMap is similar to map, with the requirement that the elements of the collection are collections as well, and
    * with an additional step of flattening the elements of a collection after executing the transformation function.
    *
    * Flatten is a standard operation of converting a lists of lists into one list with all the elements.
    * For all collection types.
    */

  val vs = List(Vector(1, 2, 5), Vector(4, 8, 7), Vector())

  for(vector <- vs; dimension <- vector; result = dimension * 2) yield result

//  def flatMap[B](f: Vector[A] => Vector[B]): List[B]
  vs.flatMap(vector => vector.map(dimension => dimension * 2))

  //translates to something that called syntactic sugar for composition
  for {
    vector    <- vs
    dimension <- vector
  } yield dimension * 2


  //flattening the collection
  vs.flatMap(x => x)

  //translates to
  for {
    v <- vs
    r <- v
  } yield r

  /**
    * Map and flatMap functions exist in other classes(monads) as well, not just collections. A good example are container
    * classes like Option, Future and Try. And we can implement them for our own classes as well.
    */

  /**
    *  Because of the flattening operation flatMap can change the collection size so that it can be used as a filter.
    */
  val newXs: List[String] = xs ++ List("poor")

  //def flatMap[B](f: A => List[B]): List[B]
  newXs.flatMap(x => x.eq("poor") match { case true => List() case _ => List(x) } )

  /**
    * flatMap can be seen as a glue for functional composition!
    */

  Option(2).flatMap(two => Option(two * two))

  //translates to
  for {
    two    <- Option(2)
    square <- Option(two * two)
  } yield square

  /**
    * Lets stop here for a moment.
    */

  /**
    * How to turn these three lists?
    * 1, 2, 3
    *
    * 4, 5, 6
    *
    * 7, 8, 9
    *
    * to that output:
    * 1, 4, 7
    * 1, 4, 8
    * 1, 4, 9
    * 1, 5, 7
    * 1, 5, 8
    * 1, 5, 9
    * 1, 6, 7
    * 1, 6, 8
    * 1, 6, 9
    * 2, 4, 7
    * 2, 4, 8
    * 2, 4, 9
    * 2, 5, 7
    * 2, 5, 8
    * 2, 5, 9
    * 2, 6, 7
    * 2, 6, 8
    * 2, 6, 9
    * 3, 4, 7
    * 3, 4, 8
    * 3, 4, 9
    * 3, 5, 7
    * 3, 5, 8
    * 3, 5, 9
    * 3, 6, 7
    * 3, 6, 8
    * 3, 6, 9
    */
}
