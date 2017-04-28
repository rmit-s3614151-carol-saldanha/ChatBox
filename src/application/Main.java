package application;
	
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;


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
