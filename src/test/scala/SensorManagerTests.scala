import akka.actor.ActorSystem
import akka.testkit.TestKit
import com.typesafe.config.ConfigFactory
import org.scalatest.{AsyncFunSpecLike, Matchers}
import vergesense.Sensor

class SensorManagerTests extends TestKit(ActorSystem(
  "SensorManagementActorTests", ConfigFactory.defaultApplication()))
  with AsyncFunSpecLike with Matchers {

  describe("Given a sensor manager") {

    it("Get a list of sensors after putting") {
      val sensors = List(Sensor("id1", "1"), Sensor("id2", "1"))
      val sensorManagementActor = system.actorOf(SensorManagementActor.actorProps())
      val sensorManager = new SensorManager(sensorManagementActor)
      sensorManager.put(sensors)
      sensorManager.get().map { response =>
        response shouldBe sensors
      }

    }

  }

}
