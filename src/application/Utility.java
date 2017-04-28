package application;

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

}
