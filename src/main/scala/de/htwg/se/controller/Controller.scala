package de.htwg.se.controller

/**
 * Scala project for the game Hero (based on Heroes of Might and Magic III - Fight)
 * @author Ronny Klotz & Alina Göttig
 * @since 17.Nov.2020
 */

import de.htwg.se.model.{Board, Cell, Player}
import de.htwg.se.util._

//noinspection ScalaStyle
class Controller() extends Observable {

    val player: Vector[Player] = Vector(Player("Castle"),Player("Inferno"))
    var gamestate = "mainmenu"
    var board: Board = start()
    inizGame()

    // ----------------------------------------------- Inizial Game ----------------------------------------------------

    def inizGame(): Boolean= {
        board = board.copy(board.field,player,player(0),CellFactory(""),createCreatureList())
        val creature = board.list(board.list.indexOf(board.list.last))
        board = board.copy(currentplayer = creature.player, currentcreature = creature)
        notifyObservers
        true
    }

    def createCreatureList(): List[Cell] = {
        val field = board.field
        List(field(0)(0),field(0)(14),field(1)(0),field(1)(14),field(2)(0),field(2)(14),field(5)(0),
            field(5)(14),field(8)(0),field(8)(14),field(9)(0),field(9)(14),field(10)(0),field(10)(14))
    }

    // ----------------------------------------------- Short liner -----------------------------------------------------

    def fieldnumber(x: String): String = if (x.length == 2) "  " + x + "   " else "   " + x + "   "

    def lines(): String = "=" * 7 * 15 + "\n"

    def getCreature(field: Vector[Vector[Cell]], x: Int, y: Int): Cell = field(x)(y)

    def active(X: Int, Y: Int): Boolean =
        if (getCreature(board.field, X, Y).player.name == board.currentplayer.name) true else false

    // --------------------------------------------------- Start -------------------------------------------------------

    def start(): Board = {
        val emptyboard = Board(Vector.fill(11, 15)(CellFactory("")),player,player(0),CellFactory(""),List.empty,List.empty)
        val board = placeCreatures(emptyboard, new CreaturelistIterator)
        board
    }

    def placeCreatures(board: Board, iterator: CreaturelistIterator): Board = {

        val info = iterator.next()
        val creature = info._2
        val coor = info._1

        val creatureadd = board.copy(board.field.updated(coor(1),board.field(coor(1)).updated(coor(0),creature)))

        if(iterator.hasNext) {
            placeCreatures(creatureadd,iterator)
        } else {
            placeObstacles(creatureadd, new ObstacleListIterator)
        }
    }

    def placeObstacles(board: Board, iterator: ObstacleListIterator): Board = {

        val info = iterator.next()
        val obstacle = info._2
        val coor = info._1

        val obstacleadd = board.copy(board.field.updated(coor(1),board.field(coor(1)).updated(coor(0),obstacle)))

        if(iterator.hasNext) {
            placeObstacles(obstacleadd,iterator)
        } else {
            obstacleadd
        }
    }

    // ---------------------------------------- State of game Change ---------------------------------------------------

    def next(): Cell = {
        val list = board.list
        val index = list.indexOf(board.currentcreature) + 1
        if (index == list.length) {
            board = board.copy(currentplayer = list.head.player,currentcreature = list.head)
            if (list.head.multiplier <= 0) {
                next()
            } else {
                list.head
            }
        } else {
            board = board.copy(currentplayer = list(index).player, currentcreature = list(index))
            if (list(index).multiplier <= 0) {
                next()
            } else {
                clear()
                list(index)
            }
        }
    }

    def winner(): Option[Int] = {
        val player1 = board.list.exists(Cell => Cell.player.equals(player.head) && Cell.multiplier > 0)
        val player2 = board.list.exists(Cell => Cell.player.equals(player.last) && Cell.multiplier > 0)
        if (player1 && !player2) {
            gamestate = "finished"
            Some(1)
        } else if (!player1 && player2) {
            gamestate = "finished"
            Some(2)
        } else {
            None
        }
    }

    // --------------------------------------------------- Attack ------------------------------------------------------

    def attack(Y1: Int, X1: Int, Y2: Int, X2: Int): String = {
        val field = board.field
        val attacker = field(Y1)(X1)
        val defender = field(Y2)(X2)

        val dmg = attacker.attackamount() * attacker.multiplier
        val multicheck = defender.hp - dmg
        val multidif = dmg.toFloat / defender.hp
        val basehp = findbasehp(defender.name)
        val multiplier = if (multicheck < 0) defender.multiplier - multidif.toInt else defender.multiplier
        val hp = if (multiplier != defender.multiplier) basehp * (multidif.toInt + 1) - dmg else defender.hp - dmg

        val newCell = Cell(defender.name, defender.dmg, hp, defender.speed, defender.style, multiplier, defender.player)

        replaceCreatureInList(defender,newCell)

        board = board.copy(field.updated(Y2, field(Y2).updated(X2, newCell)))
        val loginfo = List(board.realname(attacker.name) + " dealt " + dmg + " points to "
            + board.realname(defender.name))

        if (deathcheck(Y2,X2)) {
            val renwed = List(loginfo.head + ". The creature got killed!")
            board = board.copy(log = board.log ++ renwed)
        } else {
            board = board.copy(log = board.log ++ loginfo)
        }
        dmg.toString
    }

    def replaceCreatureInList(oldC: Cell, newC: Cell): Cell = {
        val tmp = board.list
        val index = tmp.indexOf(oldC)
        val (left, _ :: right) = tmp.splitAt(index)
        val newList = left ++ List(newC) ++ right
        board = board.copy(list = newList)
        newC
    }

    def deathcheck(X: Int, Y: Int): Boolean = {
        val field = board.field
        if (field(X)(Y).multiplier <= 0 || field(X)(Y).hp < 0) {
            board = board.copy(field.updated(X, field(X).updated(Y, CellFactory(""))))
            true
        } else {
            false
        }
    }

    def findbasehp(name: String): Int = {
        val iterator = new CreaturelistIterator
        val creature = iterator.list.filter(cell => cell._2.name.equals(name))
        creature(0)._2.hp
    }

    // ---------------------------------------------- Board interaction ------------------------------------------------

    def move(X1: Int, Y1: Int, X2: Int, Y2: Int): Vector[Int] = {
        val field = board.field
        val cret1 = field(X1)(Y1)
        val cret2 = field(X2)(Y2)
        board = board.copy(field.updated(X1, field(X1).updated(Y1, cret2)))
        board = board.copy(board.field.updated(X2, board.field(X2).updated(Y2, cret1)))

        Vector(X2,Y2)
    }

    def prediction(): Vector[Vector[Cell]] = {
        val creature = position(board.currentcreature)
        for (j <- 0 to 10) {
            for (i <- 0 to 14) {
                val field = board.field
                val dist = Math.abs(creature(0) - j) + Math.abs(creature(1) - i)
                if (field(j)(i).name.equals("   ") && dist <= field(creature(0))(creature(1)).speed) {
                    board = board.copy(field = field.updated(j, field(j).updated(i, CellFactory("marker"))))
                }
            }
        }
        board.field
    }

    def clear(): Vector[Vector[Cell]] = {
        for (i <- 0 to 14) {
            for (j <- 0 to 10) {
                val field = board.field
                if (field(j)(i).name.equals(" _ ")) {
                    board = board.copy(field = field.updated(j, field(j).updated(i, CellFactory(""))))
                }
            }
        }
        board.field
    }

    def intpos(creature: String): Vector[Int] = {
        val field = board.field
        for (i <- 0 to 10) {
            for (j <- 0 to 14) {
                if (field(i)(j).name.equals(creature)) {
                    return Vector(i, j)
                }
            }
        }
        Vector(-1, -1)
    }

    def position(creature: Cell): Vector[Int] = {
        intpos(creature.name)
    }

    def cheatCode(code: Vector[String]): String = {
         code(1) match {
             case "coconuts" =>
                 val newC = board.currentcreature.copy(speed = 20)
                 changeStats(newC)
                 prediction()
                 val info = List(board.currentplayer + " cheated! Activated infinity movment speed for "
                     + board.realname(board.currentcreature.name))
                 board = board.copy(log = board.log ++ info)
                 info.last
             case "godunit" =>
                 val newC = board.currentcreature.copy(dmg = "1000", hp = 1000, multiplier = 1000, speed = 20)
                 changeStats(newC)
                 prediction()
                 val info = List(board.currentplayer + " cheated! Activated god mode for "
                     + board.realname(board.currentcreature.name))
                 board = board.copy(log = board.log ++ info)
                 info.last
             case "feedcreature" =>
                 val basestats = baseStats()
                 val newC = board.currentcreature.copy(multiplier = basestats(0), hp = basestats(1))
                 changeStats(newC)
                 val info = List(board.currentplayer + " cheated! Set " + board.realname(board.currentcreature.name)
                     + " values back to beginning")
                 board = board.copy(log = board.log ++ info)
                 info.last
             case "handofjustice" =>
                 val enemy = board.list.filter(Cell => !Cell.player.equals(board.currentplayer))
                 for (cell <- enemy) {
                     val newC = cell.copy(multiplier = 0, hp = 0)
                     replaceCreatureInList(cell,newC)
                     val coor = position(cell)
                     board = board.copy(field = board.field.updated(coor(0), board.field(coor(0)).updated(coor(1), CellFactory(""))), currentcreature = newC)
                 }
                 val info = List(board.currentplayer + " cheated! Killed all enemy creatures")
                 board = board.copy(log = board.log ++ info)
                 info.last
         }
    }

    def changeStats(newC: Cell): Boolean = {
        replaceCreatureInList(board.currentcreature,newC)
        val coor = position(board.currentcreature)
        board = board.copy(field = board.field.updated(coor(0), board.field(coor(0)).updated(coor(1), newC)), currentcreature = newC)
        true
    }

    def baseStats(): Vector[Int] = {
        val iterator = new CreaturelistIterator
        val creature = iterator.list.filter(cell => cell._2.name.equals(board.currentcreature.name))
        Vector(creature(0)._2.multiplier,creature(0)._2.hp)
    }

    // ---------------------------------------------- Input checks -----------------------------------------------------

    def checkmove(in:Vector[String]): Boolean =
        if (in(0) == "m" && getCreature(board.field, in(2).toInt, in(1).toInt).name.equals(" _ ")) {
            true
        } else false


    def checkattack(in:Vector[String]) : Boolean = {
        val i = in(2).toInt
        val j = in(1).toInt
        val field = board.field
        if (!field(i)(j).name.equals("   ") && !field(i)(j).name.equals(" _ ") && !field(i)(j).name.equals("XXX")
            && !active(i, j)) {
            if (board.currentcreature.style) {
                return true
            } else if (areacheck(i,j)) {
                return true
            }
        }
        false
    }

    def areacheck (i: Int, j: Int) : Boolean = {
        val field = board.field
        val list : List[Any] = List(if(i - 1 >= 0 && j - 1 >= 0) field(i - 1)(j - 1).name,
                        if(i - 1 >= 0 && j >= 0) field(i - 1)(j).name,
                        if(i - 1 >= 0 && j + 1 < 14) field(i - 1)(j + 1).name,
                        if(i - 1 >= 0 && j >= 0) field(i - 1)(j).name,
                        if(i + 1 < 11 && j >= 0) field(i + 1)(j).name,
                        if(i + 1 < 11 && j - 1 >= 0) field(i + 1)(j - 1).name,
                        if(i >= 0 && j + 1 < 14) field(i)(j + 1).name,
                        if(i + 1 < 11 && j + 1 < 14) field(i + 1)(j + 1).name)
        list.exists(name => name.equals(" _ "))
    }

    // ---------------------------------------------- Output Strings ---------------------------------------------------

    def printfield(): String = {
        val field = board.field
        var text = ""
        for (x <- 0 to 14) {
            text += fieldnumber(x.toString)
        }
        text += "\n" + lines()
        for (i <- 0 to 10) {
            for (j <- 0 to 14) {
                if (!field(i)(j).name.equals("   ") && !field(i)(j).name.equals(" _ ") && !field(i)(j).name.equals("XXX") && !active(i, j)) {
                    if (areacheck(i,j)) {
                        text += field(i)(j).attackable()
                    } else if (board.currentcreature.style) {
                        text += field(i)(j).attackable()
                    }else {
                        text += field(i)(j).toString()
                    }
                } else {
                    text += field(i)(j).toString()
                }

            }
            text += " " + i.toString + "\n" + lines()
        }
        text
    }

    def printSidesStart(): String = {
        val player1 = "| " + player(0).name + " |"
        val player2 = "| " + player(1).name + " |"
        val middle = player1 + " " * (105 - player1.length - player2.length) + player2

        "\n" + lines() + middle + "\n" + lines()
    }

    def output: String = {
        printfield() + "\n" + board.currentplayerinfo() + "\n" + board.currentcreatureinfo() + "\n" + board.lastlog()
    }

    def info(in:Vector[String]) : String = {
        print(board.creatureinfo(in(1).toInt, in(2).toInt))
        board.creatureinfo(in(1).toInt, in(2).toInt)
    }

    def endInfo(playernumber: Int): String = {
        val player = if (playernumber == 1) "Castle" else "Inferno"
        val top = "\n" + "=" * 2 + " Result for the game: " + "=" * 81 + "\n" + player + " won the game !" + "\n" +
            "=" * 2 + " Creatures alive: " + "=" * 85 + "\n"
        val listofliving = board.list.filter(Cell => Cell.multiplier > 0)
        val title = "Name:\t\t\tMultiplier:\t\t\tHealth:\n"
        var middle = ""
        for (cell <- listofliving) {
            val name = board.realname(cell.name)
            val multi = cell.multiplier
            val hp = cell.hp
            middle += name + " " * (16 - name.length) + multi + " " * (20 - multi.toString.length) + hp + "\n"
        }
        top + title + middle + lines()
    }

}
