package de.htwg.se.util

import scala.util.Try
/**
 * Scala project for the game Hero (based on Heroes of Might and Magic III - Fight)
 * @author Ronny Klotz & Alina Göttig
 * @since 03.Dez.2020
 */

trait Expression {
    def interpret(): Boolean
}

//NONTERMINAL EXPRESSIONS
class Interpreter(input: Vector[String]) extends Expression {
    override def interpret(): Boolean = {
        if(input.length == 3) {
            new LetterAMI(input(0)).interpret && new XVector(input(1)).interpret && new YVector(input(2)).interpret
        } else if(input.length == 2) {
            new Cheat(input(0)).interpret && new CheatCode(input(1)).interpret
        } else if(input.length == 1) {
            new OneWord(input(0)).interpret
        } else {
            false
        }
    }
}

//TERMINAL EXPRESSIONS
class LetterAMI(input: String) extends Expression {
    override def interpret: Boolean = {
        if(input.equals("a") || input.equals("m") || input.equals("i")) {
            true
        } else {
            false
        }
    }
}

class OneWord(input: String) extends Expression {
    override def interpret: Boolean = {
        if(input.equals("p") || input.equals("exit") || input.equals("undo")) {
            true
        } else {
            false
        }
    }
}

class Cheat(input: String) extends Expression {
    override def interpret: Boolean = {
        if(input.equals("CHEAT")) {
            true
        } else {
            false
        }
    }
}

class CheatCode(input: String) extends Expression {
    override def interpret: Boolean = {
        if(input.equals("coconuts") || input.equals("godunit") ||
            input.equals("feedcreature") || input.equals("handofjustice")) {
            true
        } else {
            false
        }
    }
}

class XVector(input: String) extends Expression {
    override def interpret: Boolean = {
        if (Try(input.toInt).isSuccess) {
            val intInput = input.toInt
            if(0 <= intInput && intInput <= 14) {
                true
            } else {
                false
            }
        } else {false}
    }
}

class YVector(input: String) extends Expression {
    override def interpret: Boolean = {
        if (Try(input.toInt).isSuccess) {
            val intInput = input.toInt
            if(0 <= intInput && intInput <= 10) {
                true
            } else {
                false
            }
        } else {false}
    }
}
