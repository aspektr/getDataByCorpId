package ru.mos.it.udrvs.dbutil

import cats.effect.{Blocker, ContextShift, IO}
import doobie._
import doobie.util.transactor.Transactor.Aux
import ru.mos.it.udrvs.config.Config

import scala.concurrent.ExecutionContext


object Connection {

  implicit val cs: ContextShift[IO] = IO.contextShift(ExecutionContext.global)



  /**
   *
   * @param vHost vertica host
   * @param vPort vertica port
   * @param dbName database name
   * @return //"jdbc:vertica://VerticaHost:portNumber/databaseName?user=username&password=password"
   */
  def makeUrl(vHost: String, vPort: String, dbName: String): String =
    s"jdbc:vertica://$vHost:$vPort/$dbName"

  //private val url: String = makeUrl(verticaHost, verticaPort, databaseName)

  def getConnection(cfg: Config): Aux[IO, Unit] = {
    Transactor.fromDriverManager[IO](
      cfg.driver, // driver classname
      makeUrl(cfg.host, cfg.port, cfg.dbName), // connect URL (driver-specific)
      cfg.user, // user
      cfg.psw, // password
      Blocker.liftExecutionContext(ExecutionContext.global)
      //val xa: Aux[IO, Unit] =
    )
  }

}