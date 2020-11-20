package de.htwg.se.model

/**
 * Scala project for the game Hero (based on Heroes of Might and Magic III - Fight)
 * @author Ronny Klotz & Alina Göttig
 * @since 9.Nov.2020
 */

//noinspection ScalaStyle
case class Board(field: Vector[Vector[Cell]], player: Vector[Player], currentplayer: Player, currentcreature: Cell, list: List[Cell], log: List[String]) {

    def currentcreatureinfo(): String = {
        val attackstyle = if (currentcreature.style) "Ranged" else "Melee"
        "=" * 2 + " Info " + "=" * 97 + "\n" + "Current Unit:\t\t\t\tMultiplier:\t\t\t\tHP:\t\t\t\tDamage:\t\t\t\tAttackstyle:" + "\n" +
            currentcreature.name + " " * 25 + currentcreature.multiplier + " " * (24 - currentcreature.multiplier.toString.length) +
            currentcreature.hp + " " * (16 - currentcreature.hp.toString.length) + currentcreature.dmg +
            " " * (20 - currentcreature.dmg.length) + attackstyle + "\n" + lines()
    }

    def creatureinfo(Y: Int, X: Int): String = {
        val shortline = "=" * 33 + "\n"
        "=" * 2 + " Info " + "=" * 25 + "\n" + "Unit:\t\tMultiplier:\t\tHP:" + "\n" + field(X)(Y).name + "\t\t\t" +
            field(X)(Y).multiplier + "\t\t\t\t" + field(X)(Y).hp + "\n" + shortline
    }

    def currentplayerinfo(): String = {
        val shortline = "=" * 2 + " Current Player: " + "=" * 86
        shortline + "\n" + currentplayer.name + "\n" + lines()
    }

    def lastlog(): String = {
        val info = if(log.nonEmpty) log.last + "\n" else "\n"
        "==" + " Log: " + "=" * 97 + "\n" + info + lines()
    }

    def lines(): String = "=" * 7 * 15 + "\n"
}
