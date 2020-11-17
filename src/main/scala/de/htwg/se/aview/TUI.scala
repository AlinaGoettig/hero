package de.htwg.se.aview

import de.htwg.se.utill.Observer
import de.htwg.se.controller.Controller

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

    def isvalid(in : List[String]) : Boolean =
        if ((in(1).toInt >= 0) && (in(1).toInt <= 14) && (in(2).toInt >= 0) && (in(2).toInt <= 11)) true else false

    override def update: Unit = controller.output
}
