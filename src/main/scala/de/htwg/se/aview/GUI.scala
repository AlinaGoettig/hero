package de.htwg.se.aview

import java.awt.Color

import scala.swing._
import scala.swing.event._
import de.htwg.se.controller.Controller
import de.htwg.se.model.Cell
import de.htwg.se.util._
import javax.swing.ImageIcon
import javax.swing.border.EmptyBorder

class SwingGui(controller: Controller) extends Frame with Observer {
    controller.add(this)
    size = maximumSize

    title = "HERO"
    iconImage = toolkit.getImage("src/main/scala/de/htwg/se/aview/Graphics/Icon.png")
    //icon = new ImageIcon("src/main/scala/de/htwg/se/aview/Graphics/Background.png")
    //background = toolkit.getImage("src/main/scala/de/htwg/se/aview/Graphics/Background.png")


    override def update: Unit = {
        contents = new BoxPanel(Orientation.Vertical) {
            contents += new BoxPanel(Orientation.Vertical) {
                for {i <- 0 to 10} {
                    contents += new BoxPanel(Orientation.Horizontal) {
                        for {j <- 0 to 14} {
                            contents += new CustomButton(j, i, controller.getCreature(controller.board.field, i, j), controller) {
                                //contents += new Button(j.toString + i.toString) {
                                minimumSize = new Dimension(170, 100)
                                maximumSize = new Dimension(170, 100)
                                preferredSize = new Dimension(170, 100)
                                //icon = new ImageIcon("src/main/scala/de/htwg/se/aview/Graphics/Archangle.png")
                                if (controller.getCreature(controller.board.field, i, j).name.equals(" _ ")) {
                                    background = Color.ORANGE
                                } else if (!controller.getCreature(controller.board.field, i, j).name.equals("   ")) {
                                    if (controller.board.currentplayer.equals(controller.getCreature(controller.board.field, i, j).player))
                                        background = Color.WHITE
                                    else
                                        background = new Color(250, 180, 180)
                                } else {
                                    background = new Color(230, 200, 120)
                                }

                                if (controller.getCreature(controller.board.field, i, j).name.equals("XXX")) {
                                    background = Color.LIGHT_GRAY
                                }
                                if (controller.getCreature(controller.board.field, i, j).name.equals(controller.board.currentcreature.name)) {
                                    background = new Color(125, 190, 255)
                                }
                                if (controller.checkattack(Vector("m", j.toString, i.toString))) {
                                    background = Color.RED
                                }
                                listenTo(this)
                            }
                        }
                    }
                }
            }
            contents += new BorderPanel {
                background = Color.BLACK
                add(new Label(controller.board.currentplayer.name + ": " + controller.board.realname(controller.board.currentcreature.name))
                {
                    font = new Font("Arial", 1, 30)
                    foreground = Color.WHITE
                },BorderPanel.Position.West)

                val log = controller.board.log
                add(new Label(if(log.isEmpty)"" else log.last)
                {
                    font = new Font("Arial", 1, 30)
                    foreground = Color.WHITE
                },BorderPanel.Position.Center)

                val pass = new Button("Pass") {
                    background = Color.WHITE
                    font = new Font("Arial", 1, 30)
                    reactions += {
                        case ButtonClicked(e) => {
                            controller.next()
                            controller.prediction()
                            controller.notifyObservers
                        }
                    }
                }
                add(pass,BorderPanel.Position.East)
            }
        }
    }
}

class CustomButton(X: Int, Y: Int, cell: Cell, controller: Controller) extends Button {
    val currentcell = controller.getCreature(controller.board.field,Y,X)
    val cellname = controller.board.realname(cell.name)
    text = "<html><center>" + cellname
    font = new Font("Arial", 1, 18)
    if(!cellname.equals("   ") && !cellname.equals(" _ ") && !cellname.equals("XXX")) {
        text += "<br>" + currentcell.multiplier + "</center></html>";
    }

    reactions += {
        case ButtonClicked(e) => {
            val posi = controller.position(controller.board.currentcreature)
            if (cellname.equals(" _ ")) {
                controller.move(posi(0),posi(1),Y,X)
                next
            } else if (controller.checkattack(Vector("m",X.toString,Y.toString))){
                controller.attack(posi(0),posi(1),Y,X)
                next
            }
        }
    }
    def next: Unit = {
        controller.next()
        controller.prediction()
        controller.notifyObservers
    }
}