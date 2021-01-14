package de.htwg.se.model.fileioComponent

import de.htwg.se.controller.controllerComponent.ControllerInterface

/**
 * Scala project for the game Hero (based on Heroes of Might and Magic III - Fight)
 * @author Ronny Klotz & Alina Göttig
 * @since 14.Jan.2021
 */

trait FileIOInterface {

    /**
     * Load all wanted updates to the controller
     */
    def load(controller: ControllerInterface): Unit

    /**
     * Saves all wanted values of the controller
     */
    def save(controller: ControllerInterface): Unit

}
