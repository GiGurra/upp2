package com.github.gigurra.upp2

import org.slf4j.Logger
import org.slf4j.LoggerFactory

trait Logging {
  protected lazy val log: Logger = LoggerFactory.getLogger(getClass)
}
