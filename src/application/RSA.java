package application;

import java.util.Random;

public class RSA extends Encryptor {

	private String message;
	private int p;
	private int q;
	private int n;
	private int phi;
	private int e;
	private int d;
	private String encryptedMessage = "RSA";

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getP() {
		return p;
	}

	public void setP(int p) {
		this.p = p;
	}

	public int getQ() {
		return q;
	}

	public void setQ(int q) {
		this.q = q;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public int getPhi() {
		return phi;
	}

	public void setPhi(int phi) {
		this.phi = phi;
	}

	public int getE() {
		return e;
	}

	public void setE(int e) {
		this.e = e;
	}

	public int getD() {
		return d;
	}

	public void setD(int d) {
		this.d = d;
	}

	public String getEncryptedMessage() {
		return encryptedMessage;
	}

	public void setEncryptedMessage(String encryptedMessage) {
		this.encryptedMessage = encryptedMessage;
	}

	public RSA() {
		Utility utility = new Utility();
		this.setP(11);
		this.setQ(13);
		this.setN(this.p * this.q);
		this.setPhi((this.p - 1) * (this.q - 1));
		this.setE(utility.findCoPrimeNumberLessThan(this.phi));
		this.setD(this.computePrivateKey());
	}

	private int computePrivateKey() {

		boolean isRemainderOne = false;
		int d = 2;
		while (!isRemainderOne) {

			if ((d * this.e) % this.phi == 1) {
				isRemainderOne = true;
			}
			else {
				d++;
			}
		}

		return d;
	}
	
	public void computeEncryptedMessage()
	{
		String message = this.message;
		
		for(int i = 0; i < message.length(); i++) {
			this.encryptedMessage.concat(this.getEncryptedValue(message.charAt(i)));
			this.encryptedMessage.concat("|");
		}
		
	}

	private String getEncryptedValue(char character) {
		
		int asciiValue = (int) character;
		Utility utility = new Utility();
		return Integer.toString(utility.moduloR(asciiValue, this.e, this.n));
	}

}
