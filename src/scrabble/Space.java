package scrabble;
//Jakob Klose

public class Space extends Tile {

    private int wordBonus;
    private int ltrBonus;

    public Space() {
        //creates an empty space
        super(0);
        wordBonus = 0;
        ltrBonus = 0;
    }

    public Space(char nletter, int nvalue, int nwordBonus, int nletterBonus) {
        //creates a space from the given inputs
        super(nletter, nvalue);
        wordBonus = nwordBonus;
        ltrBonus = nletterBonus;
    }

    public void setWordBonus(int val) {
        //sets the word bonus to the input
        wordBonus = val;
    }

    public void setLtrBonus(int val) {
        //sets the letter bonus to the input
        ltrBonus = val;
    }

    public int getWordBonus() {
        //returns the word bonus
        return wordBonus;
    }

    public int getLtrBonus() {
        //returns the letter bonus
        return ltrBonus;
    }

    public String toString() {
        //outputs the space with colours
        String output;
        String color = "";
        //setting colors
        if (wordBonus == 2) {
            //magenta
            color = "\u001b[45m\u001b[37m";
        } else if (wordBonus == 3) {
            //red
            color = "\u001b[41m\u001b[37m";
        } else if (ltrBonus == 2) {
            //cyan
            color = "\u001b[46m";
        } else if (ltrBonus == 3) {
            //blue
            color = "\u001b[44m\u001b[37m";
        }
        output = color + " " + letter + " ";
        return output;
    }

    public void setLetter(char inLetter) {
        //sets the letter to the input
        letter = inLetter;
    }

    public void setValue(int inValue) {
        //sets the value to the input
        value = inValue;
    }

    public String save() {
        //outputs space
        String output = super.toString() + " " + wordBonus + " " + ltrBonus;
        return output;
    }
}
