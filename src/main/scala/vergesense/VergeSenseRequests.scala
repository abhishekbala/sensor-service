package vergesense

import org.json4s.{ShortTypeHints, native}
import org.scalactic.{Bad, ErrorMessage, Good, Or}

import scala.concurrent.Future

case class Sensor(id: String, path: String)
case class SensorEvent(timeStamp: String, id: String, count: Int)

object VergeSenseRequests {

  private implicit val serialization = native.Serialization
  private implicit val formats =
    native.Serialization.formats(ShortTypeHints(classOf[Sensor] :: Nil))

  type SensorList = List[Sensor]
  def deserialize(response: String): SensorList = {
    serialization.read[SensorList](response)
  }

  def getAllSensors(client: VergeSenseClient): Future[Or[SensorList, ErrorMessage]] = {
    client.sendRequest("/sensors").map {
      case Good(s: String) => Good(deserialize(s))
      case err@Bad(_) => err
    }
  }

  def getAllSensorHistory(client: VergeSenseClient,
                          start: Long, end: Long): Future[List[SensorEvent]] = {

  }

}
