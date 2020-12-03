package de.htwg.se.Hero.aView

import de.htwg.se.aview.TUI
import de.htwg.se.controller.Controller
import org.scalatest._

class TuiSpec extends WordSpec with Matchers {

    "A TUI" when { "new" should {
        val controller = new Controller()
        val tui = new TUI(controller)

        "have commando represent"  in {
            tui.commands() should include("a X Y")
            tui.commands() should include("m X Y")
            tui.commands() should include("i X Y")
            tui.commands() should include("p")
            tui.commands() should include("exit")
        }
        "be of Type" in {
            assert(tui.commands().isInstanceOf[String])
            assert(tui.update.isInstanceOf[Unit])
        }
    }}

}
