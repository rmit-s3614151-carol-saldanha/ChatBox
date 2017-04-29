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

	private void writePublicKey() {
		String n;
		String e;
		e = Integer.toString(rsa.getE());
		n = Integer.toString(rsa.getN());
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
			rsa.setMessage(name.getText() + ": " + msgBox1.getText());
			rsa.computeEncryptedMessage();
			System.out.println("New message: " + rsa.getEncryptedMessage());
			rsaFile.writeToFile("RSA.txt", rsa.getEncryptedMessage());
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
