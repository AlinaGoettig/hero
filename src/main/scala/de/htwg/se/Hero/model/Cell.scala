package de.htwg.se.Hero.model

/**
 * Scala project for the game Hero (based on Heroes of Might and Magic III - Fight)
 * @author Ronny Klotz & Alina Göttig
 * @since 9.Nov.2020
 */

import java.util.concurrent.ThreadLocalRandom

case class Cell(name: String, dmg: String, hp: Int, speed: Int, style: Boolean, multiplier: Int, player: Player) {

    def printcell(): String = "│ " + name + " │"

    def attackable(): String = "│>" + name + "<│"

    def attackamount(): Int = {
        val input = dmg.split("-")
        val random = ThreadLocalRandom.current()
        val value = if (input.length == 2) Array(input(0).toInt,input(1).toInt) else Array(input(0).toInt)
        val amount = if (value.length == 2) random.nextInt(value(0),value(1) + 1) else value(0)
        amount
    }

}
