package fpbasics

import fpbasics.ValueObject._

class ContextServiceNaive {

  def getContext: Context = {
    val repository = Map(
      1L -> User(1L, "gquagmire", 100000.0)
      , 2L -> User(2L, "ltaranga", 250000.0)
      , 3L -> User(3L, "zbrannigan", 150000.0)
    )
    Context(repository, "production")
  }
}
