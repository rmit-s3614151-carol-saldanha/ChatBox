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
	private String encryptedMessage = "RSA|";
	private String depcryptedMessage = "";
	
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
		System.out.println("Initializing..");

		this.initialize();
		System.out.println(this);
	}

	private void initialize() {
		Random random = new Random();
		boolean arePrime = false;
		Utility utility = new Utility();
		boolean isDAppropriate = false;
		while(!isDAppropriate) {
			
			System.out.println("Finding Primes..");
			while(!arePrime) {
			this.p = random.nextInt(100);
			this.q = random.nextInt(100);
			arePrime = true;
				for(int i = 2; i< this.p/2 ;  i++) {
					if(this.p % i == 0)arePrime = false;
				}
				for(int i = 2; i< this.q/2 ;  i++) {
					if(this.q % i == 0) arePrime = false;
				}
				if (p == q) arePrime = false;
				this.setN(this.p * this.q);

				if (this.getN() < 127 ) arePrime = false;
			}
			System.out.println("p: "+ p +" q: "+q);
			
			this.setPhi((this.p - 1) * (this.q - 1));
			this.setE(utility.findCoPrimeNumberLessThan(phi));
			
			System.out.println("Finding private key..");

			isDAppropriate = this.computePrivateKey();
;
			if(!isDAppropriate) {
				System.out.println(this);
				System.out.println("private key Value too high");
			}
			
		}
	}

	@Override
	public String toString() {
		return "RSA [p=" + p + ", q=" + q + ", n=" + n + ", phi=" + phi + ", e=" + e + ", d=" + d + "]";
	}

	private boolean computePrivateKey() {

		boolean isRemainderOne = false;
		int d = 2;
		while (!isRemainderOne) {

			if ((d * this.e) % this.phi == 1) {
				isRemainderOne = true;
			}
			else {
				d++;
				if (d>1000) return false;
			}
		}
		
		this.setD(d);
		return true;
	}
	
	public void computeEncryptedMessage()
	{
		this.setEncryptedMessage("RSA|");

		String message = this.message;
		System.out.println("Encrypting..");

		for(int i = 0; i < message.length(); i++) {
			this.encryptedMessage = this.encryptedMessage.concat(this.getEncryptedValue(message.charAt(i)));
			this.encryptedMessage = this.encryptedMessage.concat("|");
		}
		this.encryptedMessage = this.encryptedMessage.concat("end");
		
	}
	
	public void decryptEncryptedMessage() 
	{
		String encryptedMessage = this.encryptedMessage.substring(4, this.encryptedMessage.length());
		String part;
		int position;
		char depryptedChar;
		Utility utility = new Utility();
		System.out.println("Decrypting..");
		
		while(!encryptedMessage.equals("end")) {
			part = "";
			position=0;

			while(encryptedMessage.charAt(position) != '|')
			{
				part = part.concat(Character.toString(encryptedMessage.charAt(position)));
				position++;
			}
			encryptedMessage = encryptedMessage.substring(part.length() + 1, encryptedMessage.length());
			
			depryptedChar = (char) utility.moduloR(Integer.parseInt(part),this.d,this.n);
			
			this.depcryptedMessage =  this.depcryptedMessage.concat(Character.toString(depryptedChar));
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
		return Integer.toString(utility.moduloR(asciiValue, this.e, this.n));
	}

}
