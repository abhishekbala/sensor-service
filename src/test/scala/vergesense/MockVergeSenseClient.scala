package vergesense

import org.scalactic.{Good, Or}

import scala.concurrent.{ExecutionContext, Future}

// Need to add sufficient test cases
// TODO: Refactor test cases for client out of code - conf file
class MockVergeSenseClient(url: String, apiKey: String) extends VergeSenseClient(url: String, apiKey: String) {

  override def sendRequest(path: String)(implicit ec: ExecutionContext): Future[Or[String, Error]] = Future.successful {
    case url: String if url.endsWith("sensors") =>
      Good(
        """
          |{
          |  "sensors": [
          |    {
          |      "id": "sensor1",
          |      "count": 5,
          |      "timestamp": "2018-01-10T23:30:28Z"
          |    },
          |    {
          |      "id": "sensor2",
          |      "count": 3,
          |      "timestamp": "2018-01-10T23:30:28Z"
          |    }
          |  ]
          |}
        """.stripMargin)
  }

}
