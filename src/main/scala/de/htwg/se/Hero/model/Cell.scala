package de.htwg.se.Hero.model

/**
 * Scala project for the game Hero (based on Heroes of Might and Magic III - Fight)
 * @author Ronny Klotz & Alina Göttig
 * @since 9.Nov.2020
 */

import java.util.concurrent.ThreadLocalRandom

/**
 * Class for the board cells. Equals the creatures, empty cells, obstacles and marker
 *
 * @param name of the cell
 * @param dmg amount, random between two values
 * @param hp amount of a single creature
 * @param speed movment speed and attack range
 * @param style true if ranged, false if melee
 * @param multiplier amount of creatures
 * @param player which player own the creature (Player1,Player2,None)
 */
case class Cell(name: String, dmg: String, hp: Int, speed: Int, style: Boolean, multiplier: Int, player: Player) {

    /**
     * Show the cell in board style
     * @return String with basic marker on the side
     */
    def printcell(): String = "│ " + name + " │"

    /**
     * Show the cell in board style with extra attackable marker
     * @return String with basic and attack marker on the side
     */
    def attackable(): String = "│>" + name + "<│"

    /**
     * Calculates the damage of the creatures based on the range
     * @return Int with value of damage
     */
    def attackamount(): Int = {
        val input = dmg.split("-")
        val random = ThreadLocalRandom.current()
        val value = if (input.length == 2) Array(input(0).toInt,input(1).toInt) else Array(input(0).toInt)
        val amount = if (value.length == 2) random.nextInt(value(0),value(1) + 1) else value(0)
        amount
    }

}
