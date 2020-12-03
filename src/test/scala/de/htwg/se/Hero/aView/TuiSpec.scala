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
        "have a end sequence" in {
            tui.endSequence(1) should be(Vector("exit"))
        }
        "be of Type" in {
            assert(tui.endSequence(0).isInstanceOf[Vector[String]])
            assert(tui.commands().isInstanceOf[String])
            assert(tui.update.isInstanceOf[Unit])
        }
    }}

}
