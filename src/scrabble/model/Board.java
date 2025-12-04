package src.scrabble.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Board - The Board that is used to play the Scrabble Game
 * Updated for Milestone 3 with PremiumSquares
 */
public class Board {

    private static final int SIZE = 15;
    private char [] [] grid = new char[SIZE][SIZE];
    private PremiumSquare[][] premiumGrid = new PremiumSquare[SIZE][SIZE];
    private boolean[][] usedPremium = new boolean[SIZE][SIZE];

    /**
     * Creates a new board and initializes all squares.
     */
    public Board() {
        for(int var1 = 0; var1 < 15; var1++)
        {
            for (int var2 = 0; var2 < 15; var2++) {
                this.grid[var1][var2] ='_';
                this.usedPremium[var1][var2] = false;
            }
            this.initializePremiumSquares();
        }

    }


    /**
     * Initializes the Scrabble premium squares: Triple Word, Double Word, Triple Letter, Double Letter
     */
    private void initializePremiumSquares() {
        for(int var1 = 0; var1 < 15; ++var1) {
            for(int var2 = 0; var2 < 15; ++var2) {
                this.premiumGrid[var1][var2] = PremiumSquare.NORMAL;
            }
        }

        int[][] var9 = new int[][]{{0, 0}, {0, 7}, {0, 14}, {7, 0}, {7, 14}, {14, 0}, {14, 7}, {14, 14}};

        for(int[] var5 : var9) {
            this.premiumGrid[var5[0]][var5[1]] = PremiumSquare.TRIPLE_WORD;
        }

        int[][] var11 = new int[][]{{1, 1}, {1, 13}, {2, 2}, {2, 12}, {3, 3}, {3, 11}, {4, 4}, {4, 10}, {10, 4}, {10, 10}, {11, 3}, {11, 11}, {12, 2}, {12, 12}, {13, 1}, {13, 13}};

        for(int[] var6 : var11) {
            this.premiumGrid[var6[0]][var6[1]] = PremiumSquare.DOUBLE_WORD;
        }

        int[][] var13 = new int[][]{{1, 5}, {1, 9}, {5, 1}, {5, 5}, {5, 9}, {5, 13}, {9, 1}, {9, 5}, {9, 9}, {9, 13}, {13, 5}, {13, 9}};

        for(int[] var7 : var13) {
            this.premiumGrid[var7[0]][var7[1]] = PremiumSquare.TRIPLE_LETTER;
        }

        int[][] var16 = new int[][]{{0, 3}, {0, 11}, {2, 6}, {2, 8}, {3, 0}, {3, 7}, {3, 14}, {6, 2}, {6, 6}, {6, 8}, {6, 12}, {7, 3}, {7, 11}, {8, 2}, {8, 6}, {8, 8}, {8, 12}, {11, 0}, {11, 7}, {11, 14}, {12, 6}, {12, 8}, {14, 3}, {14, 11}};

        for(int[] var8 : var16) {
            this.premiumGrid[var8[0]][var8[1]] = PremiumSquare.DOUBLE_LETTER;
        }

    }


    /**
     * Returns the character at the given position.
     * Returns null if empty.
     */
    public Character get(int var1, int var2) {
        if (var1 >= 0 && var1 < 15 && var2 >= 0 && var2 < 15) {
            char var3 = this.grid[var1][var2];
            return var3 == '_' ? null : var3;
        } else {
            return null;
        }
    }

    /**
     * Returns the premium type at the given position.
     */
    public PremiumSquare getPremium(int var1, int var2) {
        return var1 >= 0 && var1 < 15 && var2 >= 0 && var2 < 15 ? this.premiumGrid[var1][var2] : PremiumSquare.NORMAL;
    }

    public boolean isPremiumUsed(int var1, int var2) {
        return var1 >= 0 && var1 < 15 && var2 >= 0 && var2 < 15 ? this.usedPremium[var1][var2] : true;
    }

    public void markPremiumUsed(int var1, int var2) {
        if (var1 >= 0 && var1 < 15 && var2 >= 0 && var2 < 15) {
            this.usedPremium[var1][var2] = true;
        }
    }

    /**
     * Places a single tile at (row, col).
     */
    public void placeTile(int var1, int var2, char var3) {
        if (var1 >= 0 && var1 < 15 && var2 >= 0 && var2 < 15) {
            this.grid[var1][var2] = Character.toUpperCase(var3);
        }
    }

    /**
     * Validates whether a word can be placed at the given location.
     */
    public boolean isValidPlacement(int var1, int var2, boolean var3, String var4) {
        if (var4 != null && !var4.isEmpty()) {
            if (var3) {
                if (var2 + var4.length() > 15) {
                    return false;
                }
            } else if (var1 + var4.length() > 15) {
                return false;
            }

            for(int var5 = 0; var5 < var4.length(); ++var5) {
                int var6 = var3 ? var1 : var1 + var5;
                int var7 = var3 ? var2 + var5 : var2;
                char var8 = this.grid[var6][var7];
                char var9 = Character.toUpperCase(var4.charAt(var5));
                if (var8 != '_' && var8 != var9) {
                    return false;
                }
            }

            if (this.isEmpty()) {
                if (var3) {
                    return var2 <= 7 && var2 + var4.length() > 7;
                } else {
                    return var1 <= 7 && var1 + var4.length() > 7;
                }
            } else {
                boolean var10 = false; // at least one connection (overlap or adjacency)
                boolean var11 = false; // at least one new tile placed
                for (int var12 = 0; var12 < var4.length(); ++var12) {
                    int var13 = var3 ? var1 : var1 + var12;
                    int var14 = var3 ? var2 + var12 : var2;
                    if (this.grid[var13][var14] == '_') {
                        var11 = true;
                        // check orthogonal neighbors for adjacency
                        if ((var13 > 0 && this.grid[var13 - 1][var14] != '_') ||
                            (var13 < 14 && this.grid[var13 + 1][var14] != '_') ||
                            (var14 > 0 && this.grid[var13][var14 - 1] != '_') ||
                            (var14 < 14 && this.grid[var13][var14 + 1] != '_')) {
                            var10 = true;
                        }
                    } else {
                        // overlapping existing tile
                        var10 = true;
                    }
                }
                return var10 && var11;
            }
        } else {
            return false;
        }
    }

    /**
     * Places a full word onto the board.
     * Removes tiles from the player as needed.
     */
    public List<Character> placeWord(int var1, int var2, boolean var3, String var4, Player var5) {
        ArrayList var6 = new ArrayList();
        var5.removeLetters(var4, this, var1, var2, var3);

        for(int var7 = 0; var7 < var4.length(); ++var7) {
            int var8 = var3 ? var1 : var1 + var7;
            int var9 = var3 ? var2 + var7 : var2;
            char var10 = Character.toUpperCase(var4.charAt(var7));
            this.grid[var8][var9] = var10;
            this.markPremiumUsed(var8, var9);
            var6.add(var10);
        }
        return var6;
    }

    /**
     * Calculates score for a placement at the given position.
     */
    public int calculateScore(String var1, int var2, int var3, boolean var4) {
        int var5 = 0;
        int var6 = 1;

        for (int var7 = 0; var7 < var1.length(); ++var7) {
            int var8 = var4 ? var2 : var2 + var7;
            int var9 = var4 ? var3 + var7 : var3;
            char var10 = Character.toUpperCase(var1.charAt(var7));
            int var11 = this.getLetterValue(var10);
            PremiumSquare var12 = this.getPremium(var8, var9);
            if (!this.isPremiumUsed(var8, var9) && var12 != PremiumSquare.NORMAL) {
                var11 *= var12.getLetterMultiplier();
                var6 *= var12.getWordMultiplier();
            }
            var5 += var11;
        }
        return var5 * var6;
    }

    /**
     * Returns the standard Scrabble point value for a letter.
     */
    private int getLetterValue(char var1) {
        var1 = Character.toUpperCase(var1);
        if ("AEILNORSTU".indexOf(var1) >= 0) {
            return 1;
        } else if ("DG".indexOf(var1) >= 0) {
            return 2;
        } else if ("BCMP".indexOf(var1) >= 0) {
            return 3;
        } else if ("FHVWY".indexOf(var1) >= 0) {
            return 4;
        } else if (var1 == 'K') {
            return 5;
        } else if ("JX".indexOf(var1) >= 0) {
            return 8;
        } else {
            return "QZ".indexOf(var1) >= 0 ? 10 : 0;
        }
    }

    /**
     * Returns true if the board is completely empty.
     */
    public boolean isEmpty() {
        for(int var1 = 0; var1 < 15; ++var1) {
            for(int var2 = 0; var2 < 15; ++var2) {
                if (this.grid[var1][var2] != '_') {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Checks if a word of length L fits starting at (row, col).
     */
    public boolean fits(int var1, int var2, boolean var3, int var4) {
        if (var3) {
            return var2 + var4 <= 15;
        } else {
            return var1 + var4 <= 15;
        }
    }

    @Override
    public String toString() {
        StringBuilder var1 = new StringBuilder();

        for(int var2 = 0; var2 < 15; ++var2) {
            for(int var3 = 0; var3 < 15; ++var3) {
                var1.append(this.grid[var2][var3]).append(" ");
            }
            var1.append("\n");
        }
        return var1.toString();
    }
}
