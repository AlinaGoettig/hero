package de.htwg.se.util

import de.htwg.se.controller.Controller
import de.htwg.se.model.Board

trait Command {
    def doStep:Unit
    def undoStep(board: Board):Unit
    def redoStep:Unit
}

class SetCommand(Command: Vector[String], controller: Controller) extends Command {
    override def doStep: Unit = {
        val current = controller.intpos(controller.board.currentcreature.name)
        if (Command.head.equals("m")) {
            controller.move(current(0),current(1),Command(2).toInt,Command(1).toInt)
        } else if (Command.head.equals("a")) {
            controller.attack(current(0),current(1),Command(2).toInt,Command(1).toInt)
        }
    }
    override def undoStep(board: Board): Unit = controller.board = board
    override def redoStep: Unit = {
        val current = controller.intpos(controller.board.currentcreature.name)
        if (Command.head.equals("m")) {
            controller.move(current(0),current(1),Command(1).toInt,Command(2).toInt)
        } else if (Command.head.equals("a")) {
            val defender = Vector(Command(1),Command(2))
            controller.attack(current(0),current(1),defender(0).toInt,defender(1).toInt)
        }
    }
}

//noinspection ScalaStyle
class UndoManager {
    private var boards: List[Board] = Nil
    private var undoStack: List[Command]= Nil
    private var redoStack: List[Command]= Nil

    def doStep(command: Command, board : Board): Unit = {
        boards = boards :+ board
        undoStack = command::undoStack
        command.doStep
    }

    def undoStep(controller: Controller): Unit = {
        undoStack match {
            case  Nil =>
            case head::stack => {
                val board = boards.last
                boards = boards.filter(boardin => !boardin.equals(board))
                controller.board = board
                head.undoStep(board)
                undoStack=stack
                redoStack= head::redoStack
            }
        }

    }
}



