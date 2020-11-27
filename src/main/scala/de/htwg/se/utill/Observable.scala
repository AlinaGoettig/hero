package de.htwg.se.utill

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
