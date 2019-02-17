package example

import java.io.{File, FileOutputStream}

import com.github.strikerx3.jxinput.XInputDevice14
import net.java.games.input.Event
import org.apache.commons.io.IOUtils

import scala.util.Try

object Hello extends Greeting with App {

  testJInput()

  private def testJInput(): Unit = {

    // Put natives on librarypath
    val tempRoot = System.getProperty("java.io.tmpdir")
    val nativesDir = s"$tempRoot/jinput-natives"
    new File(nativesDir).mkdirs()

    val natives = Seq(
      "jinput-dx8.dll",
      "jinput-dx8_64.dll",
      "jinput-raw.dll",
      "jinput-raw_64.dll",
      "jinput-wintab.dll",
      "libjinput-linux.so",
      "libjinput-linux64.so",
      "libjinput-osx.jnilib"
    )

    for (native <- natives) {
      val inputStream = getClass.getClassLoader.getResourceAsStream(native)
      try {
        val nativeBytes = IOUtils.toByteArray(inputStream)
        val outputStream = new FileOutputStream(s"$nativesDir/$native")
        try {
          IOUtils.write(nativeBytes, outputStream)
        } finally {
          Try(outputStream.close())
        }
      } finally {
        Try(inputStream.close())
      }
    }

    System.setProperty("net.java.games.input.librarypath", nativesDir)

    val event = new Event

    import net.java.games.input.Controller
    import net.java.games.input.ControllerEnvironment

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

      device.getComponent()
    }
  }

  private def testJxInput(): Unit = {
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

trait Greeting {
  lazy val greeting: String = "hello"
}
