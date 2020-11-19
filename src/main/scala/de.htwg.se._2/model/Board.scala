package de.htwg.se._2.model

/**
 * Scala project for the game Hero (based on Heroes of Might and Magic III - Fight)
 * @author Ronny Klotz & Alina Göttig
 * @since 9.Nov.2020
 */

//noinspection ScalaStyle
case class Board(field: Vector[Vector[Cell]], player: Vector[Player], currentplayer: Player, currentcreature: Cell) {
    val groeße = Vector(4,2)

    def currentcreatureinfo(): String = {
        val attackstyle = if (currentcreature.style) "Ranged" else "Melee"
        val info = "=" * 2 + " Info " + "=" * 97 + "\n" + "Current Unit:\t\t\t\tMultiplier:\t\t\t\tHP:\t\t\t\tDamage:\t\t\t\tAttackstyle:" + "\n" +
            currentcreature.name + "\t\t\t\t\t\t\t" + currentcreature.multiplier + "\t\t\t\t\t\t" + currentcreature.hp + "\t\t\t\t" +
            currentcreature.dmg + " " * (20 - currentcreature.dmg.length) + attackstyle + "\n" + lines()
        info
    }

    def creatureinfo(Y: Int, X: Int): String = {
        val shortline = "=" * 33 + "\n"
        val info = "=" * 2 + " Info " + "=" * 25 + "\n" + "Unit:\t\tMultiplier:\t\tHP:" + "\n" + field(X)(Y).name + "\t\t\t" +
            field(X)(Y).multiplier + "\t\t\t\t" + field(X)(Y).hp + "\n" + shortline
        info
    }

    def currentplayerinfo(): String = {
        val shortline = "=" * 2 + " Current Player: " + "=" * 86
        val info = shortline + "\n" + currentplayer.name + "\n" + lines()
        info
    }

    def lines(): String = "=" * 7 * groeße(0) + "\n"
}
