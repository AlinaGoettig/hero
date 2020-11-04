package de.htwg.se.Hero.model

object Hero {
    def main(args: Array[String]): Unit = {
        val student = Player("Alina & Ronny")
        println("Hello," + student.name)
        println(gameName())
        //println(stringTry())
        println(line())
    }

    def gameName(): String = {
        "Hero"
    }

    def stringTry(): String = {
        //12lines+61*-
        val line = "-" * 61
        val s = "|   "
        //11fields+15modul
        val field = Array(
            "|HA." + s * 13 + "|.FA|",
            "|MA." + s * 5 + "|xxx" + s * 7 + "|MAG|",
            "|RO.|..." + s * 5 + "|xxx" + s * 5 + "|..|.CE|",
            s * 15 + "|",
            s * 5 + "|xxx|xxx" + s * 8 + "|",
            "|AN." + s * 13 + "|.DE|",
            (s * 15) + "|",
            (s * 15) + "|",
            "|CH.|..." + s * 5 + "|xxx|xxx" + s * 5 + "|.EF|",
            "|ZE." + s * 5 + "|xxx" + s * 7 + "|.PI|",
            "|CR." + s * 13 + "|.HO|")
        //11lines&fields+1line


        val board = (line + "\n" + field(0) + "\n" + //)*11+line
            line + "\n" + field(1) + "\n" +
            line + "\n" + field(2) + "\n" +
            line + "\n" + field(3) + "\n" +
            line + "\n" + field(4) + "\n" +
            line + "\n" + field(5) + "\n" +
            line + "\n" + field(6) + "\n" +
            line + "\n" + field(7) + "\n" +
            line + "\n" + field(8) + "\n" +
            line + "\n" + field(9) + "\n" +
            line + "\n" + field(10) + "\n" + line)
        board //
    }

    def line(): String = {
        val li = Array(lines() + mid("HA.") + mid("   ") * 13 + mid(".FA") + "\n",
            lines() + mid("MA.") + mid("   ") * 5 + mid("xxx") + mid("   ") * 7 + mid("MAG") + "\n",
            lines() + mid("RO.") + mid("   ") * 6 + mid("xxx") + mid("   ") * 6 + mid(".CE") + "\n",
            lines() + mid("   ") * 15 + "\n",
            lines() + mid("   ") * 5 + mid("xxx") * 2 + mid("   ") * 8 + "\n",
            lines() + mid("AN.") + mid("   ") * 13 + mid(".DE") + "\n",
            lines() + mid("   ") * 15 + "\n",
            lines() + mid("   ") * 15 + "\n",
            lines() + mid("CH.") + mid("   ") * 6 + mid("xxx") * 2 + mid("   ") * 5 + mid(".EF") + "\n",
            lines() + mid("ZE.") + mid("   ") * 5 + mid("xxx") + mid("   ") * 7 + mid(".PI") + "\n",
            lines() + mid("CR.") + mid("   ") * 13 + mid(".HO") + "\n" + lines(),
            mid("Player1") + " " * 83 + mid("Player2") + "\n")

        val board = "%s%s%s%s%s%s%s%s%s%s%s%s"
            .format(li(11), li(0), li(1), li(2), li(3), li(4), li(5), li(6), li(7), li(8), li(9), li(10))
        board
    }

    def lines(): String = "=" * 7 * 15 + "\n"


    def mid(x: String): String = "│ " + x + " │"

}
