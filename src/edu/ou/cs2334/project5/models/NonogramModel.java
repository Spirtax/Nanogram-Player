package edu.ou.cs2334.project5.models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class NonogramModel.
 */
public class NonogramModel {

	/** The Constant DELIMITER. */
	private static final String DELIMITER = " ";
	
	/** The Constant IDX_NUM_ROWS. */
	private static final int IDX_NUM_ROWS = 0;
	
	/** The Constant IDX_NUM_COLS. */
	private static final int IDX_NUM_COLS = 1;

	/** The row clues. */
	private int[][] rowClues;
	
	/** The col clues. */
	private int[][] colClues;
	
	/** The cell states. */
	private CellState[][] cellStates;
	
	/**
	 * Instantiates a new nonogram model.
	 *
	 * @param rowClues the row clues
	 * @param colClues the col clues
	 */
	public NonogramModel(int[][] rowClues, int[][] colClues) {
		// TODO: Implement deepCopy. 
		// This is simple, and you should not ask about this on Discord.
		this.rowClues = deepCopy(rowClues);
		this.colClues = deepCopy(colClues);

		cellStates = initCellStates(getNumRows(), getNumCols());
	}
	
	/**
	 * Gets the num cols.
	 *
	 * @return the num cols
	 */
	public int getNumCols() {
		return colClues.length;
	}

	/**
	 * Gets the num rows.
	 *
	 * @return the num rows
	 */
	public int getNumRows() {
		return rowClues.length;
	}

	/**
	 * Instantiates a new nonogram model.
	 *
	 * @param file the file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public NonogramModel(File file) throws IOException {
		// Number of rows and columns
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String header = reader.readLine();
		String[] fields = header.split(DELIMITER);
		int numRows = Integer.parseInt(fields[IDX_NUM_ROWS]);
		int numCols = Integer.parseInt(fields[IDX_NUM_COLS]);

		this.rowClues = deepCopy(readClueLines(reader, numRows));
		this.colClues = deepCopy(readClueLines(reader, numCols));
		
		cellStates = initCellStates(getNumRows(), getNumCols());		
		// Close reader
		reader.close();
	}

	/**
	 * Instantiates a new nonogram model.
	 *
	 * @param filename the filename
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public NonogramModel(String filename) throws IOException {
		// TODO: Fix this constructor
		// This is simple, and you should not ask about this on Discord.
		this(new File(filename));
	}
		
	/* Helper methods */
	
	/**
	 * Inits the cell states.
	 *
	 * @param numRows the num rows
	 * @param numCols the num cols
	 * @return the cell state[][]
	 */
	private static CellState[][] initCellStates(int numRows, int numCols) {
		// Create a 2D array to store numRows * numCols elements
		CellState[][] cellStates = new CellState[numRows][numCols];
		
		// Set each element of the array to empty
		for (int rowIdx = 0; rowIdx < numRows; ++rowIdx) {
			for (int colIdx = 0; colIdx < numCols; ++colIdx) {
				cellStates[rowIdx][colIdx] = CellState.EMPTY;
			}
		}
		
		// Return the result
		return cellStates;
	}
	
	/**
	 * Deep copy.
	 *
	 * @param array the array
	 * @return the int[][]
	 */
	private static int[][] deepCopy(int[][] array) {
		//https://stackoverflow.com/questions/1564832/how-do-i-do-a-deep-copy-of-a-2d-array-in-java
		//I stared at this for a few hours to understand how it works
		
		return Arrays.stream(array) //Convert the array to a stream
				.map(idx -> idx.clone()) //Create a clone of each value in the array (Use clone so it is not referenced the same)
				.toArray(x -> array.clone()); //Clone the array and return
	}
	
	/**
	 * Read clue lines.
	 *
	 * @param reader the reader
	 * @param numLines the num lines
	 * @return the int[][]
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// This method is implemented for you. You need to figure out it is useful.
	private static int[][] readClueLines(BufferedReader reader, int numLines) throws IOException {
		// Create a new 2D array to store the clues
		int[][] clueLines = new int[numLines][];
		
		// Read in clues line-by-line and add them to the array
		for (int lineNum = 0; lineNum < numLines; ++lineNum) {
			// Read in a line
			String line = reader.readLine();
			
			// Split the line according to the delimiter character
			String[] tokens = line.split(DELIMITER);
			
			// Create new int array to store the clues in
			int[] clues = new int[tokens.length];
			for (int idx = 0; idx < tokens.length; ++idx) {
				clues[idx] = Integer.parseInt(tokens[idx]);
			}
			
			// Store the processed clues in the resulting 2D array
			clueLines[lineNum] = clues;
		}
		
		// Return the result
		return clueLines;
	}
	
	/* TODO EVERYTHING BELOW HERE */

	/**
	 * Gets the cell state.
	 *
	 * @param rowIdx the row idx
	 * @param colIdx the col idx
	 * @return the cell state
	 */
	public CellState getCellState(int rowIdx, int colIdx) {
		return cellStates[rowIdx][colIdx];
	}
	
	/**
	 * Gets the cell state as boolean.
	 *
	 * @param rowIdx the row idx
	 * @param colIdx the col idx
	 * @return the cell state as boolean
	 */
	public boolean getCellStateAsBoolean(int rowIdx, int colIdx) {
		return (getCellState(rowIdx, colIdx) == CellState.FILLED ? true:false);
	}

	/**
	 * Sets the cell state.
	 *
	 * @param rowIdx the row idx
	 * @param colIdx the col idx
	 * @param state the state
	 * @return true, if successful
	 */
	public boolean setCellState(int rowIdx, int colIdx, CellState state) {
		if (state == null) return false;
		if (isSolved()) return false;
		if (getCellState(rowIdx, colIdx) == state) return false;
		else cellStates[rowIdx][colIdx] = state;
		return true;
	}
	
	/**
	 * Gets the row clues.
	 *
	 * @return the row clues
	 */
	public int[][] getRowClues() {
		return deepCopy(rowClues);
	}
	
	/**
	 * Gets the col clues.
	 *
	 * @return the col clues
	 */
	public int[][] getColClues() {
		return deepCopy(colClues);
	}
	
	/**
	 * Gets the row clue.
	 *
	 * @param rowIdx the row idx
	 * @return the row clue
	 */
	public int[] getRowClue(int rowIdx) {
		return Arrays.copyOf(rowClues[rowIdx], rowClues[rowIdx].length);
	}

	/**
	 * Gets the col clue.
	 *
	 * @param colIdx the col idx
	 * @return the col clue
	 */
	public int[] getColClue(int colIdx) {
		return Arrays.copyOf(colClues[colIdx], colClues[colIdx].length);
	}
	
	/**
	 * Checks if is row solved.
	 *
	 * @param rowIdx the row idx
	 * @return true, if is row solved
	 */
	public boolean isRowSolved(int rowIdx) {
		return Arrays.toString(projectCellStatesRow(rowIdx)).equals(Arrays.toString(rowClues[rowIdx]));
	}
	
	/**
	 * Checks if is col solved.
	 *
	 * @param colIdx the col idx
	 * @return true, if is col solved
	 */
	public boolean isColSolved(int colIdx) {
		return Arrays.toString(projectCellStatesCol(colIdx)).equals(Arrays.toString(colClues[colIdx]));
	}
	
	/**
	 * Checks if is solved.
	 *
	 * @return true, if is solved
	 */
	public boolean isSolved() {
		for (int r = 0; r < getNumRows(); r++) {
			if (!isRowSolved(r)) return false;
		}
		for (int c = 0; c < getNumCols(); c++) {
			if (!isColSolved(c)) return false;
		}
		return true;
	}
	
	/**
	 * Reset cells.
	 */
	public void resetCells() {
		for (int r = 0; r < getNumRows(); r++) {
			for (int c = 0; c < getNumCols(); c++) {
				cellStates[r][c] = CellState.EMPTY;
			}
		}
	}
	
	/**
	 * Project.
	 *
	 * @param cells the cells
	 * @return the list
	 */
	public static List<Integer> project(boolean[] cells) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(0));
		for (int i = 0; i < cells.length; i++) {
			if (cells[i]) list.set(list.size()-1, list.get(list.size()-1)+1);
			else if (list.get(list.size()-1) != 0) list.add(0);
		}
		//Remove the last element if its 0 and there is elements before it
		if (list.get(list.size()-1) == 0 && list.size() > 1) {
			list.remove(list.size()-1);
		}
		return list;
	}
	
	/**
	 * Project cell states row.
	 *
	 * @param rowIdx the row idx
	 * @return the int[]
	 */
	public int[] projectCellStatesRow(int rowIdx) {
		boolean[] array = new boolean[getNumCols()];
		//Convert cellStates to boolean values
		for (int i = 0; i < array.length; i++) {
			array[i] = getCellStateAsBoolean(rowIdx, i);
		}
		
		//Convert to stream, filter Integer to int values, and convert to array and return
		return project(Arrays.copyOfRange(array, 0, array.length))
				.stream()
				.mapToInt(Integer::intValue)
				.toArray();
	}
	
	/**
	 * Project cell states col.
	 *
	 * @param colIdx the col idx
	 * @return the int[]
	 */
	public int[] projectCellStatesCol(int colIdx) {
		
		boolean[] array = new boolean[getNumRows()];
		for (int i = 0; i < array.length; i++) {
			array[i] = getCellStateAsBoolean(i, colIdx);
		}
		
		return project(Arrays.copyOfRange(array, 0, array.length))
				.stream()
				.mapToInt(Integer::intValue)
				.toArray();
	}
	
}
