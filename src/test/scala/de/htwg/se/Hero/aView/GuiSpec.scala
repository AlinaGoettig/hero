package de.htwg.se.Hero.aView

import de.htwg.se.aview.GUI
import de.htwg.se.controller.controllerComponent.ControllerImpl.Controller
import org.scalatest._

class GuiSpec extends WordSpec with Matchers {
    "A Gui" when { "new" should {
        val controller = new Controller
        val gui = new GUI(controller)

    }}
}
