package src.scrabble.model;

import java.util.EventListener;

public interface GameListener extends EventListener {
    void gameStateChanged(GameEvent var1);
}
