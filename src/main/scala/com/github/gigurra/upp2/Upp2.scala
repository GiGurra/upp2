package com.github.gigurra.upp2

import java.io.{File, FileOutputStream}

import com.github.strikerx3.jxinput.XInputDevice14
import net.java.games.input.Event
import org.apache.commons.io.IOUtils

import scala.util.Try

object Upp2 extends Greeting with App {

  testJInput()

  private def testJInput(): Unit = {

    val event = new Event

    import net.java.games.input.Controller
    import net.java.games.input.ControllerEnvironment

  }

}

trait Greeting {
  lazy val greeting: String = "hello"
}
