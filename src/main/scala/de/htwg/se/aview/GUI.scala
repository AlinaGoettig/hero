package de.htwg.se.aview

import scala.swing._
import scala.swing.Swing.LineBorder
import scala.swing.event._
import de.htwg.se.controller.Controller
import de.htwg.se.model.Cell
import scala.io.Source._
import de.htwg.se.util._

class CellClicked(val row: Int, val column: Int) extends Event

class SwingGui(controller: Controller) extends Frame with Observer {
    controller.add(this)

    //listenTo(controller)
    title = "HERO"

    override def update: Unit = {
        contents = new BoxPanel(Orientation.Vertical) {
            for {i <- 0 to 10} {
                contents += new BoxPanel(Orientation.Horizontal) {
                    for {j <- 0 to 14} {
                        contents += new Button(controller.getCreature(controller.board.field, i, j).name) {
                            //contents += new Button(j.toString + i.toString) {
                            minimumSize = new Dimension(100, 100)
                            maximumSize = new Dimension(100, 100)
                            preferredSize = new Dimension(100, 100)
                            if (controller.areacheck(i, j)) {
                                if (controller.getCreature(controller.board.field, i, j).name.equals(" _ ")) {
                                    background = new Color(200, 200, 255)
                                } else if (!controller.getCreature(controller.board.field, i, j).name.equals("   ")) {
                                    background = new Color(50, 200, 255)
                                }/* else if (controller.checkattack(Vector(j.toString, i.toString))) {
                                    background = new Color(255, 100, 120)
                                }*/
                            }
                        }
                    }
                }
            }
        }
        size = new Dimension(1520, 1170)
    }
}