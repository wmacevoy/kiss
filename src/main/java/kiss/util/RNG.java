package kiss.util;

public class RNG {
    public static final AESPRNG RANDOM = new AESPRNG();
    public static final void seed() { RANDOM.seed(); }
    public static final void seed(double value) {
        RANDOM.seed(Double.doubleToLongBits(value));
    }
    public static final int random(int a, int b) {
        return RANDOM.nextInt(a,b);
    }
    public static final double random() {
        return RANDOM.nextDouble();
    }
}
