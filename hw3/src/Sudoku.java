

import java.util.*;

/*
 * Encapsulates a Sudoku grid to be solved.
 * CS108 Stanford.
 */
public class Sudoku {
	// Provided grid data for main/testing
	// The instance variable strategy is up to you.
	
	// Provided easy 1 6 grid
	// (can paste this text into the GUI too)
	public static final int[][] easyGrid = Sudoku.stringsToGrid(
	"1 6 4 0 0 0 0 0 2",
	"2 0 0 4 0 3 9 1 0",
	"0 0 5 0 8 0 4 0 7",
	"0 9 0 0 0 6 5 0 0",
	"5 0 0 1 0 2 0 0 8",
	"0 0 8 9 0 0 0 3 0",
	"8 0 9 0 4 0 2 0 0",
	"0 7 3 5 0 9 0 0 1",
	"4 0 0 0 0 0 6 7 9");
	
	
	// Provided medium 5 3 grid
	public static final int[][] mediumGrid = Sudoku.stringsToGrid(
	 "530070000",
	 "600195000",
	 "098000060",
	 "800060003",
	 "400803001",
	 "700020006",
	 "060000280",
	 "000419005",
	 "000080079");
	
	// Provided hard 3 7 grid
	// 1 solution this way, 6 solutions if the 7 is changed to 0
	public static final int[][] hardGrid = Sudoku.stringsToGrid(
	"3 7 0 0 0 0 0 8 0",
	"0 0 1 0 9 3 0 0 0",
	"0 4 0 7 8 0 0 0 3",
	"0 9 3 8 0 0 0 1 2",
	"0 0 0 0 4 0 0 0 0",
	"5 2 0 0 0 6 7 9 0",
	"6 0 0 0 2 1 0 4 0",
	"0 0 0 5 3 0 9 0 0",
	"0 3 0 0 0 0 0 5 1");

    public static final int[][] zeroGrid = Sudoku.stringsToGrid(
            "0 0 0 0 0 0 0 0 0",
            "0 0 0 0 0 0 0 0 0",
            "0 0 0 0 0 0 0 0 0",
            "0 0 0 0 0 0 0 0 0",
            "0 0 0 0 0 0 0 0 0",
            "0 0 0 0 0 0 0 0 0",
            "0 0 0 0 0 0 0 0 0",
            "0 0 0 0 0 0 0 0 0",
            "0 0 0 0 0 0 0 0 0");
	
	
	public static final int SIZE = 9;  // size of the whole 9x9 puzzle
	public static final int PART = 3;  // size of each 3x3 part
	public static final int MAX_SOLUTIONS = 100;

    private int[][] grid;
    private int[][] solution;
    ArrayList<Spot> spotList;

    private int solutionsCounter;
    private long elapsedTime;


	// Provided various static utility methods to
	// convert data formats to int[][] grid.
	
	/**
	 * Returns a 2-d grid parsed from strings, one string per row.
	 * The "..." is a Java 5 feature that essentially
	 * makes "rows" a String[] array.
	 * (provided utility)
	 * @param rows array of row strings
	 * @return grid
	 */
	public static int[][] stringsToGrid(String... rows) {
		int[][] result = new int[rows.length][];
		for (int row = 0; row<rows.length; row++) {
			result[row] = stringToInts(rows[row]);
		}
		return result;
	}
	
	
	/**
	 * Given a single string containing 81 numbers, returns a 9x9 grid.
	 * Skips all the non-numbers in the text.
	 * (provided utility)
	 * @param text string of 81 numbers
	 * @return grid
	 */
	public static int[][] textToGrid(String text) {
		int[] nums = stringToInts(text);
		if (nums.length != SIZE*SIZE) {
			throw new RuntimeException("Needed 81 numbers, but got:" + nums.length);
		}
		
		int[][] result = new int[SIZE][SIZE];
		int count = 0;
		for (int row = 0; row<SIZE; row++) {
			for (int col=0; col<SIZE; col++) {
				result[row][col] = nums[count];
				count++;
			}
		}
		return result;
	}
	
	
	/**
	 * Given a string containing digits, like "1 23 4",
	 * returns an int[] of those digits {1 2 3 4}.
	 * (provided utility)
	 * @param string string containing ints
	 * @return array of ints
	 */
	public static int[] stringToInts(String string) {
		int[] a = new int[string.length()];
		int found = 0;
		for (int i=0; i<string.length(); i++) {
			if (Character.isDigit(string.charAt(i))) {
				a[found] = Integer.parseInt(string.substring(i, i+1));
				found++;
			}
		}
		int[] result = new int[found];
		System.arraycopy(a, 0, result, 0, found);
		return result;
	}


	// Provided -- the deliverable main().
	// You can edit to do easier cases, but turn in
	// solving hardGrid.
	public static void main(String[] args) {
		Sudoku sudoku;
		sudoku = new Sudoku(easyGrid);
		
		System.out.println(sudoku); // print the raw problem
		int count = sudoku.solve();
		System.out.println("solutions:" + count);
		System.out.println("elapsed:" + sudoku.getElapsed() + "ms");
		System.out.println(sudoku.getSolutionText());
	}
	
	/*
	 * Spot inner Class to store unassigned spots in grid
	 *
	 */
	private class Spot implements Comparable{

        private int row, col, validValsCounter;

        public Spot(int row, int col) {
           this.row = row;
           this.col = col;
           validValsCounter = getValidValues().size();
        }

        public void set(int value) { grid[row][col] = value; }

        private HashSet<Integer> getValidValues() {
            HashSet<Integer> validSet = new HashSet<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9));

            for(int i = 0; i < SIZE; i++) {
                validSet.remove(grid[i][col]); // removing col occupied values
                validSet.remove(grid[row][i]); // removing row occupied values
                validSet.remove(grid[row/PART * PART + i/PART][col/PART * PART  + i%PART]); //removing block occupied values
            }

            return validSet;
        }

        @Override
        public int compareTo(Object elem){
            Spot cmp = (Spot)elem;
            return this.validValsCounter - cmp.validValsCounter;
        }
    }

	/**
	 * Sets up based on the given ints.
	 */
	public Sudoku(int[][] ints) {
        assert (ints.length == SIZE && ints[0].length == SIZE);
        grid = ints;
        solution = new int[SIZE][SIZE];
        solutionsCounter = 0;
        createSpotList();
	}

    private void createSpotList() {
        spotList = new ArrayList<Spot>();

        for(int row = 0; row < SIZE; row++)
            for(int col = 0; col < SIZE; col++)
                if(grid[row][col] == 0){
                    spotList.add(new Spot(row, col));
                }

        Collections.sort(spotList);
    }

    public Sudoku(String values) {
        this(textToGrid(values));
    }

    @Override
	public String toString(){
        return toString(grid);
    }
	
	/**
	 * Solves the puzzle, invoking the underlying recursive search.
	 */

	public int solve() {
        long startTime = System.currentTimeMillis();

        solutionHelper(0);   // run algorithm...

        long endTime = System.currentTimeMillis();

		elapsedTime = endTime - startTime;
        return solutionsCounter;
	}

    public String getSolutionText() {
		return toString(solution);
	}
	
	public long getElapsed() {
		return elapsedTime;
	}


    /********************************************************************/
    /**************************Helper Functions**************************/
    /********************************************************************/

    private String toString(int[][] grid){

        String result = "";

        if(grid != null)
            for(int i = 0;i<SIZE;i++){
                for(int j =0;j<SIZE;j++)
                    result+= grid[i][j]+" ";
                result+="\n";
            }

        return result;
    }

    private void solutionHelper(int position) {

        if(solutionsCounter >= MAX_SOLUTIONS) return;

        //found solution
        if(position == spotList.size()){
            if(solutionsCounter == 0)
                saveSolution(); // Save 1-st solution
            solutionsCounter++;
            return;
        }

        Spot currentSpot = spotList.get(position);
        HashSet<Integer> valuesSet = currentSpot.getValidValues();

        for(int value: valuesSet){
            currentSpot.set(value);
            solutionHelper(position + 1);
            currentSpot.set(0);
        }

    }

    private void saveSolution() {
        for(int i = 0 ;i < SIZE; i++)
            System.arraycopy(grid[i], 0, solution[i], 0, SIZE);
    }


}
