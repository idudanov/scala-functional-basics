package fpbasics

object KleisliExample1 extends App {

  /**
    * Encapsulation in OOP is a mechanism of wrapping the data (variables) and code acting on the data (methods)
    * together as a single unit. In encapsulation, the variables of a class will be hidden from other classes,
    * and can be accessed only through the methods of their current class.
    *
    * Therefore, it is also known as data hiding.
   */

  val contextService = new ContextServiceNaive

  val userService = new UserServiceNaive(contextService.getContext)


  /**
    * !!!The method signature does not say anything about the possible catastrophe!!!
    * If one of the users does not exist in the repository an exception will be thrown.
    *
    * Another typical solution is to return with null instead of throwing exception. Both of the solutions forces the
    * caller to open the method to obtain more knowledge about the implementation.
    *
    * contextService and userService are injected into the code using encapsulation technique
    */
  def userPipeline: Double = {

    val user1 = userService.get(24L)

    if(user1.isDefined) {
      val user2 = userService.find("gquagmire")

      if(user2.isDefined) {
        math.abs(user1.get.salary - user2.get.salary)
      } else {
        throw new RuntimeException(s"Unable to find a user")
      }
    } else {
      throw new RuntimeException(s"Unable to get a user")
    }
  }

  println(userPipeline)

}