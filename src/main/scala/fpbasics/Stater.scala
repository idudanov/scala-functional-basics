package fpbasics

case class Stater[S, A](run: S => (S, A)) {

  def map[B](ab: A => B): Stater[S, B] =
    Stater(s => run(s) match {
      case (s, a) => (s, ab(a))
    })

  def flatMap[B](afb: A => Stater[S, B]): Stater[S, B] =
    Stater(s => run(s) match {
      case (s, a) => afb(a).run(s)
    })

}

object Stater {

  def get[S]: Stater[S, S] = Stater(s => (s, s))

  def set[S](s: S): Stater[S, Unit] = Stater(_ => (s, ()))

  def modify[S](ss: S => S): Stater[S, Unit] =
    for {
      s <- get[S]
      _ <- set(ss(s))
    } yield ()
}
