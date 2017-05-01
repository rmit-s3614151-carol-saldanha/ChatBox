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
	private final int MAXIMUM_PRIME_VALUE = 20;
	private final int ASCII_VALUE_COUNT = 127;

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

		//this.initialize();
		System.out.println(this);
	}

	public int computePrivateKey() {
		Utility utility = new Utility();
		return this.miu = utility.inverseModulo(this.k, this.n);

	}
	
	public void computePrivateKey(int n, int g)
	{
		Random random = new Random();
		Utility utility = new Utility();
		int privateKey = 2;
		int p=utility.findPrimeFactor(n);
		int q = n/p;
		setLamda(computeLcm(p - 1,q - 1));
		System.out.println("lamda:" + lamda);
		
		this.setG(3);
		boolean checkGcd = false;
		while (!checkGcd) {
			if (computeGcd(g, (int) Math.pow(n, 2)) != 1) {
				setG(random.nextInt(n));
				checkGcd = true;
				break;
			} else {
				checkGcd = false;
				break;
			}

		}
		
		computeK(g,n);
		System.out.println("k:" + k);
		int miu = utility.inverseModulo(k,n);
		
		this.setLamda(lamda);
		this.setMiu(miu);
		System.out.println("Computed private key and Lamda"+this.miu +" "+ this.lamda);
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
		this.l = utility.moduloR(this.g, this.lamda, (int) Math.pow(this.n, 2));
		return this.k = (this.l - 1) / n;
	}

	private void initialize() {

		Random random = new Random();
		boolean arePrime;
		Utility utility = new Utility();
		boolean isDAppropriate = false;
		arePrime = false;
			while (!arePrime) {
				this.p = random.nextInt(MAXIMUM_PRIME_VALUE - 2) + 2;
				this.q = random.nextInt(MAXIMUM_PRIME_VALUE - 2) + 2;
				arePrime = true;
				for (int i = 2; i < this.p / 2; i++) {
					if (this.p % i == 0)
						arePrime = false;
				}
				for (int i = 2; i < this.q / 2; i++) {
					if (this.q % i == 0)
						arePrime = false;
				}
				if (p == q) {
					arePrime = false;
				} else if (computeGcd(this.p * this.q, (this.p - 1) * (this.q - 1)) != 1) {
					arePrime = false;
				}
				this.setN(this.p * this.q);
				this.setnEncryption(this.p * this.q);

				if (this.getnEncryption() <= ASCII_VALUE_COUNT)
					arePrime = false;
			}
			System.out.println("p: " + p + " q: " + q);

			this.setG(3);
			boolean checkGcd = false;
			while (!checkGcd) {
				if (computeGcd(this.g, (int) Math.pow(this.n, 2)) != 1) {
					this.setG(random.nextInt(this.n));
					checkGcd = true;
					break;
				} else {
					checkGcd = false;
					break;
				}

			}
			System.out.println("g: " + g);
			setLamda(computeLcm(this.p - 1, this.q - 1));
			System.out.println("lamda:" + lamda);

			computeK(this.g, this.n);
			System.out.println("k:" + k);
			
			this.setMiu(computePrivateKey());
			System.out.println("Private key" + this.miu);
			
			this.computeEncryptedMessage();
			this.decryptEncryptedMessage();
			
}
	



	private String getEncryptedValue(char character) {

		int asciiValue = (int) character;
		Utility utility = new Utility();
		return Integer.toString(utility.moduloR(utility.moduloR(g,asciiValue, (int) Math.pow(n, 2)) * utility.moduloR(3,n, (int) Math.pow(n, 2)),1, (int) Math.pow(n, 2)));
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
		String encryptedMessage = this.encryptedMessage.substring(4, this.encryptedMessage.length());
		String part;
		int position;
		char decryptedChar;
		Utility utility = new Utility();
		System.out.println("Decrypting..");
		System.out.println("lamda "+ this.lamda+" n "+ this.nDecryption);
		while (!encryptedMessage.equals("end")) {
			part = "";
			position = 0;
			while (encryptedMessage.charAt(position) != '|') {
				part = part.concat(Character.toString(encryptedMessage.charAt(position)));

				position++;
			}
			encryptedMessage = encryptedMessage.substring(part.length() + 1, encryptedMessage.length());

			computeK((char) utility.moduloR(Integer.parseInt(part), this.lamda, (int) Math.pow(this.nDecryption, 2)),this.nDecryption);

			decryptedChar = (char) utility.moduloR(this.k * this.miu , 1, this.nDecryption) ;
			this.depcryptedMessage = this.depcryptedMessage.concat(Character.toString(decryptedChar));
		}

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
