package de.htwg.se._2.model

/**
 * Scala project for the game Hero (based on Heroes of Might and Magic III - Fight)
 *
 * @author Ronny Klotz & Alina Göttig
 * @since 9.Nov.2020
 */

case class Player(name: String) {
    override def toString: String = name
}
