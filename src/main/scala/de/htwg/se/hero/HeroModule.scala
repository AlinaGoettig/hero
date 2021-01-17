package de.htwg.se.hero

import com.google.inject.AbstractModule
import de.htwg.se.hero.controller.controllerComponent._
import de.htwg.se.hero.model.fileioComponent.FileIOInterface
import de.htwg.se.hero.model.fileioComponent.Fileiojsonimpl.Json
import net.codingwell.scalaguice.ScalaModule

/**
 * @author Ronny Klotz & Alina Göttig
 * @since 13.Jan.2021
 */

// ================== Implementation for concrete storage ==================
// =========================================================================

class HeroModule extends AbstractModule with ScalaModule{

    override def configure(): Unit = {

        bind[ControllerInterface].to[ControllerImpl.Controller]

        bind[FileIOInterface].to[Json] // [Json] or [Xml]

    }

}
