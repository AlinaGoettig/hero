package de.htwg.se.aview

/**
 * Scala project for the game Hero (based on Heroes of Might and Magic III - Fight)
 * @author Ronny Klotz & Alina Göttig
 * @since 16.Dez.2020
 */

import de.htwg.se.util._
import de.htwg.se.controller.Controller
import de.htwg.se.model.{Cell, Player}

import scala.swing._
import scala.swing.event._
import java.awt.{Color, ComponentOrientation}
import java.awt.image.BufferedImage
import java.io.File

import javax.imageio.ImageIO
import javax.swing.ImageIcon
import javax.swing.border.EmptyBorder

//noinspection ScalaStyle
class SwingGui(controller: Controller) extends Frame with Observer {

    controller.add(this)
    //size = maximumSize
    minimumSize = new Dimension(1920, 1100)
    preferredSize = new Dimension(1920, 1100)
    maximumSize = new Dimension(1920, 1100)
    resizable = false

    title = "HERO"
    iconImage = toolkit.getImage("src/main/scala/de/htwg/se/aview/Graphics/Icon.png")

    def mainmenu(): Unit = {
        contents = new ImagePanel() {
            imagePath = "src/main/scala/de/htwg/se/aview/Graphics/Menubackground.png"
            contents += new BoxPanel(Orientation.Vertical) {

                opaque = false
                border = new EmptyBorder(20, 450, 20, 0)

                contents += new Label() {
                    icon = new ImageIcon("src/main/scala/de/htwg/se/aview/Graphics/Font.png")
                }

                contents += new BoxPanel(Orientation.Vertical) {

                    opaque = false
                    border = new EmptyBorder(20, 270, 20, 0)

                    contents += new Button {
                        text = "New Game"
                        foreground = new Color(200, 200, 200)
                        font = new Font("Arial", 1, 30)
                        focusPainted = false
                        contentAreaFilled = false
                        borderPainted = false
                        horizontalTextPosition = Alignment.Center
                        verticalTextPosition = Alignment.Center
                        icon = new ImageIcon("src/main/scala/de/htwg/se/aview/Graphics/Buttonframe.png")
                        reactions += {
                            case ButtonClicked(_) =>
                                controller.gamestate = "gamerun"
                                controller.notifyObservers
                        }

                    }

                    contents += new Button {
                        text = "Load Game"
                        foreground = new Color(200, 200, 200)
                        font = new Font("Arial", 1, 30)
                        focusPainted = false
                        contentAreaFilled = false
                        borderPainted = false
                        horizontalTextPosition = Alignment.Center
                        verticalTextPosition = Alignment.Center
                        icon = new ImageIcon("src/main/scala/de/htwg/se/aview/Graphics/Buttonframe.png")
                    }

                    contents += new Button {
                        text = "Exit"
                        foreground = new Color(200, 200, 200)
                        font = new Font("Arial", 1, 30)
                        focusPainted = false
                        contentAreaFilled = false
                        borderPainted = false
                        horizontalTextPosition = Alignment.Center
                        verticalTextPosition = Alignment.Center
                        icon = new ImageIcon("src/main/scala/de/htwg/se/aview/Graphics/Buttonframe.png")
                        reactions += {
                            case ButtonClicked(_) =>
                                System.exit(1)
                        }
                    }
                    contents += new Label {
                        this.border = new EmptyBorder(50, 110, 0, 0)
                        foreground = Color.WHITE
                        text = "Code written: Alina Göttig & Ronny Klotz"
                    }

                    contents += new Label {
                        this.border = new EmptyBorder(0, 160, 0, 0)
                        foreground = Color.WHITE
                        text = "Graphics: Ronny Klolz"
                    }

                    contents += new Label {
                        this.border = new EmptyBorder(0, 120, 0, 0)
                        foreground = Color.WHITE
                        text = "Gameversion: 1.3 GUI Implemenation"
                    }

                }
            }

        }
    }

    def gamerun(): Unit = {
        contents = new ImagePanel {
            imagePath = "src/main/scala/de/htwg/se/aview/Graphics/Background.png"
            contents += new BoxPanel(Orientation.Vertical) {
                opaque = false
                contents += new BoxPanel(Orientation.Vertical) {
                    border = new EmptyBorder(225, 54, 0, 50)
                    opaque = false

                    contents += new BoxPanel(Orientation.Vertical) {
                        opaque = false
                        for {i <- 0 to 10} {
                            contents += new BoxPanel(Orientation.Horizontal) {
                                opaque = false
                                for {j <- 0 to 14} {
                                    contents += new CustomButton(j, i, controller.getCreature(controller.board.field, i, j), controller) {
                                        foreground = Color.BLACK
                                        minimumSize = new Dimension(100, 80)
                                        maximumSize = new Dimension(170, 100)
                                        preferredSize = new Dimension(121, 70)
                                        val cell: Cell = controller.getCreature(controller.board.field, i, j)
                                        val currentplayer: Player = controller.board.currentplayer
                                        if (cell.name.equals(" _ ")) {
                                            icon = new ImageIcon("src/main/scala/de/htwg/se/aview/Graphics/Marker.png")
                                            focusPainted = false
                                            contentAreaFilled = false
                                            borderPainted = false
                                            opaque = false
                                        } else if (!cell.name.equals("   ")) {
                                            if (currentplayer.equals(cell.player)) {
                                                background = Color.WHITE
                                            } else {
                                                background = new Color(250, 180, 180)
                                            }
                                        } else {
                                            contentAreaFilled = false
                                            borderPainted = false
                                            opaque = false
                                            background = new Color(230, 200, 120)
                                        }

                                        /*
                                        if (!cell.player.name.equals("none")) {
                                            if (cell.name.equals(controller.board.currentcreature.name)) {
                                                icon = loadcellimage(cellname)(2)
                                            } else if (controller.checkattack(Vector("m", j.toString, i.toString))) {
                                                icon = loadcellimage(cellname)(3)
                                            } else {
                                                icon = loadcellimage(cellname)(1)
                                            }
                                        }
                                         */

                                        if (cell.name.equals("XXX")) {
                                            background = Color.LIGHT_GRAY
                                        }

                                        if (cell.name.equals(controller.board.currentcreature.name)) {
                                            background = new Color(125, 190, 255)
                                        }
                                        if (controller.checkattack(Vector("a", j.toString, i.toString))) {
                                            background = Color.RED
                                        }

                                    }
                                }
                            }
                        }
                    }

                    contents += new BorderPanel {
                        opaque = false

                        add(new Label(controller.board.currentplayer.name + ": " + controller.board.realname(controller.board.currentcreature.name)) {
                            font = new Font("Arial", 1, 30)
                            border = new EmptyBorder(20, 20, 0, 0)
                            foreground = Color.WHITE
                        }, BorderPanel.Position.West)

                        val log: List[String] = controller.board.log
                        add(new Label(if (log.isEmpty) "" else log.last) {
                            border = new EmptyBorder(20, 0, 0, 0)
                            font = new Font("Arial", 1, 30)
                            foreground = Color.WHITE
                        }, BorderPanel.Position.Center)

                        val pass: Button = new Button("Pass") {
                            background = Color.WHITE
                            font = new Font("Arial", 1, 30)
                            reactions += {
                                case ButtonClicked(e) => {
                                    if (controller.winner().isDefined) {
                                        controller.gamestate = "finished"
                                    } else {
                                        controller.next()
                                        controller.prediction()
                                    }
                                    controller.notifyObservers
                                }
                            }
                        }
                        add(pass, BorderPanel.Position.East)

                    }
                }
            }
        }
    }

    def scoreboard(): Unit = {
        contents = new ImagePanel() {
            imagePath = "src/main/scala/de/htwg/se/aview/Graphics/Menubackground.png"

            contents += new BorderPanel() {
                opaque = false
                border = new EmptyBorder(100, 400, 100, 0)

                layout(new BoxPanel(Orientation.Vertical) {
                    opaque = false
                    contents += new Label("WINNER:") {
                        border = new EmptyBorder(0, 150, 0, 0)
                        foreground = Color.WHITE
                        horizontalAlignment = Alignment.Center
                        horizontalTextPosition = Alignment.Center
                        verticalTextPosition = Alignment.Center
                        font = new Font("Arial", 1, 30)
                    }

                    contents += new Label {
                        controller.winner() match {
                            case Some(value) =>
                                if (value == 1) text = "Castle"
                                else text = "Inferno"
                            case None => text = "Winner not found"
                        }
                        foreground = new Color(230, 200, 130)
                        font = new Font("Arial", 1, 30)
                        horizontalTextPosition = Alignment.Center
                        verticalTextPosition = Alignment.Center
                        icon = new ImageIcon("src/main/scala/de/htwg/se/aview/Graphics/Buttonframe.png")
                    }
                }) = BorderPanel.Position.North

                add(new BoxPanel(Orientation.Horizontal) {
                    opaque = false
                    //border = new EmptyBorder(20, 100, 0, 0)
                    border = new EmptyBorder(20, 0, 0, 0)
                    //Name
                    contents += new BoxPanel(Orientation.Vertical) {
                        opaque = false
                        border = new EmptyBorder(20, 0, 0, 20)
                        contents += new Label {
                            controller.winner() match {
                                case Some(value) => text = "Name:"
                                case None => text = "Error: Winner not found"
                            }
                            //border = new EmptyBorder(0, 140, 0, 0)
                            foreground = Color.WHITE
                            font = new Font("Arial", 1, 30)
                        }
                        for (cell <- controller.winnercreatures) {
                            val name = controller.board.realname(cell.name)
                            contents += new Label(name) {
                                //border = new EmptyBorder(0, 140, 0, 0)
                                foreground = Color.WHITE
                                font = new Font("Arial", 1, 30)
                            }
                        }
                    }
                    //Multiplier
                    contents += new BoxPanel(Orientation.Vertical) {
                        opaque = false
                        border = new EmptyBorder(20, 0, 0, 20)
                        contents += new Label {
                            controller.winner() match {
                                case Some(value) => text = "Multiplier:"
                                case None => text = "Error: Winner not found"
                            }
                            //border = new EmptyBorder(0, 140, 0, 0)
                            foreground = Color.WHITE
                            horizontalAlignment = Alignment.Center
                            horizontalTextPosition = Alignment.Center
                            verticalTextPosition = Alignment.Center
                            font = new Font("Arial", 1, 30)
                        }
                        for (cell <- controller.winnercreatures) {
                            val multi = cell.multiplier
                            contents += new Label(multi.toString) {
                                foreground = Color.WHITE
                                font = new Font("Arial", 1, 30)
                            }
                        }
                    }
                    //Health
                    contents += new BoxPanel(Orientation.Vertical) {
                        opaque = false
                        border = new EmptyBorder(20, 0, 0, 20)
                        contents += new Label {
                            controller.winner() match {
                                case Some(value) => text = "Health:"
                                case None => text = "Error: Winner not found"
                            }
                            //border = new EmptyBorder(0, 140, 0, 0)
                            foreground = Color.WHITE
                            horizontalAlignment = Alignment.Center
                            horizontalTextPosition = Alignment.Center
                            verticalTextPosition = Alignment.Center
                            font = new Font("Arial", 1, 30)
                        }
                        for (cell <- controller.winnercreatures) {
                            val hp = cell.hp
                            contents += new Label(hp.toString) {
                                foreground = Color.WHITE
                                font = new Font("Arial", 1, 30)
                            }
                        }
                    }
                }, BorderPanel.Position.Center)

                add(new BoxPanel(Orientation.Vertical) {
                    opaque = false
                    //border = new EmptyBorder(200, 270, 20, 0)
                    border = new EmptyBorder(40, 0, 0, 0)

                    contents += new Button {
                        text = "Menu"
                        foreground = new Color(200, 200, 200)
                        font = new Font("Arial", 1, 30)
                        focusPainted = false
                        contentAreaFilled = false
                        borderPainted = false
                        horizontalTextPosition = Alignment.Center
                        verticalTextPosition = Alignment.Center
                        icon = new ImageIcon("src/main/scala/de/htwg/se/aview/Graphics/Buttonframe.png")
                        reactions += {
                            case ButtonClicked(_) =>
                                controller.gamestate = "mainmenu"
                                controller.notifyObservers
                        }
                    }
                    contents += new Button {
                        text = "Exit"
                        foreground = new Color(200, 200, 200)
                        font = new Font("Arial", 1, 30)
                        focusPainted = false
                        contentAreaFilled = false
                        borderPainted = false
                        horizontalTextPosition = Alignment.Center
                        verticalTextPosition = Alignment.Center
                        icon = new ImageIcon("src/main/scala/de/htwg/se/aview/Graphics/Buttonframe.png")
                        reactions += {
                            case ButtonClicked(_) =>
                                System.exit(1)
                        }
                    }
                    contents += new Label {
                        border = new EmptyBorder(10, 175, 0, 0)
                        foreground = Color.WHITE
                        horizontalTextPosition = Alignment.Center
                        verticalTextPosition = Alignment.Center
                        text = "Thanks for playing!"
                    }

                }, BorderPanel.Position.South)
            }
        }
    }

    def loadcellimage(cellname: String)(mode: Int): ImageIcon = {
        val name = cellname.replaceAll("_", "")
        if (mode == 1) {
            new ImageIcon("src/main/scala/de/htwg/se/aview/Graphics/" + name + ".png")
        } else if (mode == 2) {
            new ImageIcon("src/main/scala/de/htwg/se/aview/Graphics/" + name + "activ.png")
        } else {
            new ImageIcon("src/main/scala/de/htwg/se/aview/Graphics/" + name + "attack.png")
        }
    }

    override def update: Unit = {
        controller.gamestate match {
            case "mainmenu" => mainmenu()
            case "gamerun" => gamerun()
            case "finished" => scoreboard()
        }
    }
}


class ImagePanel() extends BoxPanel(Orientation.Vertical) {

    var _imagePath = ""
    var bufferedImage:BufferedImage = null

    def imagePath = _imagePath

    def imagePath_=(value:String) {
        _imagePath = value
        bufferedImage = ImageIO.read(new File(_imagePath))
    }


    override def paintComponent(g:Graphics2D) = {
        g.drawImage(bufferedImage, 0, 0, null)
    }
}

class CustomButton(X: Int, Y: Int, cell: Cell, controller: Controller) extends Button {

    val currentcell: Cell = controller.getCreature(controller.board.field, Y, X)
    val cellname: String = controller.board.realname(cell.name)

    text = "<html><center>" + cellname
    font = new Font("Arial", 1, 18)
    if (!cell.player.name.equals("none")) {
        text += "<br>" + currentcell.multiplier + "</center></html>";
        tooltip = "Multiplier: " + currentcell.multiplier + " Health: " + currentcell.hp
    }

    reactions += {
        case ButtonClicked(_) => {
            val posi = controller.position(controller.board.currentcreature)
            if (cell.name.equals(" _ ")) {
                controller.move(posi(0), posi(1), Y, X)
                next()
            } else if (controller.checkattack(Vector("a", X.toString, Y.toString))) {
                controller.attack(posi(0), posi(1), Y, X)
                next()
            }
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