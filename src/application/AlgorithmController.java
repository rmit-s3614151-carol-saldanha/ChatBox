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
	    private TextArea msgBox1;

	    @FXML
	    private TextArea msgBox2;
	    
	    public void onClickRSA(ActionEvent event) {
	    RSA rsa = new RSA();
	    rsa.setMessage(msgBox1.getText());
	    rsa.computeEncryptedMessage();
	    msgBox2.setText(rsa.getEncryptedMessage());;
	    System.out.println(rsa.getEncryptedMessage());
	}

	public void onClickPaillier(ActionEvent event) {
		System.out.println("Paillier");
	}
	
	public void onClickElegmal(ActionEvent event) {
		System.out.println("Elegmal");
	}

}
