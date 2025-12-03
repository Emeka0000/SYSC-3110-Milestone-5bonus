package src.scrabble.model;

/**
 * Represents the types of premium squares on the Scrabble board,
 * each with specific letter and word score multipliers.
 *
 * @author Ashfaqul Alam, 101195158
 * @version Nov 23rd, Milestone 3
 */
public enum PremiumSquare {
    NORMAL(1, 1),
    DOUBLE_LETTER(2, 1),
    TRIPLE_LETTER(3, 1),
    DOUBLE_WORD(1, 2),
    TRIPLE_WORD(1, 3);

    private final int letterMultiplier;
    private final int wordMultiplier;

    /**
     * Constructs a PremiumSquare enum constant with specified letter and word multipliers.
     *
     * @param var1 multiplier for letter score on this square
     * @param var2 multiplier for word score on this square
     */
    private PremiumSquare(int var3, int var4) {
        this.letterMultiplier = var3;
        this.wordMultiplier = var4;
    }

     /**
     * Returns the letter score multiplier for this premium square.
     *
     * @return letter multiplier (e.g., 1 for normal, 2 for double letter)
     */
    public int getLetterMultiplier() {
        return this.letterMultiplier;
    }

    /**
     * Returns the word score multiplier for this premium square.
     *
     * @return word multiplier (e.g., 1 for normal, 3 for triple word)
     */
    public int getWordMultiplier() {
        return this.wordMultiplier;
    }
}
