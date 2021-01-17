case class Modul(creature: Boolean) {
    def isCreature:Boolean = creature
}

val c = Modul(true)
c.isCreature

val c2 = Modul(false)
c2.isCreature

object Field {
    val size = 10
    var field:Array[Modul] = Array.fill[Modul](10)(Modul(false))
    def setCell(x:Int): Unit = {
        field(x) = Modul(true)
    }
}

val v1 = Vector(1,2)
val v3 = v1 + Vector(2,1).toString()
val v4 = v1.updated(1,3)

val opt = 1+2
opt