package de.htwg.se.model

import org.scalatest._

class CellSpec extends WordSpec with Matchers {

    "A Cell " when { "new" should {
        val cell = Cell("Test","10-20", 100,10,false,10,Player("Test"))
        "have informations"  in {
            cell.name should be("Test")
            cell.dmg should be("10-20")
            cell.hp should be(100)
            cell.style should be(false)
            cell.multiplier should be(10)
            cell.player should be(Player("Test"))
            cell should be(Cell)
        }
        "have a nice String representation" in {
            cell.toString should be("│ Test │")
        }
        ", a marker for attackable" in {
            cell.attackable() should be("│>Test<│")
        }
        "and a damage calculation" in {
            cell.attackamount() should be >= 10
            cell.attackamount() should be <= 20
        }
    }}

    "A instance of a Player" when { "new" should {
        val player = Player
        "have the typ"  in {
            player should be(Player)
        }
    }}

}
