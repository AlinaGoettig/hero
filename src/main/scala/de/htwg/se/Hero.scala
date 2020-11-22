package de.htwg.se.Hero.model

/**
 * Scala project for the game Hero (based on Heroes of Might and Magic III - Fight)
 * @author Ronny Klotz & Alina Göttig
 * @since 9.Nov.2020
 */

import de.htwg.se.aview.TUI
import de.htwg.se.controller.Controller

//noinspection ScalaStyle
object Hero {

    val controller = new Controller()
    val tui = new TUI(controller)

    def main(args: Array[String]): Unit = {
        println(startinfo())
        print(controller.printSidesStart())

        var input: Vector[String] = tui.inputLine(true)

        while(!input.head.equals("exit")) {
            if((input.head.equals("m") && !controller.checkmove(input)) ||
                (input.head.equals("a") && !controller.checkattack(input))) {
                input = tui.inputLine(false)
            } else if (input.head.equals("i")) {
                controller.info(input)
                input = tui.inputLine(false)
            } else if (input.head.equals("CHEAT")) {
                input = tui.inputLine(false)
            } else {
                input = tui.inputLine(true)
            }
        }
    }

    def startinfo(): String = {
        val version = "Version 1.0 MVC Structur"
        val top = "\n" + "=" * 44 + " Welcome to Hero " + "=" * 44
        val middle = "\n" + " " * 35 + "Made by Alina Göttig & Ronny Klotz" + "\n"
        val bottom = " " * ((105 - version.length) / 2) + version + "\n"
        top + middle + bottom + "=" * 105 + "\n"
    }
}