package scrabble;
//Philip Bondoc
import java.util.ArrayList;

public class Board {

    public Space[][] spaces;    //array that holds the spaces

    public Board(Space[][] nspaces) {
        spaces = nspaces;
    }

    public Board() { //creates a blank board with bonuses
        spaces = new Space[15][15];
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                spaces[row][col] = new Space();
            }
        }

        //Adding word bonuses to spaces
        //3 word bonuses
        spaces[0][0].setWordBonus(3);
        spaces[0][7].setWordBonus(3);
        spaces[0][14].setWordBonus(3);
        spaces[7][0].setWordBonus(3);
        spaces[7][14].setWordBonus(3);
        spaces[14][0].setWordBonus(3);
        spaces[14][7].setWordBonus(3);
        spaces[14][14].setWordBonus(3);

        //2 word bonuses
        spaces[1][1].setWordBonus(2);
        spaces[2][2].setWordBonus(2);
        spaces[3][3].setWordBonus(2);
        spaces[4][4].setWordBonus(2);
        spaces[10][4].setWordBonus(2);
        spaces[11][3].setWordBonus(2);
        spaces[12][2].setWordBonus(2);
        spaces[13][1].setWordBonus(2);
        spaces[1][13].setWordBonus(2);
        spaces[2][12].setWordBonus(2);
        spaces[3][11].setWordBonus(2);
        spaces[4][10].setWordBonus(2);
        spaces[10][10].setWordBonus(2);
        spaces[11][11].setWordBonus(2);
        spaces[12][12].setWordBonus(2);
        spaces[13][13].setWordBonus(2);
        spaces[7][7].setWordBonus(2);

        //3 Letter Bonuses
        spaces[1][5].setLtrBonus(3);
        spaces[1][9].setLtrBonus(3);
        spaces[5][1].setLtrBonus(3);
        spaces[5][5].setLtrBonus(3);
        spaces[5][9].setLtrBonus(3);
        spaces[5][13].setLtrBonus(3);
        spaces[9][1].setLtrBonus(3);
        spaces[9][5].setLtrBonus(3);
        spaces[9][9].setLtrBonus(3);
        spaces[9][13].setLtrBonus(3);
        spaces[13][5].setLtrBonus(3);
        spaces[13][9].setLtrBonus(3);

        //2 Letter Bonuses
        spaces[0][3].setLtrBonus(2);
        spaces[0][11].setLtrBonus(2);
        spaces[2][6].setLtrBonus(2);
        spaces[2][8].setLtrBonus(2);
        spaces[3][0].setLtrBonus(2);
        spaces[3][7].setLtrBonus(2);
        spaces[3][14].setLtrBonus(2);
        spaces[6][2].setLtrBonus(2);
        spaces[6][6].setLtrBonus(2);
        spaces[6][8].setLtrBonus(2);
        spaces[6][12].setLtrBonus(2);
        spaces[7][3].setLtrBonus(2);
        spaces[7][11].setLtrBonus(2);
        spaces[8][2].setLtrBonus(2);
        spaces[8][6].setLtrBonus(2);
        spaces[8][8].setLtrBonus(2);
        spaces[8][12].setLtrBonus(2);
        spaces[11][0].setLtrBonus(2);
        spaces[11][7].setLtrBonus(2);
        spaces[11][14].setLtrBonus(2);
        spaces[12][6].setLtrBonus(2);
        spaces[12][8].setLtrBonus(2);
        spaces[14][3].setLtrBonus(2);
        spaces[14][11].setLtrBonus(2);
    }

    public String toString() {
        //Displays the board
        String output = "  ============================================================="
                + "\n                          Current Board"
                + "\n  ============================================================="
                + "\n   0   1   2   3   4   5   6   7   8   9   10  11  12  13  14"
                + "\n\u001b[0m  \u001b[43m+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n";
        for (int row = 0; row < 15; row++) {
            if (row < 10) {
                output += "\u001b[0m ";
            }
            output += "\u001b[0m" + row;
            output += "\u001b[43m|";
            for (int col = 0; col < 15; col++) {
                output += spaces[row][col];
                output += "\u001b[0m\u001b[43m|";
            }
            output += "\u001b[0m" + row;
            output += "\n\u001b[0m  \u001b[43m+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+\n";

        }
        output += "\u001b[0m   0   1   2   3   4   5   6   7   8   9   10  11  12  13  14\n"
                + "\n\u001b[34mBlue\u001b[0m = Triple Letter Bonus"
                + "\n\u001b[36mCyan\u001b[0m = Double Letter Bonus"
                + "\n\u001b[31mRed\u001b[0m = Triple Word Bonus"
                + "\n\u001b[35mMagenta\u001b[0m = Double Word Bonus";
        return output;
    }

    public void addWord(String word, int direction, int startCol, int startRow, int endTile, Player[] players, int playerTurn) { //method to add word to board and record scores
        int key = 0;    //used to move through index of inputted word
        int points = 0; //number of points given by entering the word
        int dblWordBonus = 0;   //holds the number of 2x word bonus tiles the tile goes through
        int tplWordBonus = 0;   //holds the number of 3x word bonus tiles the tile goes through
        word = word.toUpperCase();  
        ArrayList<Tile> hand = players[playerTurn].getHand();   //gets the players hand to remove the required tiles
        if (direction == 1) {

            //adding tiles to spaces
            for (int col = startCol; col <= endTile; col++) {
                if (spaces[startRow][col].getLetter() == '-') {
                    int index = getIndexByLetter(word.charAt(key), hand);
                    spaces[startRow][col].setLetter(hand.get(index).getLetter());
                    spaces[startRow][col].setValue(hand.get(index).getValue());
                    hand.remove(index);
                }

                //adding letter points
                if (spaces[startRow][col].getLtrBonus() > 0) {
                    points += spaces[startRow][col].getValue() * spaces[startRow][col].getLtrBonus();
                    spaces[startRow][col].setLtrBonus(0);
                } else {
                    points += spaces[startRow][col].getValue();
                }
                if (spaces[startRow][col].getWordBonus() == 2) {
                    dblWordBonus++;
                    spaces[startRow][col].setWordBonus(0);
                } else if (spaces[startRow][col].getWordBonus() == 3) {
                    tplWordBonus++;
                    spaces[startRow][col].setWordBonus(0);
                }
                key++;
            }

            //adding word bonuses
            for (int i = 0; i < dblWordBonus; i++) {
                points = points * 2;
            }
            for (int i = 0; i < tplWordBonus; i++) {
                points = points * 3;
            }
        } else {

            //adding tiles to spaces
            for (int row = startRow; row <= endTile; row++) {
                if (spaces[row][startCol].getLetter() == '-') {
                    int index = getIndexByLetter(word.charAt(key), hand);
                    spaces[row][startCol].setLetter(hand.get(index).getLetter());
                    spaces[row][startCol].setValue(hand.get(index).getValue());
                    hand.remove(index);
                }

                //adding letter points
                if (spaces[row][startCol].getLtrBonus() > 0) {
                    points += spaces[row][startCol].getValue() * spaces[row][startCol].getLtrBonus();
                    spaces[row][startCol].setLtrBonus(0);
                } else {
                    points += spaces[row][startCol].getValue();
                }
                if (spaces[row][startCol].getWordBonus() == 2) {
                    dblWordBonus++;
                    spaces[row][startCol].setWordBonus(0);
                } else if (spaces[row][startCol].getWordBonus() == 3) {
                    tplWordBonus++;
                    spaces[row][startCol].setWordBonus(0);
                }
                key++;
            }

            //adding word bonuses
            for (int i = 0; i < dblWordBonus; i++) {
                points = points * 2;
            }
            for (int i = 0; i < tplWordBonus; i++) {
                points = points * 3;
            }
        }
        //adds scores and word
        players[playerTurn].addScore(points);
        players[playerTurn].recordWord(word);
        System.out.println(points + " points have been awarded to " + players[playerTurn].getName() + "!");
    }

    public ArrayList<Character> getRequired(int direction, int startCol, int startRow, int endTile, String word) {//this method checks what tiles are needed
        word = word.toUpperCase();
        ArrayList<Character> required = new ArrayList<>(); //holds the required tiles 
        int key = 0;
        if (direction == 1) { //horizontal
            //goes through the range of tiles, if empty, add to required
            for (int col = startCol; col <= endTile; col++) {
                if (spaces[startRow][col].getLetter() == '-') {
                    required.add(word.charAt(key)); //adds the corresponding letter of the word to the required list
                }
                key++;
            }
        } else {
            //goes through the range of tiles, if empty, add to required
            for (int row = startRow; row <= endTile; row++) {
                if (spaces[row][startCol].getLetter() == '-') {
                    required.add(word.charAt(key)); //adds the corresponding letter of the word to the required list
                }
                key++;
            }
        }
        return required;
    }

    public int getIndexByLetter(char letter, ArrayList<Tile> atHand) {  //method to find the index of a tile on hand based on its letter value
        for (int i = 0; i < atHand.size(); i++) {
            if (atHand.get(i).getLetter() == letter) {
                return i;
            }
        }
        return -1;// not there is list
    }

    public boolean checkConnection(int direction, int startRow, int startCol, int endRow, int endCol) { //method to make sure the range is connected to the already placed tiles
        boolean connected = false;
        if (direction == 1) {
            for (int col = startCol; col <= endCol; col++) {
                if (spaces[startRow][col].getLetter() != '-') {
                    connected = true;
                }
            }
        } else {
            for (int row = startRow; row <= endRow; row++) {
                if (spaces[row][startCol].getLetter() != '-') {
                    connected = true;
                }
            }
        }
        return connected;
    }

    public String save() {  //saves the board to a string
        String output = "";
        for (int i = 0; i < 15; i++) {
            for (int k = 0; k < 15; k++) {
                output += spaces[i][k].save() + "\n";
            }
        }
        return output;
    }
}
