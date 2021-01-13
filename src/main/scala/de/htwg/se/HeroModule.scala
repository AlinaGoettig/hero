package de.htwg.se

import com.google.inject.AbstractModule
import de.htwg.se.controller.controllerComponent.{ControllerImpl, ControllerInterface}
import net.codingwell.scalaguice.ScalaModule

class HeroModule extends  AbstractModule with ScalaModule{

    override def configure(): Unit = {

        bind[ControllerInterface].to[ControllerImpl.Controller]

    }

}
