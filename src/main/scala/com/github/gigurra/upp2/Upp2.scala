package com.github.gigurra.upp2

import net.java.games.input.Controller

object Upp2 extends Logging {

  def main(args: Array[String]): Unit = {

    putNativesOnJavaLibPath()

    val allControllers: Array[Controller] = getAllSystemControllers

    val controllerSelectionConfig = readControllerSelectionConfig()

    controllerSelectionConfig foreach println

    val selectedControllers: Seq[MonitoredController] = allControllers.flatMap(c => controllerSelectionConfig.flatMap(_.matches(c)))

    log.info(s"Selected controllers:")
    for (controller <- selectedControllers) {
      log.info(s"  $controller")
    }
  }

}
