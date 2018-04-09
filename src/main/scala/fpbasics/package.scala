import cats.data.Kleisli
import fpbasics.ValueObject._

package object fpbasics {

  type Repository = Map[Long, User]

  type Action[A] = Kleisli[Result, Context, A]

  type Result[A] = Exception Either A

}
