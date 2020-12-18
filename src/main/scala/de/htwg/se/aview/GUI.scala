package de.htwg.se.aview

/**
 * Scala project for the game Hero (based on Heroes of Might and Magic III - Fight)
 * @author Ronny Klotz & Alina Göttig
 * @since 16.Dez.2020
 */

import de.htwg.se.util._
import de.htwg.se.controller.Controller
import de.htwg.se.model.Cell

import scala.swing._
import scala.swing.event._
import java.awt.Color

import de.htwg.se.aview.swingparts.{CustomButton, ImagePanel}
import javax.swing.ImageIcon
import javax.swing.border.EmptyBorder

//noinspection ScalaStyle
class SwingGui(controller: Controller) extends Frame with Observer {

    controller.add(this)
    //size = maximumSize
    minimumSize = new Dimension(1920,1080)
    preferredSize = new Dimension(1920,1080)
    maximumSize = new Dimension(1920,1080)
    resizable = false

    title = "HERO"
    iconImage = toolkit.getImage("src/main/scala/de/htwg/se/aview/Graphics/UI/Icon.png")


    def mainmenu(): Unit = {
        contents = new ImagePanel() {
            imagePath = "src/main/scala/de/htwg/se/aview/Graphics/UI/Menubackground.png"
            contents += new BoxPanel(Orientation.Vertical) {

                opaque = false
                border = new EmptyBorder(0, 450, 20, 0)

                contents += new Label() {
                    icon = new ImageIcon("src/main/scala/de/htwg/se/aview/Graphics/UI/Font.png")
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
                        icon = new ImageIcon("src/main/scala/de/htwg/se/aview/Graphics/UI/Buttonframe.png")
                        reactions += {
                            case ButtonClicked(_) =>
                                controller.gamestate = "gamerun"
                                controller.inizGame()
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
                        icon = new ImageIcon("src/main/scala/de/htwg/se/aview/Graphics/UI/Buttonframe.png")
                    }

                    contents += new Button {
                        text = "Credit"
                        foreground = new Color(200, 200, 200)
                        font = new Font("Arial", 1, 30)
                        focusPainted = false
                        contentAreaFilled = false
                        borderPainted = false
                        horizontalTextPosition = Alignment.Center
                        verticalTextPosition = Alignment.Center
                        icon = new ImageIcon("src/main/scala/de/htwg/se/aview/Graphics/UI/Buttonframe.png")
                        reactions += {
                            case ButtonClicked(_) =>
                                controller.gamestate = "credit"
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
                        icon = new ImageIcon("src/main/scala/de/htwg/se/aview/Graphics/UI/Buttonframe.png")
                        reactions += {
                            case ButtonClicked(_) =>
                                System.exit(1)
                        }
                    }

                    contents += new Label {
                        border = new EmptyBorder(0,155,0,0)
                        foreground = Color.WHITE
                        text = "Version: 1.3 Swing GUI"
                    }

                }
            }

        }
    }

    def credit(): Unit = {
        contents = new ImagePanel() {
            imagePath = "src/main/scala/de/htwg/se/aview/Graphics/UI/Menubackground.png"
            contents += new BorderPanel {
                opaque = false

                val top = new Label() {

                    foreground = new Color(200, 200, 200)
                    horizontalTextPosition = Alignment.Center
                    verticalTextPosition = Alignment.Center
                    font = new Font("Arial", 1, 30)
                    icon = new ImageIcon("src/main/scala/de/htwg/se/aview/Graphics/UI/Buttonframe.png")
                    text = "Credit"
                }
                add(top,BorderPanel.Position.North)

                val middle = new BoxPanel(Orientation.Vertical) {

                    border = new EmptyBorder(50,300,0,300)
                    opaque = false

                    contents += new Label("<html><center>Code written</center></html>") {
                        foreground = Color.WHITE
                        font = new Font("Arial", 1, 30)
                    }
                    contents += new Label("<html><center>Alina Göttig and Ronny Klotz</center></html>") {
                        foreground = Color.WHITE
                        border = new EmptyBorder(0,0,100,0)
                        font = new Font("Arial", 1, 20)
                    }

                    contents += new Label("<html><center>Graphic design</center></html>") {
                        foreground = Color.WHITE
                        font = new Font("Arial", 1, 30)
                    }
                    contents += new Label("<html><center>Ronny Klotz</center></html>") {
                        foreground = Color.WHITE
                        border = new EmptyBorder(0,0,100,0)
                        font = new Font("Arial", 1, 20)
                    }

                    contents += new Label("<html><center>Programs and Language</center></html>") {
                        foreground = Color.WHITE
                        font = new Font("Arial", 1, 30)
                    }
                    contents += new Label() {
                        foreground = Color.WHITE
                        text = "<html><center>IDE: IntelliJ IDEA Community Edition<br>Scala: 2.13.3" +
                            "<br>Java JDK: 1.8.0<br>Sbt: 1.4.5<br><br>Adobe Photoshop CC 2019" +
                            "<br>Marmoset Hexels<br><br><br><br>Project for Software Engineering at HTWG Konstanz AIN</center></html>"
                        font = new Font("Arial", 1, 20)
                    }

                }
                add(middle,BorderPanel.Position.Center)

                val bottom = new Button() {
                    opaque = false
                    contentAreaFilled = false
                    borderPainted = false
                    focusPainted = false
                    foreground = new Color(200, 200, 200)
                    horizontalTextPosition = Alignment.Center
                    verticalTextPosition = Alignment.Center
                    font = new Font("Arial", 1, 30)
                    icon = new ImageIcon("src/main/scala/de/htwg/se/aview/Graphics/UI/Buttonframe.png")
                    text = "Back"

                    reactions += {
                        case ButtonClicked(_) =>
                            controller.gamestate = "mainmenu"
                            controller.notifyObservers
                    }
                }
                add(bottom,BorderPanel.Position.South)
            }

        }

    }

    def gamerun(): Unit = {
        contents = new ImagePanel {
            imagePath = "src/main/scala/de/htwg/se/aview/Graphics/UI/Background.png"
            contents += new BoxPanel(Orientation.Vertical) {
                opaque = false
                contents += new BoxPanel(Orientation.Vertical) {
                    border = new EmptyBorder(46, 82, 0, 40)
                    opaque = false

                    contents += new BoxPanel(Orientation.Vertical) {
                        opaque = false
                        for {i <- 0 to 10} {
                            contents += new BoxPanel(Orientation.Horizontal) {
                                opaque = false
                                for {j <- 0 to 14} {
                                    contents += new CustomButton(j, i, controller.getCreature(controller.board.field, i, j), controller) {

                                        opaque = false
                                        contentAreaFilled = false
                                        borderPainted = false
                                        focusPainted = false
                                        foreground = Color.WHITE

                                        minimumSize = new Dimension(119, 85)
                                        maximumSize = new Dimension(119, 85)
                                        preferredSize = new Dimension(119, 85)

                                        val cell: Cell = controller.getCreature(controller.board.field, i, j)

                                        if (!cell.player.name.equals("none")) {
                                            if (cell.name.equals(controller.board.currentcreature.name)) {
                                                icon = loadcellimage(cellname)(2)
                                            } else if (controller.checkattack(Vector("m", j.toString, i.toString))) {
                                                icon = loadcellimage(cellname)(3)
                                            } else {
                                                icon = loadcellimage(cellname)(1)
                                            }
                                        } else {
                                            if (cell.name.equals(" _ ")) {
                                                icon = new ImageIcon("src/main/scala/de/htwg/se/aview/Graphics/UI/Marker.png")
                                            }
                                        }

                                    }
                                }
                            }
                        }
                    }

                    contents += new BorderPanel {
                        opaque = false

                        add(new Label(controller.board.currentplayer.name + ": "
                            + controller.board.realname(controller.board.currentcreature.name.replace("_",""))) {
                            font = new Font("Arial", 1, 30)
                            border = new EmptyBorder(10,70,0,0)
                            foreground = Color.WHITE
                        }, BorderPanel.Position.West)

                        val log: TextField = new TextField() {
                            opaque = false
                            foreground = Color.WHITE
                            font = new Font("Arial", 1, 20)
                            border = new EmptyBorder(10,300,0,300)
                            columns = 30
                            reactions += {
                                case EditDone(_) =>
                                    if (text.contains("CHEAT") && (text.contains("coconuts") || text.contains("godunit")
                                        || text.contains("feedcreature") || text.contains("handofjustice"))) {
                                        val split = text.split(" ")
                                        if (split.length == 2) {
                                            val code = Vector(split(0),split(1))
                                            controller.cheatCode(code)
                                            controller.notifyObservers
                                        }
                                    }
                            }
                        }

                        val boardlog: List[String] = controller.board.log
                        log.text = if (boardlog.isEmpty) "" else boardlog.last.replace("_","")
                        add(log,BorderPanel.Position.Center)
                        listenTo(log)


                        val passpanel: FlowPanel = new FlowPanel() {
                            opaque = false
                            border = new EmptyBorder(20,0,0,30)
                            contents += new Button("Pass") {
                                background = Color.WHITE
                                font = new Font("Arial", 1, 30)
                                reactions += {
                                    case ButtonClicked(_) =>
                                        if (controller.winner().isDefined){
                                            controller.gamestate = "finished"
                                        } else {
                                            controller.next()
                                            controller.prediction()
                                        }
                                        controller.notifyObservers
                                }
                            }
                        }

                        add(passpanel, BorderPanel.Position.East)

                    }
                }
            }
        }
    }

    def scoreboard(): Unit = {
        contents = new ImagePanel() {
            imagePath = "src/main/scala/de/htwg/se/aview/Graphics/UI/Menubackground.png"

            contents += new BorderPanel() {
                opaque = false
                border = new EmptyBorder(100, 750, 100, 0)

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
                        icon = new ImageIcon("src/main/scala/de/htwg/se/aview/Graphics/UI/Buttonframe.png")
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
                        icon = new ImageIcon("src/main/scala/de/htwg/se/aview/Graphics/UI/Buttonframe.png")
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
                        icon = new ImageIcon("src/main/scala/de/htwg/se/aview/Graphics/UI/Buttonframe.png")
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
        val name = cellname.replaceAll("_","")
        if (mode == 1) {
            new ImageIcon("src/main/scala/de/htwg/se/aview/Graphics/Model/" + name + ".png")
        } else if (mode == 2) {
            new ImageIcon("src/main/scala/de/htwg/se/aview/Graphics/Model/" + name + "activ.png")
        }else {
            new ImageIcon("src/main/scala/de/htwg/se/aview/Graphics/Model/" + name + "attack.png")
        }
    }

    override def update: Unit = {
        controller.gamestate match {
            case "mainmenu" => mainmenu()
            case "credit" => credit()
            case "gamerun" => gamerun()
            case "finished" => scoreboard()
        }
    }

}