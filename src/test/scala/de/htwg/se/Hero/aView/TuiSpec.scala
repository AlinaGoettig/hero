package de.htwg.se.Hero.aView

import de.htwg.se.aview.TUI
import de.htwg.se.controller.Controller
import de.htwg.se.util.UndoManager
import org.scalatest._

class TuiSpec extends WordSpec with Matchers {

    "A TUI" when { "new" should {
        val controller = new Controller()
        val executer = new UndoManager
        val tui = new TUI(controller, executer)

        "have commando represent"  in {
            tui.commands() should include("a X Y")
            tui.commands() should include("m X Y")
            tui.commands() should include("i X Y")
            tui.commands() should include("undo")
            tui.commands() should include("redo")
            tui.commands() should include("p")
            tui.commands() should include("exit")
        }
        "be of Type" in {
            assert(tui.commands().isInstanceOf[String])
            assert(tui.update.isInstanceOf[Unit])
        }

        "accept input" in {
            tui.nextRound(true)
            tui.inputLine("undo".split(" ").toVector) should be(false)
            tui.inputLine("redo".split(" ").toVector) should be(false)
            tui.inputLine("i 0 0".split(" ").toVector) should be(false)
            tui.inputLine("m 3 0".split(" ").toVector) should be(false)
            tui.inputLine("m 9 0".split(" ").toVector) should be(false)
            tui.inputLine("a 9 0".split(" ").toVector) should be(false)
            tui.inputLine("undo".split(" ").toVector) should be(false)
            tui.inputLine("redo".split(" ").toVector) should be(false)
            tui.inputLine("CHEAT handofjustice".split(" ").toVector) should be(true)
        }
    }}

}
