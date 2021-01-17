package de.htwg.se.hero.util

import de.htwg.se.hero
import de.htwg.se.hero.model.boardComponent.boardImpl
import de.htwg.se.hero.model.playerComponent.Player
import de.htwg.se.hero.util.CellFactory
import org.scalatest._

class FactoryMethod extends WordSpec with Matchers {

    "An FactoryMethod" when { "new" should {
        val marker = CellFactory("marker")
        val obstacle = CellFactory("obstacle")
        val emptycell = CellFactory("")
        "provide the cell" in {
            marker should be(boardImpl.Cell(" _ ", "0", 0, 0, style = false, 0, Player("none")))
            obstacle should be(boardImpl.Cell("XXX", "0", 0, 0, style = false, 0, Player("none")))
            emptycell should be(boardImpl.Cell("   ", "0", 0, 0, style = false, 0, Player("none")))
        }
    }
    }

}
