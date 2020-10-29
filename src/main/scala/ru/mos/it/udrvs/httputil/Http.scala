package ru.mos.it.udrvs.httputil

import cats.effect.{ContextShift, IO}
import org.http4s.Method.GET
import org.http4s.client.Client
import org.http4s.client.blaze.BlazeClientBuilder
import org.http4s.client.dsl.io._
import org.http4s.{AuthScheme, Credentials, EntityDecoder, Header, MediaType, Request, Status, Uri}
import org.http4s.headers.{Accept, Authorization}
import org.http4s.circe.jsonOf
import ru.mos.it.udrvs.config.Data.Message
import ru.mos.it.udrvs.config.Data.Message.EmptyMessage
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.ExecutionContext.global
import io.circe.generic.auto._

import scala.concurrent.ExecutionContext


object Http extends LazyLogging{

  implicit val cs: ContextShift[IO] = IO.contextShift(ExecutionContext.global)

  def prepareRequest(url: Uri, authBearer: String, system: String, systemToken: String, legalCorpID: String): IO[Request[IO]] =
    GET(url,
      Authorization(Credentials.Token(AuthScheme.Bearer, authBearer)),
      Accept(MediaType.application.json),
      Header("SYSTEM", system),
      Header("SYSTEMTOKEN", systemToken),
      Header("LEGALCORPID", legalCorpID)
    )

  implicit val legalDecoder: EntityDecoder[IO, Message] = jsonOf[IO, Message]
  def getResponseStatus(client: Client[IO], request: Request[IO]): IO[Status] = client.status(request)

  def getResponseResult(client: Client[IO], status: Status, request: Request[IO]): IO[Message] = status.code match {
    case 200 => client.expect[Message](request)
    case 400 => logger.error(s"${status.code.toString} code: Request problem"); IO(EmptyMessage)
    case 401 => logger.error(s"${status.code.toString} code: Authorization problem, ${status.reason}}"); IO(EmptyMessage)
    case 403 => logger.error(s"${status.code.toString} code: Data problem"); IO(EmptyMessage)
    case 405 => logger.error(s"${status.code.toString} code: Query (logical) problem"); IO(EmptyMessage)
    case _ => logger.error(s"${status.code.toString}"); IO(EmptyMessage)

  }


  def getEntity(request: Request[IO]): IO[Message] = {
    BlazeClientBuilder[IO](global).resource.use { client =>
      for {
        status <- getResponseStatus(client, request)
        entity <- getResponseResult(client, status, request)
      } yield entity
    }
  }

}
