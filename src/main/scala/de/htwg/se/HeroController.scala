package de.htwg.se

import de.htwg.se.Hero.model.{Board, Cell, Player}

object HeroController {

    def obstacle(): Cell = Cell("XXX","0",0,0,false,0,Player("none"))

    def emptycell(): Cell = Cell("   ","0",0,0,false,0,Player("none"))

    def marker(): Cell = Cell(" _ ","0",0,0,false,0,Player("none"))

    def creatureliststart(player: Vector[Player]): Map[Vector[Int], Cell] = Map(
        Vector(0,0) -> Cell("HA.","2-3",10,3,style = false,28,player(0)),//5
        Vector(14,0) -> Cell(".FA","1-2",4,5,style = false,44,player(1)),//7
        Vector(0,1) -> Cell("MA.","4-6",10,3,style = false,28,player(0)),//5
        Vector(14,1) -> Cell("MAG","2-4",13,4,style = true,20,player(1)),//6
        Vector(0,2) -> Cell("RO.","3-6",10,4,style = true,18,player(0)),//6
        Vector(14,2) -> Cell(".CE","2-7",25,5,style = false,10,player(1)),//8
        Vector(0,5) -> Cell("AN.","50",250,12,style = false,2,player(0)),//18
        Vector(14,5) -> Cell(".DE","30-40",200,11,style = false,2,player(1)),//17
        Vector(0,8) -> Cell("CH.","20-25",100,6,style = false,4,player(0)),//9
        Vector(14,8) -> Cell(".EF","16-24",90,9,style = false,4,player(1)),//13
        Vector(0,9) -> Cell("ZE.","10-12",24,5,style = true,6,player(0)),//7
        Vector(14,9) -> Cell(".PI","13-17",45,5,style = false,6,player(1)),//7
        Vector(0,10) -> Cell("CR.","7-10",35,4,style = false,8,player(0)),//6
        Vector(14,10) -> Cell(".HO","7-9",40,4,style = false,8,player(1)))//6

    def obstaclelist(): Map[Vector[Int], Cell] = Map(
        Vector(6,1) -> obstacle(),
        Vector(7,2) -> obstacle(),
        Vector(5,4) -> obstacle(),
        Vector(6,4) -> obstacle(),
        Vector(7,8) -> obstacle(),
        Vector(8,8) -> obstacle(),
        Vector(6,9) -> obstacle())

    def deathcheck (X:Int, Y:Int, field:Vector[Vector[Cell]]): Vector[Vector[Cell]] = {
        if (field(X)(Y).multiplier <= 0) {
            field.updated(X, field(X).updated(Y, emptycell()))
        } else {
            field
        }
    }

    def findbasehp(name: String, player: Vector[Player]): Int = {
        for (cell <- creatureliststart(player)) {
            if (cell._2.name.equals(name)) {
                return cell._2.hp
            }
        }
        0
    }

    def nextplayer(current: String, names: Vector[Player]): Player =
        if (current.equals(names(0).toString)) names(1) else names(0)

    //noinspection ScalaStyle
    def printfield(field: Vector[Vector[Cell]],board: Board): String = {
        var text = ""
        for (x <- 0 to 14) {
            text += fieldnumber(x.toString)
        }

        text += "\n" + lines()
        for (i <- 0 to 10) {
            for (j <- 0 to 14) {
                if (!field(i)(j).name.equals("   ") && !field(i)(j).name.equals(" _ ") && !field(i)(j).name.equals("XXX") && !active(board,i,j)) {
                    if (((i-1 >= 0 && j-1 >= 0) && field(i-1)(j-1).name.equals(" _ ")) ||
                        ((i-1 >= 0 && j >= 0) && field(i-1)(j).name.equals(" _ ")) ||
                        ((i-1 >= 0 && j+1 < 14) && field(i-1)(j+1).name.equals(" _ ")) ||
                        ((i-1 >= 0 && j >= 0) && field(i-1)(j).name.equals(" _ ")) ||
                        ((i+1 < 11 && j >= 0) && field(i+1)(j).name.equals(" _ ")) ||
                        ((i+1 < 11 && j-1 >= 0) && field(i+1)(j-1).name.equals(" _ ")) ||
                        ((i >= 0 && j+1 < 14) && field(i)(j+1).name.equals(" _ ")) ||
                        ((i+1 < 11 && j+1 < 14) && field(i+1)(j+1).name.equals(" _ "))) {
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

    def active(board: Board,X : Int, Y: Int) : Boolean =
        if(getCreature(board.field, X,Y).player.name == board.currentplayer.name) true else false

    def getCreature(field: Vector[Vector[Cell]],x: Int, y: Int): Cell = field(x)(y)

    def fieldnumber(x: String): String = if (x.length == 2) "  " + x + "   " else "   " + x + "   "

    def lines(): String = "=" * 7 * 15 + "\n"

    def fill(player: Vector[Player], field: Vector[Vector[Cell]], nextstart: Nextstart): Vector[Vector[Cell]] = {
        val info = nextstart.next()
        fill(player,field.updated(info._1.head,info._2),nextstart)
    }

    case class Nextstart(player: Vector[Player]) {
        val iterator = creatureliststart(player).iterator
        def next(): (Vector[Int], Cell) = iterator.next()
    }

    def start(player: Vector[Player], field: Vector[Vector[Cell]]): Vector[Vector[Cell]] = {
        val line1 = Vector.fill(11,14)(emptycell())
        fill(player,field, Nextstart(player))
    }

    def move(Y1:Int,X1:Int, X2:Int, Y2:Int, field: Vector[Vector[Cell]]) : Vector[Vector[Cell]] = {
        val cret1 = field(Y1)(X1)
        val cret2 = field(Y2)(X2)
        field(Y1)(X1) = cret2
        field(Y2)(X2) = cret1

        field
    }

    def attack (Y1:Int, X1:Int, X2:Int, Y2:Int, field: Vector[Vector[Cell]], player: Vector[Player]): String = {
        val attacker = field(Y1)(X1)
        val defender = field(Y2)(X2)
        val dmg = attacker.attackamount() * attacker.multiplier
        val multicheck = defender.hp - dmg
        val multidif = dmg.toFloat/defender.hp
        val basehp = findbasehp(defender.name,player)
        val multiplier = if(multicheck < 0) defender.multiplier - multidif.toInt else defender.multiplier
        val hp = if (multiplier != defender.multiplier) basehp * (multidif.toInt + 1) - dmg else defender.hp - dmg

        if (multiplier <= 0) {
            field(Y2)(X2) = emptycell()
        } else {
            field(Y2)(X2) = Cell(defender.name,defender.dmg,hp,defender.speed,defender.style,multiplier,defender.player)
        }

        dmg.toString
    }

    def prediction (Y: Int, X: Int,field: Vector[Vector[Cell]]): Vector[Vector[Cell]] = {
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


    def clear(field: Vector[Vector[Cell]]): Vector[Vector[Cell]] = {
        for (i <- 0 to 14) {
            for (j <- 0 to 10) {
                if(field(j)(i).name.equals(" _ ")) {
                    field(j)(i) = emptycell()
                }
            }
        }
        field
    }

    def postition(creature: Cell, field: Vector[Vector[Cell]]): Vector[Int] = {
        val posi = Array(Vector(0,0))
        for (i <- 0 to 10) {
            for (j <- 0 to 14) {
                if (field(i)(j).equals(creature)) {
                    posi(0) = Vector(i,j)
                }
            }
        }
        posi(0)
    }

}
