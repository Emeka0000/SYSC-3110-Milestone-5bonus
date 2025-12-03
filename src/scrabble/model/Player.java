package src.scrabble.model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int score;
    private List<Character> rack;
    private int turns;
    private List<String> wordsPlayed;
    private List<Integer> wordScores;

    public Player(String var1) {
        this.name = var1;
        this.score = 0;
        this.rack = new ArrayList();
        this.turns = 0;
        this.wordsPlayed = new ArrayList();
        this.wordScores = new ArrayList();
    }

    public String getName() {
        return this.name;
    }

    public int getScore() {
        return this.score;
    }

    public void addScore(int var1) {
        this.score += var1;
    }

    public List<Character> getRack() {
        return new ArrayList(this.rack);
    }

    protected List<Character> getInternalRack() {
        return this.rack;
    }

    public int getTurnCount() {
        return this.turns;
    }

    public void incrementTurnCount() {
        ++this.turns;
    }

    public void addPlayedWord(String var1, int var2) {
        if (var1 != null && !var1.isEmpty()) {
            this.wordsPlayed.add(var1);
            this.wordScores.add(var2);
        }
    }

    public List<String> getWordsPlayed() {
        return new ArrayList(this.wordsPlayed);
    }

    public List<Integer> getWordScores() {
        return new ArrayList(this.wordScores);
    }

    public String getRackDisplay() {
        StringBuilder var1 = new StringBuilder();

        for(Character var3 : this.rack) {
            var1.append(var3).append(" ");
        }

        return var1.toString().trim();
    }

    public void fillRack(TileBag var1) {
        while(this.rack.size() < 7 && !var1.isEmpty()) {
            Character var2 = var1.drawTile();
            if (var2 != null) {
                this.rack.add(var2);
            }
        }

    }

    public void refillRack(TileBag var1) {
        this.fillRack(var1);
    }

    public boolean hasLetters(String var1, Board var2, int var3, int var4, boolean var5) {
        ArrayList var6 = new ArrayList();

        for(int var7 = 0; var7 < var1.length(); ++var7) {
            int var8 = var5 ? var3 : var3 + var7;
            int var9 = var5 ? var4 + var7 : var4;
            Character var10 = var2.get(var8, var9);
            char var11 = Character.toUpperCase(var1.charAt(var7));
            if (var10 == null || var10 == '_') {
                var6.add(var11);
            }
        }

        ArrayList var12 = new ArrayList(this.rack);

        for(Object var14 : var6) {
            if (!var12.contains(var14)) {
                return false;
            }

            var12.remove(var14);
        }

        return true;
    }

    public boolean removeLetters(String var1, Board var2, int var3, int var4, boolean var5) {
        ArrayList var6 = new ArrayList();

        for(int var7 = 0; var7 < var1.length(); ++var7) {
            int var8 = var5 ? var3 : var3 + var7;
            int var9 = var5 ? var4 + var7 : var4;
            Character var10 = var2.get(var8, var9);
            char var11 = Character.toUpperCase(var1.charAt(var7));
            if (var10 == null || var10 == '_') {
                var6.add(var11);
            }
        }

        for(Object var13 : var6) {
            if (!this.rack.remove(var13)) {
                return false;
            }
        }

        return true;
    }

    public void exchangeTiles(List<Character> var1, TileBag var2) {
        for(Character var4 : var1) {
            if (this.rack.remove(var4)) {
                var2.returnTile(var4);
            }
        }

        this.fillRack(var2);
    }

    public boolean isAI() {
        return false;
    }

    public String toString() {
        String var10000 = this.name;
        return var10000 + " - Score: " + this.score + (this.isAI() ? " (AI)" : "");
    }

    public int getRackSize() {
        return this.rack.size();
    }
}