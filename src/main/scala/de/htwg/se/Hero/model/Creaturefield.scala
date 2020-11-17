package de.htwg.se.Hero.model

/**
 * Scala project for the game Hero (based on Heroes of Might and Magic III - Fight)
 * @author Ronny Klotz & Alina Göttig
 * @since 9.Nov.2020
 */

case class Creaturefield(field: Vector[Cell], current: Cell, player: Vector[Player]) {

    def next(): Creaturefield = {
        if (field.indexOf(current) + 1 == field.length) {
            if (field(0).multiplier <= 0) {
                next()
            } else {
                Creaturefield(field, field(0), player)
            }
        } else {
            if (field(field.indexOf(current) + 1).multiplier <= 0) {
                next()
            } else {
                Creaturefield(field, field(field.indexOf(current) + 1), player)
            }
        }
    }

    def hasCreatures(player: Player) : Boolean = {
        for (y <- field) {
            if (y.player == player) {
                return true
            }
        }
        false
    }

    def winner(): Int = {
        if (hasCreatures(player(0)) && !hasCreatures(player(1))) {
            1
        } else if (!hasCreatures(player(0)) && hasCreatures(player(1))) {
            2
        } else {
            0
        }
    }

}
