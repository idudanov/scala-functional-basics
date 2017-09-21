package fpbasics

abstract class Monoid[A] extends SemiGroup[A] {
  def empty: A
}
