package kiss.util;

import static kiss.API.*;

public class Test {
    void testRandomDieRange()
    {
        for (int i=0; i < 100; ++i) {
            int die = random(1,6);
            assert 1 <= die && die <= 6;
        }
    }

    void testRandomUniformRange()
    {
        for (int i=0; i < 100; ++i) {
            double x = random();
            assert 0 <= x && x < 1;
        }
    }
    
    void testRandomRepeatability()
    {
        double t = time();
        double [] samples = new double[100];
        seed(t);
        for (int i=0; i<samples.length; ++i) samples[i]=random();
        seed(t);
        for (int i=0; i<samples.length; ++i) assert samples[i] == random();
    }

    void testRandomDistribution() {
        println("test!");
        int n = 1000;
        double[] omegas = new double[] { 0, 1, sqrt(2), 2*PI };
        for (double omega : omegas) {
            double exact = (omega != 0) ? sin(omega)/omega : 1;

            double numeric = 0;
            for (int i=0; i<n; ++i) {
                numeric += cos(omega*random());
            }
            numeric = numeric/n;
            assert abs(exact-numeric) < 1/sqrt(n);
        }
    }
}
