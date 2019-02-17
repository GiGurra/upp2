package com.github.gigurra.upp2

import com.github.strikerx3.jxinput.XInputDevice14
import net.java.games.input.ControllerEnvironment
import org.scalatest._

class JoystickLibraryTests extends FlatSpec with Matchers with BeforeAndAfterAll {

  override protected def beforeAll(): Unit = {
    putNativesOnJavaLibPath()
  }

  "Testing JInput" should "do something" in {

    val devices = ControllerEnvironment.getDefaultEnvironment.getControllers

    for (device <- devices) {
      println(s"device: $device")
      println(s"  device.getName: ${device.getName}")
      println(s"  device.getType: ${device.getType}")
      println(s"  device.getPortNumber: ${device.getPortNumber}")
      println(s"  device.getPortType ${device.getPortType}")
      println(s"  device.getComponents: ${device.getComponents.mkString(", ")}")
      println(s"  device.getControllers: ${device.getControllers.mkString(", ")}")
      println()

    }
  }

  "Testing JXInput" should "do something" in {

    val devices = XInputDevice14.getAllDevices

    for (device <- devices) {
      device.poll()
      println(device)
      println(device.getPlayerNum)
      println(device.getCapabilities)
      println(device.getGamepadCapabilities)
      println(device.getComponents)
      val comps = device.getComponents
      println(comps.getAxes)
      println(comps.getButtons)
      println()
    }
  }

}
