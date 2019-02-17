package com.github.gigurra

import java.io.{File, FileOutputStream}

import org.apache.commons.io.IOUtils

import scala.util.Try

package object upp2 {

  def putNativesOnJavaLibPath(): Unit = {

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
}
