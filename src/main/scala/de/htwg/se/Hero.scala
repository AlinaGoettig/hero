package de.htwg.se.Hero.model

/**
 * Scala project for the game Hero (based on Heroes of Might and Magic III - Fight)
 * @author Ronny Klotz & Alina Göttig
 * @since 9.Nov.2020
 */

import de.htwg.se.aview.{SwingGui, TUI}
import de.htwg.se.controller.Controller
import de.htwg.se.util.{Interpreter, UndoManager}

import scala.io.StdIn

//noinspection ScalaStyle
object Hero {

    val controller = new Controller()
    val executer = new UndoManager
    val tui = new TUI(controller, executer)
    val gui = new SwingGui(controller)

    def main(args: Array[String]): Unit = {
        gui.update
        gui.visible = true

        while (true) {
            controller.gamestate match {
                case "mainmenu" => {
                    println(startinfo() + mainmenu)
                    while (controller.gamestate.equals("mainmenu")) {
                        val input = StdIn.readLine()
                        controller.gamestate match {
                            case "mainmenu" => {
                                if (input.equals("n")) controller.gamestate = "gamerun"
                                else if (input.equals("c")) println("Function not implemented yet.")
                                else if (input.equals("exit")) return
                                else println("Ungültige Eingabe. ")
                            }
                            case "gamerun" => {
                                println(controller.printSidesStart())
                                controller.notifyObservers
                                print(tui.commands())
                                newgame(input)
                            }
                            case "finished" => return
                        }
                    }
                }
                case "gamerun" => newgame("")
                case "finished" =>
            }
        }
    }

    def newgame(s:String): Unit = {
        controller.inizGame()
        if (s.equals("")){
            println(controller.printSidesStart())
            controller.notifyObservers
            print(tui.commands())
        } else {
            if (new Interpreter(s.split(" ").toVector).interpret()) {
                if (s.split(" ").toVector(0).equals("exit") || !tui.inputLine(s.split(" ").toVector)) return
            } else {
                print("Ungültige Eingabe. ")
            }
            print("Neue Eingabe: ")
        }
        while (controller.gamestate.equals("gamerun")) {
            val input = StdIn.readLine().split(" ").toVector
            if (new Interpreter(input).interpret()) {
                if (input(0).equals("exit") || !tui.inputLine(input)) return
            } else {
                print("Ungültige Eingabe. ")
            }
            if (!controller.gamestate.equals("gamerun")) print("Neue Eingabe: ")
        }
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
            "| exit | \tExit the game\n" + line + "\n"
    }

}