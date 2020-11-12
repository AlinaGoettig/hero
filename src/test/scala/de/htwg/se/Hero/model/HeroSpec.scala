package de.htwg.se

import de.htwg.se.Hero.model.Hero._
import de.htwg.se.Hero.model.Player
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

    "At start of the game," when { "first text appear" should {
        val start = gameName()
        "show the title"  in {
           start should be("\n ======== Welcome to Hero ======== \n")
        }
    }}

    "A line" when { "shown" should {
        val line = lines()
        "be out of ="  in {
            line should be("=" * 7 * 15 + "\n")
        }
    }}
}
