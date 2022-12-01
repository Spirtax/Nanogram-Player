package edu.ou.cs2334.project5.views;

import java.util.Arrays;

import edu.ou.cs2334.project5.models.CellState;
import edu.ou.cs2334.project5.views.clues.LeftCluesView;
import edu.ou.cs2334.project5.views.clues.TopCluesView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

// TODO: Auto-generated Javadoc
/**
 * The Class NonogramView.
 */
public class NonogramView extends BorderPane {
	
	/** The style class. */
	private final String STYLE_CLASS = "nonogram-view";
	
	/** The solved style class. */
	private final String SOLVED_STYLE_CLASS = "nonogram-view-solved";
	
	/** The left clues view. */
	private LeftCluesView leftCluesView; //Row clues view
	
	/** The top clues view. */
	private TopCluesView topCluesView; //Col clues view
	
	/** The cell grid view. */
	private CellGridView cellGridView;
	
	/** The bottom H box. */
	private HBox bottomHBox;
	
	/** The load btn. */
	private Button loadBtn;
	
	/** The reset btn. */
	private Button resetBtn;
	
	/**
	 * Instantiates a new nonogram view.
	 */
	public NonogramView() {
		getStyleClass().add(STYLE_CLASS);
	}
	
	/**
	 * Initialize.
	 *
	 * @param rowClues the row clues
	 * @param colClues the col clues
	 * @param cellLength the cell length
	 */
	public void initialize(int[][] rowClues, int[][] colClues, int cellLength) {
		leftCluesView = new LeftCluesView(rowClues, cellLength, getLength(rowClues));
		topCluesView = new TopCluesView(colClues, cellLength, getLength(colClues));
		cellGridView = new CellGridView(rowClues.length, colClues.length, cellLength);
		this.setLeft(leftCluesView);
		this.setTop(topCluesView);
		this.setCenter(cellGridView);
		BorderPane.setAlignment(topCluesView, Pos.TOP_RIGHT);
		initBottomHBox();
		this.setBottom(bottomHBox);
	}
	
	/**
	 * Gets the length.
	 *
	 * @param arr the arr
	 * @return the length
	 */
	//If this fails, it may be because of width/height. Create getHeight method if it fails?
	public int getLength(int[][] arr) {
		//Convert the array to a stream, then get each row length and get max value
		return Arrays.stream(arr).map(row -> row.length).max(Integer::compare).get();
	}
	
	/**
	 * Inits the bottom H box.
	 */
	public void initBottomHBox() {
		loadBtn = new Button("Load");
		resetBtn = new Button("Reset");
		bottomHBox = new HBox(loadBtn, resetBtn);
		this.setBottom(bottomHBox);
		bottomHBox.setAlignment(Pos.CENTER);
	}
	
	/**
	 * Gets the cell view.
	 *
	 * @param rowIdx the row idx
	 * @param colIdx the col idx
	 * @return the cell view
	 */
	public CellView getCellView(int rowIdx, int colIdx) {
		return cellGridView.getCellView(rowIdx, colIdx);
	}
	
	/**
	 * Sets the cell state.
	 *
	 * @param rowIdx the row idx
	 * @param colIdx the col idx
	 * @param state the state
	 */
	public void setCellState(int rowIdx, int colIdx, CellState state) {
		cellGridView.setCellState(rowIdx, colIdx, state);
	}
	
	/**
	 * Sets the row clue state.
	 *
	 * @param rowIdx the row idx
	 * @param solved the solved
	 */
	public void setRowClueState(int rowIdx, boolean solved) {
		leftCluesView.setState(rowIdx, solved);
	}
	
	/**
	 * Sets the col clue state.
	 *
	 * @param rowIdx the row idx
	 * @param solved the solved
	 */
	public void setColClueState(int rowIdx, boolean solved) {
		topCluesView.setState(rowIdx, solved);
	}
	
	/**
	 * Sets the puzzle state.
	 *
	 * @param solved the new puzzle state
	 */
	public void setPuzzleState(boolean solved) {
		if (solved) {
			getStyleClass().add(SOLVED_STYLE_CLASS);
		} else {
			getStyleClass().removeAll(SOLVED_STYLE_CLASS);
		}
	}
	
	/**
	 * Gets the load button.
	 *
	 * @return the load button
	 */
	public Button getLoadButton() {
		return loadBtn;
	}
	
	/**
	 * Gets the reset button.
	 *
	 * @return the reset button
	 */
	public Button getResetButton() {
		return resetBtn;
	}
	
	/**
	 * Show victory alert.
	 */
	public void showVictoryAlert() {
		Alert a = new Alert(AlertType.INFORMATION);
	    a.setHeaderText("Congratulations!");
	    a.setTitle("Puzzle Solved");
	    a.setContentText("You Win!");
	    a.show();
	}
}
