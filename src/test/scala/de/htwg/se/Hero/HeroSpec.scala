package de.htwg.se.Hero

import de.htwg.se.Hero.model.Hero.startinfo
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class HeroSpec extends AnyWordSpec with Matchers {

    "A game run" when { "new" should {
        val info = startinfo()
        "have a set of informations"  in {
            info should include("Gameversion")
            info should include("Welcome to Hero")
            info should include("Made by Alina Göttig & Ronny Klotz")
        }
    }}

}
