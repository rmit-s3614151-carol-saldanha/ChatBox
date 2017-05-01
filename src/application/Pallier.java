package application;

import java.util.Random;
import java.util.Scanner;

public class Pallier {

	private String message;
	private int p;
	private int q;
	private int g;
	private int n;
	private int lamda;
	private int k;
	private int miu;
	private int nEncryption;
	private int nDecryption;
	private String encryptedMessage = "Pallier|";
	private String depcryptedMessage = "";
	private int MAX_LCM;
	private int MIN_LCM;
	private int l;
	private final int MAXIMUM_G_VALUE = 100;
	private final int R = 3;

	Utility utility = new Utility();

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

	public int getG() {
		return g;
	}

	public int getL() {
		return l;
	}

	public void setL(int l) {
		this.l = l;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public int getLamda() {
		return lamda;
	}

	public void setLamda(int lamda) {
		this.lamda = lamda;
	}

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}

	public int getMiu() {
		return miu;
	}

	public void setMiu(int miu) {
		this.miu = miu;
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

	public Pallier() {
		System.out.println("Initializing..");

		this.initialize();
		System.out.println(this);
	}

	public int computePrivateKey() {
		Utility utility = new Utility();

		return this.miu = utility.inverseModulo(this.k, this.n);

	}

	public void computePrivateKey(int n, int g) {
		Random random = new Random();
		Utility utility = new Utility();
		int privateKey = 2;
		int p = utility.findPrimeFactor(n);
		int q = n / p;
		setLamda(computeLcm(p - 1, q - 1));
		this.setG(random.nextInt(100));

		computeK(this.g, this.n);
		System.out.println("k:" + k);
		this.miu = utility.inverseModulo(this.k, this.n);

		this.setLamda(lamda);
		this.setMiu(miu);

	}

	public int computeGcd(int a, int b) {

		return (b == 0) ? a : computeGcd(b, a % b);

	}

	public int computeLcm(int a, int b) {
		int lcm = 0;
		int MAX_LCM;
		int MIN_LCM;
		if (a > b) {
			MAX_LCM = a;
			MIN_LCM = b;
		} else {
			MAX_LCM = b;
			MIN_LCM = a;
		}

		for (int i = 1; i <= MIN_LCM; i++) {
			int x = MAX_LCM * i;
			if (x % MIN_LCM == 0) {
				lcm = x;
				break;
			}
		}

		return lcm;
	}

	public int computeK(int g, int n) {
		this.l = utility.moduloR(this.g, this.lamda, this.n * this.n);
		return this.k = (this.l - 1) / n;
	}

	public int computeDecryptedK(int c, int n) {
		this.l = utility.moduloR(c, this.lamda, this.n * this.n);
		return this.k = (this.l - 1) / n;
	}

	private void initialize() {

		this.setP(19);
		this.setQ(7);
		this.setN(this.p * this.q);
		this.setnEncryption(this.p * this.q);
		System.out.println("p: " + p + " q: " + q);
		this.setG(3);
		setLamda(computeLcm(this.p - 1, this.q - 1));
		computeK(this.g, this.n);
		this.setMiu(computePrivateKey());

	}

	private String getEncryptedValue(char character) {

		int asciiValue = (int) character;
		Utility utility = new Utility();
		return Integer.toString(utility.moduloR(
				utility.moduloR(g, asciiValue, this.n * this.n) * utility.moduloR(R, n, this.n * this.n), 1,
				this.n * this.n));
	}

	public void computeEncryptedMessage() {
		this.setEncryptedMessage("Pallier|");

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
		String encryptedMessage = this.encryptedMessage.substring(8, this.encryptedMessage.length());
		String part;
		int position;
		int decryptedK;
		char decryptedChar;
		Utility utility = new Utility();
		System.out.println("Decrypting..");
		System.out.println("lamda " + this.lamda + " n " + this.n);
		System.out.println(this.encryptedMessage);
		while (!encryptedMessage.equals("end")) {
			part = "";
			position = 0;
			while (encryptedMessage.charAt(position) != '|') {
				part = part.concat(Character.toString(encryptedMessage.charAt(position)));

				position++;
			}
			encryptedMessage = encryptedMessage.substring(part.length() + 1, encryptedMessage.length());

			decryptedK = this.computeDecryptedK(Integer.parseInt(part), n);

			decryptedChar = (char) utility.moduloR(
					(utility.moduloR(this.miu, 1, this.n) * utility.moduloR(decryptedK, 1, this.n)), 1, this.n);
			this.depcryptedMessage = this.depcryptedMessage.concat(Character.toString(decryptedChar));

		}
		System.out.println("Encrypted message  :  " + getEncryptedMessage());
		System.out.println(this.getDepcryptedMessage());

	}

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter a msg");
		String msg = sc.nextLine();
		Pallier p = new Pallier();
		p.setMessage(msg);
		p.initialize();
		p.computeEncryptedMessage();
		p.decryptEncryptedMessage();

	}
}
