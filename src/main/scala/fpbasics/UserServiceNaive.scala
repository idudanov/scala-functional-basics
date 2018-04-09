package fpbasics

import fpbasics.ValueObject._

class UserServiceNaive(ctx: Context) {

  def get(id: Long): Option[User] = {
    ctx.repository.get(id)
  }

  def find(username: String): Option[User] = {
    ctx.repository
      .find { case (_, user) => username.equals(user.username) }
      .map { case (_, user) => user }
  }
}
