package fpbasics

abstract class SemiGroup[A] {
  def combine(x: A, y: A): A
}