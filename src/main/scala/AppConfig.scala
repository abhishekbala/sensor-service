import com.typesafe.config.ConfigFactory

trait AppConfig {

  lazy implicit val config = {
    if(sys.env.getOrElse("LOCAL_DEV", "") == "true") {
      ConfigFactory.load("local")
    } else {
      ConfigFactory.load()
    }
  }
}
