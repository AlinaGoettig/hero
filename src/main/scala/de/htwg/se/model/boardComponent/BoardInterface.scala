package de.htwg.se.model.boardComponent

import de.htwg.se.model.playerComponent.Player

trait BoardInterface {

    val field: Vector[Vector[CellInterface]]
    val player: Vector[Player]
    val currentplayer: Player
    val currentcreature: CellInterface
    val list: List[CellInterface]
    val log: List[String]

    def currentcreatureinfo(): String
    def creatureinfo(Y: Int, X: Int): String
    def currentplayerinfo(): String
    def lastlog(): String
    def realname(name: String): String = name match {
        case "HA." => "Ha_lberdier"
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
        case "   " => "   "
        case " _ " => "   "
        case "XXX" => "   "
    }
    def lines(): String = "=" * 7 * 15 + "\n"

    def copy(field: Vector[Vector[CellInterface]],
             player: Vector[Player],
             currentplayer: Player,
             currentcreature: CellInterface,
             list: List[CellInterface],
             log: List[String]): BoardInterface

}

trait CellInterface {

    def name: String
    def dmg: String
    def hp: Int
    def speed: Int
    def style: Boolean
    def multiplier: Int
    def player: Player

    def toString(): String
    def attackable(): String
    def attackamount(): Int
    def copy(name: String,
             dmg: String,
             hp: Int,
             speed: Int,
             style: Boolean,
             multiplier: Int,
             player: Player): CellInterface

}
