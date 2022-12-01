package edu.ou.cs2334.project5;
import edu.ou.cs2334.project5.presenters.NonogramPresenter;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

// TODO: Auto-generated Javadoc
/**
 * The Class Main.
 */
public class Main extends Application {
	
	/** The idx cell size. */
	private final int IDX_CELL_SIZE = 0;
	
	private final int DEFAULT_CELL_SIZE = 30;
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		launch(args);  
	}
	
	/**
	 * Start.
	 *
	 * @param primaryStage the primary stage
	 */
	@Override
	public void start(Stage primaryStage) {
		try {	
		//Use the getParameters() method to extract the number of rows, number of columns, and cell size.		
		//Construct a NonogramMakerPresenter.
		NonogramPresenter presenter;
		if (getParameters().getUnnamed() != null) { 
			int cellSize = Integer.parseInt(getParameters().getUnnamed().get(IDX_CELL_SIZE));
			presenter = new NonogramPresenter(cellSize);
		} else {
			presenter = new NonogramPresenter(DEFAULT_CELL_SIZE);
		}
		//Create a Scene with the NonogramMakerPresenter's pane and set it on the primary Stage.
		Scene scene = new Scene(presenter.getPane());
		primaryStage.setScene(scene);
		
		//Add the style sheet style.css to the scene.
		scene.getStylesheets().add("style.css");
		
		//Add the application name to the title bar, prevent the window from resizing, and then show the window.
		primaryStage.setTitle("Nonogram Maker");
		primaryStage.setResizable(false);
		primaryStage.show();
		} catch (Exception e) { 
			System.out.println(e.getMessage());
		}
	}
}
