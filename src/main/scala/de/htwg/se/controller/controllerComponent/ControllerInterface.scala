package de.htwg.se.controller.controllerComponent

import de.htwg.se.model.boardComponent.{BoardInterface, CellInterface}
import de.htwg.se.model.playerComponent.Player
import de.htwg.se.util.{CreaturelistIterator, Observable, ObstacleListIterator}

trait ControllerInterface extends Observable  {

    def player: Vector[Player]
    var gamestate: String
    var board: BoardInterface

    def inizGame(): Boolean
    def createCreatureList(): List[CellInterface]
    def fieldnumber(x: String): String = if (x.length == 2) "  " + x + "   " else "   " + x + "   "
    def lines(): String = "=" * 7 * 15 + "\n"
    def getCreature(field: Vector[Vector[CellInterface]], x: Int, y: Int): CellInterface = field(x)(y)
    def winnercreatures: List[CellInterface]
    def active(X: Int, Y: Int): Boolean
    def start(): BoardInterface
    def placeCreatures(board: BoardInterface, iterator: CreaturelistIterator): BoardInterface
    def placeObstacles(board: BoardInterface, iterator: ObstacleListIterator): BoardInterface
    def next(): CellInterface
    def winner(): Option[Int]
    def attack(Y1: Int, X1: Int, Y2: Int, X2: Int): String
    def replaceCreatureInList(oldC: CellInterface, newC: CellInterface): CellInterface
    def deathcheck(X: Int, Y: Int): Boolean
    def findbasehp(name: String): Int
    def move(X1: Int, Y1: Int, X2: Int, Y2: Int): Vector[Int]
    def prediction(): Vector[Vector[CellInterface]]
    def clear(): Vector[Vector[CellInterface]]
    def intpos(creature: String): Vector[Int]
    def position(creature: CellInterface): Vector[Int]
    def cheatCode(code: Vector[String]): String
    def changeStats(newC: CellInterface): Boolean
    def baseStats(): Vector[Int]
    def checkmove(in:Vector[String]): Boolean
    def checkattack(in:Vector[String]): Boolean
    def areacheck (i: Int, j: Int) : Boolean
    def printfield(): String
    def printSidesStart(): String
    def output: String
    def info(in:Vector[String]): String
    def endInfo(playernumber: Int): String

}
