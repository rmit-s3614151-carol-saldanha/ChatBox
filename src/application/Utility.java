package application;

import java.util.Random;

public class Utility {

	public Utility() {

	}

	public int moduloR(int a, int b, int c) {

		// BASE
		if (b == 1)
			return (a % c);
		else {
			return (moduloR((moduloR(a, 1, c) * moduloR(a, b - 1, c)), 1, c));
		}
	}
	
	public int findCoPrimeNumberLessThan(int phi) {
		int e = 0;
		boolean isCoprime = false;
		Random random = new Random();
		
		while(!isCoprime)
		{
			e = random.nextInt(phi - 2) + 2;
			isCoprime = true;
			for(int i=phi-1; i>1; i++) {
				if((e % i == 0) && (phi % i == 0)) isCoprime = false;
			}
		}
		
		return e;
	}

}
