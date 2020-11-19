package de.htwg.se.Hero.model

/**
 * Scala project for the game Hero (based on Heroes of Might and Magic III - Fight)
 * @author Ronny Klotz & Alina Göttig
 * @since 9.Nov.2020
 */

import de.htwg.se.aview.TUI
import de.htwg.se.controller.Controller
import de.htwg.se.model.{Board, Cell, Player}

import scala.io.StdIn

//noinspection ScalaStyle
object Hero {

    val controller = new Controller()
    val tui = new TUI(controller)
    controller.notifyObservers


    def main(args: Array[String]): Unit = {
        println("\n ======== Welcome to Hero ======== \n")
        println("Made by Alina Göttig & Ronny Klotz\n")
        var input: String = ""

        for (count <- 1 to 2) {
            tui.createPlayer(count)
        }

        do {
            input = StdIn.readLine()
            tui.inputLine(input)
        } while (input != "exit")

    }

    //------------------------------------------------------------------------------------------------------------------

    def playerside(player: Vector[Player]): String = {
        val player1 = "│ " + player(0).name + " │"
        val player2 = "│ " + player(1).name + " │"
        val distance = 15*7 - (player1.length + player2.length)
        val info = lines() + player1 + " " * distance + player2 + "\n" + lines()
        info
    }

    def lines(): String = "=" * 7 * 15 + "\n"

    def currentPlayerOutput(player: Player) : String = lines() + "Current Player: " + player + "\n" + lines()

    def game(): String = {

        // Initialise board and playerlist
        val player = Vector(player1,player2)
        val board = Board(Vector.ofDim[Cell](11,15),player,player(1))
        // Fills the board and create a refernece list
        board.start()
        val field = board.field
        val creatures = Vector(field(0)(0),field(0)(14),field(1)(0),field(1)(14),field(2)(0),field(2)(14),field(5)(0),
            field(5)(14),field(8)(0),field(8)(14),field(9)(0),field(9)(14),field(10)(0),field(10)(14))

        val creatureTurn = Creaturefield(creatures,Vector(creatures(creatures.length-1)),player)

        while(command(creatureTurn.next(),board)){
            val winner = creatureTurn.winner()

            if (winner == 1) {
                return printwin(creatureTurn.field,player1)
            } else if (winner == 2) {
                return printwin(creatureTurn.field,player2)
            }

        }

        "\n" + lines() + "Game got closed!" + "\n" + lines()

    }

    def printwin(field: Vector[Cell],player: Player): String = {

        val top = lines() + player.name + " won the game!\n" + lines()
        val middle = "Remaining creatures of winner\n\nName:\t\t\tMultiplier:\t\t\tHealth:\n"
        println(top + middle)

        for(c <- field) {
            if (c.multiplier > 0) {
                println(c.name + "\t\t\t" + c.multiplier + "\t\t\t" + c.hp)
            }
        }

        println(lines())

        top
    }

    def command(creature: Cell, field:Board) : Boolean = {
        val p = Vector(nextplayer(field.currentplayer(0).toString,field.player))
        val coordinates = field.postition(creature)
        field.currentplayer(0) = p(0)
        field.clear()
        field.prediction(coordinates.head, coordinates.last)
        field.printfield()
        println(currentPlayerOutput( p(0) ))
        field.currentcreatureinfo(coordinates.head,coordinates.last)

        println("=============================")
        println("a X Y   = attack")
        println("m X Y   = move")
        println("i X Y   = info")
        println("p       = pass")
        println("exit    = exit game")
        println("=============================")
        print("neue Eingabe: ")
        val input = validInput(field, creature)

        // X15>B   Y11^Z
        if (input.length == 3) {
            if (input(0).equals("a")) {
                if(!active(field, input(2).toInt, input(1).toInt)) {

                        val dmg = field.attack(coordinates.head, coordinates.last, input(1).toInt, input(2).toInt)
                        val defender = getCreature(field.field,input(2).toInt, input(1).toInt)

                        val info = lines() + creature.name + " dealt " + dmg + " points of damage to " +
                            defender.name + "\n" + lines() + "Remaining values\n\nMultiplier: " + defender.multiplier +
                            "\nHP: " + getCreature(field.field,input(2).toInt, input(1).toInt).hp + "\n" + lines()

                        println(info)

                }
                else
                    println("Creature can't be attacked")
            }
            if (input(0).equals("m")) {
                    field.move(coordinates.head,coordinates.last, input(1).toInt, input(2).toInt)
            }
        } else if (input.length == 1) {
            if (input(0).equals("p")) {
            }
            if(input(0).equals("exit")) {
                return false
            }
        } else {
            println("Ungültige Eingabe")
        }
        true
    }

    def validInput(field:Board, creature:Cell) : Vector[String] = {

        val in = StdIn.readLine().split(" ")

        if(!(in(0).equals("a") || in(0).equals("m") || in(0).equals("p") || in(0).equals("exit") || in(0).equals("i"))) {
            println("Ungültige Eingabe")
            println("neue Eingabe: ")
            val out = validInput(field, creature)
            return out
        }

        if (in.length == 3) {
            if (checkmove(in.toVector, field) || in(0).equals("a") && creature.style || in(0).equals("a") && checkattack(in, field)) {
                return in.toVector
            } else if (in(0).equals("i") && isvalid(in.toVector)) {
                field.creatureinfo(in(1).toInt, in(2).toInt)
                println("neue Eingabe: ")
                val out = validInput(field, creature)
                return out
            } else {
                println("Ungültige Eingabe")
                println("neue Eingabe: ")
                val out = validInput(field, creature)
                return out
            }

        }

        in.toVector

    }

    def checkmove(in:Vector[String], field:Board): Boolean =
        if (in(0) == "m" && isvalid(in) &&
            getCreature(field.field, in(2).toInt, in(1).toInt).name.equals(" _ ")) true else false


    def checkattack(in:Vector[String], board:Board) : Boolean = {
        val i = in(2).toInt
        val j = in(1).toInt
        val field = board.field
        if (!field(i)(j).name.equals("   ") && !field(i)(j).name.equals(" _ ") && !field(i)(j).name.equals("XXX")
            && !active(board, i, j)) {
            if (((i - 1 >= 0 && j - 1 >= 0) && field(i - 1)(j - 1).name.equals(" _ ")) ||
                ((i - 1 >= 0 && j >= 0) && field(i - 1)(j).name.equals(" _ ")) ||
                ((i - 1 >= 0 && j + 1 < 14) && field(i - 1)(j + 1).name.equals(" _ ")) ||
                ((i - 1 >= 0 && j >= 0) && field(i - 1)(j).name.equals(" _ ")) ||
                ((i + 1 < 11 && j >= 0) && field(i + 1)(j).name.equals(" _ ")) ||
                ((i + 1 < 11 && j - 1 >= 0) && field(i + 1)(j - 1).name.equals(" _ ")) ||
                ((i >= 0 && j + 1 < 14) && field(i)(j + 1).name.equals(" _ ")) ||
                ((i + 1 < 11 && j + 1 < 14) && field(i + 1)(j + 1).name.equals(" _ "))) {
                return true
            }
        }
        false
    }

    def isvalid(in : Vector[String]) : Boolean =
        if ((in(1).toInt >= 0) && (in(1).toInt <= 14) && (in(2).toInt >= 0) && (in(2).toInt <= 11)) true else false
}