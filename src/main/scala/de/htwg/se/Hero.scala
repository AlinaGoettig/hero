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
        println("\n" + "=" * 44 + " Welcome to Hero " + "=" * 44)
        println(" " * 35 + "Made by Alina Göttig & Ronny Klotz")
        println(" " * 40 + "Version 1.0 MVC Structur")
        println("=" * 105 + "\n")
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
}