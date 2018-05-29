import SensorManagementActor.{Get, Put}
import akka.actor.ActorSystem
import akka.testkit.TestKit
import akka.pattern.ask
import com.typesafe.config.ConfigFactory
import org.scalatest.{AsyncFunSpecLike, Matchers}
import vergesense.Sensor
import vergesense.VergeSenseRequests.SensorList

class SensorManagementActorTests extends TestKit(ActorSystem(
  "SensorManagementActorTests", ConfigFactory.defaultApplication()))
  with AsyncFunSpecLike with Matchers {

  describe("Given a sensor management actor") {

    it("Get a list of sensors after putting") {
      val sensors = List(Sensor("id1", "1"), Sensor("id2", "1"))

      val sensorManagementActor = system.actorOf(SensorManagementActor.actorProps())
      sensorManagementActor ! Put(sensors)
      (sensorManagementActor ? Get()).map { response =>
        response.asInstanceOf[SensorList] shouldBe sensors
      }

    }

  }

}
