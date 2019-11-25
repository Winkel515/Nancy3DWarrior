package nancy3dwarrior;

import java.util.Random;
import java.util.Scanner;

public class LetUsPlay {

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        Random random = new Random();
        int userInput;
        int boardLevel;
        int boardSize;
        Board board;
        Dice dice = new Dice();
        Player[] players = new Player[2];
        int firstPlayer;

        System.out.print(""
                + "\t*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*\n"
                + "\t*                                           *\n"
                + "\t*   WELCOME to Nancy's 3D Warrior Game!     *\n"
                + "\t*                                           *\n"
                + "\t*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*_*\n\n"
                + "The default game board has 3 levels and each level has a 4x4 board.\n"
                + "You can use this default board size or change the size\n"
                + "\t0 to use the default board\n"
                + "\t-1 to enter your own size\n"
                + "==> What do you want to do? ");

        // ----------------------------------------- BOARD CREATION START --------------------------------------
        userInput = keyboard.nextInt();
        while (userInput != 0 && userInput != -1) { //Invalid option case
            System.out.println("Sorry but " + userInput + " is not a legal choice.");
            userInput = keyboard.nextInt();
        }

        if (userInput == -1) { //User inputed -1, so custom board

            //START Board Level Selection
            System.out.print("How many levels would you like? (minimum level " + Board.MIN_LEVEL + ", max " + Board.MAX_LEVEL + ") ");
            userInput = keyboard.nextInt();
            while (userInput > Board.MAX_LEVEL || userInput < Board.MIN_LEVEL) { //Invalid board size case
                System.out.println("Sorry but " + userInput + " is not a legal choice.");
                userInput = keyboard.nextInt();
            }
            boardLevel = userInput;
            //END Board Level Selection

            //START Board Size Selection
            System.out.println("\nWhat size do you want the nxn boards on each level to be?\n"
                    + "Minimum size is " + Board.MIN_SIZE + "x" + Board.MIN_SIZE + ", max is " + Board.MAX_SIZE + "x" + Board.MAX_SIZE);
            userInput = keyboard.nextInt();
            while (userInput > Board.MAX_SIZE || userInput < Board.MIN_SIZE) {
                System.out.println("Sorry but " + userInput + " is not a legal choice.");
                userInput = keyboard.nextInt();
            }
            boardSize = userInput;
            //END Board Size Selection

            board = new Board(boardLevel, boardSize); //Created the custom board
        } else { //User inputed 0, so default board
            board = new Board(); //Created the default board
        }
        System.out.println("\nYour 3D board has been set up and looks like this:\n\n" + board);
        // -------------------------------------- BOARD CREATION END ----------------------------------------------------

        // -------------------------------------- PLAYER CREATION START -------------------------------------------------
        for (int i = 0; i < players.length; i++) {
            System.out.print("What is player " + i + "'s name (one word only): ");
            players[i] = new Player(keyboard.next());
        }
        // -------------------------------------- PLAYER CREATION END ---------------------------------------------------

        
        
        // -------------------------------------- GAME START ------------------------------------------------------------
        firstPlayer = random.nextInt(players.length); //Choose the starting player
        System.out.println("\nThe game has started, " + players[firstPlayer].getName() + " goes first\n"
                + "================================\n");

        while (true) {
            for (int turn = 0; turn < players.length; turn++) {
                int playerTurn = (firstPlayer + turn) % players.length; //Turns are cyclical mod the number of players
                Player currentPlayer = players[playerTurn];
                System.out.println("It is " + currentPlayer.getName() + "'s turn");
                //Player has less or equal to 0 energy case
                if (currentPlayer.getEnergy() <= 0) {
                    System.out.println("\tYou are too weak to move, rolling dice 3 times to replenish energy");
                    for (int i = 0; i < 3; i++) {
                        dice.rollDice();
                        System.out.println("\t" + currentPlayer.getName() + " you just rolled " + dice);
                        if (dice.isDouble()) {
                            System.out.println("\tCongratulations you rolled double " + dice.getDie1() + ". Your energy went up by " + Player.DOUBLE_ENERGY + " units");
                            currentPlayer.addEnergy(Player.DOUBLE_ENERGY);
                        }
                    }
                }
                // Player has more than 0 energy case
                else {
                    int rollValue = dice.rollDice();
                    System.out.println("\t" + currentPlayer.getName() + " you just rolled " + dice);

                    //Double roll case
                    if (dice.isDouble()) {
                        System.out.println("\tCongratulations you rolled double " + dice.getDie1() + ". Your energy went up by " + Player.DOUBLE_ENERGY + " units");
                        currentPlayer.addEnergy(Player.DOUBLE_ENERGY);
                    }
                    int[] potentialLocation;
                    
                    //Checking if the player is at the second to last square
                    if(currentPlayer.getLevel() != board.getLevel()-1 || currentPlayer.getX() != board.getSize()-1 || currentPlayer.getY() != board.getSize()-2) {
                        potentialLocation = board.potentialLocation(currentPlayer, rollValue);
                    }
                    // Player at second to last square of the top level case
                    else {
                        System.out.println("\tYou are at the second to last square. Moving backwards");
                        potentialLocation = board.potentialLocation(currentPlayer, -rollValue); //Move backwards
                    }
                    
                    Player playerOnLocation = board.occupiedBy(potentialLocation[0], potentialLocation[1], potentialLocation[2], players);
                    if (playerOnLocation == null) {
                        //Out of Bound Case
                        if(potentialLocation[0] >= Board.MIN_LEVEL){
                            System.out.println("\t!!! Sorry you need to stay where you are - that throw takes you off the grid and\n"
                                    + "\t\tyou lose 2 units of energy");
                            currentPlayer.addEnergy(-2);
                            continue;
                        }
                        //In Bound Case
                        else {
                            //Move player to new location
                            currentPlayer.setLevel(potentialLocation[0]);
                            currentPlayer.setX(potentialLocation[1]);
                            currentPlayer.setY(potentialLocation[2]);
                        }

                    } else { // Potential location is occupied case
                        System.out.println("\tPlayer " + playerOnLocation.getName() + " is at your new location\n\tWhat do you want to do?\n"
                                + "\t\t0 - Challenge and risk losing 50% of your energy units if you lose\n"
                                + "\t\t\tor move to new location and get 50% of other player's energy unit\n"
                                + "\t\t1 - to move down one level or move to (0,0) if at level 0 and lose 2 energy units");
                        userInput = keyboard.nextInt();
                        while (userInput != 0 && userInput != 1) {
                            System.out.println("Sorry but " + userInput + " is not a legal choice.");
                            userInput = keyboard.nextInt();
                        }

                        //Challenge Case
                        if (userInput == 0) {
                            //Lose Challenge Case
                            if (random.nextInt(2) == 0) {
                                System.out.println("\tYou lost the challenge.");
                                currentPlayer.setEnergy(currentPlayer.getEnergy() / 2); //Half the energy
                            } //Win Challenge Case
                            else {
                                System.out.println("\tBravo!! You won the challenge.");
                                // Swap Location
                                Player temp = new Player(currentPlayer);
                                currentPlayer.moveTo(playerOnLocation);
                                playerOnLocation.moveTo(temp);

                                //Energy Steal
                                currentPlayer.addEnergy(playerOnLocation.getEnergy() / 2);
                                playerOnLocation.setEnergy(playerOnLocation.getEnergy() - playerOnLocation.getEnergy() / 2); // Subtracting to account the naturally flooring of int
                            }
                        } //Forfeit Case
                        else {
                            //At level 0 Case
                            if (currentPlayer.getLevel() == 0) {
                                currentPlayer.setX(0);
                                currentPlayer.setY(0);
                            } //Not at 0 Case
                            else {
                                currentPlayer.setLevel(currentPlayer.getLevel() - 1);
                            }
                            currentPlayer.addEnergy(-2); //Energy lost from forfeit
                        }
                    }
                    System.out.println("\tYour energy is adjusted by " + board.getEnergyAdj(currentPlayer) + " for landing at (" + currentPlayer.getX() + ", " + currentPlayer.getY() + ") at level " + currentPlayer.getLevel());
                    currentPlayer.addEnergy(board.getEnergyAdj(currentPlayer));
                }
            }
            
            System.out.println("\nAt the end of this round:");
            //Using the same loop to preserve the order of players apparition 
            for (int turn = 0; turn < players.length; turn++) {
                int playerTurn = (firstPlayer + turn) % players.length; //Turns are cyclical mod the number of players
                System.out.println("\t" + players[playerTurn]);
            }
            if(players[0].won(board) || players[1].won(board))
                break;
            System.out.print("Any key to continue to next round ...");
            keyboard.next();
        }
        // -------------------------------------- GAME END --------------------------------------------------------------

        for (Player p : players) {
            if (p.won(board)) {
                System.out.println("\nThe winner is player " + p.getName() + ". Well done!!!");
                break;
            }
        }
    }
}
