package de.htwg.se.Hero.model

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PlayerSpec extends WordSpec with Matchers {
  "A Player" when { "new" should {
    val player = Player("Test")
    "have a name"  in {
      player.name should be("Test")
    }
    "have a nice String representation" in {
      player.toString should be("Test")
    }
  }}
}
