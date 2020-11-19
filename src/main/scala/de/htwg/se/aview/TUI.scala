package de.htwg.se.aview

/**
 * Scala project for the game Hero (based on Heroes of Might and Magic III - Fight)
 * @author Ronny Klotz & Alina Göttig
 * @since 17.Nov.2020
 */

import de.htwg.se.utill.Observer
import de.htwg.se.controller.Controller
import scala.io.StdIn

class TUI(controller: Controller) extends Observer{

    controller.add(this)

    //noinspection ScalaStyle
    def inputLine(withOutput: Boolean): Boolean = {
        if (withOutput) {
            println("=============================")
            println("a X Y   = attack")
            println("m X Y   = move")
            println("i X Y   = info")
            println("p       = pass")
            println("exit    = exit game")
            println("=============================")
        }
        print("neue Eingabe: ")
        val in = StdIn.readLine().split(" ").toList

        if (in.size == 3) {
            if (isinBoard(in) && (in.head.equals("a") || in.head.equals("m") || in.head.equals("i")))
                true
            else {
                println("Ungültige Eingabe")
                inputLine(false)
            }
        } else if (in.head.equals("p")) {
            true
        } else if (in.head.equals("exit")) {
            false
        } else {
            println("Ungültige Eingabe")
            inputLine(false)
        }
    }

    def createPlayer(number: Int): Unit = {
        val side = if (number == 1) "(Castle)" else "(Underworld)"
        println("=============================")
        println("Enter Player" + number + " " + side + ":")
        println("=============================")
        val input = StdIn.readLine()
        controller.addPlayer(input)
    }

    def isinBoard(in : List[String]) : Boolean =
        if ((in(1).toInt >= 0) && (in(1).toInt <= 14) && (in(2).toInt >= 0) && (in(2).toInt <= 11)) true else false

    override def update: Unit = controller.output
}
