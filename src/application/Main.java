package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override // Override the start method from the superclass
	  public void start(Stage primaryStage) {
try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/ChatBox.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Cryptosystem");
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	  
	  }
	 /**
	   * The main method is not needed for running from the command line.
	 */ 
	   
	    public static void main(String[] args) {
	       launch(args);
	    }
}
