package de.htwg.se

import com.google.inject.AbstractModule
import de.htwg.se.controller.controllerComponent.{ControllerImpl, ControllerInterface}
import de.htwg.se.model.fileioComponent.FileIOInterface
import net.codingwell.scalaguice.ScalaModule

// ================== Implementation for concrete storage ==================
import de.htwg.se.model.fileioComponent.fileiojsonimpl.Json
import de.htwg.se.model.fileioComponent.fileioxmlimpl.Xml
// =========================================================================

class HeroModule extends  AbstractModule with ScalaModule{

    override def configure(): Unit = {

        bind[ControllerInterface].to[ControllerImpl.Controller]

        bind[FileIOInterface].to[Xml] // [Json] or [Xml]

    }

}
