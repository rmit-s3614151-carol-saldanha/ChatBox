package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;


public class AlgorithmController {

		@FXML
	    private Button RSA;

	    @FXML
	    private Button Paillier;

	    @FXML
	    private Button elgamal;

	    @FXML
	    private TextArea msgBox;

	
	public void onClickRSA(ActionEvent event) {
		System.out.println("RSA");
	}

	public void onClickPaillier(ActionEvent event) {
		System.out.println("Paillier");
	}
	
	public void onClickElegmal(ActionEvent event) {
		System.out.println("Elegmal");
	}

}
