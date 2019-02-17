package com.github.gigurra.upp2

import io.circe.Json
import net.java.games.input.Controller

case class ControllerSelectionFilter(alias: String, nameFilter: String, componentsFilter: Option[String]) {

  def matches(controller: Controller): Option[MonitoredController] = {

    val trimmedName = controller.getName.toLowerCase.trim
    val componentsString = controller.getComponents.mkString(", ").toLowerCase

    def nameMatches = trimmedName.matches(nameFilter)
    def componentsMatch = componentsFilter.forall(f => componentsString.matches(f))

    if (nameMatches && componentsMatch) {
      Some(MonitoredController(alias, controller))
    } else {
      None
    }
  }
}

object ControllerSelectionFilter {

  def apply(json: Json): ControllerSelectionFilter = {
    ControllerSelectionFilter(
      alias = json.\\("alias").flatMap(_.asString).headOption.getOrElse(s"Devices must have 'alias' field").toLowerCase,
      nameFilter = json.\\("name-filter").flatMap(_.asString).headOption.getOrElse(s"Devices must have 'name-filter' field").toLowerCase,
      componentsFilter = json.\\("components-filter").flatMap(_.asString).headOption.map(_.toLowerCase)
    )
  }
}
