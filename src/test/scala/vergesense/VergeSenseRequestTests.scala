package vergesense

import org.scalactic.Good
import org.scalatest.{AsyncFunSpecLike, Matchers}

class VergeSenseRequestTests extends AsyncFunSpecLike with Matchers {

  describe("make a request with a mock client") {

    val client = new MockVergeSenseClient("", "")
    VergeSenseRequests.getAllSensors(client).map { resp =>
      resp shouldBe Good(List(Sensor("sensor1", "1")))
    }

  }

}
