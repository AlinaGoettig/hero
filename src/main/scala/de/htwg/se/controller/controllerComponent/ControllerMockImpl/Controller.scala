package de.htwg.se.controller.controllerComponent.ControllerMockImpl

import de.htwg.se.controller.controllerComponent.ControllerInterface
import de.htwg.se.model.boardComponent.boardImpl.Board
import de.htwg.se.model.boardComponent.{BoardInterface, CellInterface}
import de.htwg.se.model.playerComponent.Player
import de.htwg.se.util.{CellFactory, CreaturelistIterator, ObstacleListIterator}

class Controller(var board: BoardInterface, var cell: CellInterface) extends ControllerInterface {

    override def player: Vector[Player] = Vector(Player("Castle"),Player("Inferno"))
    var gamestate: String = "mainmenu"
    board = Board(Vector.fill(11, 15)(CellFactory("")),player,player(0),CellFactory(""),List.empty,List.empty)
    cell = CellFactory("")

    override def inizGame(): Boolean = false

    override def createCreatureList(): List[CellInterface] = List(cell)

    override def winnercreatures: List[CellInterface] = List(cell)

    override def active(X: Int, Y: Int): Boolean = false

    override def start(): BoardInterface = board

    override def placeCreatures(board: BoardInterface, iterator: CreaturelistIterator): BoardInterface = board

    override def placeObstacles(board: BoardInterface, iterator: ObstacleListIterator): BoardInterface = board

    override def next(): CellInterface  = cell

    override def winner(): Option[Int] = Option(1)

    override def attack(Y1: Int, X1: Int, Y2: Int, X2: Int): String = ""

    override def replaceCreatureInList(oldC: CellInterface, newC: CellInterface): CellInterface = cell

    override def deathcheck(X: Int, Y: Int): Boolean = false

    override def findbasehp(name: String): Int = 0

    override def move(X1: Int, Y1: Int, X2: Int, Y2: Int): Vector[Int] = Vector(1)

    override def prediction(): Vector[Vector[CellInterface]] = Vector(Vector(cell))

    override def clear(): Vector[Vector[CellInterface]] = Vector(Vector(cell))

    override def intpos(creature: String): Vector[Int] = Vector(1)

    override def position(creature: CellInterface): Vector[Int] = Vector(1)

    override def cheatCode(code: Vector[String]): String = ""

    override def changeStats(newC: CellInterface): Boolean = false

    override def baseStats(): Vector[Int] = Vector(1)

    override def checkmove(in:Vector[String]): Boolean = false

    override def checkattack(in:Vector[String]): Boolean = false

    override def areacheck (i: Int, j: Int) : Boolean = false

    override def printfield(): String = ""

    override def printSidesStart(): String = ""

    override def output: String = ""

    override def info(in:Vector[String]): String = ""

    override def endInfo(playernumber: Int): String = ""

}
