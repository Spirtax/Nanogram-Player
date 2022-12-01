package edu.ou.cs2334.project5.presenters;

import java.io.File;
import java.io.IOException;

import edu.ou.cs2334.project5.handlers.OpenHandler;
import edu.ou.cs2334.project5.interfaces.Openable;
import edu.ou.cs2334.project5.models.CellState;
import edu.ou.cs2334.project5.models.NonogramModel;
import edu.ou.cs2334.project5.views.NonogramView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.stage.FileChooser.ExtensionFilter;

// TODO: Auto-generated Javadoc
/**
 * The Class NonogramPresenter.
 */
public class NonogramPresenter implements Openable {
	
	/** The view. */
	private NonogramView view;
	
	/** The model. */
	private NonogramModel model;
	
	/** The cell length. */
	private int cellLength;
	
	/** The default puzzle. */
	private final String DEFAULT_PUZZLE = "puzzles/space-invader.txt";
	
	/**
	 * Instantiates a new nonogram presenter.
	 *
	 * @param cellSize the cell size
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public NonogramPresenter(int cellSize) throws IOException {
		model = new NonogramModel(DEFAULT_PUZZLE);
		view = new NonogramView();
		cellLength = cellSize;
		initializePresenter();
	}
	
	/**
	 * Initialize presenter.
	 */
	private void initializePresenter() {
		initializeView();
		bindCellViews();
		synchronize();
		configureButtons();
	}
	
	/**
	 * Initialize view.
	 */
	public void initializeView() {
		view.initialize(model.getRowClues(), model.getColClues(), cellLength);
		if (getWindow() != null) getWindow().sizeToScene();
	}
	
	/**
	 * Bind cell views.
	 */
	public void bindCellViews() {
		for (int r = 0; r < model.getNumRows(); r++) {
			for (int c = 0; c < model.getNumCols(); c++) {
				final int row = r;
				final int col = c;
				view.getCellView(r, c).setOnMouseClicked(event -> {
		            if (event.getButton() == MouseButton.PRIMARY) { //left 
		                handleLeftClick(row, col);
		            } else if (event.getButton() == MouseButton.SECONDARY) { //right
		                handleRightClick(row, col);
		            }
		        });
			}
		}
	}
	
	/**
	 * Handle left click.
	 *
	 * @param rowIdx the row idx
	 * @param colIdx the col idx
	 */
	public void handleLeftClick(int rowIdx, int colIdx) {
		if (model.getCellState(rowIdx, colIdx) == CellState.FILLED) {
			updateCellState(rowIdx, colIdx, CellState.EMPTY);
		} else if (model.getCellState(rowIdx, colIdx) == CellState.EMPTY || model.getCellState(rowIdx, colIdx) == CellState.MARKED) {
			updateCellState(rowIdx, colIdx, CellState.FILLED);
		}
	}
	
	/**
	 * Handle right click.
	 *
	 * @param rowIdx the row idx
	 * @param colIdx the col idx
	 */
	public void handleRightClick(int rowIdx, int colIdx) {
		if (model.getCellState(rowIdx, colIdx) == CellState.MARKED) {
			updateCellState(rowIdx, colIdx, CellState.EMPTY);
		} else if (model.getCellState(rowIdx, colIdx) == CellState.EMPTY || model.getCellState(rowIdx, colIdx) == CellState.FILLED) {
			updateCellState(rowIdx, colIdx, CellState.MARKED);
		}
	}
	
	/**
	 * Update cell state.
	 *
	 * @param rowIdx the row idx
	 * @param colIdx the col idx
	 * @param state the state
	 */
	//This method may be entirely wrong
	public void updateCellState(int rowIdx, int colIdx, CellState state) {
		//model.setCellState(rowIdx, colIdx, state); //Update the model (but don't update the view if nothing changed). ?????
		if (model.setCellState(rowIdx, colIdx, state)) {																																											
			view.setCellState(rowIdx, colIdx, state);
			view.setRowClueState(rowIdx, model.isRowSolved(rowIdx));
			view.setColClueState(colIdx, model.isColSolved(colIdx));
		}
		if (model.isSolved()) processVictory();
	}
	
	/**
	 * Synchronize.
	 */
	public void synchronize() {//this entire method is prob wrong
		for (int r = 0; r < model.getNumRows(); r++) {
			for (int c = 0; c < model.getNumCols(); c++) {
				view.setCellState(r, c, model.getCellState(r, c));
				view.setRowClueState(r, model.isRowSolved(r));
				view.setColClueState(c, model.isColSolved(c));
				view.setPuzzleState(model.isSolved());
			}
		}
	}
	
	/**
	 * Process victory.
	 */
	public void processVictory() {
		synchronize();
		removeCellViewMarks();
		view.showVictoryAlert();
	}
	
	/**
	 * Removes the cell view marks.
	 */
	public void removeCellViewMarks() {
		for (int r = 0; r < model.getNumRows(); r++) {
			for (int c = 0; c < model.getNumCols(); c++) {
				if (model.getCellState(r, c) == CellState.MARKED) view.setCellState(r, c, CellState.EMPTY);
			}
		}
	}
	
	/**
	 * Configure buttons.
	 */
	public void configureButtons() {
		//Load button
		FileChooser openChooser = new FileChooser();
		openChooser.setTitle("Open");
		openChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));
		openChooser.setInitialDirectory(new File("."));
		view.getLoadButton()
		    .setOnAction(new OpenHandler(getWindow(), openChooser, this));
		
		//Reset button
		view.getResetButton().setOnAction(new EventHandler<ActionEvent>() {
		      public void handle(ActionEvent event) {
			        resetPuzzle();
			      }
			   }
		);
	}
	
	/**
	 * Reset puzzle.
	 */
	public void resetPuzzle() {
		model.resetCells();
		synchronize();
	}
	
	/**
	 * Gets the pane.
	 *
	 * @return the pane
	 */
	public Pane getPane() {
		return view;
	}
	
	/**
	 * Gets the window.
	 *
	 * @return the window
	 */
	public Window getWindow() {
		try {
			return this.getPane().getScene().getWindow();
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	/**
	 * Open.
	 *
	 * @param file the file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void open(File file) throws IOException {
		model = new NonogramModel(file);
		initializePresenter();
	}
	
}
