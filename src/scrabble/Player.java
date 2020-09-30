package scrabble;
//Jakob Klose
import java.util.ArrayList;

public class Player {

    private String name;
    private int score;
    private ArrayList<Tile> hand;
    private ArrayList<String> wordsMade;

    public Player(String inName) {
        //creates a new player with a name
        name = inName;
        score = 0;
        hand = new ArrayList<>();
        wordsMade = new ArrayList<>();
    }

    public Player(String inName, int inScore, ArrayList<Tile> inhand, ArrayList<String> inwordsMade) {
        //creates a new player with the given names (used for loading)
        name = inName;
        score = inScore;
        hand = inhand;
        wordsMade = inwordsMade;
    }

    public String getName() {
        //returns the name
        return name;
    }

    public void setScore(int inScore) {
        //sets the score
        score = inScore;
    }

    public int getScore() {
        //returns the score 
        return score;
    }

    public void addScore(int points) {
        //adds to the score
        score += points;
    }

    public void refillhand() {
        //fills the hand with up to 7 new tiles
        while (hand.size() < 7) {
            hand.add(new Tile());
        }
    }

    public ArrayList<Tile> getHand() {
        //returns hands
        return hand;
    }

    public String showHand() {
        //outputs the player's hand to a string (only shows letters)
        String output = "";
        for (int i = 0; i < hand.size(); i++) {
            output += hand.get(i).getLetter() + " ";
        }
        return output;
    }

    public void recordWord(String word) {
        //adds to wordsMade list
        wordsMade.add(word);
    }

    public void printScore() {
        //prints the score in a formatted output along with words made
        System.out.println("Score: " + score);
        System.out.println("Words made:");
        for (int i = 0; i < wordsMade.size(); i++) {
            System.out.println(wordsMade.get(i));
        }
    }

    public String save() {
        //saves player data to string
        String output = name + " " + score + "\n";
        for (int i = 0; i < 7; i++) {
            output += hand.get(i) + "\n";
        }
        for (int i = 0; i < wordsMade.size(); i++) {
            output += wordsMade.get(i) + "\n";
        }
        output += "/";
        return output;
    }

    public void setHand(Tile[] inHand) {
        //loads hand from input
        hand.clear();
        for (int i = 0; i < inHand.length; i++) {
            hand.add(inHand[i]);
        }
    }

    public void setWordsMade(ArrayList<String> inWordsMade) {
        //loads words made list from input
        wordsMade.clear();
        for (int i = 0; i < inWordsMade.size(); i++) {
            wordsMade.add(inWordsMade.get(i));
        }
    }
}
