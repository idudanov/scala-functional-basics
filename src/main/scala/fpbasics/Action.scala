package fpbasics

import fpbasics.ValueObject._

import cats.data.Kleisli
import cats.implicits._

object Action {
  def apply[A](f: Context => A): Action[A] = Kleisli[Result, Context, A] { context =>
    try {
      Result.ok(f(context))
    } catch {
      case e: Exception => Result.error(e)
    }
  }

  def fromOption[A](fa: Context => Option[A], fb: Context => Exception): Action[A] = Kleisli[Result, Context, A] { context =>
    Either.fromOption[Exception, A](fa(context), fb(context))
  }
}
