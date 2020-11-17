package de.htwg.se.model

import java.util.concurrent.ThreadLocalRandom

case class Cell(name: String, dmg: String, hp: Int, speed: Int, style: Boolean, multiplier: Int, player: Player) {

    override def toString(): String = "│ " + name + " │"

    def attackable(): String = "│>" + name + "<│"

    def attackamount(): Int = {
        val input = Vector(dmg.split("-"))
        val random = ThreadLocalRandom.current()
        val amount = if (input(0).length == 2) random.nextInt(input(0).head.toInt, input(0).last.toInt + 1) else input(0).head.toInt
        amount
    }

}
