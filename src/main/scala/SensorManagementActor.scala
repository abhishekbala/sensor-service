import SensorManagementActor.{Get, Put}
import akka.actor.{Actor, Props}
import vergesense.Sensor

// Not a full cache implementation (with library), just actor managing local cache

object SensorManagementActor {

  def actorProps(): Props = {
    Props(new SensorManagementActor())
  }

  case class Get()
  case class Put(sensors: List[Sensor])

}

// This actor can maintain the list of users that our system has knowledge of
// Example - a sensor's zone/path changes, this is sensor history
class SensorManagementActor() extends Actor {

  private var set: Set[Sensor] = Set[Sensor]()

  override def receive: Receive = {
    case Get() => sender ! set.toList
    case Put(sensors) => set = set.union(sensors.toSet)

  }

}
