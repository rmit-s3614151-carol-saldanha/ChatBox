package application;

import java.util.Random;

public class RSA extends Encryptor {

	private String message;
	private int p;
	private int q;
	private int nEncryption;
	private int nDecryption;
	private int phi;
	private int e;
	private int d;
	private String encryptedMessage = "RSA|";
	private String depcryptedMessage = "";
	private final int MAXIMUM_PRIME_VALUE = 200;
	private final int MAXIMUM_PRIVATE_KEY = 10000;
	private final int ASCII_VALUE_COUNT = 127;

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

	public int getnEncryption() {
		return nEncryption;
	}

	public void setnEncryption(int nEncryption) {
		this.nEncryption = nEncryption;
	}

	public int getnDecryption() {
		return nDecryption;
	}

	public void setnDecryption(int nDecryption) {
		this.nDecryption = nDecryption;
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
		System.out.println("Initializing..");

		this.initialize();
		System.out.println(this);
	}

	public void computePrivateKey(int n, int e)
	{
		Utility utility = new Utility();
		int privateKey = 2;
		int p=utility.findPrimeFactor(n);
		int q = n/p;
		int phi = (p-1)*(q-1);
		boolean isRemainderOne = false;
		while (!isRemainderOne) {

			if ((privateKey * e) % phi == 1) {
				isRemainderOne = true;
			} else {
				privateKey++;
			}
		}
		
		this.setD(privateKey);
		this.setnDecryption(n);
		System.out.println("Computed private key and N"+this.d +" "+ this.nDecryption);
	}
	
	private void initialize() {
		Random random = new Random();
		boolean arePrime;
		Utility utility = new Utility();
		boolean isDAppropriate = false;
		while (!isDAppropriate) {
			arePrime = false;
			while (!arePrime) {
				this.p = random.nextInt(MAXIMUM_PRIME_VALUE-2) + 2;
				this.q = random.nextInt(MAXIMUM_PRIME_VALUE-2) + 2;
				arePrime = true;
				for (int i = 2; i < this.p / 2; i++) {
					if (this.p % i == 0)
						arePrime = false;
				}
				for (int i = 2; i < this.q / 2; i++) {
					if (this.q % i == 0)
						arePrime = false;
				}
				if (p == q)
					arePrime = false;
				this.setnEncryption(this.p * this.q);

				if (this.getnEncryption() <= ASCII_VALUE_COUNT)
					arePrime = false;
			}
			System.out.println("p: " + p + " q: " + q);

			this.setPhi((this.p - 1) * (this.q - 1));
			this.setE(utility.findCoPrimeNumberLessThan(phi));


			isDAppropriate = this.computePrivateKey();
			
			if (!isDAppropriate) {
				System.out.println(this);
				System.out.println("private key Value too high");
			}

		}
	}

	

	@Override
	public String toString() {
		return "RSA [message=" + message + ", p=" + p + ", q=" + q + ", nEncryption=" + nEncryption + ", nDecryption="
				+ nDecryption + ", phi=" + phi + ", e=" + e + ", d=" + d + ", encryptedMessage=" + encryptedMessage
				+ ", depcryptedMessage=" + depcryptedMessage + ", MAXIMUM_PRIME_VALUE=" + MAXIMUM_PRIME_VALUE
				+ ", MAXIMUM_PRIVATE_KEY=" + MAXIMUM_PRIVATE_KEY + ", ASCII_VALUE_COUNT=" + ASCII_VALUE_COUNT + "]";
	}

	private boolean computePrivateKey() {

		boolean isRemainderOne = false;
		int d = 2;
		while (!isRemainderOne) {

			if ((d * this.e) % this.phi == 1) {
				isRemainderOne = true;
			} else {
				d++;
				if (d > MAXIMUM_PRIVATE_KEY)
					return false;
			}
		}

		this.setD(d);
		return true;
	}

	public void computeEncryptedMessage() {
		this.setEncryptedMessage("RSA|");

		String message = this.message;
		System.out.println("Encrypting..");

		for (int i = 0; i < message.length(); i++) {
			this.encryptedMessage = this.encryptedMessage.concat(this.getEncryptedValue(message.charAt(i)));
			this.encryptedMessage = this.encryptedMessage.concat("|");
		}
		this.encryptedMessage = this.encryptedMessage.concat("end");

	}

	public void decryptEncryptedMessage() {
		this.setDepcryptedMessage("");
		String encryptedMessage = this.encryptedMessage.substring(4, this.encryptedMessage.length());
		String part;
		int position;
		char depryptedChar;
		Utility utility = new Utility();
		System.out.println("Decrypting..");
		System.out.println("d "+d+" n "+ this.nDecryption);
		while (!encryptedMessage.equals("end")) {
			part = "";
			position = 0;
			while (encryptedMessage.charAt(position) != '|') {
				part = part.concat(Character.toString(encryptedMessage.charAt(position)));

				position++;
			}
			encryptedMessage = encryptedMessage.substring(part.length() + 1, encryptedMessage.length());

			depryptedChar = (char) utility.moduloR(Integer.parseInt(part), this.d, this.nDecryption);

			this.depcryptedMessage = this.depcryptedMessage.concat(Character.toString(depryptedChar));
		}

	}

	public String getDepcryptedMessage() {
		return depcryptedMessage;
	}

	public void setDepcryptedMessage(String depcryptedMessage) {
		this.depcryptedMessage = depcryptedMessage;
	}

	private String getEncryptedValue(char character) {

		int asciiValue = (int) character;
		Utility utility = new Utility();
		return Integer.toString(utility.moduloR(asciiValue, this.e, this.nEncryption));
	}

}
