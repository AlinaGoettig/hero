package de.htwg.se.aview.swingparts

import java.awt.image.BufferedImage
import java.io.File

import javax.imageio.ImageIO

import scala.swing.{BoxPanel, Graphics2D, Orientation}

class ImagePanel() extends BoxPanel(Orientation.Vertical) {

    var _imagePath = ""
    var bufferedImage:BufferedImage = null

    def imagePath: String = _imagePath

    def imagePath_=(value:String) {
        _imagePath = value
        bufferedImage = ImageIO.read(new File(_imagePath))
    }


    override def paintComponent(g:Graphics2D): Unit = {
        g.drawImage(bufferedImage, 0, 0, null)
    }
}
