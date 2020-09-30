package scrabble;
//Jakob Klose

public class Tile {

    protected char letter;
    protected int value;

    public Tile(int a) {
        //creates an empty tile
        letter = '-';
        value = 0;
    }

    public Tile(char nletter, int nvalue) {
        //loads a tile from input
        letter = nletter;
        value = nvalue;
    }

    public Tile() {
        //creates a random tile
        int randnum = (int) Math.round(Math.random() * 95);
        if (randnum < 7) {//a
            letter = 65;
            value = 1;
        } else if (randnum < 9) {//b
            letter = 66;
            value = 3;
        } else if (randnum < 11) {//c
            letter = 67;
            value = 3;
        } else if (randnum < 15) {//d
            letter = 68;
            value = 2;
        } else if (randnum < 27) {//e
            letter = 69;
            value = 1;
        } else if (randnum < 29) {//f
            letter = 70;
            value = 4;
        } else if (randnum < 32) {//g
            letter = 71;
            value = 2;
        } else if (randnum < 34) {//h
            letter = 71;
            value = 4;
        } else if (randnum < 43) {//i
            letter = 73;
            value = 1;
        } else if (randnum < 44) {//j
            letter = 74;
            value = 8;
        } else if (randnum < 45) {//k
            letter = 75;
            value = 5;
        } else if (randnum < 49) {//l
            letter = 76;
            value = 1;
        } else if (randnum < 51) {//m
            letter = 77;
            value = 3;
        } else if (randnum < 57) {//n
            letter = 78;
            value = 1;
        } else if (randnum < 65) {//o
            letter = 79;
            value = 1;
        } else if (randnum < 67) {//p
            letter = 80;
            value = 3;
        } else if (randnum < 68) {//q
            letter = 81;
            value = 10;
        } else if (randnum < 74) {//r
            letter = 82;
            value = 1;
        } else if (randnum < 78) {//s
            letter = 83;
            value = 1;
        } else if (randnum < 84) {//t
            letter = 84;
            value = 1;
        } else if (randnum < 88) {//u
            letter = 85;
            value = 1;
        } else if (randnum < 90) {//v
            letter = 86;
            value = 4;
        } else if (randnum < 92) {//w
            letter = 87;
            value = 4;
        } else if (randnum < 93) {//x
            letter = 88;
            value = 8;
        } else if (randnum < 95) {//y
            letter = 89;
            value = 4;
        } else if (randnum < 96) {//z
            letter = 90;
            value = 10;
        }
    }

    public char getLetter() {
        //returns the character value
        return letter;
    }

    public int getValue() {
        //returns the value
        return value;
    }

    public String toString() {
        //outputs the content of the tile
        String output = letter + " " + value;
        return output;
    }
}
