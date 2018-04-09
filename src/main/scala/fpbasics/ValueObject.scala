package fpbasics

object ValueObject {

  case class User(id: Long, username: String, salary: Double)

  case class Context(repository: Repository, environment: String)


}
