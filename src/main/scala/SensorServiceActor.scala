import akka.actor.{Actor, ActorRef, Props}
import vergesense.VergeSenseClient

object SensorServiceActor {

  def props(vergeSenseClient: VergeSenseClient,
            mediator: ActorRef): Props = {
    Props(new SensorServiceActor(vergeSenseClient, mediator))
  }

}

class SensorServiceActor(vergeSenseClient: VergeSenseClient,
                         mediator: ActorRef) extends Actor {

//  context.system.scheduler.schedule(0, 5 minutes)(
//    VergeSenseFunctions.fetchData(vergeSenseClient)
//  )

  override def receive: Receive = ???

}
