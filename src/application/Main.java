package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Main extends Application {
	@Override // Override the start method from the superclass
	  public void start(Stage primaryStage) {
	  // Create a scene with a single Button - button events are not handled at this stage
	     Button btOK = new Button("Click me");
	     Scene scene = new Scene(btOK, 200, 250);
	     primaryStage.setTitle("MyJavaFX"); // Set the stage title
	     primaryStage.setScene(scene); // Place the scene in the stage
	     primaryStage.show(); // Display the stage
	  }
	 /**
	   * The main method is not needed for running from the command line.
	 */ 
	    public static void main(String[] args) {
	       Application.launch(args);
	    }
}
