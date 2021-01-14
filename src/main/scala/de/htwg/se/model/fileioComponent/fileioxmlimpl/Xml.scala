package de.htwg.se.model.fileioComponent.fileioxmlimpl

import java.io.{File, PrintWriter}
import de.htwg.se.controller.controllerComponent.ControllerInterface
import de.htwg.se.model.boardComponent.boardImpl.Cell
import de.htwg.se.model.boardComponent.CellInterface
import de.htwg.se.model.fileioComponent.FileIOInterface
import de.htwg.se.model.playerComponent.Player
import scala.collection.mutable.ListBuffer
import scala.xml.{Node, PrettyPrinter}

/**
 * Scala project for the game Hero (based on Heroes of Might and Magic III - Fight)
 * @author Ronny Klotz & Alina Göttig
 * @since 14.Jan.2021
 */

class Xml extends FileIOInterface{

    override def load(controller: ControllerInterface): Unit = {
        controller.clearCreatures()
        val source = scala.xml.XML.loadFile("HeroSave.xml")
        val fieldcreatures = (source \ "field") \ "cell"
        for (c <- fieldcreatures) {
            controller.loadCreature(Integer.parseInt((c \ "x").text),Integer.parseInt((c \ "y").text),extractCreature(c))
        }
        controller.loadCurrentplayer(Player((source \ "cp").head.text))
        controller.loadCurrentCreature(extractCreature(((source \ "cc") \ "cell").head))
        for (c <- (source \ "list") \ "cell") {
            controller.loadList(extractCreature(c))
        }
        val log = (source \ "log").head.text.split("|")
        if (log.nonEmpty && !log(0).equals("")) {
            for (entry <- log) {
                controller.loadLog(entry)
            }
        }

    }

    override def save(controller: ControllerInterface): Unit = {
        val pw = new PrintWriter(new File("HeroSave.xml"))
        val xml = boardToXml(controller)
        pw.write(new PrettyPrinter(80,4).format(xml))
        pw.close()
    }

    def extractCreature (c:Node): CellInterface = {
        Cell((c \ "name").text,
            (c \ "damage").text,
            Integer.parseInt((c \ "health").text),
            Integer.parseInt((c \ "speed").text),
            (c \ "style").text.toBoolean,
            Integer.parseInt((c \ "multiplier").text),
            Player((c \ "player").text)
        )
    }

    def boardToXml(controller: ControllerInterface): Node = {
        var creatures = new ListBuffer[Node]()
        for (x <- 0 to 10) {
            for (y <- 0 to 14) {
                if (!controller.board.field(x)(y).name.equals("   ") &&
                    !controller.board.field(x)(y).name.equals("XXX") &&
                    !controller.board.field(x)(y).name.equals(" _ ")) {
                    creatures += cellToXml(x,y,controller.board.field(x)(y))(typ = true)
                }
            }
        }
        creatures.toList
        val field: Node = <field>{ creatures.map(c => c) }</field>
        val currentplayer: Node = <cp>{ controller.board.currentplayer.name }</cp>
        val currentcreature: Node = <cc>{ cellToXml(0, 0, controller.board.currentcreature)(typ = false) }</cc>
        val list: Node = <list>{ controller.board.list.map(c => cellToXml(0, 0, c)(typ = false)) }</list>
        val log: Node = <log>{ controller.board.log.map(l => l + "|") }</log>

        val board = <board>{ field }{ currentplayer }{ currentcreature }{ list }{ log }</board>
        board
    }

    def cellToXml(Y: Int, X: Int, cell: CellInterface)(typ: Boolean): Node = {
        if (typ){
            <cell>
                <x>{ X }</x>
                <y>{ Y }</y>
                <name>{ cell.name }</name>
                <damage>{ cell.dmg }</damage>
                <health>{ cell.hp }</health>
                <speed>{ cell.speed }</speed>
                <style>{ cell.style }</style>
                <multiplier>{ cell.multiplier }</multiplier>
                <player>{ cell.player.name }</player>
            </cell>
        } else {
            <cell>
                <name>{ cell.name }</name>
                <damage>{ cell.dmg }</damage>
                <health>{ cell.hp }</health>
                <speed>{ cell.speed }</speed>
                <style>{ cell.style }</style>
                <multiplier>{ cell.multiplier }</multiplier>
                <player>{ cell.player.name }</player>
            </cell>
        }
    }

}
