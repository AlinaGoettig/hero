package de.htwg.se

import de.htwg.se.Hero.model.Hero._
import de.htwg.se.controller.Controller
import de.htwg.se.model.{Board, Cell, Player}
import org.scalatest._

class HeroSpec extends WordSpec with Matchers {

    "A Player" when { "new" should {
        val player = Player("Test")
        "have a name"  in {
            player.name should be("Test")
        }
        "have a nice String representation" in {
            player.toString should be("Test")
        }
    }}

    "A instance of a Player" when { "new" should {
        val player = Player
        "have the typ"  in {
            player should be(Player)
        }
    }}

}
