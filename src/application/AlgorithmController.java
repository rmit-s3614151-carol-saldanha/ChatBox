package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

public class AlgorithmController {

	private RSA rsa = new RSA();
	private Elgamal el = new Elgamal();
	private final int RSA_ENCRYPTION = 1;
	private final int ELG_ENCRYPTION = 2;
	private int encryptionType = 0;

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

	// RSA
	private int e;
	private int n;
	// ELG
	private int y;
	private int p;
	private int g;
	private FileHandle fileHandle = null;

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
		exit.writeToFile("ELG.txt", "empty");
		exit.writeToFile("ACKELG.txt", "empty");
		System.exit(0);
	}

	public void onClickStart(ActionEvent event) {
		if (name.getText().isEmpty() || ipAddress.getText().isEmpty()) {
			validate();

		} else {
			this.fileHandle = new FileHandle(ipAddress.getText());

			establishRSAConnection();
			establishELGConnection();

			enableChat();

		}
	}

	private void establishELGConnection() {
		System.out.println("Establishing Elegmal Connection..");

		writeELGPublicKey(); // WRITE KEY FOR PUBLIC

		String key;
		String ack;

		System.out.println("Reading Public key..");

		while (fileHandle.readFile("ELG.txt") != null) {
			key = fileHandle.readFile("ELG.txt"); // READ KEY FROM FILE
			if (key.contains("key")) {

				computeYandGandP(key);
				el.generateK(this.y, this.g);
				el.setyReceived(this.y);
				el.setGeneratorReceived(this.g);
				el.setPrimeNumberReceived(p);

				fileHandle.writeToFile("ACKELG.txt", "ACK"); // ACK KEY RECEIVED
				System.out.println("Key received, acknowledgement sent..");

				break;
			}
		}

		System.out.println("Waiting for Acknowledgement..");

		while (fileHandle.readFile("ACKELG.txt") != null) {
			ack = fileHandle.readFile("ACKELG.txt");
			if (ack.contains("ACK")) {
				System.out.println("ELG CONNECTION ESTABLISHED.."); // CONNECTION
																	// ESTABLISHED
				break;
			}
		}
	}

	private void computeYandGandP(String key) {
		int firstD = 0;
		int secondD = 0;
		int thirdD = 0;
		for (int i = 0; i < key.length(); i++) {
			if (key.charAt(i) == '|') {
				firstD = i;
				break;
			}
		}
		for (int i = firstD + 1; i < key.length(); i++) {
			if (key.charAt(i) == '|') {
				secondD = i;
				break;
			}
		}
		for (int i = secondD + 1; i < key.length(); i++) {
			if (key.charAt(i) == '|') {
				thirdD = i;
				break;
			}
		}

		this.y = Integer.parseInt(key.substring(firstD + 1, secondD));
		this.p = Integer.parseInt(key.substring(secondD + 1, thirdD));
		this.g = Integer.parseInt(key.substring(thirdD + 1, key.length()));
	}

	private void establishRSAConnection() {
		System.out.println("Establishing RSA Connection..");

		writePublicKey(); // WRITE KEY FOR PUBLIC

		String key;
		String ack;

		System.out.println("Reading Public key..");

		while (fileHandle.readFile("RSA.txt") != null) {
			key = fileHandle.readFile("RSA.txt"); // READ KEY FROM FILE
			if (key.contains("key")) {

				computeEandN(key);
				rsa.computePrivateKey(this.n, this.e);

				fileHandle.writeToFile("ACK.txt", "ACK"); // ACK KEY RECEIVED
				System.out.println("Key received, acknowledgement sent..");

				break;
			}
		}

		System.out.println("Waiting for Acknowledgement..");

		while (fileHandle.readFile("ACK.txt") != null) {
			ack = fileHandle.readFile("ACK.txt");
			if (ack.contains("ACK")) {
				System.out.println("RSA CONNECTION ESTABLISHED.."); // CONNECTION
																	// ESTABLISHED
				break;
			}
		}
	}

	private void computeEandN(String key) {

		int firstD = 0;
		int secondD = 0;
		for (int i = 0; i < key.length(); i++) {
			if (key.charAt(i) == '|') {
				firstD = i;
				break;
			}
		}
		for (int i = firstD + 1; i < key.length(); i++) {
			if (key.charAt(i) == '|') {
				secondD = i;
				break;
			}
		}

		this.e = Integer.parseInt(key.substring(firstD + 1, secondD));
		this.n = Integer.parseInt(key.substring(secondD + 1, key.length()));
	}

	private void writePublicKey() {
		String n;
		String e;
		e = Integer.toString(rsa.getE());
		n = Integer.toString(rsa.getnEncryption());
		String publicKey = "key" + "|" + e + "|" + n;
		fileHandle.writeToFile("RSA.txt", publicKey); // WRITE KEY TO FILE

	}

	private void writeELGPublicKey() {
		String y;
		String p;
		String g;

		y = Integer.toString(el.getValueY());
		p = Integer.toString(el.getPrimeNumber());
		g = Integer.toString(el.getGenerator());
		String publicKey = "key" + "|" + y + "|" + p + "|" + g;
		fileHandle.writeToFile("ELG.txt", publicKey); // WRITE KEY TO FILE

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
			this.encryptionType = RSA_ENCRYPTION;
			rsa.setMessage(name.getText() + ": " + msgBox1.getText());
			rsa.computeEncryptedMessage();
			System.out.println("New message: " + rsa.getEncryptedMessage());
			fileHandle.writeToFile("RSA.txt", rsa.getEncryptedMessage());
		}

	}

	public void onClickPaillier(ActionEvent event) {
		System.out.println("Paillier");
	}

	public void onClickElegmal(ActionEvent event) {

		if (name.getText().isEmpty() || ipAddress.getText().isEmpty()) {
			validate();

		} else {
			this.encryptionType = ELG_ENCRYPTION;
			el.computeEncryption(name.getText() + ": " + msgBox1.getText());
			System.out.println("New message: " + el.getEncryptedMessage());
			fileHandle.writeToFile("ELG.txt", el.getEncryptedMessage());
		}

	}

	public void onClickRead(ActionEvent event) {

		if (this.encryptionType == RSA_ENCRYPTION) {
			String encryptedMsg = fileHandle.readFile("RSA.txt");
			System.out.println(encryptedMsg);
			rsa.setEncryptedMessage(encryptedMsg);
			rsa.decryptEncryptedMessage();
			System.out.println(rsa.getDepcryptedMessage());
			msgBox2.appendText("\n" + rsa.getDepcryptedMessage());
		} else if (this.encryptionType == ELG_ENCRYPTION) {
			String encryptedMsg = fileHandle.readFile("ELG.txt");
			System.out.println(encryptedMsg);
			el.setEncryptedMessage(encryptedMsg);
			el.decrypt();
			System.out.println(el.getDepcryptedMessage());
			msgBox2.appendText("\n" + el.getDepcryptedMessage());
		}
	}

}
