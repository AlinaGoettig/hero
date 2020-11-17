package de.htwg.se

import de.htwg.se.Hero.model.Hero._
import de.htwg.se.Hero.model.{Board, Cell, Player}
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

    "Test kacka" when { "new" should {
        val creature = Cell("HE.","10-20",2,2,true,2,Player("Test"))
        val players = Vector(Player("Test"),Player("Test2"))
        val board = Board(Vector(Vector(emptycell(),emptycell()),Vector(emptycell(),creature)),players,players(0))
        "have the typ"  in {
            board.toString() should be ("test")
            board.currentcreatureinfo(1,1) should be("Test0")
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
