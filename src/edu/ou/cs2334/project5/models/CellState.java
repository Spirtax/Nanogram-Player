package edu.ou.cs2334.project5.models;

// TODO: Auto-generated Javadoc
/**
 * The Enum CellState.
 */
public enum CellState {
	
	/** The empty. */
	EMPTY, 
 /** The filled. */
 //represents an empty cell (shown in a white box in the earlier images)
	FILLED, 
 /** The marked. */
 //represents a filled cell (shown in a blue box in the earlier images)
	MARKED; //represents an X'd out cell (shown using a red X in the earlier images)
	/**
  * To boolean.
  *
  * @param state the state
  * @return true, if successful
  */
 public static boolean toBoolean(CellState state) {
		return (state == FILLED);
	}
}
