package src.scrabble.model;

import java.util.EventObject;

/**
 * Represents an event occurring in the Scrabble game.
 * 
 * @author Ashfaqul Alam, 101195158
 * @version Nov 22rd, Milestone 3
 */
public class GameEvent extends EventObject {
    private final Type type;
    private final String message;
    private final Object data;

    /**
     * Constructs a GameEvent with the specified source, type,
     * and message. Additional data is set to null.
     *
     * @param var1 The object on which the Event initially occurred.
     * @param var2 The type of the game event.
     * @param var3 Optional descriptive message.
     */
    public GameEvent(Object var1, Type var2, String var3) {
        this(var1, var2, var3, (Object)null);
    }

    
    /**
     * Constructs a GameEvent with the specified source, type,
     * message, and additional data.
     *
     * @param var1 The object on which the Event initially occurred.
     * @param var2 The type of the game event.
     * @param var3 Optional descriptive message.
     * @param var4 Optional additional data related to the event.
     */
    public GameEvent(Object var1, Type var2, String var3, Object var4) {
        super(var1);
        this.type = var2;
        this.message = var3;
        this.data = var4;
    }

    /**
     * Returns the type of this game event.
     *
     * @return The event type.
     */
    public Type getType() {
        return this.type;
    }

    /**
     * Returns the message describing this game event.
     *
     * @return The event message, possibly null.
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Returns any additional data associated with this event.
     *
     * @return Additional event data, or null if none.
     */
    public Object getData() {
        return this.data;
    }

    /**
     * Defines possible game event types that can occur in the Scrabble game.
     */
    public static enum Type {
        GAME_STARTED,
        BOARD_UPDATED,
        TURN_CHANGED,
        SCORE_UPDATED,
        RACK_UPDATED,
        PLAYER_UPDATED,
        INVALID_MOVE,
        GAME_ENDED;

        // Private constructor for enum types
        private Type() {
        }
    }
}
