package de.htwg.se.Hero.model

/**
 * Scala project for the game Hero (based on Heroes of Might and Magic III - Fight)
 * @author Ronny Klotz & Alina Göttig
 * @since 9.Nov.2020
 */

import de.htwg.se.Hero.model.Hero.
{active, creatureliststart, emptycell, fieldnumber, findbasehp, lines, marker, obstaclelist, playerside}

/**
 * Class for the Board with all functions
 *
 * @param field 2D Array to represent the Board
 * @param player Array with both player
 * @param currentplayer current activ player
 */
case class Board(field: Array[Array[Cell]],player: Array[Player],currentplayer: Array[Player]) {

    /**
     * Initialize the Board by filling it with all cells
     * @return filled field
     */
    def start(): Array[Array[Cell]] = {
        fill()
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
        println( playerside(player) )
        field
    }

    /**
     * Move a creature from pointA to pointB
     * @param Y1 Start
     * @param X1 Start
     * @param X2 End
     * @param Y2 End
     * @return renewed field
     */
    def move(Y1:Int,X1:Int, X2:Int, Y2:Int) : Array[Array[Cell]] = {
        val cret1 = field(Y1)(X1)
        val cret2 = field(Y2)(X2)
        field(Y1)(X1) = cret2
        field(Y2)(X2) = cret1

        field
    }

    /**
     * Action of one creature attack another
     * @param Y1 Attacker
     * @param X1 Attacker
     * @param X2 Defender
     * @param Y2 Defender
     * @return renewed field
     */
    def attack (Y1:Int, X1:Int, X2:Int, Y2:Int): String = {
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

    /**
     * Prediction where the creature is able to walk
     * @param Y creature
     * @param X creature
     * @return renewed field
     */
    def prediction (Y: Int, X: Int): Array[Array[Cell]] = {
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

    /**
     * Prints the Board into the console
 *
     * @return String with entire entries
     */
    //noinspection ScalaStyle
    def printfield() : String = {
        var text = ""
        for (x <- 0 to 14) {
            text += fieldnumber(x.toString)
        }

        text += "\n" + lines()
        for (i <- 0 to 10) {
            for (j <- 0 to 14) {
                if (!field(i)(j).name.equals("   ") && !field(i)(j).name.equals(" _ ") && !field(i)(j).name.equals("XXX") && !active(this,i,j)) {
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
                        text += field(i)(j).printcell()
                    }
                } else {
                    text += field(i)(j).printcell()
                }
            }
            text += " " + i.toString + "\n" + lines()
        }
        println(text)
        text
    }

    /**
     * Replace prediction marker with emptycells
     * @return renewed field
     */
    def clear():Array[Array[Cell]] = {
        for (i <- 0 to 14) {
            for (j <- 0 to 10) {
                if(field(j)(i).name.equals(" _ ")) {
                    field(j)(i) = emptycell()
                }
            }
        }
        field
    }

    /**
     * Fill the board with empty cells
     * @return renewed field
     */
    def fill(): Array[Array[Cell]] = {
        for (i <- 0 to 10) {
            for (j <- 0 to 14) {
                field(i)(j) = emptycell()
            }
        }
        field
    }

    /**
     * Prints information about current creature (automatic)
     * @param X creature
     * @param Y creature
     * @return String with all informations
     */
    def currentcreatureinfo(X: Int, Y: Int): String = {
        val attackstyle = if(field(X)(Y).style) "Ranged" else "Melee"
        val info = "=" * 2 + " Info " + "=" * 97 + "\n" + "Current Unit:\t\t\t\tMultiplier:\t\t\t\tHP:\t\t\t\tDamage:\t\t\t\tAttackstyle:" + "\n" +
            field(X)(Y).name + "\t\t\t\t\t\t\t" + field(X)(Y).multiplier + "\t\t\t\t\t\t" + field(X)(Y).hp + "\t\t\t\t" +
            field(X)(Y).dmg + " " * (20 - field(X)(Y).dmg.length) + attackstyle + "\n" + lines()
        println(info)
        info
    }

    /**
     * Prints information about asked field/creature (command call)
     * @param Y
     * @param X
     * @return
     */
    def creatureinfo(Y: Int, X: Int): String = {
        val shortline = "=" * 33 + "\n"
        val info =  "=" * 2 + " Info " + "=" * 25 + "\n" + "Unit:\t\tMultiplier:\t\tHP:" + "\n" + field(X)(Y).name + "\t\t\t" +
            field(X)(Y).multiplier + "\t\t\t\t" + field(X)(Y).hp + "\n" + shortline
        println(info)
        info
    }

    /**
     * Gives Vector where the asked creature is in the field
     * @param creature which needs to be searched
     * @return Vector(Int,Int) with the coordinates
     */
    def postition(creature: Cell): Vector[Int] = {
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

