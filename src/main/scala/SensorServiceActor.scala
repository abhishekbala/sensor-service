import akka.actor.{Actor, ActorRef, Props}

import scala.concurrent.duration._
import vergesense.{VergeSenseClient, VergeSenseRequests}

case class GetAllSensors()
case class FetchAndUpdate()

object SensorServiceActor {

  def props(vergeSenseClient: VergeSenseClient,
            sensorManager: SensorManagementActor,
            mediator: ActorRef): Props = {
    Props(new SensorServiceActor(vergeSenseClient, sensorManager, mediator))
  }

}

class SensorServiceActor(vergeSenseClient: VergeSenseClient,
                         sensorManager: SensorManagementActor,
                         mediator: ActorRef) extends Actor {

  implicit val ec = context.dispatcher

  private var lastUpdateTime = System.currentTimeMillis()

  context.system.scheduler.scheduleOnce(0 minute, self, GetAllSensors)

  context.system.scheduler.schedule(1 minute, 5 minutes, self, FetchAndUpdate)

  override def receive: Receive = {
    case GetAllSensors =>
      val sensors = VergeSenseRequests.getAllSensors(vergeSenseClient)
    case FetchAndUpdate =>
      val newUpdateTime = System.currentTimeMillis()
      val sensors = VergeSenseRequests.getAllSensorHistory(vergeSenseClient,
        lastUpdateTime, newUpdateTime)


  }

}