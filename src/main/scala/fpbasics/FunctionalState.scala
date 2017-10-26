package fpbasics

import cats.data.State

object FunctionalState extends {

  case class Job(id: Long, date: Long)
  type Jobs = List[Job]

  /**
    * Create a function that retrieves a job item [Job] from the state List[Job]
    */
  def get(a: Job): State[Jobs, Option[Job]] = ???

  /**
    * Create a function that adds a job item [Job] to the state List[Job]
    */
  def add(a: Job): State[Jobs, Unit] = ???

  /**
    * Create a function that removes a job item [Job] from the state List[Job]
    */
  def remove(a: Job): State[Jobs, Unit] = ???

  /**
    * This exercise is for advanced students (functional kids :)) )
    *
    * If you feel like you do not want to do it, please proceed to the next one...
    *
    * Create a function that only keeps a unique set of jobs. There should be no duplicate IDs in the state List[Job].
    * If a client is trying to add an ID that already exists in the state List[Job] that this function should compare two
    * records using date field and keep the the most current one.
    *
    * If
    *
    * State(List(Job(1L, 100L)))
    *
    * addUnique(Job(1L, 50L))
    *
    * 100L > 50L
    *
    * Then
    *
    * State(List(Job(1L, 100L)))
    *
    * *****************************
    *
    * If
    *
    * State(List(Job(1L, 100L)))
    *
    * addUnique(Job(1L, 200L))
    *
    * 100L < 200L
    *
    * Then
    *
    * State(List(Job(1L, 200L)))
    */
  def addUnique(a: Job): State[Jobs, Unit] = ???

  /**
    * Create a function that will use a for-comprehension to do the following:
    * Add:
    * Job(1L, 50L)
    * Job(2L, 100L)
    * Job(3L, 150L)
    * Remove:
    * Job(4L, 200L)
    * Get:
    * Job(1L, 50L)
    *
    * The order in not important.
    *
    * As you may notes this function should be called with an initial state of State[List(Job(4L, 200L))]
    *
    * This function goes together with stateResult function below
    */

  def jobsManipulation: State[Jobs, Option[Job]] = ???

  /**
    * Create a function that will set an initial state of State[List(Job(4L, 200L))]
    * Then call jobsManipulation for the result. Use function run(initial state).value to execute the state
    */
  def stateResult: (Jobs, Option[Job]) = ???

}
