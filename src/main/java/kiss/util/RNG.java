package kiss.util;

public class RNG {
	static ThreadLocal<MersenneTwisterFast> rng = new ThreadLocal<MersenneTwisterFast>() {
		@Override
		protected MersenneTwisterFast initialValue() {
			return new MersenneTwisterFast();
		}
	};

	public static void seed() {
		seed(System.currentTimeMillis());
	}

	public static void seed(long value) {
		rng.get().setSeed(value);
	}

	// return a random integer between a and b (including ends)
        // if b <= a, just returns a
	public static int random(int a, int b) {
            if (a <= b) {
                long n = ((long) b) - ((long) a) + 1;
		return (int)
                    ((rng.get().nextLong() & Long.MAX_VALUE) % n + a);
            } else {
                throw new IllegalArgumentException("random(" + a + "," + b + ") does not make sense.");
            }
	}

	// return a random integer between 1..n
	public static int random(int n) {
		return rng.get().nextInt(n) + 1;
	}

	// return a random real between in [0,1)
	public static double random() {
		return rng.get().nextDouble();
	}

}
