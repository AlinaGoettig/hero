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
     * Class for the board cells. Equals the creatures, empty cells, obstacles and marker
     * @param name of the cell
     * @param dmg amount, random between two values
     * @param hp amount of a single creature
     * @param speed movment speed and attack range
     * @param style true if ranged, false if melee
     * @param multiplier amount of creatures
     * @param player which player own the creature (Player1,Player2,None)
     */
    case class Cell(name: String, dmg: String, hp: Int, speed: Int, style: Boolean, multiplier: Int, player: Player) {

        /**
         * Show the cell in board style
         * @return String with basic marker on the side
         */
        def printcell(): String = "│ " + name + " │"

        /**
         * Show the cell in board style with extra attackable marker
         * @return String with basic and attack marker on the side
         */
        def attackable(): String = "│>" + name + "<│"

        /**
         * Calculates the damage of the creatures based on the range
         * @return Int with value of damage
         */
        def attackamount(): Int = {
            val input = dmg.split("-")
            val random = ThreadLocalRandom.current()
            val value = if (input.length == 2) Array(input(0).toInt,input(1).toInt) else Array(input(0).toInt)
            val amount = if (value.length == 2) random.nextInt(value(0),value(1)+1) else value(0)
            amount
        }

    }

    /**
     * Class for the Board with all functions
     * @param field 2D Array to represent the Board
     * @param player Array with both player
     * @param currentplayer current activ player
     */
    case class Board(field: Array[Array[Cell]],player: Array[Player],currentplayer: Array[Player]) {

        /**
         * Initialize the Board by filling it with all cells
         * @return filled field
         */
        def start(): Array[Array[Hero.Cell]] = {
            fill()
            val list = creatureliststart(player)
            for (c <- list) {
                val y = c._1.head
                val x = c._1.last
                field(x)(y) = c._2
            }
            val obs = obstaclelist()
            for (o <- obs) {
                val y = o._1.head
                val x = o._1.last
                field(x)(y) = o._2
            }
            println(playerside(player))
            field
        }

        /**
         * Move a creature from pointA to pointB
         * @param Y1 Start
         * @param X1 Start
         * @param X2 End
         * @param Y2 End
         * @return renewed field
         */
        def move(Y1:Int,X1:Int, X2:Int, Y2:Int) : Array[Array[Cell]] = {
            val cret1 = field(Y1)(X1)
            val cret2 = field(Y2)(X2)
            field(Y1)(X1) = cret2
            field(Y2)(X2) = cret1

            field
        }

        /**
         * Action of one creature attack another
         * @param Y1 Attacker
         * @param X1 Attacker
         * @param X2 Defender
         * @param Y2 Defender
         * @return renewed field
         */
        def attack (Y1:Int, X1:Int, X2:Int, Y2:Int): String = {
            val attacker = field(Y1)(X1)
            val defender = field(Y2)(X2)
            val dmg = attacker.attackamount() * attacker.multiplier
            val multicheck = defender.hp - dmg
            val multidif = dmg.toFloat/defender.hp
            val basehp = findbasehp(defender.name,player)
            val multiplier = if(multicheck < 0) defender.multiplier - multidif.toInt else defender.multiplier
            val hp = if (multiplier != defender.multiplier) basehp * (multidif.toInt+1) - dmg else defender.hp - dmg

            if (multiplier <= 0) {
                field(Y2)(X2) = emptycell()
            } else {
                field(Y2)(X2) = Cell(defender.name,defender.dmg,hp,defender.speed,defender.style,multiplier,defender.player)
            }

            dmg.toString
        }

        /**
         * Prediction where the creature is able to walk
         * @param Y creature
         * @param X creature
         * @return renewed field
         */
        def prediction (Y: Int, X: Int): Array[Array[Cell]] = {
            for (i <- 0 to 14) {
                for (j <- 0 to 10) {
                    val dist = Math.abs(X - i) + Math.abs(Y - j)
                    if(field(j)(i).name.equals("   ") && dist <= field(Y)(X).speed) {
                        field(j)(i) = marker()
                    }
                }
            }
            field
        }

        /**
         * Prints the Board into the console
         * @return String with entire entries
         */
        def printfield() : String = {
            var text = ""
            for (x <- 0 to 14) {
                text += fieldnumber(x.toString)
            }

            text += "\n" + lines()
            for (i <- 0 to 10) {
                for (j <- 0 to 14) {
                    if (!field(i)(j).name.equals("   ") && !field(i)(j).name.equals(" _ ") && !field(i)(j).name.equals("XXX") && !active(this,i,j)) {
                        if (((i-1 >= 0 && j-1 >= 0) && field(i-1)(j-1).name.equals(" _ ")) ||
                            ((i-1 >= 0 && j >= 0) && field(i-1)(j).name.equals(" _ ")) ||
                            ((i-1 >= 0 && j+1 < 14) && field(i-1)(j+1).name.equals(" _ ")) ||
                            ((i-1 >= 0 && j >= 0) && field(i-1)(j).name.equals(" _ ")) ||
                            ((i+1 < 11 && j >= 0) && field(i+1)(j).name.equals(" _ ")) ||
                            ((i+1 < 11 && j-1 >= 0) && field(i+1)(j-1).name.equals(" _ ")) ||
                            ((i >= 0 && j+1 < 14) && field(i)(j+1).name.equals(" _ ")) ||
                            ((i+1 < 11 && j+1 < 14) && field(i+1)(j+1).name.equals(" _ "))) {
                            text += field(i)(j).attackable()
                        } else {
                            text += field(i)(j).printcell()
                        }
                    } else {
                        text += field(i)(j).printcell()
                    }
                }
                text += " " + i.toString + "\n" + lines()
            }
            println(text)
            text
        }

        /**
         * Replace prediction marker with emptycells
         * @return renewed field
         */
        def clear():Array[Array[Cell]] = {
            for (i <- 0 to 14) {
                for (j <- 0 to 10) {
                    if(field(j)(i).name.equals(" _ ")) {
                        field(j)(i) = emptycell()
                    }
                }
            }
            field
        }

        /**
         * Fill the board with empty cells
         * @return renewed field
         */
        def fill(): Array[Array[Cell]] = {
            for (i <- 0 to 10) {
                for (j <- 0 to 14) {
                    field(i)(j) = emptycell()
                }
            }
            field
        }

        /**
         * Prints information about current creature (automatic)
         * @param X creature
         * @param Y creature
         * @return String with all informations
         */
        def currentcreatureinfo(X: Int, Y: Int): String = {
            val attackstyle = if(field(X)(Y).style) "Ranged" else "Melee"
            val info = "=" * 2 + " Info " + "=" * 97 + "\n" + "Current Unit:\t\t\t\tMultiplier:\t\t\t\tHP:\t\t\t\tDamage:\t\t\t\tAttackstyle:" + "\n" +
                field(X)(Y).name + "\t\t\t\t\t\t\t" + field(X)(Y).multiplier + "\t\t\t\t\t\t" + field(X)(Y).hp + "\t\t\t\t" +
                field(X)(Y).dmg + " " * (20 - field(X)(Y).dmg.length) + attackstyle + "\n" + lines()
            println(info)
            info
        }

        /**
         * Prints information about asked field/creature (command call)
         * @param Y
         * @param X
         * @return
         */
        def creatureinfo(Y: Int, X: Int): String = {
            val shortline = "=" * 33 + "\n"
            val info =  "=" * 2 + " Info " + "=" * 25 + "\n" + "Unit:\t\tMultiplier:\t\tHP:" + "\n" + field(X)(Y).name + "\t\t\t" +
                field(X)(Y).multiplier + "\t\t\t\t" + field(X)(Y).hp + "\n" + shortline
            println(info)
            info
        }

        /**
         * Gives Vector where the asked creature is in the field
         * @param creature which needs to be searched
         * @return Vector(Int,Int) with the coordinates
         */
        def postition(creature: Cell): Vector[Int] = {
            val posi = Array(Vector(0,0))
            for (i <- 0 to 10) {
                for (j <- 0 to 14) {
                    if (field(i)(j).equals(creature)) {
                        posi(0) = Vector(i,j)
                    }
                }
            }
            posi(0)
        }

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

    /**
     * Reference list of the creatures on the board
     * @param field list with creatures
     * @param current creature which is activ
     * @param player array with both players
     */
    case class Creaturefield(field: Array[Cell], current: Array[Cell], player: Array[Player]) {

        /**
         * Change current creature to the next possible
         * @return creature with multiplier > 0
         */
        def next(): Cell = {
            if (field.indexOf(current(0))+1 == field.length) {
                current(0) = field(0)
            } else {
                current(0) = field(field.indexOf(current(0))+1)
            }
            while (current(0).multiplier <= 0) {
                if (field.indexOf(current(0))+1 == field.length) {
                    current(0) = field(0)
                } else {
                    current(0) = field(field.indexOf(current(0))+1)
                }
            }
            current(0)
        }

        /**
         * Check if one of the player have <= 0 creatures of the board left with multiplier > 0
         * @return Int with information about state. 1 = player 1 won, 2 = player 2 won, 0 = game goes on
         */
        def winner(): Int = {
            val sides = Array(false,false)
            for (y <- field) {
                if (y.player == player(0)) {
                    sides(0) = true
                }
                if (y.player == player(1)) {
                    sides(1) = true
                }
            }
            if (sides(0) && !sides(1)) {
                return 1
            } else if (!sides(0) && sides(1)) {
                return 2
            } else {
                return 0
            }
        }

    }
}

