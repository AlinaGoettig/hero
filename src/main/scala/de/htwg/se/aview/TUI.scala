package de.htwg.se.aview

/**
 * Scala project for the game Hero (based on Heroes of Might and Magic III - Fight)
 * @author Ronny Klotz & Alina Göttig
 * @since 17.Nov.2020
 */

import de.htwg.se.utill.Observer
import de.htwg.se.controller.Controller
import scala.io.StdIn

//noinspection ScalaStyle
class TUI(controller: Controller) extends Observer{

    controller.add(this)

    def inputLine(withOutput: Boolean): Vector[String] = {
        val number = controller.winner()
        if (number != 0) {
            endSequence(number)
        }
        if (withOutput) {

            controller.next()
            controller.prediction()
            controller.notifyObservers

            print(commands())

        }
        print("neue Eingabe: ")
        val input = StdIn.readLine().split(" ").toVector

        if (input.size == 3) {
            if (isinBoard(input) && (input.head.equals("a") || input.head.equals("m") || input.head.equals("i"))) {
                input
            } else {
                print("Ungültige Eingabe")
                inputLine(false)
            }
        } else if (input.size == 1 && (input.head.equals("p") || input.head.equals("exit"))) {
            input
        } else if (input.size == 2 && input.head.equals("CHEAT")) {
            controller.cheatCode(input)
            controller.notifyObservers
            inputLine(false)
        } else {
            print("Ungültige Eingabe")
            inputLine(false)
        }
    }

    def isinBoard(in : Vector[String]) : Boolean =
        if ((in(1).toInt >= 0) && (in(1).toInt <= 14) && (in(2).toInt >= 0) && (in(2).toInt <= 11)) true else false

    def endSequence(side: Int): Unit = {
        print(controller.endInfo(side))
        System.exit(0)
    }

    def commands(): String = {
        val line = "=" * 105

        "| a X Y | Attack an creature in range of the current one\n" +
            "| m X Y | Move the current creature to board point X,Y\n" +
            "| i X Y | Returns the multiplier and health of the creature at X,Y\n" +
            "| p     | Skip the current round\n" +
            "| exit  | Exit the game\n" + line + "\n"

    }

    override def update: Unit = print(controller.output)
}
