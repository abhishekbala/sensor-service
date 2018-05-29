import SensorManagementActor.{Get, Put}
import akka.actor.{Actor, ActorRef, Props}
import org.scalactic.Good
import vergesense.VergeSenseRequests.SensorList
import vergesense.{VergeSenseClient, VergeSenseRequests}

import scala.concurrent.duration._

case class GetAllSensors()
case class FetchAndUpdate()

object SensorServiceActor {

  def props(vergeSenseClient: VergeSenseClient,
            sensorManager: SensorManager,
            mediator: ActorRef): Props = {
    Props(new SensorServiceActor(vergeSenseClient, sensorManager, mediator))
  }

}

class SensorServiceActor(vergeSenseClient: VergeSenseClient,
                         sensorManager: SensorManager,
                         mediator: ActorRef) extends Actor {

  implicit val ec = context.dispatcher

  private var lastUpdateTime = System.currentTimeMillis()

  context.system.scheduler.scheduleOnce(0 minute, self, GetAllSensors)

  context.system.scheduler.schedule(1 minute, 5 minutes, self, FetchAndUpdate)

  override def receive: Receive = {
    case GetAllSensors =>
      VergeSenseRequests.getAllSensors(vergeSenseClient).map {
        case Good(sensors: SensorList) => sensorManager.put(sensors)
        case _ =>
      }
    case FetchAndUpdate =>
      val newUpdateTime = System.currentTimeMillis()
      sensorManager.get().map(_.map { sensor =>
        VergeSenseRequests.getHistoryForSensor(vergeSenseClient,
          sensor.id, lastUpdateTime, newUpdateTime)
      })
      val sensors = VergeSenseRequests.getHistoryForSensor(vergeSenseClient,
        lastUpdateTime, newUpdateTime)


  }

}