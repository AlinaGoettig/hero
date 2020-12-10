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

    private var boarddo: List[Board] = Nil
    private var boardre: List[Board] = Nil
    private var undoStack: List[Command]= Nil
    private var redoStack: List[Command]= Nil

    def doStep(command: Command, board : Board): Unit = {
        boarddo = boarddo :+ board
        undoStack = command::undoStack
        command.doStep
    }

    def undoStep(controller: Controller): Unit = {
        undoStack match {
            case  Nil =>
            case head::stack => {
                val board = boarddo.last
                boardre = boardre :+ controller.board
                boarddo = boarddo.dropRight(1)
                controller.board = board
                head.changeStep(board)
                undoStack=stack
                redoStack= head::redoStack
            }
        }

    }

    def redoStep(controller: Controller): Unit = {
        redoStack match {
            case  Nil =>
            case head::stack => {
                val board = boardre.last
                boarddo = boarddo :+ controller.board
                boardre = boardre.dropRight(1)
                controller.board = board
                head.changeStep(board)
                redoStack=stack
                undoStack= head::undoStack
            }
        }

    }
}



