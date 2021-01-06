package de.htwg.se.controller.controllerComponent

import de.htwg.se.model.boardComponent.{BoardInterface, CellInterface}
import de.htwg.se.model.boardComponent.boardImpl.{Board, Cell}
import de.htwg.se.model.playerComponent.Player
import de.htwg.se.util.{CreaturelistIterator, Observable, ObstacleListIterator}

trait ControllerInterface extends Observable  {

    val player: Vector[Player]
    var gamestate: String
    var board: Board

    def inizGame(): Boolean
    def createCreatureList(): List[Cell]
    def fieldnumber(x: String): String = if (x.length == 2) "  " + x + "   " else "   " + x + "   "
    def lines(): String = "=" * 7 * 15 + "\n"
    def getCreature(field: Vector[Vector[Cell]], x: Int, y: Int): Cell = field(x)(y)
    def winnercreatures: List[Cell]
    def active(X: Int, Y: Int): Boolean
    def start(): BoardInterface
    def placeCreatures(board: Board, iterator: CreaturelistIterator): BoardInterface
    def placeObstacles(board: Board, iterator: ObstacleListIterator): BoardInterface
    def next(): CellInterface
    def winner(): Option[Int]
    def attack(Y1: Int, X1: Int, Y2: Int, X2: Int): String
    def replaceCreatureInList(oldC: Cell, newC: Cell): Cell
    def deathcheck(X: Int, Y: Int): Boolean
    def findbasehp(name: String): Int
    def move(X1: Int, Y1: Int, X2: Int, Y2: Int): Vector[Int]
    def prediction(): Vector[Vector[Cell]]
    def clear(): Vector[Vector[Cell]]
    def intpos(creature: String): Vector[Int]
    def position(creature: Cell): Vector[Int]
    def cheatCode(code: Vector[String]): String
    def changeStats(newC: Cell): Boolean
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
