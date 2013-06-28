//
// TetrisGrid encapsulates a tetris board and has
// a clearRows() capability.

public class TetrisGrid {
    private boolean[][] grid;

    /**
     * Constructs a new instance with the given grid.
     * Does not make a copy.
     *
     * @param grid to set class variable
     */
    public TetrisGrid(boolean[][] grid) {
        this.grid = grid;

    }


    /**
     * Does row-clearing on the grid (see handout).
     */
    public void clearRows() {
        for (int row = 0; row < grid[0].length; row++)
            if (isFilledRow(row))
                dropRow(row);
    }

    private void dropRow(int row) {
        for (int currRow = row; currRow < grid[0].length; currRow++)
            pushNextRowDown(currRow);
    }

    private void pushNextRowDown(int row) {
        for (int col = 0; col < grid.length; col++)
            if (row < grid[0].length - 1)          //all except last rows
                grid[col][row] = grid[col][row + 1];
            else if (row == grid[0].length - 1)  // last row
                grid[col][row] = false;
    }

    private boolean isFilledRow(int row) {
        for (int col = 0; col < grid.length; col++)
            if (!grid[col][row])
                return false;
        return true;
    }

    /**
     * Returns the internal 2d grid array.
     *
     * @return 2d grid array
     */
    boolean[][] getGrid() {
        return grid;
    }
}
