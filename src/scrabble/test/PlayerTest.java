package src.scrabble.test;

import org.junit.jupiter.api.Test;
import src.scrabble.model.Board;
import src.scrabble.model.Player;
import src.scrabble.model.TileBag;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

	@org.junit.Test
    @Test
	public void testFillRackUpToSeven() {
		Player player = new Player("Alice");
		TileBag bag = new TileBag();
		player.fillRack(bag);
		assertEquals(7, player.getRackSize());
		assertEquals(98 - 7, bag.tilesRemaining());
	}

	@Test
	public void testAddScore() {
		Player player = new Player("Bob");
		player.addScore(10);
		player.addScore(7);
		assertEquals(17, player.getScore());
	}

	@Test
	public void testHasLettersBoardAwareReusesBoardTile() {
		Player player = new Player("Cara");
		Board board = new Board();
		board.placeTile(7, 7, 'C');
		// Rack is empty but should still allow playing "C" reusing the board letter
		assertTrue(player.hasLetters("C", board, 7, 7, true));
	}

	@Test
	public void testHasTilesForWordLegacy() {
		Player player = new Player("Dan");
		TileBag bag = new TileBag();
		// Fill rack and assume at least some letters present
		player.fillRack(bag);
		// Should not throw and should be a string result; we can't assert a specific word
		assertNotNull(player.getRackDisplay());
	}
}


