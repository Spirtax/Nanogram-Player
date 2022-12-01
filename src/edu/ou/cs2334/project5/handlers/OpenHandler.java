package edu.ou.cs2334.project5.handlers;

import java.io.File;

import edu.ou.cs2334.project5.interfaces.Openable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Window;

/**
 * The Class OpenHandler.
 */
public class OpenHandler extends AbstractBaseHandler implements EventHandler<ActionEvent> {
	
	/** The opener. */
	private Openable opener;
	
	/**
	 * Instantiates a new open handler.
	 *
	 * @param window the window
	 * @param fileChooser the file chooser
	 * @param opener the opener
	 */
	public OpenHandler(Window window, FileChooser fileChooser, Openable opener) {
		super(window, fileChooser);
		this.opener = opener;
	}

	/**
	 * Opens file dialog
	 *
	 * @param event the event
	 */
	public void handle(ActionEvent event) {
		File file = fileChooser.showOpenDialog(window);
		if (file != null) {
			try {
				opener.open(file);
			} catch (Exception e) {
			
			}
		}
	}
}
