package de.htwg.se.Hero.model

/**
 * Scala project for the game Hero (based on Heroes of Might and Magic III - Fight)
 * @author Ronny Klotz & Alina Göttig
 * @since 9.Nov.2020
 */

/**
 * Reference list of the creatures on the board
 *
 * @param field list with creatures
 * @param current creature which is activ
 * @param player Array with both players
 */
//noinspection ScalaStyle
case class Creaturefield(field: Array[Cell], current: Array[Cell], player: Array[Player]) {

    /**
     * Change current creature to the next possible
     * @return creature with multiplier > 0
     */
    def next(): Cell = {
        if (field.indexOf(current(0))+1 == field.length) {
            current(0) = field(0)
        } else {
            current(0) = field(field.indexOf(current(0))+1)
        }
        while (current(0).multiplier <= 0) {
            if (field.indexOf(current(0))+1 == field.length) {
                current(0) = field(0)
            } else {
                current(0) = field(field.indexOf(current(0))+1)
            }
        }
        current(0)
    }

    /**
     * Check if one of the player have <= 0 creatures of the board left with multiplier > 0
     * @return Int with information about state. 1 = player 1 won, 2 = player 2 won, 0 = game goes on
     */
    def winner(): Int = {
        val sides = Array(false,false)
        for (y <- field) {
            if (y.player == player(0)) {
                sides(0) = true
            }
            if (y.player == player(1)) {
                sides(1) = true
            }
        }
        if (sides(0) && !sides(1)) {
            return 1
        } else if (!sides(0) && sides(1)) {
            return 2
        } else {
            return 0
        }
    }

}
