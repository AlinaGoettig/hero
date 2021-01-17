package de.htwg.se.hero

import com.google.inject.{Guice, Injector}
import de.htwg.se.hero.aview.{GUI, TUI}
import de.htwg.se.hero.util.{Interpreter, UndoManager}
import de.htwg.se.hero.controller.controllerComponent.ControllerInterface

import scala.io.StdIn

//noinspection ScalaStyle
object Hero {

    val injector: Injector = Guice.createInjector(new HeroModule)
    val controller: ControllerInterface = injector.getInstance(classOf[ControllerInterface])
    val manager = new UndoManager

    val UIType: Boolean = if (System.getenv("UI_TYPE").equals("full")) true else false

    val tui = new TUI(controller, manager)

    def main(args: Array[String]): Unit = {

        if (UIType) {
            val gui = new GUI(controller)
            gui.update
            gui.visible = true
        }

        println(startinfo() + mainmenu)
        while (true) {

            controller.gamestate match {

                case "mainmenu" => {
                    while (controller.gamestate.equals("mainmenu")) {
                        val input = StdIn.readLine()

                        controller.gamestate match {

                            case "mainmenu" => {
                                if (input.equals("n")) {
                                    controller.gamestate = "gamerun"
                                    newgame("")
                                } else if (input.equals("l")) {
                                    if (controller.load()) {
                                        controller.gamestate = "gamerun"
                                        controller.prediction()
                                        controller.notifyObservers
                                    }
                                }
                                else if (input.equals("c")) {
                                    controller.gamestate = "credit"
                                    controller.notifyObservers
                                }
                                else if (input.equals("exit")) return
                                else println("Invalid Input. ")
                            }

                            case "gamerun" => {
                                println(controller.printSidesStart())
                                controller.notifyObservers
                                newgame(input)
                            }

                            case "credit" => controller.gamestate = "mainmenu"
                            case "finished" => controller.gamestate = "mainmenu"
                        }
                    }
                }

                case "gamerun" => newgame("")

                case "gamerun" => {
                    while (controller.gamestate.equals("gamerun")) {
                        val input = StdIn.readLine().split(" ").toVector
                        if (new Interpreter(input).interpret()) {
                            if (input(0).equals("exit") || !tui.inputLine(input)) return
                        } else {
                            print("Invalid Input. ")
                        }
                        if (!controller.gamestate.equals("gamerun")) print("New Input: ")
                    }
                }
                case "credit" => {
                    controller.gamestate = "mainmenu"
                    controller.notifyObservers
                }
                case "finished" => controller.gamestate = "mainmenu"

            }
        }
    }

    def newgame(s: String): Unit = {
        controller.inizGame()

        if (s.equals("")) {
            println(controller.printSidesStart())
            controller.notifyObservers
        } else {

            if (new Interpreter(s.split(" ").toVector).interpret()) {
                if (s.split(" ").toVector(0).equals("exit") || !tui.inputLine(s.split(" ").toVector)) return
            } else {
                print("Invalid Input. ")
            }

            print("New input: ")
        }

        while (controller.gamestate.equals("gamerun")) {
            val input = StdIn.readLine().split(" ").toVector

            if (new Interpreter(input).interpret()) {
                if (input(0).equals("exit") || !tui.inputLine(input)) {
                    return
                }
            } else {
                print("Invalid Input. ")
            }

            if (!controller.gamestate.equals("gamerun")) print("New Input: ")
        }

    }

    def startinfo(): String = {
        val version = "Version: 2.5 Docker capable [TUI Mode] "
        val top = "\n" + "=" * 44 + " Welcome to hero " + "=" * 44
        val middle = "\n" + " " * 35 + "Made by Alina Göttig & Ronny Klotz" + "\n"
        val bottom = " " * ((105 - version.length) / 2) + version + "\n"

        top + middle + bottom + "=" * 105 + "\n"
    }

    def mainmenu: String = {
        val line = "=" * 105
        "| n    | \tNew Game\n" +
            "| l    | \tLoad Game\n" +
            "| c    | \tCredits\n" +
            "| exit | \tExit the game\n" + line + "\n"
    }
}
