package de.htwg.se.hero.model.fileioComponent

import de.htwg.se.hero
import de.htwg.se.hero.controller.controllerComponent.ControllerInterface

/**
 * Scala project for the game hero (based on Heroes of Might and Magic III - Fight)
 * Interface for saving and loading game save files.
 * @author Ronny Klotz & Alina Göttig
 * @since 14.Jan.2021
 */

trait FileIOInterface {

    /**
     * Load all wanted updates to the controller
     */
    def load(controller: ControllerInterface): Boolean

    /**
     * Saves all wanted values of the controller
     */
    def save(controller: ControllerInterface): Unit

}
