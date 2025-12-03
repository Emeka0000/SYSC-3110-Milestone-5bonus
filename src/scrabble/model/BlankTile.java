package src.scrabble.model;

/**
 * Represents a Scrabble blank tile.
 * A blank tile has no point value and can represent any letter chosen by the player.
 *
 * @author Ashfaqul Alam, 101195158
 * @version Nov 23rd, Milestone 3
 */
public class BlankTile {
    private char representedLetter;
    private boolean isBlank = true;

    /**
     * Creates a new blank tile that represents the given letter.
     * The letter is automatically converted to uppercase.
     *
     * @param var1 the chosen character that this blank tile represents
     */
    public BlankTile(char var1) {
        this.representedLetter = Character.toUpperCase(var1);
    }

    /**
     * Returns the letter this blank tile represents.
     *
     * @return the represented letter
     */
    public char getRepresentedLetter() {
        return this.representedLetter;
    }

    /**
     * Returns true since this is always a blank tile.
     *
     * @return true
     */
    public boolean isBlank() {
        return this.isBlank;
    }

    public int getValue() {
        return 0;
    }

    /**
     * Returns a formatted string showing this tile as *(<letter>).
     * Example: If the blank represents 'A', it returns "*(A)".
     *
     * @return a string representation of this blank tile
     */
    @Override
    public String toString() {
        return "*(" + this.representedLetter + ")";
    }
}
