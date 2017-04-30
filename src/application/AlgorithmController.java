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
	private Elgamal el = new Elgamal();

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
	private int e;
	private int n;
	private FileHandle rsaFile = null;

	public void validate() {

		RSA.setDisable(true);
		Paillier.setDisable(true);
		elgamal.setDisable(true);
		readNewMsg.setDisable(true);
		label.setText("Enter your name and the Ip Address of the person you want to chat with");

	}
	public void onClickExit(ActionEvent event) {
		FileHandle exit = new FileHandle();
		exit.writeToFile("RSA.txt", "empty");
		exit.writeToFile("ACK.txt", "empty");
		System.exit(0);
	}
	public void onClickStart(ActionEvent event) {
		if (name.getText().isEmpty() || ipAddress.getText().isEmpty()) {
			validate();

		} else {
			this.rsaFile = new FileHandle(ipAddress.getText());
			
			establishConnection();

			enableChat();

		}
	}

	private void establishConnection() {
		System.out.println("Establishing Connection..");

		writePublicKey(); // WRITE KEY FOR PUBLIC

		String key;
		String ack;

		System.out.println("Reading Public key..");

		while (rsaFile.readFile("RSA.txt") != null) {
			key = rsaFile.readFile("RSA.txt"); // READ KEY FROM FILE
			if (key.contains("key")) {

				computeEandN(key);
				rsa.computePrivateKey(this.n, this.e);

				rsaFile.writeToFile("ACK.txt", "ACK"); // ACK KEY RECEIVED
				System.out.println("Key received, acknowledgement sent..");
				
				break;
			}
		}

		System.out.println("Waiting for Acknowledgement..");

		while (rsaFile.readFile("ACK.txt") != null) {
			ack = rsaFile.readFile("ACK.txt");
			if (ack.contains("ACK")) {
				System.out.println("CONNECTION ESTABLISHED.."); // CONNECTION ESTABLISHED
				break;
			}
		}
	}

	private void computeEandN(String key) {
		
		int firstD = 0;
		int secondD = 0;
		for(int i = 0; i < key.length() ; i++)
		{
			if(key.charAt(i) == '|'){
				firstD = i;
				break;
			}
		}
		for(int i = firstD + 1; i < key.length() ; i++)
		{
			if(key.charAt(i) == '|'){
				secondD = i;
				break;
			}
		}
		
		this.e = Integer.parseInt(key.substring(firstD+1, secondD));
		this.n = Integer.parseInt(key.substring(secondD+1, key.length()));
	}


	private void writePublicKey() {
		String n;
		String e;
		e = Integer.toString(rsa.getE());
		n = Integer.toString(rsa.getnEncryption() );
		String publicKey = "key" + "|" + e + "|" + n;
		rsaFile.writeToFile("RSA.txt", publicKey); // WRITE KEY TO FILE

	}

	private void enableChat() {

		RSA.setDisable(false);
		Paillier.setDisable(false);
		elgamal.setDisable(false);
		readNewMsg.setDisable(false);
	}

	public void onClickRSA(ActionEvent event) {
		if (name.getText().isEmpty() || ipAddress.getText().isEmpty()) {
			validate();

		} else {
			rsa.setMessage(name.getText() + ": " + msgBox1.getText());
			rsa.setEncryptedMessage("RSA|");
			rsa.computeEncryptedMessage();
			System.out.println("New message: " + rsa.getEncryptedMessage());
			rsaFile.writeToFile("RSA.txt", rsa.getEncryptedMessage());
		}

	}

	public void onClickPaillier(ActionEvent event) {
		System.out.println("Paillier");
	}

	public void onClickElegmal(ActionEvent event) {
		
		if (name.getText().isEmpty() || ipAddress.getText().isEmpty()) {
			validate();

		} else {
			el.generateY();
			el.setMessage(name.getText() + ": " + msgBox1.getText());
			el.setEncryptedMessage("RSA|");
			el.computeEncryption();
			System.out.println("New message: " + rsa.getEncryptedMessage());
			//rsaFile.writeToFile("Elgamal.txt", rsa.getEncryptedMessage());
		}
		
		
		

	}

	public void onClickRead(ActionEvent event) {
		
			String encryptedMsg = rsaFile.readFile("RSA.txt");
			System.out.println(encryptedMsg);
			rsa.setEncryptedMessage(encryptedMsg);
			rsa.decryptEncryptedMessage();
			System.out.println(rsa.getDepcryptedMessage());
			msgBox2.appendText("\n" + rsa.getDepcryptedMessage());
		
	}

}
