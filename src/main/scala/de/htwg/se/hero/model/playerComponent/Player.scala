package de.htwg.se.hero.model.playerComponent

import de.htwg.se.hero.Hero

/**
 * @author Ronny Klotz & Alina Göttig
 * @since 9.Nov.2020
 */

case class Player(name: String) {
    override def toString: String = name
}
