package de.htwg.se.Hero.model

import java.awt.geom.Point2D

import scala.io.StdIn

//noinspection ScalaStyle
object Hero {
    def main(args: Array[String]): Unit = {
        val student = Player("Alina Göttig & Ronny Klotz")
        println(gameName())
        println("Made by " + student.name)
        val board = Board(Array.ofDim[Cell](11,15),Array(Player("RonnyKlotz"),Player("AlinaGöttig")))

        board.start()
        board.prediction(14,5)
        board.printfield()
        board.move(14,5,9,6)
        board.printfield()
        board.prediction(9,6)
        //creatureinfo(0,5,board.field)
        //currentcreatureinfo(14,5,board.field)
        //println(board.printboard(board.field))
        board.printfield()


        //println(line())
        //game()

    }

    def playerside(player: Array[Player]): String = {
        val player1 = "│ " + player(0).name + " │"
        val player2 = "│ " + player(1).name + " │"
        val distance = 15*7 - (player1.length + player2.length)
        val info = lines() + player1 + " " * distance + player2 + "\n" + lines()
        info
    }

    def gameName(): String = {
        "\n ======== Welcome to Hero ======== \n"
    }

    case class Cell(name: String, dmg: Int, hp: Int, speed: Int, style: Boolean, multiplier: Int, player: Player) {
        def printcell(): String = "│ " + name + " │"
    }

    case class Board(field: Array[Array[Cell]],player: Array[Player]) {
        def start(): Array[Array[Hero.Cell]] = {
            fill(field)
            val list = creatureliststart(player)
            for (c <- list) {
                val y = c._1.head
                val x = c._1.last
                field(x)(y) = c._2
            }
            val obs = obstaclelist()
            for (o <- obs) {
                val y = o._1.head
                val x = o._1.last
                field(x)(y) = o._2
            }
            println(playerside(player))
            field
        }
        def move(X1:Int,Y1:Int, X2:Int, Y2:Int) : Array[Array[Cell]] = {
            val tmp = field
            for (i <- 0 to 14) {
                for (j <- 0 to 10) {
                    if(tmp(j)(i).name.equals(" _ ")) {
                        tmp(j)(i) = emptycell()
                    }
                }
            }
            val cret1 = field(Y1)(X1)
            val cret2 = field(Y2)(X2)
            field(Y1)(X1) = cret2
            field(Y2)(X2) = cret1

            field
        }
        def attack (X1:Int, Y1:Int, X2:Int, Y2:Int): String = {
            val attacker = field(Y1)(X1)
            val defender = field(Y2)(X2)
            val dmg = attacker.dmg * attacker.multiplier
            val multicheck = defender.hp - dmg
            val multidif = dmg.toFloat/defender.hp
            val basehp = findbasehp(defender.name,player)
            val multiplier = if(multicheck < 0) defender.multiplier - multidif.toInt else defender.multiplier
            val hp = if (multiplier != defender.multiplier) basehp * (multidif.toInt+1) - dmg else defender.hp - dmg

            if (multiplier <= 0) {
                field(Y2)(X2) = emptycell()
            } else {
                field(Y2)(X2) = Cell(defender.name,defender.dmg,hp,defender.speed,defender.style,multiplier,defender.player)
            }

            dmg.toString
        }
        def prediction (X: Int, Y: Int): Array[Array[Cell]] = {
            for (i <- 0 to 14) {
                for (j <- 0 to 10) {
                    val dist = Math.abs(X - i) + Math.abs(Y - j)
                    if(field(j)(i).name.equals("   ") && dist <= field(Y)(X).speed) {
                        field(j)(i) = marker()
                    }
                }
            }
            field
        }
        def printfield() : String = {
            var text = ""
            for (x <- 0 to 14) {
                text += fieldnumber(x.toString)
            }

            text += "\n" + lines()
            for (i <- 0 to 10) {
                for (j <- 0 to 14) {
                    text += field(i)(j).printcell()
                }
                text += " " + i.toString + "\n" + lines()
            }
            println(text)
            text
        }
    }

    def fill(field: Array[Array[Cell]]): Array[Array[Cell]] = {
        for (i <- 0 to 10) {
            for (j <- 0 to 14) {
                field(i)(j) = emptycell()
            }
        }
        field
    }

    def currentcreatureinfo(Y: Int, X: Int, field: Array[Array[Cell]]): String = {
        val attackstyle = if(field(X)(Y).style) "Ranged" else "Melee"
        val info = "=" * 2 + " Info " + "=" * 97 + "\n" + "Current Unit:\t\t\t\tMultiplier:\t\t\t\tHP:\t\t\t\tDamage:\t\t\t\tAttackstyle:" + "\n" +
            field(X)(Y).name + "\t\t\t\t\t\t\t" + field(X)(Y).multiplier + "\t\t\t\t\t\t" + field(X)(Y).hp + "\t\t\t\t" +
            field(X)(Y).dmg + "\t\t\t\t\t" +attackstyle + "\n" + lines()
        println(info)
        info
    }

    def creatureinfo(Y: Int, X: Int, field: Array[Array[Cell]]): String = {
        val shortline = "=" * 33 + "\n"
        val info =  "=" * 2 + " Info " + "=" * 25 + "\n" + "Unit:\t\tMultiplier:\t\tHP:" + "\n" + field(X)(Y).name + "\t\t\t" +
            field(X)(Y).multiplier + "\t\t\t\t" + field(X)(Y).hp + "\n" + shortline
        println(info)
        info
    }

    def obstacle(): Cell = Cell("XXX",0,0,0,false,0,Player("none"))

    def emptycell(): Cell = Cell("   ",0,0,0,false,0,Player("none"))

    def marker(): Cell = Cell(" _ ",0,0,0,false,0,Player("none"))

    def creatureliststart(player: Array[Player]): Map[Vector[Int], Cell] = {
        val name = namelist()

        val list = Map(
            Vector(0,0) -> Cell(name(0),3,10,3,false,28,player(0)),//5
            Vector(0,1) -> Cell(name(1),3,10,3,false,28,player(0)),//5
            Vector(0,2) -> Cell(name(2),6,10,4,true,18,player(0)),//6
            Vector(0,5) -> Cell(name(3),50,250,12,false,2,player(0)),//18
            Vector(0,8) -> Cell(name(4),25,100,6,false,4,player(0)),//9
            Vector(0,9) -> Cell(name(5),12,24,5,true,6,player(0)),//7
            Vector(0,10) -> Cell(name(6),10,35,4,false,8,player(0)),//6
            Vector(14,0) -> Cell(name(7),2,4,5,false,44,player(1)),//7
            Vector(14,1) -> Cell(name(8),4,13,4,true,20,player(1)),//6
            Vector(14,2) -> Cell(name(9),7,25,5,false,10,player(1)),//8
            Vector(14,5) -> Cell(name(10),40,200,11,false,2,player(1)),//17
            Vector(14,8) -> Cell(name(11),24,90,9,false,4,player(1)),//13
            Vector(14,9) -> Cell(name(12),17,45,5,false,6,player(1)),//7
            Vector(14,10) -> Cell(name(13),9,40,4,false,8,player(1))//6
        )
        list
    }

    def obstaclelist(): Map[Vector[Int], Cell] = {
        val list = Map(
            Vector(6,1) -> obstacle(),
            Vector(7,2) -> obstacle(),
            Vector(5,4) -> obstacle(),
            Vector(6,4) -> obstacle(),
            Vector(7,8) -> obstacle(),
            Vector(8,8) -> obstacle(),
            Vector(6,9) -> obstacle()
        )
        list
    }

    def getCreature(field: Array[Array[Cell]],x: Int, y: Int): Cell = {
        field(x)(y)
    }

    def deathcheck (X:Int, Y:Int, field:Array[Array[Cell]]): Array[Array[Cell]] = {
        if (field(X)(Y).multiplier <= 0) {
            field(X)(Y) = emptycell()
        }
        field
    }

    def namelist(): IndexedSeq[String] =
        IndexedSeq("HA.","MA.","RO.","AN.","CH.","ZE.","CR.",".FA","MAG",".CE",".DE",".EF",".PI",".HO")

    def findbasehp (name: String, player: Array[Player]): Int = {
        val crelist = creatureliststart(player)
        val hp = Array(0)
        for (cell <- creatureliststart(player)) {
            if (cell._2.name.equals(name)) {
                hp(0) = cell._2.hp
            } else {
                hp(0) = 0
            }
        }
        hp(0)
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

    def fieldnumber(x: String): String = {
        val tmp = Array("")
        if (x.length == 2) {
            tmp(0) = "  " + x + "   "
        } else {
            tmp(0) = "   " + x + "   "
        }
        tmp(0)
    }

    def nextplayer(current: String, names: Array[Player]): Player = {
        val next = if (current.contains(names(0))) {
            names(1)
        } else {
            names(0)
        }
        next
    }

    def currentPlayerOutput(player: Player) : String = lines() + "Current Player: " + player + "\n" + lines()

    def game(): Unit = {
        // Print of gamelogo and creator mention
        val student = Player("Alina Göttig & Ronny Klotz")
        println(gameName())
        println("Made by " + student.name)

        println("=============================")
        println("Enter name(player 1):")
        println("=============================")
        val player1 = Player(StdIn.readLine())

        println("=============================")
        println("Enter name(player 2):")
        println("=============================")
        val player2 = Player(StdIn.readLine())

        val player = Array(player1,player2)



        val board = Board(Array.ofDim[Cell](11,15),player)
        board.start()
        board.printfield()
        while(input(board)){
            board.printfield()
        }
    }

    def active(board: Board,X : Int, Y: Int, player:Player) : Boolean = {
        //Wenn X Y Creaturen von p dann true
        if(getCreature(board.field, X,Y).player == player)
            return true
        false
    }

    def input(field:Board) : Boolean = {
        val p = nextplayer(field.player(0).toString, field.player)
        println(currentPlayerOutput(p))
        println("=============================")
        println("a X Y   = attack")
        println("m X Y   = move")
        println("p       = pass")
        println("exit    = exit game")
        println("=============================")
        print("neue Eingabe: ")
        val input = StdIn.readLine().split(" ")

        // X15>B   Y11^Z
        if (input.length == 3) {
            if (input(0) == ("a") && isvalid(input)) {
                //attack(in(1).charAt(0), input(2).toInt)
                if(!active(field, input(1).toInt, input(2).toInt, p))
                    println("attack")
                else
                    println("Creature can't be attacked")
            }
            if (input(0) == ("m") && isvalid(input)) {
                //move(in(1).charAt(0), input(2).toInt)
                if(active(field, input(1).toInt, input(2).toInt, p))
                    println("move")
                else
                    println("Creature can't be moved")
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
