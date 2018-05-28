import akka.actor.ActorSystem
import vergesense.VergeSenseClient

object SensorService extends App with AppConfig {

  implicit val system = ActorSystem("", config)

  val mediator = DistributedPubSub(system).mediator
  val vergeSenseApiKey = sys.env.getOrElse("API_KEY", "")
  val vergeSenseUrl = sys.env.getOrElse("URL", "")
  val vergeSenseClient = new VergeSenseClient(vergeSenseUrl, vergeSenseApiKey)

  val sensorActor = system.actorOf(SensorServiceActor.props(vergeSenseClient, mediator))

}
