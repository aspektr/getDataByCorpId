package ru.mos.it.udrvs

import cats.effect._
import cats.implicits._
import com.typesafe.scalalogging.LazyLogging
import doobie._
import doobie.implicits._
import org.http4s.Uri
import ru.mos.it.udrvs.config.Config
import ru.mos.it.udrvs.config.ConfigBuilder.parser
import ru.mos.it.udrvs.dbutil.Connection.getConnection
import ru.mos.it.udrvs.dbutil.Sql.{createDictSql, insertMany, insertSql, makeSql}
import ru.mos.it.udrvs.httputil.Http.{getEntity, prepareRequest}
import scopt.OParser

import scala.concurrent.ExecutionContext

object MainApp extends IOApp with LazyLogging{

  implicit val cs: ContextShift[IO] = IO.contextShift(ExecutionContext.global)
  implicit val han: LogHandler = LogHandler.jdkLogHandler

  def run(args: List[String]): IO[ExitCode] = {

    val startTime = System.nanoTime

    OParser.parse(parser, args, Config()) match {
      case Some(config) =>
        logger.info(s"Starting the data enrichment process...")

        //define some basics stuff
        val xa = getConnection(config)
        val dictSql = createDictSql(config)
        val sql = makeSql(config)
        val restUri = Uri.unsafeFromString(config.restApi)

        //do the main things
        val messages = for {
          _ <- dictSql.update.run.transact(xa)
          //create dict if not exist
          sqlResult <- sql.query[String].to[List].transact(xa)
          //run sql query and fetch result
          _ = logger.info(s"${sqlResult.length} ${config.field} from ${config.schema}.${config.table} is recieved")
          requests <- sqlResult.take(100).parTraverse(_ =>
            prepareRequest(restUri, config.authBearer, config.system, config.token, "FA561272-DC06-11E6-BC23-2C600C28EE9D"))
          //prepare requests
          msgs <- requests.parTraverse(getEntity)
          //run requests and get responses from api
          numInsertedRows <- insertMany(msgs, insertSql(config)).transact(xa)
          //insert reuslt into dict table
          _ = logger.info(s"Total: ${numInsertedRows.toString} rows have been inserted")
          _ = logger.info(s"Total time: ${(System.nanoTime - startTime)/1e9d}s")
        } yield msgs

        messages.as(ExitCode.Success)

      case _ => IO(logger.error("arguments are bad")).as(ExitCode.Error)
      // arguments are bad, error message will have been displayed
    }




  }


}
