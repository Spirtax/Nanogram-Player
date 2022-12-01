package edu.ou.cs2334.project5.views;
import edu.ou.cs2334.project5.models.CellState;
import javafx.collections.ObservableList;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

// TODO: Auto-generated Javadoc
/**
 * The Class CellView.
 */
public class CellView extends StackPane {

	/** The Constant STYLE_CLASS. */
	private static final String STYLE_CLASS = "cell-view";
	
	/** The Constant EMPTY_STYLE_CLASS. */
	private static final String EMPTY_STYLE_CLASS = "cell-view-empty";
	
	/** The Constant FILLED_STYLE_CLASS. */
	private static final String FILLED_STYLE_CLASS = "cell-view-filled";
	
	/** The Constant MARKED_STYLE_CLASS. */
	private static final String MARKED_STYLE_CLASS = "cell-view-marked";
	
	/** The Constant X_LENGTH_SCALE. */
	private static final double X_LENGTH_SCALE = 1.0 / 2.0;

	/** The background. */
	private Rectangle background = new Rectangle();
	
	/** The x left leg. */
	private Line xLeftLeg = new Line();
	
	/** The x right leg. */
	private Line xRightLeg = new Line();

	/**
	 * Instantiates a new cell view.
	 *
	 * @param sideLength the side length
	 */
	public CellView(int sideLength) {
		getStyleClass().add(STYLE_CLASS);
		setState(CellState.EMPTY);
		setSize(sideLength);
		getChildren().addAll(background, xLeftLeg, xRightLeg);
	}

	/**
	 * Sets the state.
	 *
	 * @param state the new state
	 */
	public void setState(CellState state) {
		ObservableList<String> styleClasses = getStyleClass();
		styleClasses.removeAll(
				EMPTY_STYLE_CLASS, FILLED_STYLE_CLASS, MARKED_STYLE_CLASS);
		switch (state) {
			case EMPTY:
				styleClasses.add(EMPTY_STYLE_CLASS);
				break;
			case FILLED:
				styleClasses.add(FILLED_STYLE_CLASS);
				break;
			case MARKED:
				styleClasses.add(MARKED_STYLE_CLASS);
				break;
			default:
				throw new IllegalArgumentException();
		}
	}

	/**
	 * Sets the size.
	 *
	 * @param sideLength the new size
	 */
	public void setSize(int sideLength) {
		background.setWidth(sideLength);
		background.setHeight(sideLength);

		// Set the size of the X.
		double legLength = X_LENGTH_SCALE * sideLength;
		double xWidth = legLength / Math.sqrt(2);
		double xHeight = xWidth;
		xLeftLeg.setStartX(0);
		xLeftLeg.setStartY(0);
		xLeftLeg.setEndX(xWidth);
		xLeftLeg.setEndY(xHeight);
		xRightLeg.setStartX(0);
		xRightLeg.setStartY(xHeight);
		xRightLeg.setEndX(xWidth);
		xRightLeg.setEndY(0);
	}
}
