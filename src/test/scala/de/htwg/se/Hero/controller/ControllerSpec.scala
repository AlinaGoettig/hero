package de.htwg.se.controller

import de.htwg.se.model.{Board, Cell, Player}
import de.htwg.se.util._
//import de.htwg.se.util.Observable
import org.scalatest._

class ControllerSpec extends WordSpec with Matchers {
    "The Gameboard" when {
        "game is running" should {
            val controller = new Controller
            controller.board = controller.start()

            "have informations" in {
                controller.board shouldNot be(null)
            }

            "when game starts" in {
                assert(controller.inizGame())
                assert(controller.createCreatureList().isInstanceOf[List[Cell]])
                assert(controller.start().isInstanceOf[Board])
                assert(controller.placeCreatures(controller.board, new CreaturelistIterator).isInstanceOf[Board])
                assert(controller.placeObstacles(controller.board, new ObstacleListIterator).isInstanceOf[Board])
                assert(controller.printSidesStart().isInstanceOf[String])

            }

            "call several times" in {
                assert(controller.next().isInstanceOf[Cell])
                assert(controller.winner().isInstanceOf[Int])
                controller.winner() should (be >= 0 and be <= 2)
                assert(controller.deathcheck(0, 0).isInstanceOf[Boolean])
                assert(controller.findbasehp(controller.board.field(0)(0).name).isInstanceOf[Int])
                assert(controller.prediction().isInstanceOf[Vector[Vector[Cell]]])
                assert(controller.clear().isInstanceOf[Vector[Vector[Cell]]])
                assert(controller.position(controller.board.field(0)(0)).isInstanceOf[Vector[Int]])
                assert(controller.intpos("FALSCH").isInstanceOf[Vector[Int]])
                controller.intpos("FALSCH") should be(Vector(-1, -1))
                assert(controller.baseStats().isInstanceOf[Vector[Int]])
                assert(controller.printfield().isInstanceOf[String])
            }

            "return" in {
                assert(controller.fieldnumber("9").equals("   9   "))
                assert(controller.lines().equals("=" * 7 * 15 + "\n"))
                assert(controller.getCreature(controller.board.field, 0, 0).isInstanceOf[Cell])
                assert(controller.active(0, 0).isInstanceOf[Boolean])
            }

            "for entered commands" in {
                assert(controller.info(Vector("i", "0", "0")).isInstanceOf[String])
                assert(controller.output.isInstanceOf[String])
                controller.prediction()
                controller.checkattack(Vector("a", "14", "5")) should be(false)
                assert(controller.checkmove(Vector("m", "3", "0")).isInstanceOf[Boolean])
                controller.next()
                controller.prediction()
                controller.notifyObservers
                controller.checkmove(Vector("m", "9", "0")) should be(true)
                controller.next()
                controller.prediction()
                controller.notifyObservers
                controller.printfield()
                controller.checkattack(Vector("a", "14", "5")) should be(true)
                controller.checkmove(Vector("m", "3", "1")) should be(true)
                controller.next()
                controller.prediction()
                controller.notifyObservers
                controller.checkmove(Vector("m", "10", "1")) should be(true)
                controller.next()
                controller.prediction()
                controller.notifyObservers
                controller.checkmove(Vector("m", "4", "2")) should be(true)
                controller.next()
                controller.prediction()
                controller.notifyObservers
                controller.checkmove(Vector("m", "9", "2")) should be(true)
                controller.next()
                controller.prediction()
                controller.notifyObservers
                controller.printfield()
                controller.checkattack(Vector("a", "9", "3")) should be(false)
                controller.checkattack(Vector("a", "9", "2")) should be(true)
                controller.checkmove(Vector("m", "11", "3")) should be(false)
                controller.checkmove(Vector("m", "9", "3")) should be(true)
                controller.prediction()
                controller.checkattack(Vector("a", "9", "2")) should be(true)

                controller.checkattack(Vector("a", "9", "2"))
                controller.deathcheck(9, 2)
                controller.checkattack(Vector("a", "9", "2"))
                controller.deathcheck(9, 2)
            }

            "when cheating" in {
                controller.board = controller.start()
                controller.inizGame()
                assert(controller.cheatCode(Vector("CHEAT", "coconuts")).isInstanceOf[String])
                assert(controller.cheatCode(Vector("CHEAT", "godunit")).isInstanceOf[String])
                assert(controller.cheatCode(Vector("CHEAT", "feedcreature")).isInstanceOf[String])
                assert(controller.cheatCode(Vector("CHEAT", "handofjustice")).isInstanceOf[String])
            }

            "when creature dying" in{
                controller.next()
                controller.prediction()
                controller.notifyObservers
                assert(controller.changeStats(CellFactory("")).isInstanceOf[Boolean])
                assert(controller.endInfo(1).isInstanceOf[String])
                controller.next()
                controller.next()
                controller.next()
                controller.next()
                controller.next()
                controller.next()
                controller.next()
            }

            "to win" in {
                controller.board = controller.start()
                controller.inizGame()
                controller.cheatCode(Vector("CHEAT", "handofjustice"))
                controller.winner() should  be(2)

                controller.board = controller.start()
                controller.inizGame()
                controller.next()
                controller.cheatCode(Vector("CHEAT", "handofjustice"))
                controller.winner() should be(1)
            }
        }
    }

}
