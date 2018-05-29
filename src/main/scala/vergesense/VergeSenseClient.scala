package vergesense

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model.{HttpRequest, StatusCodes, Uri}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import org.scalactic._

import scala.concurrent.{ExecutionContext, Future}

class VergeSenseClient(url: String, apiKey: String)(implicit val system: ActorSystem) {

  final implicit val materializer: ActorMaterializer = ActorMaterializer(ActorMaterializerSettings(system))

  val http = Http(system)

  def sendRequest(path: String)(implicit ec: ExecutionContext): Future[Or[String, String]] = {
    http.singleRequest(
      HttpRequest(
        uri = Uri("https://" + url + path))
        .withHeaders(RawHeader("x-api-key", apiKey)
      )
    ).flatMap { httpResponse =>
      httpResponse.status match {
        case StatusCodes.OK =>

          // TODO: Need to match on content type and handle accordingly
/*          httpResponse.entity.contentType match {

          } */
          Unmarshal(httpResponse.entity).to[String].map { response =>
          Good(response)
        }
        case _ => Unmarshal(httpResponse.entity).to[String].map { response =>
          Bad("Request failed: " + response)
        }
      }
    }.recoverWith {
      case error =>
        Future.successful(Bad("Error for " + url + " " + error))
    }
  }.flatten
}