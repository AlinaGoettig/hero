package de.htwg.se.aview

/**
 * Scala project for the game Hero (based on Heroes of Might and Magic III - Fight)
 * @author Ronny Klotz & Alina Göttig
 * @since 17.Nov.2020
 */

import de.htwg.se.util.{Observer, SetCommand, UndoManager}
import de.htwg.se.controller.Controller

import scala.io.StdIn

//noinspection ScalaStyle
class TUI(controller: Controller, executer : UndoManager) extends Observer{

    controller.add(this)

    def inputLine(input: Vector[String]): Boolean = {
        if((input.head.equals("m") && controller.checkmove(input)) ||
            (input.head.equals("a") && controller.checkattack(input))) {
            executer.doStep(new SetCommand(input,controller), controller.board)
            nextRound(true)
        } else if (input.head.equals("i")) {
            controller.info(input)
        } else if (input.head.equals("undo")) {
            executer.undoStep(controller)
            nextRound(false)
        } else if (input.head.equals("redo")) {
            executer.redoStep(controller)
            nextRound(false)
        } else if (input.head.equals("CHEAT")) {
            controller.cheatCode(input)
            controller.notifyObservers
        }

        controller.winner() match {
            case Some(value) => {
                print(controller.endInfo(value))
                false
            }
            case None => true
        }
    }

    def nextRound(check: Boolean): Unit = {
        if (check) controller.next()
        controller.prediction()
        controller.notifyObservers

        print(commands())
    }

    def commands(): String = {
        val line = "=" * 105

        "| a X Y | Attack an creature in range of the current one\n" +
            "| m X Y | Move the current creature to board point X,Y\n" +
            "| i X Y | Returns the multiplier and health of the creature at X,Y\n" +
            "| p     | Skip the current round\n" +
            "| undo  | Restores last move\n" +
            "| redo  | Resets undo\n" +
            "| exit  | Exit the game\n" + line + "\n"
    }

    override def update: Unit = print(controller.output)

}
