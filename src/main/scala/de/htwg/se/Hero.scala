package de.htwg.se.Hero.model

import scala.io.StdIn
import scala.util.control.Breaks.break

object Hero {
    def main(args: Array[String]): Unit = {
        val student = Player("Alina & Ronny")
        println("Hello," + student.name)
        println(gameName())
        println(line())

        while(game()){}
    }

    def gameName(): String = {
        "Hero"
    }


    def line(): String = {
        val li = Array(lines() + mid("HA.") + mid("   ") * 13 + mid(".FA") + "\n",
            lines() + mid("MA.") + mid("   ") * 5 + mid("xxx") + mid("   ") * 7 + mid("MAG") + "\n",
            lines() + mid("RO.") + mid("   ") * 6 + mid("xxx") + mid("   ") * 6 + mid(".CE") + "\n",
            lines() + mid("   ") * 15 + "\n",
            lines() + mid("   ") * 5 + mid("xxx") * 2 + mid("   ") * 8 + "\n",
            lines() + mid("AN.") + mid("   ") * 13 + mid(".DE") + "\n",
            lines() + mid("   ") * 15 + "\n",
            lines() + mid("   ") * 15 + "\n",
            lines() + mid("CH.") + mid("   ") * 6 + mid("xxx") * 2 + mid("   ") * 5 + mid(".EF") + "\n",
            lines() + mid("ZE.") + mid("   ") * 5 + mid("xxx") + mid("   ") * 7 + mid(".PI") + "\n",
            lines() + mid("CR.") + mid("   ") * 13 + mid(".HO") + "\n" + lines(),
            mid("Player1") + " " * 83 + mid("Player2") + "\n")

        val board = "%s%s%s%s%s%s%s%s%s%s%s%s"
            .format(li(11), li(0), li(1), li(2), li(3), li(4), li(5), li(6), li(7), li(8), li(9), li(10))
        board
    }

    def lines(): String = "=" * 7 * 15 + "\n"


    def mid(x: String): String = "│ " + x + " │"


    def game(): Boolean = {
        println("=============================")
        println("a X Y   = attack")
        println("m X Y   = move")
        println("p       = pass")
        println("exit    = exit game")
        println("=============================")
        //Bord ausgeben
        print("neue Eingabe: ")
        val input = StdIn.readLine().split(" ")

        // X15>B   Y11^Z
        if (input.length == 3) {
            if (input(0) == ("a") && isvalid(input)) {
                //attack(input(1), input(2).toInt)
                println("attack")
            }
            if (input(0) == ("m") && isvalid(input)) {
                //move(input(1), input(2).toInt)
                println("move")
            }
        } else if (input.length == 1) {
            if (input(0) == "p") {
                //pass()
                println("pass")
            }
            if(input(0) == "exit") {
                return false
            }
        } else {
            println("Ungültige Eingabe")
        }
        true
    }

    def isvalid(in : Array[String]) : Boolean = {
        if ((in(1).charAt(0).toInt > 64) && (in(1).charAt(0).toInt < 80)
            && (in(2).toInt > 0) && (in(2).toInt < 12)
            && in(1).length == 1 && in(2).length == 1) {
            true
        } else {
            false
        }
    }
}
