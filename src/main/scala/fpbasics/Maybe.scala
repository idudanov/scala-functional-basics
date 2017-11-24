package fpbasics

object Maybe {

  /** A Maybe factory which creates Just(x) if the argument is not null,
    *  and Nothing if it is null.
    *
    *  @param  x the value
    *  @return   Just(value) if value != null, Nothing if value == null
    */
  def apply[A](x: A): Maybe[A] = if (x == null) empty else pure(x)

  def pure[A](x: A): Maybe[A] = Just(x)

  def empty[A]: Maybe[A] = Nothing
}

//What '+' means :)
//Maybe[+A] means that Maybe can take any class, but if some T is a subclass of A, then Maybe[T]
// is considered to be a subclass of Maybe[A].
sealed abstract class Maybe[+A] extends Product with Serializable {
  self =>

  /** Returns true if the Maybe is Nothing, false otherwise.
    */
  def isEmpty: Boolean

  /** Returns true if the Maybe is an instance of Just, false otherwise.
    */
  def isDefined: Boolean = !isEmpty

  /** Returns the Maybe's value.
    *  @note The Maybe must be nonEmpty.
    *  @throws java.util.NoSuchElementException if the Maybe is empty.
    */
  def get: A

  /** Returns a Just containing the result of applying $f to this Maybe's
    * value if this Maybe is nonempty.
    * Otherwise return Nothing.
    *
    *  @note This is similar to `flatMap` except here,
    *  $f does not need to wrap its result in an Maybe.
    *
    *  @param  f   the function to apply
    *  @see flatMap
    *  @see foreach
    */
  def map[B](f: A => B): Maybe[B] =
    if (isEmpty) Nothing else Just(f(get))

  /** Returns the result of applying $f to this Maybe's value if
    * this Maybe is nonempty.
    * Returns Nothing if this Maybe is empty.
    * Slightly different from `map` in that $f is expected to
    * return an Maybe (which could be Nothing).
    *
    *  @param  f   the function to apply
    *  @see map
    *  @see foreach
    */
  def flatMap[B](f: A => Maybe[B]): Maybe[B] =
    if (isEmpty) Nothing else f(get)

  /** Returns the result of applying $f to this Maybe's
    *  value if the Maybe is nonempty.  Otherwise, evaluates
    *  expression `ifEmpty`.
    *
    *  @note This is equivalent to `Maybe map f getOrElse ifEmpty`.
    *
    *  @param  ifEmpty the expression to evaluate if empty.
    *  @param  f       the function to apply if nonempty.
    */
  def fold[B](ifEmpty: => B)(f: A => B): B =
    if (isEmpty) ifEmpty else f(get)

  /** Returns this Maybe if it is nonempty '''and''' applying the predicate $p to
    * this Maybe's value returns true. Otherwise, return Nothing.
    *
    *  @param  p   the predicate used for testing.
    */
  def filter(p: A => Boolean): Maybe[A] =
    if (isEmpty || p(get)) this else Nothing

  /** Returns this Maybe if it is nonempty '''and''' applying the predicate $p to
    * this Maybe's value returns false. Otherwise, return Nothing.
    *
    *  @param  p   the predicate used for testing.
    */
  def filterNot(p: A => Boolean): Maybe[A] =
    if (isEmpty || !p(get)) this else Nothing

  /** Returns false if the Maybe is Nothing, true otherwise.
    *  @note   Implemented here to avoid the implicit conversion to Iterable.
    */
  def nonEmpty: Boolean = isDefined

  /** Apply the given procedure $f to the Maybe's value,
    *  if it is nonempty. Otherwise, do nothing.
    *
    *  @param  f   the procedure to apply.
    *  @see map
    *  @see flatMap
    */
  def foreach[U](f: A => U): Unit = {
    if (!isEmpty) f(get)
  }

}

final case class Just[+A](x: A) extends Maybe[A] {
  def isEmpty = false
  def get: A = x
}

case object Nothing extends Maybe[Nothing] {
  def isEmpty = true
  def get = throw new NoSuchElementException("Nothing.get")
}