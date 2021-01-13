package de.htwg.se.model.fileioComponent

import de.htwg.se.controller.controllerComponent.ControllerInterface

trait FileIOInterface {

    def load: ControllerInterface
    def save(controller: ControllerInterface): Unit

}
