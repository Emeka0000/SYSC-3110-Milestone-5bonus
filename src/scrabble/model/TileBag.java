package src.scrabble.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TileBag {
    private List<Character> bag = new ArrayList();

    public TileBag() {
        this.initializeTiles();
        Collections.shuffle(this.bag);
    }

    private void initializeTiles() {
        this.addTiles('A', 9);
        this.addTiles('B', 2);
        this.addTiles('C', 2);
        this.addTiles('D', 4);
        this.addTiles('E', 12);
        this.addTiles('F', 2);
        this.addTiles('G', 3);
        this.addTiles('H', 2);
        this.addTiles('I', 9);
        this.addTiles('J', 1);
        this.addTiles('K', 1);
        this.addTiles('L', 4);
        this.addTiles('M', 2);
        this.addTiles('N', 6);
        this.addTiles('O', 8);
        this.addTiles('P', 2);
        this.addTiles('Q', 1);
        this.addTiles('R', 6);
        this.addTiles('S', 4);
        this.addTiles('T', 6);
        this.addTiles('U', 4);
        this.addTiles('V', 2);
        this.addTiles('W', 2);
        this.addTiles('X', 1);
        this.addTiles('Y', 2);
        this.addTiles('Z', 1);
    }

    private void addTiles(char var1, int var2) {
        for(int var3 = 0; var3 < var2; ++var3) {
            this.bag.add(var1);
        }

    }

    public Character drawTile() {
        return this.bag.isEmpty() ? null : (Character)this.bag.remove(this.bag.size() - 1);
    }

    public List<Character> drawTiles(int var1) {
        ArrayList var2 = new ArrayList();

        for(int var3 = 0; var3 < var1 && !this.bag.isEmpty(); ++var3) {
            var2.add((Character)this.bag.remove(this.bag.size() - 1));
        }

        return var2;
    }

    public void returnTile(Character var1) {
        if (var1 != null) {
            this.bag.add(var1);
            Collections.shuffle(this.bag);
        }

    }

    public void returnTiles(List<Character> var1) {
        if (var1 != null && !var1.isEmpty()) {
            this.bag.addAll(var1);
            Collections.shuffle(this.bag);
        }

    }

    public boolean isEmpty() {
        return this.bag.isEmpty();
    }

    public int size() {
        return this.bag.size();
    }

    public int tilesRemaining() {
        return this.bag.size();
    }

    public String toString() {
        return "TileBag: " + this.bag.size() + " tiles remaining";
    }
}

