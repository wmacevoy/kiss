import static kiss.API.*;

class Die
{
    private int value;

    void roll() { value = random(1,6); }

    int getValue() { return value; }

    Die() { roll(); }

    void testDieStats() {
        int n=10000;
        int[] bins = new int[6];
        for (int i=0; i<n; ++i) {
            roll();
            assert 1 <= value;
            assert value <= 6;
            ++bins[value-1];
        }
        for (int i=0; i<6; ++i) {
            assert abs(((double)bins[i])/n-1.0/6.0) < 1/sqrt(n);
        }
    }
}
