package de.htwg.se.utill

/**
 * Scala project for the game Hero (based on Heroes of Might and Magic III - Fight)
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
