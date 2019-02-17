package example

import com.github.strikerx3.jxinput.XInputDevice14
import org.scalatest._

class HelloSpec extends FlatSpec with Matchers {

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
