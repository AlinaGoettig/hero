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

    "Two Creatures fight, " when { "the attack deal dmg, " should {
        val board = Board(Array.ofDim[Cell](11,15))
        board.fillboard(board.field)
        board.startboard(board.field)
        val attacker = Cell("HA.",3,10,5,false,28)
        val defender = Cell(".HO",9,40,6,false,8)
        val dmg = board.attack(0,0,14,10,board.field)
        "to the defender"  in {
            dmg should be("84")
            board.field(10)(14).name should be (defender.name)
            board.field(10)(14).multiplier should be (6)
            board.field(10)(14).hp should be (36)
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
