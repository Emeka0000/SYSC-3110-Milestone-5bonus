package src.scrabble.test;

import org.junit.jupiter.api.Test;
import src.scrabble.model.Tile;

import static org.junit.jupiter.api.Assertions.*;

public class TileTest {

	@org.junit.Test
    @Test
	public void testValueCalculation() {
		assertEquals(1, new Tile('A').getValue());
		assertEquals(2, new Tile('D').getValue());
		assertEquals(3, new Tile('B').getValue());
		assertEquals(4, new Tile('F').getValue());
		assertEquals(5, new Tile('K').getValue());
		assertEquals(8, new Tile('J').getValue());
		assertEquals(10, new Tile('Q').getValue());
	}

	@Test
	public void testEqualsAndHashCode() {
		Tile t1 = new Tile('z');
		Tile t2 = new Tile('Z');
		assertEquals(t1, t2);
		assertEquals(t1.hashCode(), t2.hashCode());
	}

	@Test
	public void testToStringAndSimple() {
		Tile t = new Tile('C');
		assertTrue(t.toString().startsWith("C("));
		assertEquals("C", t.toSimpleString());
	}
}


