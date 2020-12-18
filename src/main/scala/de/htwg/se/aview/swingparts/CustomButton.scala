package de.htwg.se.aview.swingparts

import de.htwg.se.controller.Controller
import de.htwg.se.model.Cell

import scala.swing.{Alignment, Button, Font}
import scala.swing.event.ButtonClicked

class CustomButton(X: Int, Y: Int, cell: Cell, controller: Controller) extends Button {

    val currentcell: Cell = controller.getCreature(controller.board.field, Y, X)
    val cellname: String = controller.board.realname(cell.name)

    font = new Font("Arial", 1, 14)
    if (!cell.player.name.equals("none")) {
        horizontalTextPosition = Alignment.Right
        verticalTextPosition = Alignment.Top
        text = currentcell.multiplier.toString
        val style = if(cell.style) "Ranged" else "Melee"
        tooltip = "<html>Player: " + cell.player.name + "<br>Name: " + cellname.replaceAll("_","") +
            "<br>Damage: " + cell.dmg + "<br>Attackstyle: " + style +
            "<br>Multiplier: " + currentcell.multiplier + "<br>Health: " + currentcell.hp + "</html>"
    } else {
        text = "<html><center>" + cellname
    }

    reactions += {
        case ButtonClicked(_) =>
            val posi = controller.position(controller.board.currentcreature)
            if (cell.name.equals(" _ ")) {
                controller.move(posi(0), posi(1), Y, X)
                next()
            } else if (controller.checkattack(Vector("a", X.toString, Y.toString))) {
                controller.attack(posi(0), posi(1), Y, X)
                next()
            }
    }

    def next(): Unit = {
        if (controller.winner().isDefined){
            controller.gamestate = "finished"
        } else {
            controller.next()
            controller.prediction()
        }
        controller.notifyObservers
    }

}
