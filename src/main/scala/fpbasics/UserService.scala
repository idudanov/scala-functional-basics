package fpbasics

import fpbasics.ValueObject._

trait UserService {

  def get(id: Long): Action[User] = Action.fromOption( context =>
    context.repository.get(id)
    , ctx => new RuntimeException(s"Unable to get a user with id: $id, in ${ctx.environment} environment")
  )

  def find(username: String): Action[User] = Action.fromOption( context =>
    context.repository
      .find { case (_, user) => username.equals(user.username) }
      .map { case (_, user) => user }
    , ctx => new RuntimeException(s"Unable to find a user with username: $username, in ${ctx.environment} environment")
  )
}