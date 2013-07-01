import static org.junit.Assert.*;

import org.junit.*;

public class BoardTest {
	Board b;
	Piece pyr1, pyr2, pyr3, pyr4, s, sRotated;
    Piece l;
	// This shows how to build things in setUp() to re-use
	// across tests.
	
	// In this case, setUp() makes shapes,
	// and also a 3X6 board, with pyr placed at the bottom,
	// ready to be used by tests.
	@Before
	public void setUp() throws Exception {
		b = new Board(3, 6);
		
		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();

		b.place(pyr1, 0, 0);

        s = new Piece(Piece.S1_STR);
        sRotated = s.computeNextRotation();

    }
	
	// Check the basic width/height/max after the one placement
	@Test
	public void testSample1() {
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(2, b.getColumnHeight(1));
		assertEquals(2, b.getMaxHeight());
		assertEquals(3, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(0, b.getRowWidth(2));
	}
	
	// Place sRotated into the board, then check some measures
	@Test
	public void testSample2() {
		b.commit();
		int result = b.place(sRotated, 1, 1);
		assertEquals(Board.PLACE_OK, result);
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(4, b.getColumnHeight(1));
		assertEquals(3, b.getColumnHeight(2));
		assertEquals(4, b.getMaxHeight());
	}
    @Test
    public void testClearRows() {

        b.clearRows();

        assertEquals(0, b.getColumnHeight(0));
        assertEquals(1, b.getColumnHeight(1));
        assertEquals(1, b.getMaxHeight());
        assertEquals(1, b.getRowWidth(0));
        assertEquals(0, b.getRowWidth(1));
        b.commit();
        b.place(pyr2, 1, 0);

        b.clearRows();

        Piece[] pieces = Piece.getPieces();
        l = pieces[Piece.L2];
        l = l.fastRotation();
        l = l.fastRotation();

        b.commit();
        b.place(l,0, b.dropHeight(l,0));

        b.clearRows();
        assertEquals(0, b.getColumnHeight(1));
        assertEquals(0, b.getMaxHeight());
        assertEquals(0, b.getRowWidth(0));


    }
	// Make  more tests, by putting together longer series of 
	// place, clearRows, undo, place ... checking a few col/row/max
	// numbers that the board looks right after the operations.
	@Test
    public void testUndo(){
        b.undo();
        assertEquals(0, b.getColumnHeight(1));
        assertEquals(0, b.getMaxHeight());
        assertEquals(0, b.getRowWidth(0));

    }
    @Test
    public void testUndo2(){
        b = new Board(3, 6);
        b.commit();
        b.place(s,0,b.dropHeight(s,0));
        b.clearRows();
        b.clearRows();
        b.clearRows();

        b.commit();
        b.place(s,0,b.dropHeight(s,0));
        b.clearRows();

        b.commit();
        b.place(s,0,b.dropHeight(s,0));
        b.clearRows();

        b.commit();
        System.out.println(b.toString());
        assertEquals(5, b.getColumnHeight(0));
        assertEquals(6, b.getColumnHeight(1));
        assertEquals(6, b.getMaxHeight());
        assertEquals(2, b.getRowWidth(0));
        assertEquals(2, b.getRowWidth(1));
        assertEquals(2, b.getRowWidth(2));

    }
	
}
