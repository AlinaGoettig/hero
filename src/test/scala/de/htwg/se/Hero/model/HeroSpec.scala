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


    "Cell created" when { "the board get shown" should {
        val cell = mid("xxx")
        "have the input of String"  in {
            cell should be("│ xxx │")
        }
    }}

    "Board created" when { "the programm starts," should {
        val board = line()
        "include all given names"  in {
            board should include("Player1")
            board should include("Player2")
            board should include("xxx")
            board should include("HA.")
            board should include("MA.")
            board should include("RO.")
            board should include("AN.")
            board should include("CH.")
            board should include("ZE.")
            board should include("CR.")
            board should include(".FA")
            board should include("MAG")
            board should include(".CE")
            board should include(".DE")
            board should include(".EF")
            board should include(".PI")
            board should include(".HO")
        }
    }}
}
