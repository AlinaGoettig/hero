package de.htwg.se.aview

/**
 * Scala project for the game Hero (based on Heroes of Might and Magic III - Fight)
 * @author Ronny Klotz & Alina Göttig
 * @since 17.Nov.2020
 */

import de.htwg.se.controller.controllerComponent.ControllerInterface
import de.htwg.se.util.{Observer, SetCommand, UndoManager}

import scala.io.StdIn

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
        } else if (input.head.equals("save")) {
            controller.save()
            println("Currently running game got saved!")
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
        } else {
            controller.prediction()
        }
        controller.notifyObservers
    }

    def commands(): String = {
        val line = "=" * 105

        "| a X Y | Attack an creature in range of the current one\n" +
            "| m X Y | Move the current creature to board point X,Y\n" +
            "| i X Y | Returns the multiplier and health of the creature at X,Y\n" +
            "| p     | Skip the current round\n" +
            "| undo  | Restores last move\n" +
            "| redo  | Resets undo\n" +
            "| save  | Save game\n" +
            "| exit  | Exit the game\n" + line + "\n"
    }

    override def update: Unit = {
        controller.gamestate match {
            case "mainmenu" => println(startinfo() + mainmenu)
            case "gamerun" => print(controller.output + commands())
            case "credit" => print(credits)
            case "finished" => controller.winner() match { case Some(value) => print (controller.endInfo(value)) }
        }
    }

    def credits: String = {
        "=" * 49 + " Credits " + "=" * 48 + "\n" +
        "Code written\t\t\tAlina Göttig and Ronny Klotz\n" +
        "Graphic design\t\t\tRonny Klotz\n" +
        "Programs and Language:\t" + "IDE: IntelliJ IDEA Community Edition\n" +
            "\t\t\t\t\t\tScala: 2.13.3\n\t\t\t\t\t\tJava JDK: 1.8.0\n" +
            "\t\t\t\t\t\tSbt: 1.4.5\n\t\t\t\t\t\tAdobe Photoshop CC 2019\n\t\t\t\t\t\t\tMarmoset Hexels\n\n" +
            "Project for Software Engineering at HTWG Konstanz AIN\n" +
        "=" * 105 + "\n"
    }


    def startinfo(): String = {
        val version = "Gameversion: 1.3 GUI Implementation"
        val top = "\n" + "=" * 44 + " Welcome to Hero " + "=" * 44
        val middle = "\n" + " " * 35 + "Made by Alina Göttig & Ronny Klotz" + "\n"
        val bottom = " " * ((105 - version.length) / 2) + version + "\n"
        top + middle + bottom + "=" * 105 + "\n"
    }

    def mainmenu: String = {
        val line = "=" * 105
        "| n    | \tNew Game\n" +
            "| l    | \tLoad Game\n" +
            "| c    | \tCredits\n" +
            "| exit | \tExit the game\n" + line
    }
}
