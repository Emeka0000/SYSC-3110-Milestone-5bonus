package src.scrabble.model;

import java.util.List;

/**
 * Represents the final evaluated move chosen by the AI.
 * Stores the word tiles placed, board position, orientation, and resulting score.
 *
 * @author Ashfaqul Alam, 101195158
 * @version Nov 23rd, Milestone 3
 */
public class AIMoveResult {
    private final int row;
    private final int col;
    private final boolean horizontal;
    private final List<Tile> wordTiles;
    private final int score;

    /**
     * Constructs an AI move result with full data.
     *
     * @param var1 row index (0-based)
     * @param var2 column index (0-based)
     * @param var3 true if word is placed horizontally
     * @param var4 tiles forming the placed word
     * @param var5 computed score for the move
     */
    public AIMoveResult(int var1, int var2, boolean var3, List<Tile> var4, int var5) {
        this.row = var1;
        this.col = var2;
        this.horizontal = var3;
        this.wordTiles = var4;
        this.score = var5;
    }

    /**
     * Constructs an AI move result without a score (defaults to 0).
     */
    public AIMoveResult(int var1, int var2, boolean var3, List<Tile> var4) {
        this(var1, var2, var3, var4, 0);
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public boolean isHorizontal() {
        return this.horizontal;
    }

    public List<Tile> getWordTiles() {
        return this.wordTiles;
    }

    public int getScore() {
        return this.score;
    }

    /**
     * Checks whether this move contains valid placement data.
     *
     * @return true if position & tiles are valid
     */
    public boolean isValid() {
        return this.wordTiles != null && !this.wordTiles.isEmpty() && this.row >= 0 && this.col >= 0;
    }

    /**
     * Returns a readable description of the move.
     */
    @Override
    public String toString() {
        int var10000 = this.row;
        return "AI Move: (" + var10000 + "," + this.col + ") " + (this.horizontal ? "H" : "V") + " - " + this.wordTiles.size() + " tiles, Score: " + this.score;
    }
}
