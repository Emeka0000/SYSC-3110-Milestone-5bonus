package src.scrabble.test;

import org.junit.jupiter.api.Test;
import src.scrabble.model.Board;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

	@org.junit.Test
    @Test
	public void testBoardInitiallyEmpty() {
		Board board = new Board();
		assertTrue(board.isEmpty());
	}

	@Test
	public void testPlaceTileAndGet() {
		Board board = new Board();
		board.placeTile(7, 7, 'a');
		assertEquals(Character.valueOf('A'), board.get(7, 7));
	}

	@Test
	public void testFitsWithinBounds() {
		Board board = new Board();
		assertTrue(board.fits(0, 0, true, 5));
		assertTrue(board.fits(14, 10, true, 5)); // 10..14 horizontally
		assertTrue(board.fits(10, 14, false, 5)); // 10..14 vertically
		assertFalse(board.fits(0, 12, true, 4)); // 12..15 out of bounds
		assertFalse(board.fits(13, 0, false, 3)); // 13..15 out of bounds
	}

	@Test
	public void testIsValidPlacementFirstMoveAnywhere() {
		Board board = new Board();
		assertTrue(board.isValidPlacement(0, 0, true, "HELLO"));
		assertTrue(board.isValidPlacement(14, 10, true, "HELLO"));
		assertTrue(board.isValidPlacement(10, 14, false, "HI"));
	}

	@Test
	public void testIsValidPlacementOverwritesDifferentLetters() {
		Board board = new Board();
		board.placeTile(7, 7, 'A');
		// Same letter overlap is okay
		assertTrue(board.isValidPlacement(7, 7, true, "A"));
		// Different letter overlap should be invalid
		assertFalse(board.isValidPlacement(7, 7, true, "B"));
	}
}


