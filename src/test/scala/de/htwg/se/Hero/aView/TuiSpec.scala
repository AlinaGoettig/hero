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

        "accept input" in {
            tui.nextRound()
            tui.inputLine("i 0 0".split(" ").toVector) should be(true)
            tui.inputLine("m 0 3".split(" ").toVector) should be(true)
            tui.inputLine("a 1 1".split(" ").toVector) should be(true)
            tui.inputLine("CHEAT handofjustice".split(" ").toVector) should be(false)
        }
    }}

}
