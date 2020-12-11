package de.htwg.se.util

import de.htwg.se.controller.Controller
import de.htwg.se.model.Board

trait Command {
    def doStep:Unit
    def changeStep(board: Board):Unit
}

class SetCommand(Command: Vector[String], controller: Controller) extends Command {
    override def doStep: Unit = {
        val current = controller.intpos(controller.board.currentcreature.name)
        if (Command.head.equals("m")) {
            controller.move(current(0), current(1), Command(2).toInt, Command(1).toInt)
        } else if (Command.head.equals("a")) {
            controller.attack(current(0), current(1), Command(2).toInt, Command(1).toInt)
        }
    }
    override def changeStep(board: Board): Unit = controller.board = board
}

//noinspection ScalaStyle
class UndoManager {

    private var boarddo: List[(Board,Command)] = List()
    private var boardre: List[(Board,Command)] = List()

    def doStep(command: Command, board : Board): Unit = {
        boarddo = boarddo :+ (board,command)
        command.doStep
    }

    def undoStep(controller: Controller): Unit = {
        boarddo match {
            case  Nil =>
            case head:: _ => {
                val board = boarddo.last
                boardre = boardre :+ (controller.board,head._2)
                boarddo = boarddo.dropRight(1)
                controller.board = board._1
                head._2.changeStep(board._1)
            }
        }

    }

    def redoStep(controller: Controller): Unit = {
        boardre match {
            case  Nil =>
            case head:: _ => {
                val board = boardre.last
                boarddo = boarddo :+ (controller.board,head._2)
                boardre = boardre.dropRight(1)
                controller.board = board._1
                head._2.changeStep(board._1)
            }
        }

    }
}



