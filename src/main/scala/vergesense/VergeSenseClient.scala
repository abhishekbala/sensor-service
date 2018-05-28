package vergesense

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, StatusCodes, Uri}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import org.scalactic._

import scala.concurrent.{ExecutionContext, Future}

class VergeSenseClient(url: String, apiKey: String)(implicit val system: ActorSystem) {

  final implicit val materializer: ActorMaterializer = ActorMaterializer(ActorMaterializerSettings(system))

  val http = Http(system)

  def sendRequest(path: String)(implicit ec: ExecutionContext): Future[Or[String, Every[String]]] = {

    http.singleRequest(
      HttpRequest(
        uri = Uri("https://" + url + path)
      )
    ).map { httpResponse =>
      Unmarshal(httpResponse.entity).to[String].map { response =>
        httpResponse.status match {
          case StatusCodes.OK => Good(response)
          case _ => Bad(One("Failure: " + response))
        }
      }.recoverWith {
        case _ =>
          httpResponse.discardEntityBytes()
          Future.successful(Bad(One("Error: " + url)))
      }
    }.flatten

  }

}
