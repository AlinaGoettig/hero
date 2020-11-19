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
    def inputLine(input: String): Unit = {
        val in = input.split(" ").toList

        if (in.size == 3) {
            if (in.head.equals("a") && isvalid(in)) {

            } else if (in.head.equals("m") && isvalid(in)) {

            } else if (in.head.equals("i") && isvalid(in)) {

            } else {
                println("Ungültige Eingabe")
                println("neue Eingabe: ")
            }

        } else if (in.head.equals("p")) {

        } else {
            println("Ungültige Eingabe")
            println("neue Eingabe: ")
        }
    }

    def createPlayer(number: Int): Unit = {
        val side = if (number == 1) "(Castle)" else "(Underworld)"
        println("=============================")
        println("Enter Player" + number + " " + side + ":")
        println("=============================")
        val input = StdIn.readLine()
        controller.createNewPlayer(input)
    }

    def isvalid(in : List[String]) : Boolean =
        if ((in(1).toInt >= 0) && (in(1).toInt <= 14) && (in(2).toInt >= 0) && (in(2).toInt <= 11)) true else false

    override def update: Unit = controller.output
}
