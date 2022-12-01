package edu.ou.cs2334.project5.views;

import edu.ou.cs2334.project5.models.CellState;
import javafx.scene.layout.GridPane;

// TODO: Auto-generated Javadoc
/**
 * The Class CellGridView.
 */
public class CellGridView extends GridPane {
	
	/** The style class. */
	private final String STYLE_CLASS = "cell-grid-view";
	
	/** The cell views. */
	private CellView[][] cellViews;
	
	/**
	 * Instantiates a new cell grid view.
	 *
	 * @param numRows the num rows
	 * @param numCols the num cols
	 * @param cellLength the cell length
	 */
	public CellGridView(int numRows, int numCols, int cellLength) {
		initCells(numRows, numCols, cellLength);
		getStyleClass().add(STYLE_CLASS);
	}
	
	/**
	 * Inits the cells.
	 *
	 * @param numRows the num rows
	 * @param numCols the num cols
	 * @param cellLength the cell length
	 */
	public void initCells(int numRows, int numCols, int cellLength) {
		this.getChildren().clear();
		cellViews = new CellView[numRows][numCols];
		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numCols; c++) {
                CellView cv = new CellView(cellLength);
                cellViews[r][c] = cv;
                this.add(cv, c, r);
			}
		}
	}
	
	/**
	 * Gets the cell view.
	 *
	 * @param rowIdx the row idx
	 * @param colIdx the col idx
	 * @return the cell view
	 */
	public CellView getCellView(int rowIdx, int colIdx) {
		return cellViews[rowIdx][colIdx];
	}
	
	/**
	 * Sets the cell state.
	 *
	 * @param rowIdx the row idx
	 * @param colIdx the col idx
	 * @param state the state
	 */
	public void setCellState(int rowIdx, int colIdx, CellState state) {
		cellViews[rowIdx][colIdx].setState(state);
	}
	
}
