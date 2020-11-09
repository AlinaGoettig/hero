package de.htwg.se.Hero.model

import scala.io.StdIn

//noinspection ScalaStyle
object Hero {
    def main(args: Array[String]): Unit = {
        val student = Player("Alina Göttig & Ronny Klotz")
        println(gameName())
        println("Made by " + student.name)
        println(line())

        while(game()){}
    }

    def gameName(): String = {
        "\n ======== Welcome to Hero ======== \n"
    }

    case class Cell(name: String, dmg: Int, hp: Int, speed: Int, style: Boolean, multiplier: Int) {
        def printcell(): String = "│ " + name + " │"
    }

    def obstacle(): Cell = Cell("XXX",0,0,0,false,0)

    def emptycell(): Cell = Cell("   ",0,0,0,false,0)

    def marker(): Cell = Cell(" V ",0,0,0,false,0)

    def creatureliststart(): Map[Vector[Int], Cell] = {
        val name = namelist()
        val list = Map(
            Vector(0,0) -> Cell(name(0),3,10,5,false,28),
            Vector(0,1) -> Cell(name(1),3,10,5,false,28),
            Vector(0,2) -> Cell(name(2),6,10,6,true,18),
            Vector(0,5) -> Cell(name(3),50,250,18,false,2),
            Vector(0,8) -> Cell(name(4),25,100,9,false,4),
            Vector(0,9) -> Cell(name(5),12,24,7,true,6),
            Vector(0,10) -> Cell(name(6),10,35,6,false,8),
            Vector(14,0) -> Cell(name(7),2,4,7,false,44),
            Vector(14,1) -> Cell(name(8),4,13,6,true,20),
            Vector(14,2) -> Cell(name(9),7,25,8,false,10),
            Vector(14,5) -> Cell(name(10),40,200,17,false,2),
            Vector(14,8) -> Cell(name(11),24,90,13,false,4),
            Vector(14,9) -> Cell(name(12),17,45,7,false,6),
            Vector(14,10) -> Cell(name(13),9,40,6,false,8)
        )
        list
    }

    def attack (attacker: Cell, defender: Cell): Cell = {
        val dmg = attacker.dmg * attacker.multiplier
        val multicheck = defender.hp - dmg
        val multiplier = if(multicheck < 0 ) (defender.hp/dmg).toInt else defender.multiplier
        val hp = if (multiplier != defender.multiplier) defender.hp*(defender.hp/dmg).toInt - dmg else defender.hp -dmg

        Cell(defender.name,defender.dmg,hp,defender.speed,defender.style,multiplier)
    }

    def move (creature: Cell, goal: Vector[Int]): String = {
        "worked"
    }

    def namelist(): IndexedSeq[String] =
        IndexedSeq("HA.","MA.","RO.","AN.","CH.","ZE.","CR.",".FA","MAG",".CE",".DE",".EF",".PI",".HO")

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
