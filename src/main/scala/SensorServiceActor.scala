import akka.actor.{Actor, ActorRef, Props}

import scala.concurrent.duration._
import vergesense.{VergeSenseClient, VergeSenseRequests}

case class FetchAndUpdate()

object SensorServiceActor {

  def props(vergeSenseClient: VergeSenseClient,
            sensorManager: SensorManagementActor,
            mediator: ActorRef): Props = {
    Props(new SensorServiceActor(vergeSenseClient, sensorManager, mediator))
  }

}

class SensorServiceActor(vergeSenseClient: VergeSenseClient,
                         sensorManager: ActorRef,
                         mediator: ActorRef) extends Actor {

  private var lastUpdateTime = System.currentTimeMillis()

  context.system.scheduler.schedule(0 second, 5 minutes, self, FetchAndUpdate)

  override def receive: Receive = {
    case FetchAndUpdate =>
      val newUpdateTime = System.currentTimeMillis()
      val sensors = VergeSenseRequests.getAllSensorHistory(vergeSenseClient,
        lastUpdateTime, newUpdateTime)

  }

}