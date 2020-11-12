package de.htwg.se.Hero.model

import java.util.concurrent.ThreadLocalRandom

import scala.io.StdIn
import scala.util.control.Breaks._

//noinspection ScalaStyle
object Hero {

    // Main for execute the game
    def main(args: Array[String]): Unit = {

        // game function
        game()

    }

    /**
     * Information which side the players are. Used after first fieldprint
     * @param player Array with both players
     * @return String with information
     */
    def playerside(player: Array[Player]): String = {
        val player1 = "│ " + player(0).name + " │"
        val player2 = "│ " + player(1).name + " │"
        val distance = 15*7 - (player1.length + player2.length)
        val info = lines() + player1 + " " * distance + player2 + "\n" + lines()
        info
    }

    /**
     * Welcome the players after game start
     * @return String with font
     */
    def gameName(): String = {
        "\n ======== Welcome to Hero ======== \n"
    }

    /**
     * An obstacle which the creatures cant interact with
     * @return Cell
     */
    def obstacle(): Cell = Cell("XXX","0",0,0,false,0,Player("none"))

    /**
     * An empty cell for the board
     * @return Cell
     */
    def emptycell(): Cell = Cell("   ","0",0,0,false,0,Player("none"))

    /**
     * An marker for the prediction of current creature movmentrange
     * @return Cell
     */
    def marker(): Cell = Cell(" _ ","0",0,0,false,0,Player("none"))

    /**
     * Map with coordinates -> creature cells for initialize the board
     * @param player Array of both players
     * @return Map with entries
     */
    def creatureliststart(player: Array[Player]): Map[Vector[Int], Cell] = {
        Map(
            Vector(0,0) -> Cell("HA.","2-3",10,3,style = false,28,player(0)),//5
            Vector(14,0) -> Cell(".FA","1-2",4,5,style = false,44,player(1)),//7
            Vector(0,1) -> Cell("MA.","4-6",10,3,style = false,28,player(0)),//5
            Vector(14,1) -> Cell("MAG","2-4",13,4,style = true,20,player(1)),//6
            Vector(0,2) -> Cell("RO.","3-6",10,4,style = true,18,player(0)),//6
            Vector(14,2) -> Cell(".CE","2-7",25,5,style = false,10,player(1)),//8
            Vector(0,5) -> Cell("AN.","50",250,12,style = false,2,player(0)),//18
            Vector(14,5) -> Cell(".DE","30-40",200,11,style = false,2,player(1)),//17
            Vector(0,8) -> Cell("CH.","20-25",100,6,style = false,4,player(0)),//9
            Vector(14,8) -> Cell(".EF","16-24",90,9,style = false,4,player(1)),//13
            Vector(0,9) -> Cell("ZE.","10-12",24,5,style = true,6,player(0)),//7
            Vector(14,9) -> Cell(".PI","13-17",45,5,style = false,6,player(1)),//7
            Vector(0,10) -> Cell("CR.","7-10",35,4,style = false,8,player(0)),//6
            Vector(14,10) -> Cell(".HO","7-9",40,4,style = false,8,player(1))//6
        )
    }

    /**
     * Map with coordinates -> obstacle cells for initialize the board
     * @return Map with entries
     */
    def obstaclelist(): Map[Vector[Int], Cell] = {
        val list = Map(
            Vector(6,1) -> obstacle(),
            Vector(7,2) -> obstacle(),
            Vector(5,4) -> obstacle(),
            Vector(6,4) -> obstacle(),
            Vector(7,8) -> obstacle(),
            Vector(8,8) -> obstacle(),
            Vector(6,9) -> obstacle()
        )
        list
    }

    /**
     * Get creature with coordinates in the field
     * @param field of the board
     * @param x Coordinate
     * @param y Coordinate
     * @return Cell of the Vector
     */
    def getCreature(field: Array[Array[Cell]],x: Int, y: Int): Cell = {
        field(x)(y)
    }

    /**
     * Checks if creature is dead and replace them with empty cell if needed
     * @param X Creature
     * @param Y Creature
     * @param field of the board
     * @return renewed field
     */
    def deathcheck (X:Int, Y:Int, field:Array[Array[Cell]]): Array[Array[Cell]] = {
        if (field(X)(Y).multiplier <= 0) {
            field(X)(Y) = emptycell()
        }
        field
    }

    /**
     * Give base health points of creature
     * @param name of the creature
     * @param player for tunneling
     * @return Int of creature
     */
    def findbasehp(name: String, player: Array[Player]): Int = {
        val cret = creatureliststart(player)
        val hp = Array(0)

        for (cell <- cret) {
            if (cell._2.name.equals(name)) {
                hp(0) = cell._2.hp
                return hp(0)
            } else {
                hp(0) = 0
            }
        }
        hp(0)
    }

    /**
     * For printing lines
     * @return String with line
     */
    def lines(): String = "=" * 7 * 15 + "\n"

    /**
     * For printing top raw of field number
     * @param x the Number to print
     * @return String adapted on length of the number
     */
    def fieldnumber(x: String): String = {
        if (x.length == 2) {
             "  " + x + "   "
        } else {
            "   " + x + "   "
        }
    }

    /**
     * Gives the next player
     * @param current is current player
     * @param names array of both player
     * @return the other player (player1,player2)
     */
    def nextplayer(current: String, names: Array[Player]): Player = {
        val next = if (current.equals(names(0).toString)) {
            names(1)
        } else {
            names(0)
        }
        next
    }

    /**
     * For printing current player
     * @param player class for the name
     * @return String with lines and name
     */
    def currentPlayerOutput(player: Player) : String = lines() + "Current Player: " + player + "\n" + lines()

    /**
     * Game procedure
     * @return information about winner
     */
    def game(): String = {

        // Print of gamelogo and creator mention
        println(gameName())
        println("Made by Alina Göttig & Ronny Klotz\n")

        println("=============================")
        println("Enter Player 1 (Castle):")
        println("=============================")
        val player1 = Player(StdIn.readLine())

        println("=============================")
        println("Enter Player 2 (Underworld):")
        println("=============================")
        val player2 = Player(StdIn.readLine())
        println()

        // Initialise board and playerlist
        val player = Array(player1,player2)
        val board = Board(Array.ofDim[Cell](11,15),player,Array(player(1)))

        // Fills the board and create a refernece list
        board.start()
        val field = board.field
        val creatures = Array(field(0)(0),field(0)(14),field(1)(0),field(1)(14),field(2)(0),field(2)(14),field(5)(0),
            field(5)(14),field(8)(0),field(8)(14),field(9)(0),field(9)(14),field(10)(0),field(10)(14))

        val creatureTurn = Creaturefield(creatures,Array(creatures(creatures.length-1)),player)

        while(command(creatureTurn.next(),board)){
            val winner = creatureTurn.winner()
            if (winner == 1) {
                return printwin(creatureTurn.field,player1)
            } else if (winner == 2) {
                return printwin(creatureTurn.field,player2)
            }
        }
        return "\n" + lines() + "Game got closed!" + "\n" + lines()

    }

    /**
     * For printing the winner information
     * @param field of board
     * @param player which won the game
     * @return top layer of winner output
     */
    def printwin(field: Array[Cell],player: Player): String = {
        val top = lines() + player.name + " won the game!\n" + lines()
        val middle = "Remaining creatures of winner\n\nName:\t\t\tMultiplier:\t\t\tHealth:\n"
        println(top+middle)
        for(c <- field) {
            if (c.multiplier > 0) {
                println(c.name + "\t\t\t" + c.multiplier + "\t\t\t" + c.hp)
            }
        }
        println(lines())
        top
    }

    /**
     * Checks if the given creatures get owned by the current player
     * @param board class for field
     * @param X coordinate
     * @param Y coordinate
     * @return true if he ownes, false if not
     */
    def active(board: Board,X : Int, Y: Int) : Boolean = {
        // Wenn X Y Creaturen von p dann true
        if(getCreature(board.field, X,Y).player.name == board.currentplayer(0).name)
            return true
        false
    }

    /**
     * Process player inputs
     * @param creature is the current creature
     * @param field of the board
     * @return false if player want to exit, true if an import appears
     */
    def command(creature: Cell, field:Board) : Boolean = {
        val p = Array(nextplayer(field.currentplayer(0).toString,field.player))
        val coordinates = field.postition(creature)
        field.currentplayer(0) = p(0)
        field.clear()
        field.prediction(coordinates.head, coordinates.last)
        field.printfield()
        println(currentPlayerOutput(p(0)))
        field.currentcreatureinfo(coordinates.head,coordinates.last)

        println("=============================")
        println("a X Y   = attack")
        println("m X Y   = move")
        println("i X Y   = info")
        println("p       = pass")
        println("exit    = exit game")
        println("=============================")
        print("neue Eingabe: ")
        val input = validInput(field, creature)

        // X15>B   Y11^Z
        if (input.length == 3) {
            if (input(0).equals("a")) {
                if(!active(field, input(2).toInt, input(1).toInt)) {
                        val dmg = field.attack(coordinates.head, coordinates.last, input(1).toInt, input(2).toInt)
                        val defender = getCreature(field.field,input(2).toInt, input(1).toInt)
                        val info = lines() + creature.name + " dealt " + dmg + " points of damage to " +
                            defender.name + "\n" + lines() + "Remaining values\n\nMultiplier: " + defender.multiplier +
                            "\nHP: " + getCreature(field.field,input(2).toInt, input(1).toInt).hp + "\n" + lines()

                        println(info)

                }
                else
                    println("Creature can't be attacked")
            }
            if (input(0).equals("m")) {
                    field.move(coordinates.head,coordinates.last, input(1).toInt, input(2).toInt)
            }
        } else if (input.length == 1) {
            if (input(0).equals("p")) {
            }
            if(input(0).equals("exit")) {
                return false
            }
        } else {
            println("Ungültige Eingabe")
        }
        true
    }

    /**
     * Check input about accuracy
     * @param field of board
     * @param creature is the current creature
     * @return the input of the player
     */
    def validInput(field:Board, creature:Cell) : Array[String] = {
        val in = StdIn.readLine().split(" ")
        if(!(in(0).equals("a") || in(0).equals("m") || in(0).equals("p") || in(0).equals("exit") || in(0).equals("i"))) {
            println("Ungültige Eingabe")
            println("neue Eingabe: ")
            val out = validInput(field, creature)
            return out
        }
        if (in.length == 3) {
            if (checkmove(in, field) || in(0).equals("a") && creature.style || in(0).equals("a") && checkattack(in, field)) {
                return in
            } else if (in(0).equals("i") && isvalid(in)) {
                field.creatureinfo(in(1).toInt, in(2).toInt)
                println("neue Eingabe: ")
                val out = validInput(field, creature)
                return out
            } else {
                println("Ungültige Eingabe")
                println("neue Eingabe: ")
                val out = validInput(field, creature)
                return out
            }

        }
        in
    }

    /**
     * More specialized review for move
     * @param in input of player
     * @param field of board
     * @return true if correct, false if the input is wrong
     */
    def checkmove(in:Array[String], field:Board): Boolean = {
        if (in(0) == ("m") && isvalid(in) &&
            getCreature(field.field, in(2).toInt, in(1).toInt).name.equals(" _ ")) {
            return true
        }
        false
    }

    /**
     * More specialized review for attack
     * @param in input of player
     * @param board as instance of
     * @return true if correct, false if input is wrong
     */
    def checkattack(in:Array[String], board:Board) : Boolean = {
        val i = in(2).toInt
        val j = in(1).toInt
        val field = board.field
        if (!field(i)(j).name.equals("   ") && !field(i)(j).name.equals(" _ ") && !field(i)(j).name.equals("XXX")
            && !active(board, i, j)) {
            if (((i - 1 >= 0 && j - 1 >= 0) && field(i - 1)(j - 1).name.equals(" _ ")) ||
                ((i - 1 >= 0 && j >= 0) && field(i - 1)(j).name.equals(" _ ")) ||
                ((i - 1 >= 0 && j + 1 < 14) && field(i - 1)(j + 1).name.equals(" _ ")) ||
                ((i - 1 >= 0 && j >= 0) && field(i - 1)(j).name.equals(" _ ")) ||
                ((i + 1 < 11 && j >= 0) && field(i + 1)(j).name.equals(" _ ")) ||
                ((i + 1 < 11 && j - 1 >= 0) && field(i + 1)(j - 1).name.equals(" _ ")) ||
                ((i >= 0 && j + 1 < 14) && field(i)(j + 1).name.equals(" _ ")) ||
                ((i + 1 < 11 && j + 1 < 14) && field(i + 1)(j + 1).name.equals(" _ "))) {
                return true
            }
        }
        false
    }

    /**
     * Check for the player input coordinates
     * @param in input of player
     * @return true if correct, false if input is wrong
     */
    def isvalid(in : Array[String]) : Boolean = {
        if ((in(1).toInt >= 0) && (in(1).toInt <= 14)
            && (in(2).toInt >= 0) && (in(2).toInt <= 11)) {
            true
        } else {
            false
        }
    }
}

