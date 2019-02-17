package com.github.gigurra

import java.io.{File, FileOutputStream}
import java.nio.charset.StandardCharsets

import io.circe.Json
import net.java.games.input.{Controller, ControllerEnvironment}
import org.apache.commons.io.{FileUtils, IOUtils}

import scala.util.Try

package object upp2 extends Logging {

  def putNativesOnJavaLibPath(): Unit = {

    log.info(s"Putting native joystick libraries on java lib path")

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

  }

  def getAllSystemControllers: Array[Controller] = {
    ControllerEnvironment.getDefaultEnvironment.getControllers
  }

  def readControllerSelectionConfig(): Seq[ControllerSelectionFilter] = {

    import io.circe.yaml.parser

    val configString = FileUtils.readFileToString(new File("profile.upp"), StandardCharsets.UTF_8)

    val config: Json = parser.parse(configString) match {
      case Right(r) => r
      case Left(err) => throw err
    }

    val devices: Seq[Json] =
      config.\\("devices")
        .headOption.getOrElse(throw new IllegalArgumentException("Missing config array 'devices'"))
        .asArray.getOrElse(throw new IllegalArgumentException("Config 'devices' must be an array of objects"))

    devices.map(ControllerSelectionFilter.apply)
  }
}
