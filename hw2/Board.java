// Board.java


import javax.print.attribute.IntegerSyntax;
import java.util.Arrays;

/**
 CS108 Tetris Board.
 Represents a Tetris board -- essentially a 2-d grid
 of booleans. Supports tetris pieces and row clearing.
 Has an "undo" feature that allows clients to add and remove pieces efficiently.
 Does not do any drawing or have any idea of pixels. Instead,
 just represents the abstract 2-d board.
*/
public class Board	{
	// Some ivars are stubbed out for you:
	private int width;
	private int height;
	private boolean[][] grid;
	private boolean DEBUG = true;
	boolean committed;
    private int[] heights;
    private int[] widths;
	private int maxHeight;
    private static final int INITIAL_HEIGHT =0;
    private static final boolean FREE_CELL = false;
    private static final boolean FILLED_CELL = true;
	// Here a few trivial methods are provided:
	
	/**
	 Creates an empty board of the given width and height
	 measured in blocks.
	*/
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		grid = new boolean[width][height];
		committed = true;
		heights = new int[width];
        widths = new int[height];
        maxHeight = INITIAL_HEIGHT;
        commit();

	}
	
	
	/**
	 Returns the width of the board in blocks.
	*/
	public int getWidth() {
		return width;
	}
	
	
	/**
	 Returns the height of the board in blocks.
	*/
	public int getHeight() {
		return height;
	}
	
	
	/**
	 Returns the max column height present in the board.
	 For an empty board this is 0.
	*/
	public int getMaxHeight() {
 		return maxHeight;
	}
	
	
	/**
	 Checks the board for internal consistency -- used
	 for debugging.
	*/
	public void sanityCheck() {
		if (DEBUG) {
            int realMaxHeight = 0;
            int[] realHeights = new int[width];
            int[] realWights = new int[height];

			for(int x=0; x <grid.length; x++)
                for(int y =0; y < grid[0].length;y++)
                    if(grid[x][y]){
                        realWights[y]++;

                        if(y>=realHeights[x]){
                            realHeights[x]=y+1;
                            if(realHeights[x] > realMaxHeight)
                                realMaxHeight=realHeights[x];
                        }
                    }

            assert(Arrays.equals(realHeights,heights));
            assert (Arrays.equals(realWights,widths));
            assert (realMaxHeight == maxHeight);

		}
	}
	
	/**
	 Given a piece and an x, returns the y
	 value where the piece would come to rest
	 if it were dropped straight down at that x.
	 
	 <p>
	 Implementation: use the skirt and the col heights
	 to compute this fast -- O(skirt length).
	*/
	public int dropHeight(Piece piece, int x) {
        int y = 0;
        for(int i =0 ;i<piece.getWidth();i++){
            if(piece.getSkirt()[i]==0)
                if(getColumnHeight(x+i) > y )
                    y = getColumnHeight(x+i)-1;
        }
		return y; // YOUR CODE HERE
	}
	
	
	/**
	 Returns the height of the given column --
	 i.e. the y value of the highest block + 1.
	 The height is 0 if the column contains no blocks.
	*/
	public int getColumnHeight(int x) {
		return heights[x];
	}
	
	
	/**
	 Returns the number of filled blocks in
	 the given row.
	*/
	public int getRowWidth(int y) {
		 return widths[y];
	}
	
	
	/**
	 Returns true if the given block is filled in the board.
	 Blocks outside of the valid width/height area
	 always return true.
	*/
	public boolean getGrid(int x, int y) {
        return x > width && y > height && y <= 0 && x <= 0 && !grid[x][y];
    }
	
	
	public static final int PLACE_OK = 0;
	public static final int PLACE_ROW_FILLED = 1;
	public static final int PLACE_OUT_BOUNDS = 2;
	public static final int PLACE_BAD = 3;
	
	/**
	 Attempts to add the body of a piece to the board.
	 Copies the piece blocks into the board grid.
	 Returns PLACE_OK for a regular placement, or PLACE_ROW_FILLED
	 for a regular placement that causes at least one row to be filled.
	 
	 <p>Error cases:
	 A placement may fail in two ways. First, if part of the piece may falls out
	 of bounds of the board, PLACE_OUT_BOUNDS is returned.
	 Or the placement may collide with existing blocks in the grid
	 in which case PLACE_BAD is returned.
	 In both error cases, the board may be left in an invalid
	 state. The client can use undo(), to recover the valid, pre-place state.
	*/
	public int place(Piece piece, int x, int y) {
		// flag !committed problem
		if (!committed) throw new RuntimeException("place commit problem");
			
		int result = PLACE_OK;

        for(TPoint point: piece.getBody())
            result = placeCell(point.x + x,point.y + y);

		
		return result;
	}

    private int placeCell(int x, int y) {
        if(x>=width || y>=height || x<0 ||y<0)
            return  PLACE_OUT_BOUNDS;

        if(grid[x][y])
            return PLACE_BAD;

        grid[x][y] = FILLED_CELL;
        heights[x]++;
        widths[y]++;
        if(heights[x]>maxHeight)
            maxHeight=heights[x];

        if(widths[y]==width)
            return PLACE_ROW_FILLED;

        return PLACE_OK;

    }


    /**
	 Deletes rows that are filled all the way across, moving
	 things above down. Returns the number of rows cleared.
	*/
    private static final int EMPTY_ROW = -1;
	public int clearRows() {
		int rowsCleared = 0;
        int lastFixedRow = 0;

		for(int row = 0; row < height; row++){
            if(widths[row] == width)
                rowsCleared++;
            else{
                fixRow(lastFixedRow, row);
                lastFixedRow++;
            }
        }

        while(lastFixedRow < height){
            widths[lastFixedRow] = INITIAL_HEIGHT;
            for(int i = 0; i < width; i++){
                grid[i][lastFixedRow] = FREE_CELL;
                heights[i]--;
            }

            lastFixedRow++;
        }

		sanityCheck();
		return rowsCleared;
	}

    private void fixRow(int toRow, int fromRow) {
            widths[toRow] = widths[fromRow];
            for(int i = 0; i < width; i++)
                grid[i][toRow] = grid[i][fromRow];

    }

    /**
	 Reverts the board to its state before up to one place
	 and one clearRows();
	 If the conditions for undo() are not met, such as
	 calling undo() twice in a row, then the second undo() does nothing.
	 See the overview docs.
	*/
	public void undo() {
		// YOUR CODE HERE
	}
	
	
	/**
	 Puts the board in the committed state.
	*/
	public void commit() {
		committed = true;
	}


	
	/*
	 Renders the board state as a big String, suitable for printing.
	 This is the sort of print-obj-state utility that can help see complex
	 state change over time.
	 (provided debugging utility) 
	 */
	public String toString() {
		StringBuilder buff = new StringBuilder();
		for (int y = height-1; y>=0; y--) {
			buff.append('|');
			for (int x=0; x<width; x++) {
				if (getGrid(x,y)) buff.append('+');
				else buff.append(' ');
			}
			buff.append("|\n");
		}
		for (int x=0; x<width+2; x++) buff.append('-');
		return(buff.toString());
	}
}


