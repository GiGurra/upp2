package com.github.gigurra.upp2

import net.java.games.input.{Component, Controller}

object Upp2 extends Logging {

  def main(args: Array[String]): Unit = {

    putNativesOnJavaLibPath()

    val allControllers: Array[Controller] = getAllSystemControllers

    val controllerSelectionConfig = readControllerSelectionConfig()

    log.info(s"Controller selection filters:")
    for (filter <- controllerSelectionConfig) {
      log.info(s"  $filter")
    }

    val selectedControllers: Seq[MonitoredController] = allControllers.flatMap(c => controllerSelectionConfig.flatMap(_.matches(c)))

    log.info(s"Selected controllers:")
    for (controller <- selectedControllers) {
      log.info(s"  $controller")
    }

    val ascii = new AnsiPrinter

    while (true) {

      ascii.clearScreen()
      for (controller <- selectedControllers) {
        controller.undelying.poll()
        ascii.text(s"$controller:").endl()

        def isPressed(component: Component): Boolean = {
          math.abs(component.getPollData) > 1e-5
        }

        for (component <- controller.undelying.getComponents) {
          if (isPressed(component)) {
            ascii.text(s"  ${component.getName}: ${component.getIdentifier}: ${component.getPollData}").endl()
          }
        }

        ascii.endl()
      }
      ascii.endl()

      Thread.sleep(150)
    }

    /*
    ascii.startOfLine().clearLine().text("first line").right(5).text("hook").text("123")
    Thread.sleep(1000)
    ascii.left().text("3").endl()
    Thread.sleep(1000)
    ascii.text("second line").endl()
    Thread.sleep(1000)
    ascii.up()
    ascii.text("12345678904").endl()
    Thread.sleep(1000)
    ascii.text("disappearing line").endl()
    Thread.sleep(1000)
    ascii.up().clearLine().endl()
    ascii.up(3).text("wawa").down(3)
*/
  }

  class AnsiPrinter(printer: String => Unit = print) {

    def apply(txt: String): this.type = {
      printer.apply(txt)
      this
    }

    def text(txt: String): this.type = {
      apply(txt)
    }

    def up(nLines: Int = 1): this.type = {
      apply(s"\u001b[${nLines}A")
    }

    def down(nLines: Int = 1): this.type = {
      apply(s"\u001b[${nLines}B")
    }

    def right(nLines: Int = 1): this.type = {
      apply(s"\u001b[${nLines}C")
    }

    def left(nLines: Int = 1): this.type = {
      apply(s"\u001b[${nLines}D")
    }

    def endl(): this.type = {
      apply("\n")
    }

    def clearLineAfter(): this.type = {
      apply("\u001b[0K")
    }

    def clearLineBefore(): this.type = {
      apply("\u001b[1K")
    }

    def clearLine(): this.type = {
      apply("\u001b[2K")
    }

    def clearScreenAfter(): this.type = {
      apply("\u001b[0J")
    }

    def clearScreenBefore(): this.type = {
      apply("\u001b[1J")
    }

    def clearScreen(): this.type = {
      apply("\u001b[2J")
    }

    def startOfLine(): this.type = {
      apply(s"\u001b[0G")
    }
  }

}
