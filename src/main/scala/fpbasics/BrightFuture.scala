package fpbasics

import scala.concurrent.{Future}

//import scala.util.{Failure, Success}

import scala.concurrent.ExecutionContext.Implicits.global

object BrightFuture {

  /**
    * This function will be called in a test with some random integer. This integer will be in a string form. Convert
    * that string into an integer, do that in a Future.
    *
    * Some strings cannot be converted to integers, recover from this situation using 0 as a default value.
    */
  def convertToIntLiftToFuture(s: String): Future[Int] = {
    ???
  }

/**
  * This function will be called in a test with some string message. Register a callback an that Future[T] print the
  * message in a case of success and/or failure
  */
  def callBack(f: Future[String]): Unit = {
    ???
  }

  /**
    * Old dog new tricks...
    * This function will be called in a test with Future["Seek is a fast and reliable job search"]
    * Remove all vowels from this sentence above, do NOT complete the Future.
    * Return Future[T] back to the caller.
    * The answer should be:
    * Future["Sk s  fst nd rlbl jb srch"]
    */
  //List("a", "e", "i", "o", "u").fold(x)((x, vowel) => x.replaceAll(vowel, ""))
  def removeVowels(line: Future[String]): Future[String] = {
    ???
  }

  def findIntersec(setA: List[Int], setB: List[Int]): Future[List[Int]] = {
    Future{ setA.intersect(setB) }
  }

  /**
    * This function will be called in a test with a = List(1, 4, 3), b = List(1, 5, 3)
    * Find intersection of two sets, do NOT complete the Future. Try to unpack a and b independently (use flatMap)
    * Return Future[T] back to the caller.
    */
  //List(1, 4, 3).intersect(List(1, 5, 3)) == List(1, 3)
  def intersection(a: Future[List[Int]], b: Future[List[Int]]): Future[List[Int]] = {
    ???
  }

}