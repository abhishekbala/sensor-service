package vergesense

import org.json4s.{ShortTypeHints, native}
import org.scalactic.{Bad, ErrorMessage, Good, Or}
import vergesense.VergeSenseRequests.SensorList

import scala.concurrent.Future

case class Sensors(sensors: SensorList)
case class Sensor(id: String, path: String)
case class SensorEvent(timeStamp: String, id: String, count: Int)

object VergeSenseRequests {

  private implicit val serialization = native.Serialization
  private implicit val formats =
    native.Serialization.formats(ShortTypeHints(classOf[Sensor] :: Nil))

  type SensorList = List[Sensor]
  private def deserialize[T](response: String): T = {
    serialization.read[T](response)
  }

  def getAllSensors(client: VergeSenseClient): Future[Or[SensorList, ErrorMessage]] = {
    client.sendRequest("/sensors").map {
      case Good(s: String) => Good(deserialize[Sensors](s).sensors)
      case err@Bad(_) => err
    }
  }

  /*
    Can also implement a method that takes in multiple sensors;
    However, VergeSense API is only GET, and this does not make sense for large # of sensors
  */
  def getHistoryForSensor(client: VergeSenseClient,
                          id: String,
                          start: Long,
                          end: Long): Future[Or[SensorEvent, ErrorMessage]] = {
    client.sendRequest(s"/sensors/history?start=$start?end=$end?ids=$id").map {
      case Good(s: String) => Good(deserialize(s))
      case err@Bad(_) => err
    }
  }

}
