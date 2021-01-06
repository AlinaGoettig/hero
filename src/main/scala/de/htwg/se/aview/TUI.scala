package de.htwg.se.aview

/**
 * Scala project for the game Hero (based on Heroes of Might and Magic III - Fight)
 * @author Ronny Klotz & Alina Göttig
 * @since 17.Nov.2020
 */

import de.htwg.se.controller.controllerComponent.ControllerInterface
import de.htwg.se.util.{Observer, SetCommand, UndoManager}

//noinspection ScalaStyle
class TUI(controller: ControllerInterface, executer : UndoManager) extends Observer {

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
        if (controller.winner().isDefined) true
        else false
    }

    def nextRound(check: Boolean): Unit = {
        if (check) {
            if (controller.winner().isDefined){
                controller.gamestate = "finished"
            } else {
                controller.next()
                controller.prediction()
            }
            controller.notifyObservers
        } else {
            controller.prediction()
            controller.notifyObservers

            print(commands())
        }
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

    override def update: Unit = {
        controller.gamestate match {
            case "credit" => print("Not supported feature! -> Credit view")
            case "mainmenu" => print(controller.output)
            case "gamerun" => print(controller.output)
            case "finished" => controller.winner() match { case Some(value) => print (controller.endInfo(value)) }
        }
    }
}
