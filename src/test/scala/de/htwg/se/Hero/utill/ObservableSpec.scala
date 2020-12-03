package de.htwg.se.Hero.utill

import de.htwg.se.aview.TUI
import de.htwg.se.controller.Controller
import de.htwg.se.utill.Observable
import org.scalatest._

class ObservableSpec extends WordSpec with Matchers {

    "An Observer" when { "new" should {
            val obserable = new Observable
            val controller = new Controller
            val tui = new TUI(controller)
            "add an Observer" in {
                obserable.add(tui)
                obserable.subscribers should not be(Vector.empty)
            }
            "remove an Observer" in {
                obserable.remove(tui)
                obserable.subscribers should be(Vector.empty)
            }
            "notify an Observer" in {
                obserable.add(tui)
                obserable.notifyObservers should be(true)
            }
        }
    }

}
