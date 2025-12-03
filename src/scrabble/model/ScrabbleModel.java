package src.scrabble.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import src.scrabble.model.GameEvent.Type;

public class ScrabbleModel {
    private final Board board = new Board();
    private final TileBag bag = new TileBag();
    private final List<Player> players = new ArrayList();
    private int currentPlayerIndex = 0;
    private final List<GameListener> listeners = new ArrayList();
    private int consecutivePasses = 0;
    private Dictionary dictionary = null;
    private static final HashSet<String> ALLOWED_TWO_LETTER = new HashSet<>(Arrays.asList(
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

    public ScrabbleModel(List<String> var1) {
        for(String var3 : var1) {
            Player var4 = new Player(var3);
            this.players.add(var4);
        }

    }

    public void addGameListener(GameListener var1) {
        this.listeners.add(var1);
    }

    public void removeGameListener(GameListener var1) {
        this.listeners.remove(var1);
    }

    protected void fireGameEvent(GameEvent.Type var1, String var2) {
        this.fireGameEvent(var1, var2, (Object)null);
    }

    protected void fireGameEvent(GameEvent.Type var1, String var2, Object var3) {
        GameEvent var4 = new GameEvent(this, var1, var2, var3);

        for(GameListener var6 : this.listeners) {
            var6.gameStateChanged(var4);
        }

    }

    public void startGame() {
        for(Player var2 : this.players) {
            var2.fillRack(this.bag);
        }

        this.consecutivePasses = 0;
        this.fireGameEvent(Type.GAME_STARTED, "Game started!");
        GameEvent.Type var10001 = Type.TURN_CHANGED;
        String var10002 = this.getCurrentPlayer().getName();
        this.fireGameEvent(var10001, "Current player: " + var10002 + (this.getCurrentPlayer().isAI() ? " (AI)" : ""));
        this.fireGameEvent(Type.RACK_UPDATED, "Rack updated");
    }

    public void placeWord(int var1, int var2, boolean var3, String var4) {
        Player var5 = this.getCurrentPlayer();
        var4 = var4.toUpperCase();
        if (var4.length() < 2) {
            this.fireGameEvent(Type.INVALID_MOVE, "Word must be at least 2 letters");
            throw new IllegalArgumentException("Word too short");
        }
        if (this.dictionary != null && !this.dictionary.isEmpty() && !this.dictionary.isValid(var4)) {
            this.fireGameEvent(Type.INVALID_MOVE, "Word '" + var4 + "' is not in the dictionary");
            throw new IllegalArgumentException("Word not in dictionary");
        } else if (!this.board.isValidPlacement(var1, var2, var3, var4)) {
            this.fireGameEvent(Type.INVALID_MOVE, "Invalid placement - word doesn't fit or overlaps incorrectly");
            throw new IllegalArgumentException("Invalid placement");
        } else if (!var5.hasLetters(var4, this.board, var1, var2, var3)) {
            this.fireGameEvent(Type.INVALID_MOVE, "You don't have the required tiles");
            throw new IllegalArgumentException("Insufficient tiles");
        } else {
            // Validate main and cross words with dictionary
            WordAt var12 = this.buildMainWordAt(var1, var2, var3, var4);
            if (!this.isLikelyValidWord(var12.word)) {
                this.fireGameEvent(Type.INVALID_MOVE, "Invalid move - forms invalid word '" + var12.word + "'");
                throw new IllegalArgumentException("Forms invalid word: " + var12.word);
            }
            List<WordAt> var13 = this.buildCrossWordsDetailed(var1, var2, var3, var4);
            for (WordAt var15 : var13) {
                if (!this.isLikelyValidWord(var15.word)) {
                    this.fireGameEvent(Type.INVALID_MOVE, "Invalid move - forms invalid word '" + var15.word + "'");
                    throw new IllegalArgumentException("Forms invalid word: " + var15.word);
                }
            }
            // Compute total score (main + cross) BEFORE placing (so premiums count)
            int var7 = this.board.calculateScore(var12.word, var12.row, var12.col, var3);
            for (WordAt var16 : var13) {
                var7 += this.board.calculateScore(var16.word, var16.row, var16.col, var16.horizontal);
            }
            // Place and update state
            this.board.placeWord(var1, var2, var3, var4, var5);
            var5.addScore(var7);
            var5.addPlayedWord(var4, var7);
            var5.incrementTurnCount();
            var5.refillRack(this.bag);
            this.consecutivePasses = 0;
            this.fireGameEvent(Type.BOARD_UPDATED, "Board updated");
            this.fireGameEvent(Type.SCORE_UPDATED, var5.getName() + " scored " + var7 + " points. Pass counter reset.", var7);
            if (this.bag.isEmpty() && var5.getRackSize() == 0) {
                this.fireGameEvent(Type.GAME_ENDED, "Game ended - tiles exhausted");
                return;
            }
            this.nextTurn();
        }
    }
    
    private boolean isLikelyValidWord(String var1) {
        if (var1 == null) return false;
        if (var1.length() < 2) return false;
        if (var1.length() == 2) {
            return ALLOWED_TWO_LETTER.contains(var1.toUpperCase());
        }
        if (this.dictionary == null || this.dictionary.isEmpty()) return false;
        return this.dictionary.isValid(var1);
    }
    
    private WordAt buildMainWordAt(int var1, int var2, boolean var3, String var4) {
        StringBuilder var5 = new StringBuilder();
        int varStartRow;
        int varStartCol;
        if (var3) {
            int var6 = var2;
            while (var6 > 0 && this.board.get(var1, var6 - 1) != null) {
                --var6;
            }
            int var7 = var2 + var4.length() - 1;
            while (var7 < 14 && this.board.get(var1, var7 + 1) != null) {
                ++var7;
            }
            for (int var8 = var6; var8 <= var7; ++var8) {
                Character var9 = this.board.get(var1, var8);
                if (var8 >= var2 && var8 < var2 + var4.length() && var9 == null) {
                    var5.append(Character.toUpperCase(var4.charAt(var8 - var2)));
                } else {
                    var5.append(var9);
                }
            }
            varStartRow = var1;
            varStartCol = var6;
        } else {
            int var10 = var1;
            while (var10 > 0 && this.board.get(var10 - 1, var2) != null) {
                --var10;
            }
            int var11 = var1 + var4.length() - 1;
            while (var11 < 14 && this.board.get(var11 + 1, var2) != null) {
                ++var11;
            }
            for (int var12 = var10; var12 <= var11; ++var12) {
                Character var13 = this.board.get(var12, var2);
                if (var12 >= var1 && var12 < var1 + var4.length() && var13 == null) {
                    var5.append(Character.toUpperCase(var4.charAt(var12 - var1)));
                } else {
                    var5.append(var13);
                }
            }
            varStartRow = var10;
            varStartCol = var2;
        }
        WordAt var14 = new WordAt();
        var14.word = var5.toString();
        var14.row = varStartRow;
        var14.col = varStartCol;
        var14.horizontal = var3;
        return var14;
    }

    private List<WordAt> buildCrossWordsDetailed(int var1, int var2, boolean var3, String var4) {
        ArrayList<WordAt> var5 = new ArrayList<>();
        for (int var6 = 0; var6 < var4.length(); ++var6) {
            int var7 = var3 ? var1 : var1 + var6;
            int var8 = var3 ? var2 + var6 : var2;
            if (this.board.get(var7, var8) == null) {
                StringBuilder var9 = new StringBuilder();
                int varStartRow;
                int varStartCol;
                boolean varHorizontal;
                if (var3) {
                    int var10 = var7;
                    while (var10 > 0 && this.board.get(var10 - 1, var8) != null) {
                        --var10;
                    }
                    int var11 = var7;
                    while (var11 < 14 && this.board.get(var11 + 1, var8) != null) {
                        ++var11;
                    }
                    for (int var12 = var10; var12 <= var11; ++var12) {
                        if (var12 == var7) {
                            var9.append(Character.toUpperCase(var4.charAt(var6)));
                        } else {
                            var9.append(this.board.get(var12, var8));
                        }
                    }
                    varStartRow = var10;
                    varStartCol = var8;
                    varHorizontal = false;
                } else {
                    int var14 = var8;
                    while (var14 > 0 && this.board.get(var7, var14 - 1) != null) {
                        --var14;
                    }
                    int var15 = var8;
                    while (var15 < 14 && this.board.get(var7, var15 + 1) != null) {
                        ++var15;
                    }
                    for (int var16 = var14; var16 <= var15; ++var16) {
                        if (var16 == var8) {
                            var9.append(Character.toUpperCase(var4.charAt(var6)));
                        } else {
                            var9.append(this.board.get(var7, var16));
                        }
                    }
                    varStartRow = var7;
                    varStartCol = var14;
                    varHorizontal = true;
                }
                String var17 = var9.toString();
                if (var17.length() > 1) {
                    WordAt var18 = new WordAt();
                    var18.word = var17;
                    var18.row = varStartRow;
                    var18.col = varStartCol;
                    var18.horizontal = varHorizontal;
                    var5.add(var18);
                }
            }
        }
        return var5;
    }

    private static class WordAt {
        String word;
        int row;
        int col;
        boolean horizontal;
    }

    public void exchangeTiles(String var1) {
        Player var2 = this.getCurrentPlayer();
        ArrayList var3 = new ArrayList();

        for(char var7 : var1.toUpperCase().toCharArray()) {
            var3.add(var7);
        }

        if (!var2.getRack().containsAll(var3)) {
            this.fireGameEvent(Type.INVALID_MOVE, "You don't have those tiles");
            throw new IllegalArgumentException("Invalid tiles");
        } else {
            var2.exchangeTiles(var3, this.bag);
            var2.incrementTurnCount();
            this.consecutivePasses = 0;
            this.fireGameEvent(Type.RACK_UPDATED, "Exchanged " + var1.length() + " tiles. Pass counter reset.");
            this.nextTurn();
        }
    }

    public void passTurn() {
        Player var1 = this.getCurrentPlayer();
        var1.incrementTurnCount();
        ++this.consecutivePasses;
        String var10000 = var1.getName();
        String var2 = var10000 + " passed. (" + this.consecutivePasses + "/" + this.players.size() + " consecutive passes)";
        this.fireGameEvent(Type.TURN_CHANGED, var2);
        if (this.consecutivePasses >= this.players.size() * 2) {
            this.fireGameEvent(Type.GAME_ENDED, "Game ended - all players passed twice consecutively");
        } else {
            this.nextTurn();
        }
    }

    private void nextTurn() {
        this.currentPlayerIndex = (this.currentPlayerIndex + 1) % this.players.size();
        Player var1 = this.getCurrentPlayer();
        GameEvent.Type var10001 = Type.TURN_CHANGED;
        String var10002 = var1.getName();
        this.fireGameEvent(var10001, "Current player: " + var10002 + (var1.isAI() ? " (AI)" : ""));
        this.fireGameEvent(Type.RACK_UPDATED, "Rack updated");
        if (var1.isAI()) {
            this.handleAITurn();
        }

    }

    private void handleAITurn() {
        Player var1 = this.getCurrentPlayer();
        if (var1 instanceof AIPlayer) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException var6) {
                var6.printStackTrace();
            }

            AIPlayer var2 = (AIPlayer)var1;
            AIMove var3 = var2.selectBestMove(this.board, (Set)null);
            if (var3 == null) {
                this.fireGameEvent(Type.TURN_CHANGED, var1.getName() + " (AI) has no valid moves and passes");
                this.passTurn();
            } else {
                try {
                    GameEvent.Type var10001 = Type.TURN_CHANGED;
                    String var10002 = var1.getName();
                    this.fireGameEvent(var10001, var10002 + " (AI) plays: " + var3.word + " at (" + (var3.row + 1) + "," + (var3.col + 1) + ") " + (var3.horizontal ? "H" : "V"));
                    this.placeWord(var3.row, var3.col, var3.horizontal, var3.word);
                } catch (Exception var5) {
                    System.err.println("AI move failed: " + var5.getMessage());
                    this.fireGameEvent(Type.TURN_CHANGED, var1.getName() + " (AI) move failed, passing");
                    this.passTurn();
                }
            }

        }
    }

    private int calculateScore(String var1) {
        int var2 = 0;

        for(char var6 : var1.toCharArray()) {
            var2 += this.getLetterValue(var6);
        }

        return var2;
    }

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

    public Board getBoard() {
        return this.board;
    }

    public Player getCurrentPlayer() {
        return (Player)this.players.get(this.currentPlayerIndex);
    }

    public List<Player> getPlayers() {
        return new ArrayList(this.players);
    }

    public List<Player> getPlayersList() {
        return this.players;
    }

    public int getPlayerCount() {
        return this.players.size();
    }

    public int getTilesRemaining() {
        return this.bag.tilesRemaining();
    }

    public TileBag getTileBag() {
        return this.bag;
    }

    public Dictionary getDictionary() {
        return this.dictionary;
    }

    public void setDictionary(Dictionary var1) {
        this.dictionary = var1;

        for(Player var3 : this.players) {
            if (var3 instanceof AIPlayer) {
                ((AIPlayer)var3).setDictionary(var1);
            }
        }

    }
}