package src.scrabble.test;

import org.junit.jupiter.api.Test;
import src.scrabble.model.TileBag;

import static org.junit.jupiter.api.Assertions.*;

public class TileBagTest {

	@org.junit.Test
    @Test
	public void testInitialSizeIs98() {
		TileBag bag = new TileBag();
		assertEquals(98, bag.size());
		assertEquals(98, bag.tilesRemaining());
	}

	@Test
	public void testDrawTileReducesSize() {
		TileBag bag = new TileBag();
		Character tile = bag.drawTile();
		assertNotNull(tile);
		assertEquals(97, bag.size());
	}

	@Test
	public void testReturnTileIncreasesSize() {
		TileBag bag = new TileBag();
		Character tile = bag.drawTile();
		assertNotNull(tile);
		int afterDraw = bag.size();
		bag.returnTile(tile);
		assertEquals(afterDraw + 1, bag.size());
	}

	@Test
	public void testEmptyAfterDrawingAll() {
		TileBag bag = new TileBag();
		for (int i = 0; i < 98; i++) {
			assertNotNull(bag.drawTile());
		}
		assertTrue(bag.isEmpty());
		assertNull(bag.drawTile());
	}
}


