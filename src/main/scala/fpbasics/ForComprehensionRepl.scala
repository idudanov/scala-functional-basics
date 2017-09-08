package fpbasics

object ForComprehensionRepl {

  val xs = List("accurate", "fast", "reliable", "rich")

  /**
    * for-comprehension: foreach
    *
    * def foreach(f: (A) ⇒ Unit): Unit
    *
    * The foreach method takes a function as parameter and applies it to every element in the collection.
    * As an example, you can use foreach method to loop through all elements in a collection.
    */

  //final override def foreach[U](f: String => U): Unit
  xs.foreach(x => println(s"$x search"))
  //translates to
  for(x <- xs) {
    println(s"$x search")
  }

  def p(x: String): Unit = println(s"$x search")

  xs.foreach(x => p(x))
  //or
  xs.foreach(p(_))
  //or
  xs.foreach(p)
  //or
  xs foreach p

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
    * def filter(p: (A) ⇒ Boolean): Repr
    *
    * def filterNot(p: (A) ⇒ Boolean): Repr
    *
    * The filter method takes a predicate function as its parameter and uses it to select all the elements in the
    * collection which matches the predicate. It will return a new collection with elements that matched the predicate.
    *
    * The filterNot method is similar to the filter method except that it will create a new collection with elements
    * that do not match the predicate function.
    *
    */

  xs.filter(_.eq("rich")).foreach(p)

  //translates to
  for(x <- xs; if x.eq("rich")) {
    p(x)
  }

  xs.filterNot(_.eq("rich")).foreach(p)

  //translates to
  for(x <- xs; if x.ne("rich")) {
    p(x)
  }

  /**
    * for-comprehension: map
    *
    * def map[B](f: (A) ⇒ B): Traversable[B]
    *
    * Map is applying a function on each element of a collection similar to foreach, with the difference that map
    * returns a new collection as a result. So we can think of the function supplied to map as a
    * transformation function, which we can use to transform a collection of N elements into a new collection of N
    * transformed elements.
    */

  xs.map(x => s"$x search")

  //translates to
  for(x <- xs; y = s"$x search") yield y

  def pUpper(x: String): String = s"$x search".toUpperCase()

  xs.map(pUpper)
  //translates to
  for(x <- xs; y = pUpper(x)) yield y

  //final def map[B](f: Int => B): Option[B]
  safeDiv(6, 0).map(x => x)

  safeDiv(6, 3).map { x => x + 1 }

  /**
    * for-comprehension: flatMap
    *
    * def flatMap[B](f: (A) ⇒ GenTraversableOnce[B]): TraversableOnce[B]
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

  vs.flatMap(x => x.map(y => y * 2))
  //translates to
  for(x <- vs; y <- x; z = y * 2) yield z


  vs.flatMap(x => x)
  //translates to something that called syntactic sugar for composition
  for {
    v <- vs
    r <- v
  } yield r

  /**
    * Map and flatMap functions exist in other classes as well, not just collections. A good example are container
    * classes like Option, Future and Try. And we can implement them for our own classes as well.
    */

  val oxs = List(Some("accurate"), Some("fast"), None, Some("reliable"), None, Some("rich"))

  //def flatMap[B](f: Option[String] => scala.collection.GenTraversableOnce[B]): scala.collection.TraversableOnce[B]
  oxs.flatMap(x => x)

  oxs.flatten.foreach(x => println(s"$x search"))

  def pS(x: Option[String]): Option[String] = x match {
    case Some(y) => Some(s"$y search") case _ => x
  }

  oxs.flatMap(pS)

  /**
    * How to turn this?
    * 1, 2, 3
    * 4, 5, 6
    * 7, 8, 9
    *
    * To that:
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
