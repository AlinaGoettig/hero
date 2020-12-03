package de.htwg.se.Hero.model

/**
 * Scala project for the game Hero (based on Heroes of Might and Magic III - Fight)
 * @author Ronny Klotz & Alina Göttig
 * @since 9.Nov.2020
 */

import java.util.concurrent.ThreadLocalRandom

import de.htwg.se.aview.TUI
import de.htwg.se.controller.Controller
import de.htwg.se.utill.Interpreter

import scala.io.StdIn

//noinspection ScalaStyle
object Hero {

    val controller = new Controller()
    val tui = new TUI(controller)

    def main(args: Array[String]): Unit = {
        println(startinfo())

        print(controller.printSidesStart())

        tui.nextRound()
        while(true) {
            val input = StdIn.readLine().split(" ").toVector
            if(new Interpreter(input).interpret()) {
                if (input(0).equals("exit"))
                    return
                tui.inputLine(input)
            } else {
                print("Ungültige Eingabe. ")
            }
            print("Neue Eingabe: ")
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