package kiss.util;

public class RNG {
    public static final AESPRNG RANDOM = new AESPRNG();
    public static void seed() { RANDOM.seed(); }
    public static void seed(double value) {
        RANDOM.seed(Double.doubleToLongBits(value));
    }
    public static int random(int a, int b) {
        return RANDOM.nextInt(a,b);
    }
    public static double random() {
        return RANDOM.nextDouble();
    }
}
