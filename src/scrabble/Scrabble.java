package scrabble;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.io.*;

//Created by: Philip & Jakob
//Program Description: A simple Scrabble clone that allows one to save their game and pickup from before.
public class Scrabble {

    public static String[] wordList = new String[276643];

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        boolean clean = true;   //if game is new
        boolean play = true;    //game state
        int numPlayers; //number of players
        int userChoice; //general userchoice
        int direction;  //direction of words; 1 = left to right, 2 = up to donw
        int playerTurn; //which player's turn
        int startRow = -1;  //x-coordinate of starting tile
        int startCol = -1;  //y-coordinate ^^
        int endRow = -1;    //x-coordinate of ending tile
        int endCol = -1;    //y-coordinate ^^
        long time = -1; //timer variable
        String word;    //inputted word to be checked
        Board gameboard;    //holds the array of tiles and bonuses
        Player players[];   //array of players
        ArrayList<Character> required; //holds the required characters to form a word
        System.out.println("Loading word list...");
        time = System.currentTimeMillis();  //recording time to load
        loadWordList(); //loads the list of accepted words
        time = System.currentTimeMillis() - time;
        System.out.println("Succesfully loaded word list. (" + time + "ms)");
        //asking whether to resume or create a new game
        System.out.println("Enter 1 to load an existing game from file:");
        userChoice = getInt();
        if (userChoice == 1) {
            Scanner file = new Scanner(new FileReader("scrabblesave.txt"));
            //loading from files
            System.out.println("Loading save...");
            time = System.currentTimeMillis();  //recording time to load
            gameboard = new Board(loadBoard(file));
            players = loadPlayers(file);
            numPlayers = players.length;
            playerTurn = loadTurn(file);
            if (gameboard.spaces[7][7].getLetter() == '-') {
                clean = true; //the game will resume depending on which player was last in the file
            } else {
                clean = false;
            }
            time = System.currentTimeMillis() - time;
            System.out.println("Succesfully loaded save. (" + time + "ms)");
        } else {
            System.out.println("How many players are playing? (2-4)");
            numPlayers = getInt();
            while (numPlayers < 2 || numPlayers > 4) {
                System.out.println("There can only be 2-4 players."
                        + "\nPlease try again:");
                numPlayers = getInt();
            }
            players = new Player[numPlayers];
            playerTurn = 0;
            //filling player array
            for (int i = 0; i < players.length; i++) {
                System.out.println("Enter the name of player " + (i + 1) + ":");
                String name = in.nextLine();
                players[i] = new Player(name);
                players[i].refillhand();
            }
            //initiliazing new board
            gameboard = new Board();
        }
        while (play) {
            pause(1000); //simple delay between outputs
            boolean turnOver = false; //will be true if turn is finished
            System.out.println(gameboard);
            pause(1000);
            System.out.println("\nIt is now " + players[playerTurn].getName() + "'s turn.");
            while (!turnOver) {
                pause(1000);
                System.out.println("\nPlease choose an option:"
                        + "\n0 - Show Hand"
                        + "\n1 - Enter a word"
                        + "\n2 - Pass/Re-Roll"
                        + "\n3 - Show Scores"
                        + "\n4 - End game"
                        + "\n5 - Show letter values");
                userChoice = getInt();
                while (userChoice < 0 || userChoice > 5) {
                    System.out.println("Choice invalid. You must input a number between 0 and 5."
                            + "\nPlease try again:");
                    userChoice = getInt();
                }
                switch (userChoice) {
                    case 0: //shows available tiles
                        System.out.println("\n" + players[playerTurn].getName() + " has the following: "
                                + players[playerTurn].showHand());
                        break; //end case
                    case 1: //entering word

                        //get direction
                        System.out.println("\nWhat direction will the word span?"
                                + "\nEnter 1 to go from left to right or 2 "
                                + "to go downward:");
                        direction = getInt();
                        while (direction < 1 || direction > 2) {
                            System.out.println("\nChoice invalid. You must input either 1 or 2."
                                    + "\nPlease try again:");
                            direction = getInt();
                        }

                        //Getting starting tile
                        if (clean == true) {
                            //first word has to start from the bottom;
                            System.out.println("\nThe first word will begin in the middle.");
                            startRow = 7;
                            startCol = 7;

                            //getting end tile
                            if (direction == 1) {
                                System.out.println("\nUp to what column will the word go?");
                                endCol = getInt();
                                while (endCol < 0 || endCol > 14 || endCol <= startCol) {
                                    System.out.println("\nChoice invalid.");
                                    if (endCol < 0 || endCol > 14) {
                                        System.out.println("You may only choose 0-14.");
                                    }
                                    if (endCol <= startCol) {
                                        System.out.println("The column must be further"
                                                + " down from the starting one.");
                                    }
                                    System.out.println("Please try again:");
                                    endCol = getInt();
                                }
                            } else {
                                System.out.println("\nUp to what row will the word go?");
                                endRow = getInt();
                                while (endRow < 0 || endRow > 14 || endRow <= startRow) {
                                    System.out.println("\nChoice invalid.");
                                    if (endRow < 0 || endRow > 14) {
                                        System.out.println("You may only choose 0-14.");
                                    }
                                    if (endRow <= startRow) {
                                        System.out.println("The row must be further"
                                                + " down from the starting one.");
                                    }
                                    System.out.println("Please try again:");
                                    endRow = getInt();
                                }
                            }
                        } else {
                            while (true) {
                                //Getting start points
                                System.out.println("\nPlease enter the row of the starting"
                                        + " tile: ");
                                startRow = getInt();
                                while (startRow < 0 || startRow > 14) {
                                    System.out.println("\nChoice invalid. You may choose 0-14."
                                            + "\nPlease try again:");
                                    startRow = getInt();
                                }
                                System.out.println("\nPlease enter the column of the starting"
                                        + " tile: ");
                                startCol = getInt();
                                while (startCol < 0 || startCol > 14) {
                                    System.out.println("\nChoice invalid. You may choose only 0-14."
                                            + "\nPlease try again:");
                                    startCol = getInt();
                                }

                                //Getting ending points
                                if (direction == 1) {
                                    System.out.println("\nUp to what column will the word go?");
                                    endCol = getInt();
                                    while (endCol < 0 || endCol > 14 || endCol <= startCol) {
                                        System.out.println("\nChoice invalid.");
                                        if (endCol < 0 || endCol > 14) {
                                            System.out.println("You may only choose 0-14.");
                                        }
                                        if (endCol <= startCol) {
                                            System.out.println("The column must be further"
                                                    + " down from the starting one.");
                                        }
                                        System.out.println("Please try again:");
                                        endCol = getInt();
                                    }
                                } else {
                                    System.out.println("\nUp to what row will the word go?");
                                    endRow = getInt();
                                    while (endRow < 0 || endRow > 14 || endRow <= startRow) {
                                        System.out.println("\nChoice invalid.");
                                        if (endRow < 0 || endRow > 14) {
                                            System.out.println("You may only choose 0-14.");
                                        }
                                        if (endRow <= startRow) {
                                            System.out.println("The row must be further"
                                                    + " down from the starting one.");
                                        }
                                        System.out.println("Please try again:");
                                        endRow = getInt();
                                    }
                                }
                                //Verify that the word stems from the already placed pieces
                                if (!gameboard.checkConnection(direction, startRow, startCol, endRow, endCol)) {
                                    System.out.println("The word must be connected to the"
                                            + " already placed tiles."
                                            + "\nPlease try again.");
                                } else {
                                    break; //ends the loop asking for coordinates once verified
                                }
                            }
                        }

                        while (true) {
                            //Getting the words
                            System.out.println("\nPlease enter the word, or 0 to cancel:");
                            word = in.nextLine();
                            if (word.equals("0")) {
                                break;
                            }

                            //Making sure the word fits
                            if (direction == 1) {
                                while (word.length() != (endCol - startCol + 1)) {
                                    System.out.println("\nThe word does not fit into the"
                                            + "specified length. \nPlease try again "
                                            + "or enter 0 to cancel.");
                                    word = in.nextLine();
                                    if (word.equals("0")) {
                                        break;
                                    }
                                }
                            } else {
                                while (word.length() != (endRow - startRow + 1)) {
                                    System.out.println("\nThe word does not fit into the"
                                            + "specified length. \nPlease try again "
                                            + "or enter 0 to cancel.");
                                    word = in.nextLine();
                                    if (word.equals("0")) {
                                        break;
                                    }
                                }
                            }
                            if (word.equals("0")) {
                                break; //end of case
                            }

                            //Making sure the word is accepted
                            if (wordTest(0, wordList.length, word)) {
                                System.out.println("The word entered is an accepted word.");
                                break; //end of case
                            } else {
                                System.out.println("The word is not an accepted word."
                                        + "\nPlease try again.");
                            }
                        }

                        if (word.equals("0")) {
                            break; //end of case
                        }

                        //word = word.toUpperCase();
                        //Making sure the user has the required tiles.
                        if (direction == 1) {
                            required = gameboard.getRequired(direction, startCol, startRow, endCol, word); //gets a list of required tiles
                        } else {
                            required = gameboard.getRequired(direction, startCol, startRow, endRow, word); //gets a list of required tiles
                        }

                        ArrayList<Tile> hand = new ArrayList<>(players[playerTurn].getHand());
                        ArrayList<Tile> handCopy = new ArrayList<>(hand);
                        boolean hasRequired = true;

                        //loops through the required tiles and makes sure each tile is present in hand
                        for (int i = 0; i < required.size(); i++) {
                            int index = getIndexByLetter(required.get(i), handCopy);
                            if (index == -1) {
                                hasRequired = false;
                                break;
                            }
                            handCopy.remove(index);
                        }
                        if (!hasRequired) {
                            System.out.println("\nYou do not have the required number of tiles!");
                            break; //end of case
                        }

                        //if all conditions are satisfied, add word and scores
                        if (direction == 1) {
                            gameboard.addWord(word, direction, startCol, startRow, endCol, players, playerTurn);
                        } else {
                            gameboard.addWord(word, direction, startCol, startRow, endRow, players, playerTurn);
                        }
                        turnOver = true;
                        players[playerTurn].refillhand();
                        clean = false;
                        break; //end of case

                    case 2:
                        System.out.println("Enter 1 if you would like to re-roll your tiles:");
                        userChoice = getInt();
                        turnOver = true;
                        if (userChoice != 1) {
                            break; //end of case
                        } else {

                            //asking the number of letters to remove
                            System.out.println("How many letters do you wish to remove?");
                            int amount = getInt();
                            while (amount > 7 || amount < 0) {
                                System.out.println("You may only choose 0-7."
                                        + "\nPlease try again");
                                amount = getInt();
                            }
                            if (amount == 7) {
                                //clearing all in one go
                                players[playerTurn].getHand().clear();
                            } else {
                                //loops through until the number of tiles wished to be removed are gone
                                for (int i = 0; i < amount; i++) {
                                    System.out.println("Removing tile " + i + " of " + amount + " .");
                                    while (true) {
                                        System.out.println("Tiles remaining: " + players[playerTurn].showHand());
                                        System.out.println("Enter the letter you would like to remove or enter 0 to cancel:");
                                        String str = in.nextLine();
                                        while (str.length() != 1 || (int) str.charAt(0) < 65 || (int) str.charAt(0) > 122) {
                                            System.out.println("Invalid input. You can only enter a single letter."
                                                    + "\nPlease try again:");
                                            str = in.nextLine();
                                        }
                                        str = str.toUpperCase();
                                        char letter = str.charAt(0);
                                        if (getIndexByLetter(letter, players[playerTurn].getHand()) == -1) {
                                            System.out.println("The letter does not exist in your hand.");
                                        } else {
                                            int index = getIndexByLetter(letter, players[playerTurn].getHand());
                                            players[playerTurn].getHand().remove(index);
                                            break;
                                        }
                                    }
                                }
                            }
                            players[playerTurn].refillhand();
                            break;
                        }
                    case 3: //printing out scores
                        System.out.println("\n================================");
                        for (int i = 0; i < players.length; i++) {
                            System.out.println("Player: " + players[i].getName());
                            players[i].printScore();
                            System.out.println("================================");
                        }
                        break;
                    case 4: //ending game
                        turnOver = true;
                        play = false;
                        break;
                    case 5: //letter values
                        System.out.println("========"
                                + "\nA - 1:"
                                + "\nB - 3:"
                                + "\nC - 3:"
                                + "\nD - 2:"
                                + "\nE - 1:"
                                + "\nF - 4:"
                                + "\nG - 2:"
                                + "\nH - 1:"
                                + "\nI - 4:"
                                + "\nJ - 1"
                                + "\nK - 8:"
                                + "\nL - 5:"
                                + "\nM - 1:"
                                + "\nN - 3:"
                                + "\nO - 1:"
                                + "\nP - 1:"
                                + "\nQ - 3:"
                                + "\nR - 10:"
                                + "\nS - 1:"
                                + "\nT - 1:"
                                + "\nU - 1:"
                                + "\nV - 1:"
                                + "\nW - 4:"
                                + "\nX - 4:"
                                + "\nY - 8:"
                                + "\nZ - 10:"
                                + "\n========");
                        break;
                }

            }

            //Cycling through the players' turns
            if (play) {
                playerTurn++;
                if (playerTurn == numPlayers) {
                    playerTurn = 0;
                }
            }
        }

        //File saving
        System.out.println("Would you like to save the game in progress?"
                + " Enter 1 to say yes:");
        userChoice = getInt();
        if (userChoice == 1) {
            PrintWriter fileOut = new PrintWriter(new FileWriter("scrabblesave.txt")); //has to be moved to the section were the user has the option to save otherwise the save will be overwritten
            save(fileOut, gameboard, players, playerTurn);
            fileOut.close();
        }
        System.out.println("Thanks for playing!");
    }

    public static int getInt() {
        Scanner sc = new Scanner(System.in);
        int intVal;
        while (true) {
            try {
                intVal = sc.nextInt();                                              //gets the integer;
                break;
            } catch (InputMismatchException e) {
                sc.nextLine();                                                      //clears out the scanner
                System.out.println("Please enter a proper integer:");               //prompt if the user does not enter an integer
            }
        }
        return intVal;
    }

    public static boolean wordTest(int left, int right, String word) {
        word = word.toUpperCase();
        int middle;
        if (left > right) {
            return false;
        }
        middle = (left + right) / 2;
        if (wordList[middle].equals(word)) {
            return true;
        }
        if (word.compareTo(wordList[middle]) < 0) {
            return wordTest(left, middle - 1, word);
        } else {
            return wordTest(middle + 1, right, word);
        }
    }

    public static void loadWordList() { //method to load the accepted list
        try {
            Scanner text = new Scanner(new FileReader("wordlist.txt"));
            for (int i = 0; i < wordList.length; i++) {
                wordList[i] = text.nextLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("The notepad document is not in the correct location or has been renamed. "
                    + "Make sure that it is in the .txt format named wordlist and is stored in the C: drive");
            System.exit(0);
        }
    }

    public static int getIndexByLetter(char letter, ArrayList<Tile> atHand) { //method to search for tile only
        for (int i = 0; i < atHand.size(); i++) {
            if (atHand.get(i).getLetter() == letter) {
                return i;
            }
        }
        return -1;// not in the list
    }

    public static void pause(int milliseconds) {    //method for simple delays
        // method to delay
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public static Space[][] loadBoard(Scanner file) {
        //takes the first set of lines from the file to load the board
        Space[][] spaces = new Space[15][15];
        char letter;
        int value, wordBonus, ltrBonus;
        for (int i = 0; i < 15; i++) {
            for (int k = 0; k < 15; k++) {
                letter = file.next().charAt(0);
                value = file.nextInt();
                wordBonus = file.nextInt();
                ltrBonus = file.nextInt();
                spaces[i][k] = new Space(letter, value, wordBonus, ltrBonus); //creates space based off of saved data into its proper coordinate
            }
        }
        return spaces;
    }

    public static Player[] loadPlayers(Scanner file) {  //method to load player data from file
        int numPlayers = file.nextInt();
        String name, word;
        int score, value;
        Tile[] hand = new Tile[7]; //will hold the player's previous hand
        ArrayList<String> wordsMade = new ArrayList<>();
        Player[] players = new Player[numPlayers];  //creates an array based on number of players
        char letter;
        for (int i = 0; i < numPlayers; i++) {
            wordsMade.clear();
            name = file.next();
            players[i] = new Player(name);
            score = file.nextInt();
            players[i].setScore(score);
            for (int k = 0; k < 7; k++) {
                letter = file.next().charAt(0);
                value = file.nextInt();
                hand[k] = new Tile(letter, value); //loads previous hand into current hand
            }

            while (true) {  //checks for words made
                word = file.nextLine();
                if (word.equals("/")) {
                    break;  //stops searching if it meets /
                } else if (word.equals("\n") || word.equals(" ") || word.equals("")) {
                    continue;
                } else {
                    wordsMade.add(word); //adds word to list
                }
            }
            players[i].setHand(hand);
            if (wordsMade.size() > 0) {
                players[i].setWordsMade(wordsMade);
            }
        }
        return players;
    }

    public static int loadTurn(Scanner file) { //gets playerTurn from last integer
        return file.nextInt();
    }

    public static void save(PrintWriter fileout, Board gameBoard, Player[] players, int playerTurn) { //method to save player array to file
        fileout.print(gameBoard.save());
        fileout.println(players.length);
        for (int i = 0; i < players.length; i++) {
            fileout.println(players[i].save());
        }
        fileout.println(playerTurn);
    }
}
