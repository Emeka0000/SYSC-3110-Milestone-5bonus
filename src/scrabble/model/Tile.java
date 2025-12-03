package src.scrabble.model;

public class Tile {
    private final char letter;
    private final int value;
    private final boolean blank;

    public Tile(char var1) {
        this.letter = Character.toUpperCase(var1);
        this.blank = false;
        this.value = this.calculateValue(this.letter);
    }

    private Tile(char var1, int var2, boolean var3) {
        this.letter = var1;
        this.value = var2;
        this.blank = var3;
    }

    public static Tile createBlank() {
        return new Tile('_', 0, true);
    }

    private int calculateValue(char var1) {
        switch (var1) {
            case 'A':
            case 'E':
            case 'I':
            case 'L':
            case 'N':
            case 'O':
            case 'R':
            case 'S':
            case 'T':
            case 'U':
                return 1;
            case 'B':
            case 'C':
            case 'M':
            case 'P':
                return 3;
            case 'D':
            case 'G':
                return 2;
            case 'F':
            case 'H':
            case 'V':
            case 'W':
            case 'Y':
                return 4;
            case 'J':
            case 'X':
                return 8;
            case 'K':
                return 5;
            case 'Q':
            case 'Z':
                return 10;
            default:
                return 0;
        }
    }

    public char getLetter() {
        return this.letter;
    }

    public int getValue() {
        return this.value;
    }

    public boolean isBlank() {
        return this.blank;
    }

    public String toString() {
        return this.blank ? "_" : String.valueOf(this.letter);
    }

    public boolean equals(Object var1) {
        if (!(var1 instanceof Tile var2)) {
            return false;
        } else {
            return this.letter == var2.letter && this.blank == var2.blank;
        }
    }

    public int hashCode() {
        return ("" + this.letter + this.blank).hashCode();
    }

    public String toSimpleString() {
        return String.valueOf(letter);
    }
}

