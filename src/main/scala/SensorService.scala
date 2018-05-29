import akka.actor.ActorSystem
import vergesense.VergeSenseClient

object SensorService extends App with AppConfig {

  implicit val system = ActorSystem("", config)

  val mediator = DistributedPubSub(system).mediator // TODO: import not working
  val vergeSenseApiKey = sys.env.getOrElse("API_KEY", "")
  val vergeSenseUrl = sys.env.getOrElse("URL", "")
  val vergeSenseClient = new VergeSenseClient(vergeSenseUrl, vergeSenseApiKey)
  val sensorManagementActor = SensorManagementActor.actorProps()

  val sensorActor = system.actorOf(SensorServiceActor.props(vergeSenseClient,
    sensorManagementActor, mediator))

}
