package de.htwg.se.model

//noinspection ScalaStyle
case class Board(field: Vector[Vector[Cell]], player: Vector[Player], currentplayer: Player) {

    def currentcreatureinfo(X: Int, Y: Int): String = {
        val attackstyle = if (field(X)(Y).style) "Ranged" else "Melee"
        val info = "=" * 2 + " Info " + "=" * 97 + "\n" + "Current Unit:\t\t\t\tMultiplier:\t\t\t\tHP:\t\t\t\tDamage:\t\t\t\tAttackstyle:" + "\n" +
            field(X)(Y).name + "\t\t\t\t\t\t\t" + field(X)(Y).multiplier + "\t\t\t\t\t\t" + field(X)(Y).hp + "\t\t\t\t" +
            field(X)(Y).dmg + " " * (20 - field(X)(Y).dmg.length) + attackstyle + "\n" + lines()
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
