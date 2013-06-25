// Test cases for CharGrid -- a few basic tests are provided.

import static org.junit.Assert.*;
import org.junit.Test;

public class CharGridTest {
	
	@Test
	public void testCharArea1() {
		char[][] grid = new char[][] {
				{'a', 'y', ' '},
				{'x', 'a', 'z'},
			};
		
		
		CharGrid cg = new CharGrid(grid);
				
		assertEquals(4, cg.charArea('a'));
		assertEquals(1, cg.charArea('z'));
	}
	
	
	@Test
	public void testCharArea2() {
		char[][] grid = new char[][] {
				{'c', 'a', ' '},
				{'b', ' ', 'b'},
				{' ', ' ', 'a'}
			};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(6, cg.charArea('a'));
		assertEquals(3, cg.charArea('b'));
		assertEquals(1, cg.charArea('c'));
	}
    @Test
    public void testCharArea3() {
        char[][] grid = new char[][] {
                {' '},
                {'b'},
                {'a'}
        };

        CharGrid cg = new CharGrid(grid);

        assertEquals(1, cg.charArea(' '));
        assertEquals(1, cg.charArea('b'));
        assertEquals(1, cg.charArea('a'));
    }
    @Test
    public void testCharArea4() {
        char[][] grid = new char[][] {
                {'c', 'a', ' '}
        };

        CharGrid cg = new CharGrid(grid);

        assertEquals(1, cg.charArea('a'));
        assertEquals(1, cg.charArea(' '));
        assertEquals(1, cg.charArea('c'));
    }

    @Test
    public void testCharArea5() {
        char[][] grid = new char[][] {
                {'c', 'a', ' '},
                {'b', 'a', 'b'},
                {' ', 'a', ' '}
        };

        CharGrid cg = new CharGrid(grid);

        assertEquals(3, cg.charArea('a'));
    }

    @Test
	public void testCountPlus1(){
        char[][] grid = new char[][] {
                {'c', 'a', ' '},
                {'a', 'a', 'a'},
                {' ', 'a', ' '}
        };

        CharGrid cg = new CharGrid(grid);

        assertEquals(1, cg.countPlus());
    }

    @Test
    public void testCountPlus2(){
        char[][] grid = new char[][] {
                {'c', 'v', ' ', 'a', ' ', 'a', ' ', 'a', ' '},
                {'v', 'v', 'v', 'a', ' ', 'v', ' ', 'a', ' '},
                {' ', 'v', ' ', 'a', ' ', 'v', ' ', 'a', ' '},
                {' ', ' ', ' ', 'v', 'v', 'v', 'v', 'v', ' '},
                {' ', 'a', ' ', 'a', ' ', 'v', ' ', 'a', ' '},
                {' ', 'a', ' ', 'a', ' ', 'v', ' ', 'a', ' '}
        };

        CharGrid cg = new CharGrid(grid);

        assertEquals(2, cg.countPlus());
    }

    @Test
    public void testCountPlus3(){
        char[][] grid = new char[][] {
                {'c', 'c', 'c', 'a', ' ', 'a', ' ', 'a', ' '},
        };

        CharGrid cg = new CharGrid(grid);

        assertEquals(0, cg.countPlus());
    }

    @Test
    public void testCountPlus4(){
        char[][] grid = new char[][] {
                {'a'},
                {'a'},
                {'a'}

        };

        CharGrid cg = new CharGrid(grid);

        assertEquals(0, cg.countPlus());
    }
    @Test
    public void testCountPlus5(){
        char[][] grid = new char[][] {
                {'c', 'v', ' ', 'a', ' ', 'a', ' ', 'a', ' '},
                {'v', 'v', 'v', 'v', ' ', 'v', ' ', 'v', ' '},
                {' ', 'v', ' ', 'a', ' ', 'v', ' ', 'v', ' '},
                {' ', 'v', ' ', 'v', ' ', 'v', 'v', 'v', 'v'},
                {' ', 'a', ' ', 'a', ' ', 'v', ' ', 'v', ' '},
                {' ', 'a', ' ', 'a', ' ', 'v', ' ', 'v', ' '}
        };

        CharGrid cg = new CharGrid(grid);

        assertEquals(0, cg.countPlus());
    }
    @Test
    public void testCountPlus6(){
        char[][] grid = new char[][] {
                {'c', 'v', ' ' },
                {'v', 'v', 'v' },
                {' ', 'v', ' ' },
                {' ', 'v', ' ' },

        };

        CharGrid cg = new CharGrid(grid);

        assertEquals(0, cg.countPlus());
    }
    @Test
    public void testCountPlus7(){
        char[][] grid = new char[][] {  };

        CharGrid cg = new CharGrid(grid);

        assertEquals(0, cg.countPlus());
    }
    @Test
    public void testCountPlus8(){
        char[][] grid = new char[][] {
                { 'v', 'v', 'v' },
                { 'v', 'v', 'v' },
                { 'v', 'v', 'v' },

        };

        CharGrid cg = new CharGrid(grid);

        assertEquals(1, cg.countPlus());
    }
    @Test
    public void testCountPlus9() {
        char[][] grid = new char[][] {
                {'c'}
        };

        CharGrid cg = new CharGrid(grid);

        assertEquals(0, cg.countPlus());
    }


}
