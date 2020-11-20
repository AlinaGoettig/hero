package de.htwg.se.controller

/**
 * Scala project for the game Hero (based on Heroes of Might and Magic III - Fight)
 * @author Ronny Klotz & Alina Göttig
 * @since 17.Nov.2020
 */

import de.htwg.se.model.{Board, Cell, Player}
import de.htwg.se.utill.Observable

//noinspection ScalaStyle
class Controller() extends Observable{

    val player: Vector[Player] = Vector(Player("Castle"),Player("Underground"))
    var board: Board = start()
    val creaurelist: Vector[Cell] = createCreatureList();
    inizGame()

    def obstacle: Cell = Cell("XXX", "0", 0, 0, false, 0, Player("none"))

    def emptycell: Cell = Cell("   ", "0", 0, 0, false, 0, Player("none"))

    def marker: Cell = Cell(" _ ", "0", 0, 0, false, 0, Player("none"))

    def creatureliststart(player: Vector[Player]): Vector[(Vector[Int], Cell)] = Vector(
        Vector(0, 0) -> Cell("HA.", "2-3", 10, 3, style = true, 28, player(0)), //5
        Vector(14, 0) -> Cell(".FA", "1-2", 4, 5, style = false, 44, player(1)), //7
        Vector(0, 1) -> Cell("MA.", "4-6", 10, 3, style = true, 28, player(0)), //5
        Vector(14, 1) -> Cell("MAG", "2-4", 13, 4, style = true, 20, player(1)), //6
        Vector(0, 2) -> Cell("RO.", "3-6", 10, 4, style = false, 18, player(0)), //6
        Vector(14, 2) -> Cell(".CE", "2-7", 25, 5, style = false, 10, player(1)), //8
        Vector(0, 5) -> Cell("AN.", "50", 250, 12, style = false, 2, player(0)), //18
        Vector(14, 5) -> Cell(".DE", "30-40", 200, 11, style = false, 2, player(1)), //17
        Vector(0, 8) -> Cell("CH.", "20-25", 100, 6, style = false, 4, player(0)), //9
        Vector(14, 8) -> Cell(".EF", "16-24", 90, 9, style = false, 4, player(1)), //13
        Vector(0, 9) -> Cell("ZE.", "10-12", 24, 5, style = true, 6, player(0)), //7
        Vector(14, 9) -> Cell(".PI", "13-17", 45, 5, style = false, 6, player(1)), //7
        Vector(0, 10) -> Cell("CR.", "7-10", 35, 4, style = false, 8, player(0)), //6
        Vector(14, 10) -> Cell(".HO", "7-9", 40, 4, style = false, 8, player(1))) //6

    def obstaclelist(): Vector[(Vector[Int], Cell)] = Vector(
        Vector(6, 1) -> obstacle,
        Vector(7, 2) -> obstacle,
        Vector(5, 4) -> obstacle,
        Vector(6, 4) -> obstacle,
        Vector(7, 8) -> obstacle,
        Vector(8, 8) -> obstacle,
        Vector(6, 9) -> obstacle)

    def deathcheck(X: Int, Y: Int): Boolean = {
        val field = board.field
        if (field(X)(Y).multiplier <= 0) {
            board = board.copy(field.updated(X, field(X).updated(Y, emptycell)),board.player,board.currentplayer,board.currentcreature)
            true
        } else {
            false
        }
    }

    //
    def createCreatureList(): Vector[Cell] = {
        val field = board.field
        Vector(field(0)(0),field(0)(14),field(1)(0),field(1)(14),field(2)(0),field(2)(14),field(5)(0),
            field(5)(14),field(8)(0),field(8)(14),field(9)(0),field(9)(14),field(10)(0),field(10)(14))
    }

    def next(): Cell = {
        val field = creaurelist
        val index = field.indexOf(board.currentcreature) + 1
        if (index == field.length) {
            board = board.copy(board.field,board.player,field(0).player,field(0))
            if (field(0).multiplier <= 0) {
                next()
            } else {
                field(0)
            }
        } else {
            board = board.copy(board.field,board.player,field(index).player,field(index))
            if (field(index).multiplier <= 0) {
                next()
            } else {
                clear()
                field(index)
            }
        }
    }

    def inizGame(): Boolean= {
        board = board.copy(board.field,board.player,creaurelist(creaurelist.length-1).player,creaurelist(creaurelist.length-1))
        notifyObservers
        true
    }

    def printSidesStart(): String = {
        val player1 = "| " + player(0).name + " |"
        val player2 = "| " + player(1).name + " |"
        val middle = player1 + " " * (105 - player1.length - player2.length) + player2

        lines() + middle + "\n" + lines()
    }

    def findbasehp(name: String): Int = {
        for (cell <- creatureliststart(player)) {
            if (cell._2.name.equals(name)) {
                return cell._2.hp
            }
        }
        0
    }

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
                    if (((i - 1 >= 0 && j - 1 >= 0) && field(i - 1)(j - 1).name.equals(" _ ")) ||
                        ((i - 1 >= 0 && j >= 0) && field(i - 1)(j).name.equals(" _ ")) ||
                        ((i - 1 >= 0 && j + 1 < 14) && field(i - 1)(j + 1).name.equals(" _ ")) ||
                        ((i - 1 >= 0 && j >= 0) && field(i - 1)(j).name.equals(" _ ")) ||
                        ((i + 1 < 11 && j >= 0) && field(i + 1)(j).name.equals(" _ ")) ||
                        ((i + 1 < 11 && j - 1 >= 0) && field(i + 1)(j - 1).name.equals(" _ ")) ||
                        ((i >= 0 && j + 1 < 14) && field(i)(j + 1).name.equals(" _ ")) ||
                        ((i + 1 < 11 && j + 1 < 14) && field(i + 1)(j + 1).name.equals(" _ "))) {
                        text += field(i)(j).attackable()
                    } else {
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

    def winner(): Int = {
        val player1 = creaurelist.filter(Cell => if(Cell.player.equals(player.head) && Cell.multiplier > 0) true else false)
        val player2 = creaurelist.filter(Cell => if(Cell.player.equals(player.last) && Cell.multiplier > 0) true else false)
        if (player1.nonEmpty && player2.isEmpty) {
            1
        } else if (player1.isEmpty && player2.nonEmpty) {
            2
        } else {
            0
        }
    }

    def active(X: Int, Y: Int): Boolean =
        if (getCreature(board.field, X, Y).player.name == board.currentplayer.name) true else false

    def getCreature(field: Vector[Vector[Cell]], x: Int, y: Int): Cell = field(x)(y)

    def fieldnumber(x: String): String = if (x.length == 2) "  " + x + "   " else "   " + x + "   "

    def lines(): String = "=" * 7 * 15 + "\n"

    def move(X1: Int, Y1: Int, X2: Int, Y2: Int): Vector[Vector[Cell]] = {
        val field = board.field
        val cret1 = field(X1)(Y1)
        val cret2 = field(X2)(Y2)
        board = board.copy(field.updated(X1, field(X1).updated(Y1, cret2)),
            board.player, board.currentplayer, board.currentcreature)
        board = board.copy(board.field.updated(X2, board.field(X2).updated(Y2, cret1)),
            board.player, board.currentplayer, board.currentcreature)

        board.field
    }

    def start(): Board = {
        val emptyboard = Board(Vector.fill(11, 15)(emptycell),player,player(0),emptycell)
        val board = placeCreatures(emptyboard,creatureliststart(player),obstaclelist())
        board
    }

    def placeCreatures(board: Board, list: Vector[(Vector[Int], Cell)], obstacles: Vector[(Vector[Int], Cell)]): Board = {

        val coor = list.head._1

        val creatureadd = board.copy(board.field.updated(coor(1),board.field(coor(1)).updated(coor(0),list.head._2))
            ,board.player,board.currentplayer,board.currentcreature)

        if(list.size > 1) {
            placeCreatures(creatureadd,list.slice(1,list.length),obstacles)
        } else {
            placeObstacles(board,obstacles)
        }
    }

    def placeObstacles(board: Board, obstacles: Vector[(Vector[Int], Cell)]): Board = {
        val cooro = obstacles.head._1
        val obstacleadd = board.copy(board.field.updated(cooro(1),board.field(cooro(1)).updated(cooro(0),obstacles.head._2))
            ,board.player,board.currentplayer,board.currentcreature)
        if(obstacles.size > 1) {
            placeObstacles(obstacleadd,obstacles.slice(1,obstacles.length))
        } else {
            obstacleadd
        }
    }

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

        if (multiplier <= 0) {
            board = board.copy(field.updated(Y2, field(Y2).updated(X2, emptycell)),
                board.player, board.currentplayer, board.currentcreature)
        } else {
            board = board.copy(field.updated(Y2, field(Y2).
                updated(X2, Cell(defender.name, defender.dmg, hp, defender.speed, defender.style, multiplier, defender.player))),
                board.player, board.currentplayer, board.currentcreature)
        }

        dmg.toString
    }

    def prediction(): Vector[Vector[Cell]] = {
        val creature = postition(board.currentcreature)
        for (j <- 0 to 10) {
            for (i <- 0 to 14) {
                val field = board.field
                val dist = Math.abs(creature(0) - j) + Math.abs(creature(1) - i)
                if (field(j)(i).name.equals("   ") && dist <= field(creature(0))(creature(1)).speed) {
                    board = Board(field.updated(j, field(j).updated(i, marker)),
                        board.player, board.currentplayer, board.currentcreature)
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
                    board = board.copy(field.updated(j, field(j).updated(i, emptycell)),
                        board.player, board.currentplayer, board.currentcreature)
                }
            }
        }
        board.field
    }

    def endInfo(playernumber: Int): String = {
        val player = if (playernumber == 1) "Castle" else "Underworld"
        val top = "=" * 2 + " Result for the game: " + "=" * 81 + "\n" + player + " won the game !" + "\n" +
            "=" * 2 + " Creatures alive: " + "=" * 85 + "\n"
        val listofliving = creaurelist.filter(Cell => Cell.multiplier > 0)
        val title = "Name:\t\tMultiplier:\t\tHealth:\n"
        var middle = ""
        for (cell <- listofliving) {
            middle += cell.name.replace(".","") + "\t\t\t" + cell.multiplier + "\t\t\t\t" + cell.hp + "\n"
        }
        top + title + middle + lines()
    }

    //
    def postition(creature: Cell): Vector[Int] = {
        val field = board.field
        for (i <- 0 to 10) {
            for (j <- 0 to 14) {
                if (field(i)(j).equals(creature)) {
                    return Vector(i, j)
                }
            }
        }
        Vector(-1,-1)
    }

    def output: String = {
        printfield() + "\n" + board.currentplayerinfo() + "\n" + board.currentcreatureinfo()
    }

    def checkmove(in:Vector[String]): Boolean =
        if (in(0) == "m" && getCreature(board.field, in(2).toInt, in(1).toInt).name.equals(" _ ")) {
            val creature = postition(board.currentcreature)
            move(creature(0), creature(1), in(2).toInt, in(1).toInt)
            true
        } else false


    def checkattack(in:Vector[String]) : Boolean = {
        val i = in(2).toInt
        val j = in(1).toInt
        val field = board.field
        if (!field(i)(j).name.equals("   ") && !field(i)(j).name.equals(" _ ") && !field(i)(j).name.equals("XXX")
            && !active(i, j)) {
            val creature = postition(board.currentcreature)
            if (board.currentcreature.style) {
                attack(creature(0), creature(1), in(2).toInt, in(1).toInt)
                return true;
            } else if (((i - 1 >= 0 && j - 1 >= 0) && field(i - 1)(j - 1).name.equals(" _ ")) ||
                ((i - 1 >= 0 && j >= 0) && field(i - 1)(j).name.equals(" _ ")) ||
                ((i - 1 >= 0 && j + 1 < 14) && field(i - 1)(j + 1).name.equals(" _ ")) ||
                ((i - 1 >= 0 && j >= 0) && field(i - 1)(j).name.equals(" _ ")) ||
                ((i + 1 < 11 && j >= 0) && field(i + 1)(j).name.equals(" _ ")) ||
                ((i + 1 < 11 && j - 1 >= 0) && field(i + 1)(j - 1).name.equals(" _ ")) ||
                ((i >= 0 && j + 1 < 14) && field(i)(j + 1).name.equals(" _ ")) ||
                ((i + 1 < 11 && j + 1 < 14) && field(i + 1)(j + 1).name.equals(" _ "))) {
                attack(creature(0), creature(1), in(2).toInt, in(1).toInt)
                return true
            }
        }
        false
    }

    def info(in:Vector[String]) : String = {
        print(board.creatureinfo(in(1).toInt, in(2).toInt))
        board.creatureinfo(in(1).toInt, in(2).toInt)
    }

}
