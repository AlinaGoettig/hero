package de.htwg.se.Hero.model

import com.ibm.jvm.dtfjview.Output
import de.htwg.se.Hero.model.Hero._
import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
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

    "At start of the game," when { "first text appear" should {
        val start = gameName()
        "it show the title"  in {
           start should be("Hero")
        }
    }}

    "Number of lines" when { "shown" should {
        val line = lines()
        "get a line out of ="  in {
            line should be("=" * 7 * 15 + "\n")
        }
    }}


    "Cell created" when { "the board get shown" should {
        val cell = mid("xxx")
        "have the input of String"  in {
            cell should be("│ xxx │")
        }
    }}

    "Board created" when { "the programm start," should {
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
