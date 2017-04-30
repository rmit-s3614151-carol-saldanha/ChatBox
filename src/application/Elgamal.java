package application;

import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class Elgamal {

	// Instance variables

	private int primeNumber = 0; // p
	private int generator = 0; // g
	private int private_key = 0; // x
	private int maximum = 33998; // Limit MAX for Generator
	private int pMax = 13699; // Limit for MAX
	private int y = 0; // public key bob sends to alice
	private int primeNumberReceived = 0; // p
	private int generatorReceived = 0; // g
	private int yReceived = 0; // public key bob sends to alice
	private int k = 0;
	public int getPrimeNumberReceived() {
		return primeNumberReceived;
	}


	public void setPrimeNumberReceived(int primeNumberReceived) {
		this.primeNumberReceived = primeNumberReceived;
	}


	public int getGeneratorReceived() {
		return generatorReceived;
	}


	public void setGeneratorReceived(int generatorReceived) {
		this.generatorReceived = generatorReceived;
	}


	public int getyReceived() {
		return yReceived;
	}


	public void setyReceived(int yReceived) {
		this.yReceived = yReceived;
	}

	private int r = 0;
	private String encryptedMessage;
	private String depcryptedMessage;
private int minimum = 1;
	private final int GRANGE = maximum - minimum + 1;
	private final int PRANGE = pMax - minimum + 1;
	Random rand = new Random();
	Utility utility = new Utility();
	
	public Elgamal() {
		generateY();
	}

	
	public void setPrimeNumber(int p) {
		this.primeNumber = p;
	}

	public int getPrimeNumber() {
		return primeNumber;
	}

	public int getPrivate_key() {
		return private_key;
	}

	public void setPrivate_key(int private_key) {
		this.private_key = private_key;
	}

	public int getGenerator() {
		return generator;
	}

	public void setGenerator(int generator) {
		this.generator = generator;
	}

	public void setValueY(int y) {
		this.y = y;
	}

	public int getValueY() {
		return y;
	}

	public void setValueK(int k) {
		this.k = k;
	}

	public int getValueK() {
		return k;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getR() {
		return r;
	}

	public String getEncryptedMessage() {
		return encryptedMessage;
	}

	public void setEncryptedMessage(String encryptedMessage) {
		this.encryptedMessage = encryptedMessage;
	}

	public String getDepcryptedMessage() {
		return depcryptedMessage;
	}

	public void setDepcryptedMessage(String depcryptedMessage) {
		this.depcryptedMessage = depcryptedMessage;
	}

	// Key that Bob sends to Alice
	public void generateY() {
		// Generate random prime number

		primeNumber = rand.nextInt(3000);
		while (!utility.isPrime(primeNumber)) {
			primeNumber = rand.nextInt(3000) + 1;
		}
		// Set prime number
		setPrimeNumber(primeNumber);

		System.out.println("Prime Number" + primeNumber);

		// Generate random generator
		setGenerator(rand.nextInt(GRANGE) + minimum);

		// Set random private key
		setPrivate_key(rand.nextInt(PRANGE) + minimum);

		System.out.println("G : " + getGenerator());
		System.out.println("Private key" + getPrivate_key());

		// GENERATE Y : G ^ PK % P
		setValueY(utility.moduloR(getGenerator(), getPrivate_key(), primeNumber));

		// Send Keys to Alice key(Y,P)
		generateK(getValueY(), getPrimeNumber());

	}

	// Key that Alice uses to computeEncryption
	public void generateK(int Y, int P) {
		// Alice selects random number r ;
		int r = rand.nextInt(100) + 1;
		int k = utility.moduloR(Y, r, P);
		setValueK(k);
		setR(r);

	}

	public void computeEncryption(String msg) {
		// Alice needs to send C1 and C2

		// C1 : g ^ r % P
		int c1 = utility.moduloR(this.getGeneratorReceived(), getR(), this.getPrimeNumberReceived());
		System.out.println("C1:" + c1);
		this.setEncryptedMessage("Elgamal|"+c1+"|");
		String message = msg;
		System.out.println("Encrypting..");
		System.out.println("Encrypting using "+ this.getGeneratorReceived() +" "+ getR()+" "+this.getPrimeNumberReceived());

		for (int i = 0; i < message.length(); i++) {
			this.encryptedMessage = this.encryptedMessage.concat(this.getEncryptedValue(message.charAt(i)));
			this.encryptedMessage = this.encryptedMessage.concat("|");
		}
		this.encryptedMessage = this.encryptedMessage.concat("end");

		System.out.println(getEncryptedMessage());
	}

	private String getEncryptedValue(char character) {

		int asciiValue = (int) character;
		// Calculate CTWO : M * K % P
		int c2 = (asciiValue * getValueK()) % getPrimeNumber();

		return Integer.toString(c2);
	}

	public void decrypt() {

		int dKey = utility.moduloR(extractC1(), getPrivate_key(), getPrimeNumber());
		int inverseValue = utility.inverseModulo(dKey, getPrimeNumber());
		System.out.println("Decrypting using " +getPrivate_key()+" "+getPrimeNumber()+" ");
		
		this.setDepcryptedMessage("");
		String encryptedMessage = this.encryptedMessage;

		String part;
		int position;
		int decryptedValue;
		char decryptedChar;
		
		System.out.println(encryptedMessage);
		
		while (!encryptedMessage.equals("end")) {
			part = "";
			position = 0;

			while (encryptedMessage.charAt(position) != '|') {
				part = part.concat(Character.toString(encryptedMessage.charAt(position)));

				position++;
			}
			// System.out.println("Checking Parts " + part);
			decryptedValue = (inverseValue * Integer.parseInt(part)) % getPrimeNumber();
			decryptedChar = (char) decryptedValue;
			this.depcryptedMessage = depcryptedMessage.concat(Character.toString(decryptedChar));
			encryptedMessage = encryptedMessage.substring(part.length() + 1, encryptedMessage.length());
			// System.out.println("Checking new encrypted mesg " +
			// encryptedMessage);

		}

	}


	private int extractC1() {
		String msg = this.encryptedMessage.substring(8, this.encryptedMessage.length());
		String part = "";
		int position = 0;
		while (msg.charAt(position) != '|') {
			part = part.concat(Character.toString(msg.charAt(position)));

			position++;
		}
		this.encryptedMessage = msg.substring(position + 1, msg.length());
		return Integer.parseInt(part);
	}

}
