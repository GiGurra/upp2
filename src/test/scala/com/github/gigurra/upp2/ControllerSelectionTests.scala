package com.github.gigurra.upp2

import com.github.strikerx3.jxinput.XInputDevice14
import org.scalatest._

class ControllerSelectionTests extends FlatSpec with Matchers with BeforeAndAfterAll {

  override protected def beforeAll(): Unit = {
    putNativesOnJavaLibPath()
  }

  "Find by name" should "find it" in {
    val long = "y axis, x axis, hat switch, button 0, button 1, button 2, button 3, button 4, button 5, button 6, button 7, button 8, button 9, button 10, button 11, button 12, button 13, button 14, button 15, button 16, button 17, button 18, button 19, button 20, button 21, button 22, button 23, button 24, button 25, button 26, button 27, button 28, button 29, button 30, button 31"
    val pattern = "y axis, x axis, hat switch.*"
    long.matches(pattern) shouldBe true
  }

}
