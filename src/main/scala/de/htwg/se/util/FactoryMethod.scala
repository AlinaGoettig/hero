package de.htwg.se.util

/**
 * Scala project for the game Hero (based on Heroes of Might and Magic III - Fight)
 *
 * @author Ronny Klotz & Alina Göttig
 * @since 03.Dez.2020
 */

import de.htwg.se.model.boardComponent.boardImpl.Cell
import de.htwg.se.model.playerComponent.Player

trait SpecialCell {}

object CellFactory {

     def apply(s: String): Cell = {
         if (s.equals("marker")) {
             Cell(" _ ", "0", 0, 0, style = false, 0, Player("none"))
         } else if (s.equals("obstacle")) {
             Cell("XXX", "0", 0, 0, style = false, 0, Player("none"))
         } else {
             Cell("   ", "0", 0, 0, style = false, 0, Player("none"))
         }
     }

}
