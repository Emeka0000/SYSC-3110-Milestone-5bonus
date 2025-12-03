package src.scrabble.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.scrabble.model.GameEvent;
import src.scrabble.model.GameListener;
import src.scrabble.model.Player;
import src.scrabble.model.ScrabbleModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ScrabbleModelTest {

	private ScrabbleModel model;
	private RecordingListener listener;

	@BeforeEach
	public void setup() {
		List<String> players = new ArrayList<>();
		players.add("Alice");
		players.add("Bob");
		model = new ScrabbleModel(players);
		listener = new RecordingListener();
		model.addGameListener(listener);
	}

	@org.junit.Test
    @Test
	public void testStartGameFillsRacksAndFiresEvents() {
		model.startGame();
		for (Player p : model.getPlayers()) {
			assertEquals(7, p.getRackSize());
		}
		assertEquals(98 - (7 * model.getPlayerCount()), model.getTilesRemaining());
		assertFalse(listener.events.isEmpty());
		assertEquals(GameEvent.Type.RACK_UPDATED, listener.getLastType());
	}

	@Test
	public void testPassTurnChangesCurrentPlayer() {
		model.startGame();
		String first = model.getCurrentPlayer().getName();
		model.passTurn();
		String second = model.getCurrentPlayer().getName();
		assertNotEquals(first, second);
		assertTrue(listener.containsType(GameEvent.Type.TURN_CHANGED));
	}

	@Test
	public void testPlaceWordUsingExistingBoardTileIncrementsScoreAndAdvancesTurn() {
		model.startGame();
		// Pre-place 'A' so the player can play "A" without needing rack tiles
		model.getBoard().placeTile(7, 7, 'A');
		Player previous = model.getCurrentPlayer();
		int beforeScore = previous.getScore();
		String currentName = previous.getName();

		model.placeWord(7, 7, true, "A");

		// Score for 'A' is 1
		assertEquals(beforeScore + 1, previous.getScore());
		// Turn should advance
		assertNotEquals(currentName, model.getCurrentPlayer().getName());
		// Events: BOARD_UPDATED and SCORE_UPDATED at least
		assertTrue(listener.containsType(GameEvent.Type.BOARD_UPDATED));
		assertTrue(listener.containsType(GameEvent.Type.SCORE_UPDATED));
	}

	@Test
	public void testPlaceWordInvalidThrows() {
		model.startGame();
		// Word doesn't fit horizontally starting at column 14 with length 4
		assertThrows(IllegalArgumentException.class, () -> model.placeWord(0, 14, true, "ABCD"));
		assertTrue(listener.containsType(GameEvent.Type.INVALID_MOVE));
	}

	@Test
	public void testExchangeTilesEmptyStringAdvancesTurn() {
		model.startGame();
		String before = model.getCurrentPlayer().getName();
		model.exchangeTiles("");
		String after = model.getCurrentPlayer().getName();
		assertNotEquals(before, after);
		assertTrue(listener.containsType(GameEvent.Type.RACK_UPDATED));
		assertTrue(listener.containsType(GameEvent.Type.TURN_CHANGED));
	}

	private static class RecordingListener implements GameListener {
		private final List<GameEvent> events = new CopyOnWriteArrayList<>();

		@Override
		public void gameStateChanged(GameEvent event) {
			events.add(event);
		}

		boolean containsType(GameEvent.Type type) {
			for (GameEvent e : events) {
				if (e.getType() == type) {
					return true;
				}
			}
			return false;
		}

		GameEvent.Type getLastType() {
			if (events.isEmpty()) return null;
			return events.get(events.size() - 1).getType();
		}
	}
}


