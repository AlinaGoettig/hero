package de.htwg.se.controller

import de.htwg.se.model.{Board, Cell, Player}
//import de.htwg.se.utill.Observable
import org.scalatest._

class ControllerSpec extends WordSpec with Matchers {
    "The Gameboard" when {
        "game is running" should {
            val controller = new Controller
            val player = Vector(Player("Castle"),Player("Underground"))
            var board = controller.start()
            "have informations" in {
                board shouldNot be(null)

                /*controller.creatureliststart(player) should contain(Vector(0, 0) -> Cell("HA.", "2-3", 10, 3, style = true, 28, player(0)))
                controller.creatureliststart(player) should contain(Vector(14, 0) -> Cell(".FA", "1-2", 4, 5, style = false, 44, player(1)))
                controller.creatureliststart(player) should contain(Vector(0, 1) -> Cell("MA.", "4-6", 10, 3, style = true, 28, player(0)))
                controller.creatureliststart(player) should contain(Vector(14, 1) -> Cell("MAG", "2-4", 13, 4, style = true, 20, player(1)))
                controller.creatureliststart(player) should contain(Vector(0, 2) -> Cell("RO.", "3-6", 10, 4, style = false, 18, player(0)))
                controller.creatureliststart(player) should contain(Vector(14, 2) -> Cell(".CE", "2-7", 25, 5, style = false, 10, player(1)))
                controller.creatureliststart(player) should contain(Vector(0, 5) -> Cell("AN.", "50", 250, 12, style = false, 2, player(0)))
                controller.creatureliststart(player) should contain(Vector(14, 5) -> Cell(".DE", "30-40", 200, 11, style = false, 2, player(1)))
                controller.creatureliststart(player) should contain(Vector(0, 8) -> Cell("CH.", "20-25", 100, 6, style = false, 4, player(0)))
                controller.creatureliststart(player) should contain(Vector(14, 8) -> Cell(".EF", "16-24", 90, 9, style = false, 4, player(1)))
                controller.creatureliststart(player) should contain(Vector(0, 9) -> Cell("ZE.", "10-12", 24, 5, style = true, 6, player(0)))
                controller.creatureliststart(player) should contain(Vector(14, 9) -> Cell(".PI", "13-17", 45, 5, style = false, 6, player(1)))
                controller.creatureliststart(player) should contain(Vector(0, 10) -> Cell("CR.", "7-10", 35, 4, style = false, 8, player(0)))
                controller.creatureliststart(player) should contain(Vector(14, 10) -> Cell(".HO", "7-9", 40, 4, style = false, 8, player(1)))
                */
            }

            "return" in {
                assert(controller.inizGame())
                assert(controller.createCreatureList().isInstanceOf[List[Cell]])

                assert(controller.obstacle.equals(Cell("XXX", "0", 0, 0, false, 0, Player("none"))))
                assert(controller.emptycell.equals(Cell("   ", "0", 0, 0, false, 0, Player("none"))))
                assert(controller.marker.equals(Cell(" _ ", "0", 0, 0, false, 0, Player("none"))))
                assert(controller.fieldnumber("9").equals("   9   "))
                assert(controller.lines().equals("=" * 7 * 15 + "\n"))
                assert(controller.getCreature(board.field, 0,0).isInstanceOf[Cell])
                assert(controller.active(0,0).isInstanceOf[Boolean])
                assert(controller.obstaclelist().isInstanceOf[Vector[(Vector[Int], Cell)]])
                assert(controller.start().isInstanceOf[Board])
                assert(controller.placeCreatures(board, controller.creatureliststart(player), controller.obstaclelist(),
                    true).isInstanceOf[Board])
                assert(controller.placeObstacles(board, controller.obstaclelist(),true).isInstanceOf[Board])
                assert(controller.printSidesStart().isInstanceOf[String])
                assert(controller.next().isInstanceOf[Cell])
                assert(controller.winner().isInstanceOf[Int])
                assert(controller.deathcheck(0,0).isInstanceOf[Boolean])
                assert(controller.findbasehp(board.field(0)(0).name).isInstanceOf[Int])
                assert(controller.prediction().isInstanceOf[Vector[Vector[Cell]]])
                assert(controller.clear().isInstanceOf[Vector[Vector[Cell]]])
                assert(controller.position(board.field(0)(0)).isInstanceOf[Vector[Int]])
                //assert(controller.cheatCode(Vector("hcoconuts")).isInstanceOf[String])
                assert(controller.changeStats(controller.emptycell).isInstanceOf[Boolean])
                assert((controller.baseStats().isInstanceOf[Vector[Int]]))

                assert(controller.checkmove(Vector("m", "3", "0")).isInstanceOf[Boolean])
                controller.next()
                controller.prediction()
                controller.notifyObservers
                controller.checkmove(Vector("m", "9", "0"))
                controller.next()
                controller.prediction()
                controller.notifyObservers
                controller.checkmove(Vector("m", "3", "1"))
                controller.next()
                controller.prediction()
                controller.notifyObservers
                controller.checkmove(Vector("m", "10", "1"))
                controller.next()
                controller.prediction()
                controller.notifyObservers
                controller.checkmove(Vector("m", "4", "2"))
                controller.next()
                controller.prediction()
                controller.notifyObservers
                controller.checkmove(Vector("m", "9", "2"))
                controller.next()
                controller.prediction()
                controller.notifyObservers
                assert(controller.checkattack(Vector("a", "9", "2")).isInstanceOf[Boolean])
                //assert(controller.replaceCreatureInList(board.field(2)(9),controller.emptycell).isInstanceOf[Boolean])

                assert(controller.printfield().isInstanceOf[String])
                //assert(controller.output.isInstanceOf[String])
                //assert(controller.info(Vector("i 0 0")).isInstanceOf[String])
                assert(controller.endInfo(1).isInstanceOf[String])
            }
        }
    }

}