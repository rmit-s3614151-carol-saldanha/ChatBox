package application;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	private String publicKey ="";
	
	private String n;
	
	private String e;
	
	public void validate() {

		
		RSA.setDisable(true);
		Paillier.setDisable(true);
		elgamal.setDisable(true);
		readNewMsg.setDisable(true);
		label.setText("Enter your name and the Ip Address of the person you want to chat with");

	}

	public void onClickStart(ActionEvent event) {
		if (name.getText().isEmpty() || ipAddress.getText().isEmpty()) {
			validate();

		} else {
			System.out.println("Establishing Connection");
			RSA.setDisable(false);
			Paillier.setDisable(false);
			elgamal.setDisable(false);
			readNewMsg.setDisable(false);
			this.rsaFile = new FileHandle(ipAddress.getText());
			e = Integer.toString(rsa.getE());
			n= Integer.toString(rsa.getN());
			publicKey = "key" + "|" + e +"|"+n;
			rsaFile.writeToFile(publicKey);
			
			String key;
			while(rsaFile.readFile("RSA.txt")!= null){
				key= rsaFile.readFile("RSA.txt");
				if(key.substring(0, 3).contains("key")){
					System.out.println("Acknowledge key recieved..");
				}
				else
				{
					System.out.println("Key validation failed");
				}
				
			}
			
			
			}


			
	
		
	    
	}

	public void onClickRSA(ActionEvent event) {
		if (name.getText().isEmpty() || ipAddress.getText().isEmpty()) {
			validate();

		} else {
			rsa.setMessage(name.getText() + ": " + msgBox1.getText());
			rsa.setEncryptedMessage("RSA|");
			rsa.computeEncryptedMessage();
			System.out.println("New message: " + rsa.getEncryptedMessage());
		rsa.setMessage(name.getText()+": "+msgBox1.getText());
		rsa.computeEncryptedMessage();
		System.out.println("New message: "+rsa.getEncryptedMessage());
			rsaFile.writeToFile(rsa.getEncryptedMessage());
		}

	}

	public void onClickPaillier(ActionEvent event) {
		System.out.println("Paillier");
	}

	public void onClickElegmal(ActionEvent event) {
		System.out.println("Elegmal");
	}

	public void onClickRead(ActionEvent event) {
		if (name.getText().isEmpty() || ipAddress.getText().isEmpty()) {
			validate();
		String encryptedMsg = rsaFile.readFile("RSA.txt");
		rsa.setEncryptedMessage(encryptedMsg);
		rsa.decryptEncryptedMessage();
		System.out.println(rsa.getDepcryptedMessage());
		msgBox2.appendText("\n" + rsa.getDepcryptedMessage());
		
	}

		else {

			String encryptedMsg = rsaFile.readFile("RSA.txt");
			rsa.setDepcryptedMessage("");
			rsa.setEncryptedMessage(encryptedMsg);
			rsa.decryptEncryptedMessage();
			System.out.println(rsa.getDepcryptedMessage());
			msgBox2.appendText("\n" + rsa.getDepcryptedMessage());
		}
	}

}
