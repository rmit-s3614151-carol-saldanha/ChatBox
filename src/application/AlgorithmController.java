package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

public class AlgorithmController {

	private RSA rsa = new RSA();

	@FXML
	private Button RSA;

	
	@FXML
	private Button Paillier;
	
	@FXML
	private Label label;

	@FXML
	private Button elgamal;

	@FXML
	private TextArea msgBox1;

	@FXML
	private TextArea msgBox2;
	
	@FXML
    private TextField name;
	
	@FXML
    private TextField ipAddress;
	
	@FXML
    private Button start;
	
	@FXML
	private Button readNewMsg;
	
	private FileHandle rsaFile = null;
	
	
	public void onClickStart(ActionEvent event) {
		if(name.getText().isEmpty() || ipAddress.getText().isEmpty())
		{
			//start.setDisable(true);
			RSA.setDisable(true);
			Paillier.setDisable(true);
			elgamal.setDisable(true);
			readNewMsg.setDisable(true);
			label.setText("Enter your name and the Ip Address of the person you want to chat with");
		}
		else
		{
			//start.setDisable(false);
			RSA.setDisable(false);
			Paillier.setDisable(false);
			elgamal.setDisable(false);
			readNewMsg.setDisable(false);
		this.rsaFile= new FileHandle(ipAddress.getText());
		}
	}


	public void onClickRSA(ActionEvent event) {
		rsa.setMessage(name.getText()+": "+msgBox1.getText());
		rsa.setEncryptedMessage("RSA|");
		rsa.computeEncryptedMessage();
		System.out.println("New message: "+rsa.getEncryptedMessage());
			rsaFile.writeToFile(rsa.getEncryptedMessage());
		
	}

	public void onClickPaillier(ActionEvent event) {
		System.out.println("Paillier");
	}

	public void onClickElegmal(ActionEvent event) {
		System.out.println("Elegmal");
	}
	
	
	public void onClickRead(ActionEvent event) {
		String encryptedMsg = rsaFile.readFile();
		rsa.setDepcryptedMessage("");
		rsa.setEncryptedMessage(encryptedMsg);
		rsa.decryptEncryptedMessage();
		System.out.println(rsa.getDepcryptedMessage());
		msgBox2.appendText("\n" + rsa.getDepcryptedMessage());
		
	}



}
