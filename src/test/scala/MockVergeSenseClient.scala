import org.scalactic.Or
import vergesense.VergeSenseClient

import scala.concurrent.{ExecutionContext, Future}

class MockVergeSenseClient(url: String, apiKey: String) extends VergeSenseClient(url: String, apiKey: String) {

  override def sendRequest(path: String)(implicit ec: ExecutionContext): Future[Or[String, Error]] = {



  }

}
