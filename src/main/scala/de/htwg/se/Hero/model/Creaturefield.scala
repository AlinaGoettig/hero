package de.htwg.se.Hero.model

/**
 * Scala project for the game Hero (based on Heroes of Might and Magic III - Fight)
 * @author Ronny Klotz & Alina Göttig
 * @since 9.Nov.2020
 */

case class Creaturefield(field: Array[Cell], current: Array[Cell], player: Array[Player]) {

    def next(): Cell = {
        if (field.indexOf(current(0)) + 1 == field.length) {
            current(0) = field(0)
        } else {
            current(0) = field(field.indexOf(current(0)) + 1)
        }
        while (current(0).multiplier <= 0) {
            if (field.indexOf(current(0)) + 1 == field.length) {
                current(0) = field(0)
            } else {
                current(0) = field(field.indexOf(current(0)) + 1)
            }
        }
        current(0)
    }

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
            1
        } else if (!sides(0) && sides(1)) {
            2
        } else {
            0
        }
    }

}
