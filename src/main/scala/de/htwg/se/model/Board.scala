package de.htwg.se.model

/**
 * Scala project for the game Hero (based on Heroes of Might and Magic III - Fight)
 * @author Ronny Klotz & Alina Göttig
 * @since 9.Nov.2020
 */

//noinspection ScalaStyle
case class Board(field: Vector[Vector[Cell]], player: Vector[Player], currentplayer: Player, currentcreature: Cell) {

    def currentcreatureinfo(creature: Cell): String = {
        val attackstyle = if (creature.style) "Ranged" else "Melee"
        val info = "=" * 2 + " Info " + "=" * 97 + "\n" + "Current Unit:\t\t\t\tMultiplier:\t\t\t\tHP:\t\t\t\tDamage:\t\t\t\tAttackstyle:" + "\n" +
            creature.name + "\t\t\t\t\t\t\t" + creature.multiplier + "\t\t\t\t\t\t" + creature.hp + "\t\t\t\t" +
            creature.dmg + " " * (20 - creature.dmg.length) + attackstyle + "\n" + lines()
        info
    }

    def creatureinfo(Y: Int, X: Int): String = {
        val shortline = "=" * 33 + "\n"
        val info = "=" * 2 + " Info " + "=" * 25 + "\n" + "Unit:\t\tMultiplier:\t\tHP:" + "\n" + field(X)(Y).name + "\t\t\t" +
            field(X)(Y).multiplier + "\t\t\t\t" + field(X)(Y).hp + "\n" + shortline
        info
    }

    def lines(): String = "=" * 7 * 15 + "\n"
}
