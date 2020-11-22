package de.htwg.se.controller

import de.htwg.se.model.{Board, Cell, Player}
//import de.htwg.se.utill.Observable
import org.scalatest._

class ControllerSpec extends WordSpec with Matchers {
    "The Gameboard" when {
        "game is running" should {
            val controller = new Controller
            val player = Vector(Player("Castle"), Player("Underground"))
            controller.board = controller.start()
            "have informations" in {
                controller.board shouldNot be(null)
            }

            "when game starts" in {
                assert(controller.inizGame())
                assert(controller.createCreatureList().isInstanceOf[List[Cell]])
                assert(controller.obstacle.equals(Cell("XXX", "0", 0, 0, false, 0, Player("none"))))
                assert(controller.emptycell.equals(Cell("   ", "0", 0, 0, false, 0, Player("none"))))
                assert(controller.marker.equals(Cell(" _ ", "0", 0, 0, false, 0, Player("none"))))
                assert(controller.start().isInstanceOf[Board])
                assert(controller.placeCreatures(controller.board, controller.creatureliststart(player), controller.obstaclelist(),
                    true).isInstanceOf[Board])
                assert(controller.placeObstacles(controller.board, controller.obstaclelist(), true).isInstanceOf[Board])
                assert(controller.printSidesStart().isInstanceOf[String])

            }

            "call several times" in {
                assert(controller.next().isInstanceOf[Cell])
                assert(controller.winner().isInstanceOf[Int])
                assert(controller.deathcheck(0, 0).isInstanceOf[Boolean])
                assert(controller.findbasehp(controller.board.field(0)(0).name).isInstanceOf[Int])
                assert(controller.prediction().isInstanceOf[Vector[Vector[Cell]]])
                assert(controller.clear().isInstanceOf[Vector[Vector[Cell]]])
                assert(controller.position(controller.board.field(0)(0)).isInstanceOf[Vector[Int]])
                assert(controller.baseStats().isInstanceOf[Vector[Int]])
                assert(controller.printfield().isInstanceOf[String])
            }

            "return" in {
                assert(controller.fieldnumber("9").equals("   9   "))
                assert(controller.lines().equals("=" * 7 * 15 + "\n"))
                assert(controller.getCreature(controller.board.field, 0, 0).isInstanceOf[Cell])
                assert(controller.active(0, 0).isInstanceOf[Boolean])
                assert(controller.obstaclelist().isInstanceOf[Vector[(Vector[Int], Cell)]])
            }
            "for entered commands" in {
                assert(controller.info(Vector("i", "0", "0")).isInstanceOf[String])
                assert(controller.output.isInstanceOf[String])
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
            }
            "when cheating" in {
                assert(controller.cheatCode(Vector("CHEAT", "coconuts")).isInstanceOf[String])
                assert(controller.cheatCode(Vector("CHEAT", "godunit")).isInstanceOf[String])
                assert(controller.cheatCode(Vector("CHEAT", "feedcreature")).isInstanceOf[String])
                assert(controller.cheatCode(Vector("CHEAT", "handofjustice")).isInstanceOf[String])
            }

            "when creature dying" in{
                controller.next()
                controller.prediction()
                controller.notifyObservers
                assert(controller.changeStats(controller.emptycell).isInstanceOf[Boolean])
                assert(controller.endInfo(1).isInstanceOf[String])
            }
        }
    }

}