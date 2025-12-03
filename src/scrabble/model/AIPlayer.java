package src.scrabble.model;


import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * AIPlayer automatically generates moves using dictionary-based word
 * generation and brute-force board scanning.
 *
 * For Milestone 3, this AI uses:
 *  - Rack permutations (3 to 7 letters)
 *  - Dictionary validation
 *  - Board validation (fits + placement rules)
 *  - Score calculation
 *  - Best-move selection
 *
 * The algorithm is intentionally simple for this milestone.
 *
 * @author Ashfaqul Alam, 101195158
 * @version Nov 23rd, Milestone 3
 */
public class AIPlayer extends Player {
    private Dictionary dictionary = null;
    private static final java.util.Set<String> ALLOWED_TWO_LETTER = new java.util.HashSet<>(java.util.Arrays.asList(
        "AA","AB","AD","AE","AG","AH","AI","AL","AM","AN","AR","AS","AT","AW","AX","AY",
        "BA","BE","BI","BO","BY",
        "DA","DE","DO",
        "ED","EE","EF","EH","EL","EM","EN","ER","ES","ET","EW","EX",
        "FA","FE",
        "GO",
        "HA","HE","HI","HM","HO",
        "ID","IF","IN","IS","IT",
        "JA","JO",
        "KA","KI",
        "LA","LI","LO",
        "MA","ME","MI","MM","MO","MU","MY",
        "NA","NE","NO","NU",
        "OD","OE","OF","OH","OI","OK","OM","ON","OP","OR","OS","OW","OX","OY",
        "PA","PE","PI",
        "QI",
        "RE",
        "SH","SI","SO",
        "TA","TE","TI","TO",
        "UH","UM","UN","UP","US","UT",
        "WE","WO",
        "XI","XU",
        "YA","YE","YO",
        "ZA"
    ));

    public AIPlayer(String var1) {
        super(var1);
    }

    public void setDictionary(Dictionary var1) {
        this.dictionary = var1;
    }

    public boolean isAI() {
        return true;
    }

    /**
     * Selects the highest-scoring legal move from all possible permutations
     * of letters in the rack.
     *
     * @param var1 the Scrabble board
     * @param var2 not used in milestone 3 (for future duplicates checking)
     * @return best AIMove or null if no valid move
     */
    public AIMove selectBestMove(Board var1, Set<String> var2) {
        List var3 = new ArrayList();
        List var4 = this.getRack();
        System.out.println("\ud83e\udd16 AI analyzing board...");
        System.out.println("   Rack: " + String.valueOf(var4));
        boolean var10001 = this.dictionary != null && !this.dictionary.isEmpty();
        System.out.println("   Dictionary available: " + var10001);
        List<String> var5 = this.generateWordsFromDictionary(var4);
        System.out.println("   Possible words found: " + var5.size());
        if (var5.isEmpty()) {
            System.out.println("    No words found in dictionary - AI PASSES");
            return null;
        } else {
            for(int var6 = 0; var6 < 15; ++var6) {
                for(int var7 = 0; var7 < 15; ++var7) {
                    for( String var9 : var5) {
                        if (var1.fits(var6, var7, true, var9.length())) {
                            try {
								if (var1.isValidPlacement(var6, var7, true, var9) && this.hasLetters(var9, var1, var6, var7, true) && this.formsAllValidWords(var1, var9, var6, var7, true)) {
									int var10 = computeTotalScore(var1, var9, var6, var7, true);
									var3.add(new AIMove(var9, var6, var7, true, var10));
                                }
                            } catch (Exception var11) {
                            }
                        }

                        if (var1.fits(var6, var7, false, var9.length())) {
                            try {
								if (var1.isValidPlacement(var6, var7, false, var9) && this.hasLetters(var9, var1, var6, var7, false) && this.formsAllValidWords(var1, var9, var6, var7, false)) {
									int var14 = computeTotalScore(var1, var9, var6, var7, false);
									var3.add(new AIMove(var9, var6, var7, false, var14));
                                }
                            } catch (Exception var12) {
                            }
                        }
                    }
                }
            }

            System.out.println("   Legal moves found: " + var3.size());
            if (var3.isEmpty()) {
                System.out.println("    No valid placements - AI PASSES");
                return null;
            } else {
                var3 = this.removeDuplicateMoves(var3);
                PrintStream var10000 = System.out;
                String var15 = ((AIMove)var3.get(0)).word;
                var10000.println("    Best move: " + var15 + " at (" + (((AIMove)var3.get(0)).row + 1) + "," + (((AIMove)var3.get(0)).col + 1) + ")");
                return (AIMove)var3.get(0);
            }
        }
    }
	
	private boolean formsAllValidWords(Board var1, String var2, int var3, int var4, boolean var5) {
		if (this.dictionary == null || this.dictionary.isEmpty()) {
			return true;
		}
		String var6 = this.buildMainWord(var1, var3, var4, var5, var2);
		if (!isLikelyValidWord(var6)) {
			return false;
		}
		List<String> var7 = this.buildCrossWords(var1, var3, var4, var5, var2);
		for (String var9 : var7) {
			if (!isLikelyValidWord(var9)) {
				return false;
			}
		}
		return true;
	}

	private boolean isLikelyValidWord(String var1) {
		if (var1 == null) return false;
        if (var1.length() < 2) return false;
        if (var1.length() == 2) {
            return ALLOWED_TWO_LETTER.contains(var1.toUpperCase());
        }
        return this.dictionary != null && this.dictionary.isValid(var1);
	}

	private int computeTotalScore(Board var1, String var2, int var3, int var4, boolean var5) {
		// main word start
		int varStartRow;
		int varStartCol;
		if (var5) {
			int var6 = var4;
			while (var6 > 0 && var1.get(var3, var6 - 1) != null) --var6;
			varStartRow = var3;
			varStartCol = var6;
		} else {
			int var7 = var3;
			while (var7 > 0 && var1.get(var7 - 1, var4) != null) --var7;
			varStartRow = var7;
			varStartCol = var4;
		}
		String varMain = this.buildMainWord(var1, var3, var4, var5, var2);
		int varScore = var1.calculateScore(varMain, varStartRow, varStartCol, var5);
		// cross words
		List<String> varCross = this.buildCrossWords(var1, var3, var4, var5, var2);
		for (int i = 0; i < var2.length(); ++i) {
			int r = var5 ? var3 : var3 + i;
			int c = var5 ? var4 + i : var4;
			if (var1.get(r, c) == null) {
				if (var5) {
					int sr = r;
					while (sr > 0 && var1.get(sr - 1, c) != null) --sr;
					String w = varCross.isEmpty() ? "" : varCross.get(0);
					if (!w.isEmpty()) {
						varScore += var1.calculateScore(w, sr, c, false);
						varCross.remove(0);
					}
				} else {
					int sc = c;
					while (sc > 0 && var1.get(r, sc - 1) != null) --sc;
					String w = varCross.isEmpty() ? "" : varCross.get(0);
					if (!w.isEmpty()) {
						varScore += var1.calculateScore(w, r, sc, true);
						varCross.remove(0);
					}
				}
			}
		}
		return varScore;
	}

	private String buildMainWord(Board var1, int var2, int var3, boolean var4, String var5) {
		StringBuilder var6 = new StringBuilder();
		if (var4) {
			int var7 = var3;
			while (var7 > 0 && var1.get(var2, var7 - 1) != null) {
				--var7;
			}
			int var8 = var3 + var5.length() - 1;
			while (var8 < 14 && var1.get(var2, var8 + 1) != null) {
				++var8;
			}
			for (int var9 = var7; var9 <= var8; ++var9) {
				Character var10 = var1.get(var2, var9);
				if (var9 >= var3 && var9 < var3 + var5.length() && var10 == null) {
					var6.append(Character.toUpperCase(var5.charAt(var9 - var3)));
				} else {
					var6.append(var10);
				}
			}
		} else {
			int var11 = var2;
			while (var11 > 0 && var1.get(var11 - 1, var3) != null) {
				--var11;
			}
			int var12 = var2 + var5.length() - 1;
			while (var12 < 14 && var1.get(var12 + 1, var3) != null) {
				++var12;
			}
			for (int var13 = var11; var13 <= var12; ++var13) {
				Character var14 = var1.get(var13, var3);
				if (var13 >= var2 && var13 < var2 + var5.length() && var14 == null) {
					var6.append(Character.toUpperCase(var5.charAt(var13 - var2)));
				} else {
					var6.append(var14);
				}
			}
		}
		return var6.toString();
	}

	private List<String> buildCrossWords(Board var1, int var2, int var3, boolean var4, String var5) {
		ArrayList var6 = new ArrayList();
		for (int var7 = 0; var7 < var5.length(); ++var7) {
			int var8 = var4 ? var2 : var2 + var7;
			int var9 = var4 ? var3 + var7 : var3;
			if (var1.get(var8, var9) == null) {
				StringBuilder var10 = new StringBuilder();
				if (var4) {
					int var11 = var8;
					while (var11 > 0 && var1.get(var11 - 1, var9) != null) {
						--var11;
					}
					int var12 = var8;
					while (var12 < 14 && var1.get(var12 + 1, var9) != null) {
						++var12;
					}
					for (int var13 = var11; var13 <= var12; ++var13) {
						if (var13 == var8) {
							var10.append(Character.toUpperCase(var5.charAt(var7)));
						} else {
							var10.append(var1.get(var13, var9));
						}
					}
				} else {
					int var15 = var9;
					while (var15 > 0 && var1.get(var8, var15 - 1) != null) {
						--var15;
					}
					int var16 = var9;
					while (var16 < 14 && var1.get(var8, var16 + 1) != null) {
						++var16;
					}
					for (int var17 = var15; var17 <= var16; ++var17) {
						if (var17 == var9) {
							var10.append(Character.toUpperCase(var5.charAt(var7)));
						} else {
							var10.append(var1.get(var8, var17));
						}
					}
				}
				String var18 = var10.toString();
				if (var18.length() > 1) {
					var6.add(var18);
				}
			}
		}
		return var6;
	}

    /**
     * Generates all valid dictionary words that can be formed from the rack.
     */
    private List<String> generateWordsFromDictionary(List<Character> var1) {
        HashSet var2 = new HashSet();
        if (this.dictionary != null && !this.dictionary.isEmpty()) {
            for(int var3 = 3; var3 <= Math.min(7, var1.size()); ++var3) {
                List var4 = this.generateAllPermutations(new ArrayList(), var1, var3);
                System.out.println("   Length " + var3 + ": generated " + var4.size() + " permutations");
                int var5 = 0;

                for(Object var7 : var4) {
                    if (this.dictionary.isValid((String) var7)) {
                        var2.add(var7);
                        ++var5;
                    }
                }

                System.out.println("   Length " + var3 + ": " + var5 + " valid words");
            }

            return new ArrayList(var2);
        } else {
            System.err.println("    Dictionary not available");
            return new ArrayList();
        }
    }

    /**
     * Recursively generates permutations of a given length.
     */
    private List<String> generateAllPermutations(List<Character> var1, List<Character> var2, int var3) {
        ArrayList var4 = new ArrayList();
        if (var1.size() == var3) {
            StringBuilder var8 = new StringBuilder();

            for(char var11 : var1) {
                var8.append(var11);
            }

            String var10 = var8.toString();
            var4.add(var10);
            return var4;
        } else {
            for(int var5 = 0; var5 < var2.size(); ++var5) {
                char var6 = (Character)var2.get(var5);
                var1.add(var6);
                ArrayList var7 = new ArrayList(var2);
                var7.remove(var5);
                var4.addAll(this.generateAllPermutations(var1, var7, var3));
                var1.remove(var1.size() - 1);
            }

            return var4;
        }
    }

    /**
     * Removes duplicate moves.
     */
    private List<AIMove> removeDuplicateMoves(List<AIMove> var1) {
        HashSet var2 = new HashSet();
        ArrayList var3 = new ArrayList();

        for(AIMove var5 : var1) {
            String var6 = var5.word + "_" + var5.row + "_" + var5.col + "_" + var5.horizontal;
            if (!var2.contains(var6)) {
                var2.add(var6);
                var3.add(var5);
            }
        }

        return var3;
    }

    /**
     * Returns Scrabble letter value.
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
}
