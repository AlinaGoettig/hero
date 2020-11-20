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
        val name = realname(currentcreature.name)
        "=" * 2 + " Info " + "=" * 97 + "\n" + "Current Unit:\t\t\t\tMultiplier:\t\t\t\tHP:\t\t\t\tDamage:\t\t\t\tAttackstyle:" + "\n" +
            name + " " * (28 - name.length) + currentcreature.multiplier + " " * (24 - currentcreature.multiplier.toString.length) +
            currentcreature.hp + " " * (16 - currentcreature.hp.toString.length) + currentcreature.dmg +
            " " * (20 - currentcreature.dmg.length) + attackstyle + "\n" + lines()
    }

    def creatureinfo(Y: Int, X: Int): String = {
        val shortline = "=" * 105 + "\n"
        val name = realname(field(X)(Y).name)
        val multi = field(X)(Y).multiplier
        val hp = field(X)(Y).hp
        "=" * 2 + " Info " + "=" * 97 + "\n" + "Creature:\t\t\tMultiplier:\t\t\tHP:" + "\n" +
            name + " " * (20 - name.length) + multi + " " * (20 - multi.toString.length) + hp + "\n" + shortline
    }

    def currentplayerinfo(): String = {
        val shortline = "=" * 2 + " Current Player: " + "=" * 86
        shortline + "\n" + currentplayer.name + "\n" + lines()
    }

    def lastlog(): String = {
        val info = if(log.nonEmpty) log.last + "\n" else "\n"
        "==" + " Log: " + "=" * 97 + "\n" + info + lines()
    }

    def realname(name: String): String = {
        name match {
            case "HA." => "Ha_lbinger"
            case ".FA" => "Fa_miliar"
            case "MA." => "Ma_rksman"
            case "MAG" => "Mag_og"
            case "RO." => "Ro_yal Griffin"
            case ".CE" => "Ce_rberus"
            case "AN." => "Arch An_gle"
            case ".DE" => "Arch De_vil"
            case "CH." => "Ch_ampion"
            case ".EF" => "Ef_reet Sultan"
            case "ZE." => "Ze_alot"
            case ".PI" => "Pi_t Lord"
            case "CR." => "Cr_usader"
            case ".HO" => "Ho_rned Demon"
        }
    }

    def lines(): String = "=" * 7 * 15 + "\n"
}
