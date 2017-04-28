package application;

public class RSA extends Encryptor {

	private String message;
	private int p;
	private int q;
	private int n;
	private int phi;
	private int e;
	private int d;
	private String encryptedMessage;
	
	
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
		
	}
	
	

}
