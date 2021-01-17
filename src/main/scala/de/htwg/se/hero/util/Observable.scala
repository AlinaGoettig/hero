package de.htwg.se.hero.util

import de.htwg.se.hero.Hero

/**
 * @author Ronny Klotz & Alina Göttig
 * @since 03.Dez.2020
 */

trait Observer {
    def update: Unit
}

class Observable {
    var subscribers: Vector[Observer] = Vector()

    def add(s:Observer): Unit = subscribers=subscribers:+ s

    def remove(s:Observer): Unit = subscribers=subscribers.filterNot(o=>o==s)

    def notifyObservers: Boolean = {
        subscribers.foreach(o=>o.update)
        true
    }
}
