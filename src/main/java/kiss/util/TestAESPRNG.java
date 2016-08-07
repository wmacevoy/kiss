package kiss.util;

import static kiss.API.*;

class TestAESPRNG
{
    void testReseedBytes() {
        AESPRNG rng = new AESPRNG();
        rng.seed(1);
        byte[] buf = new byte[64*1024];
        rng.nextBytes(buf,0,buf.length);
        rng.seed(1);
        for (int i=0; i<buf.length; ++i) {
            assert buf[i] == rng.nextByte();
        }
    }

    void testReseedInts() {
        AESPRNG rng = new AESPRNG();
        rng.seed(1);
        int[] buf = new int[64*1024];
        rng.nextInts(buf,0,buf.length);
        rng.seed(1);
        for (int i=0; i<buf.length; ++i) {
            assert buf[i] == rng.nextInt();
        }
    }

    void testReseedLongs() {
        AESPRNG rng = new AESPRNG();
        rng.seed(1);
        long[] buf = new long[64*1024];
        rng.nextLongs(buf,0,buf.length);
        rng.seed(1);
        for (int i=0; i<buf.length; ++i) {
            assert buf[i] == rng.nextLong();
        }
    }

    void testReseedFloats() {
        AESPRNG rng = new AESPRNG();
        rng.seed(1);
        float[] buf = new float[64*1024];
        rng.nextFloats(buf,0,buf.length);
        rng.seed(1);
        for (int i=0; i<buf.length; ++i) {
            assert buf[i] == rng.nextFloat();
        }
    }

    void testReseedDoubles() {
        AESPRNG rng = new AESPRNG();
        rng.seed(1);
        double[] buf = new double[64*1024];
        rng.nextDoubles(buf,0,buf.length);
        rng.seed(1);
        for (int i=0; i<buf.length; ++i) {
            assert buf[i] == rng.nextDouble();
        }
    }

    void testReseedGaussians() {
        AESPRNG rng = new AESPRNG();
        rng.seed(1);
        double[] buf = new double[64*1024];
        rng.nextGaussians(buf,0,buf.length);
        rng.seed(1);
        for (int i=0; i<buf.length; ++i) {
            assert buf[i] == rng.nextGaussian();
        }
    }

    void testGaussian() {
        AESPRNG rng = new AESPRNG();
        rng.seed(1);
        int n = 64*1024;
        double[] buf = new double[n];
        rng.nextGaussians(buf,0,buf.length);
        double sum = 0, sum2 = 0;
        for (int i=0; i<n; ++i) { sum += buf[i]; sum2 += buf[i]*buf[i]; }
        double xbar = sum/n;
        double sigma = sqrt(sum2/n);
        assert abs(xbar) < 1/sqrt(n);
        assert abs(sigma-1) < 1/sqrt(n);
    }

    void testMonteCarloDoubles() {
        AESPRNG rng = new AESPRNG();
        rng.seed(1);        
        // try monte-carlo integration of $\int_0^1 cos(omega*x) dx
        int n = 1_000_000;
        double[] x = new double[n];
        double[] omegas = new double[] { 0, 1, sqrt(2), 2*PI };
        for (double omega : omegas) {
            double exact = (omega != 0) ? sin(omega)/omega : 1;

            double numeric = 0;
            rng.nextDoubles(x,0,x.length);
            for (int i=0; i<n; ++i) {
                numeric += cos(omega*x[i]);
            }
            numeric = numeric/n;
            assert abs(exact-numeric) < 1/sqrt(n);
        }
    }

    void testMonteCarloFloats() {
        AESPRNG rng = new AESPRNG();
        rng.seed(1);
        // try monte-carlo integration of $\int_0^1 cos(omega*x) dx
        int n = 1_000_000;
        float[] x = new float[n];
        double[] omegas = new double[] { 0, 1, sqrt(2), 2*PI };
        for (double omega : omegas) {
            double exact = (omega != 0) ? sin(omega)/omega : 1;

            double numeric = 0;
            rng.nextFloats(x,0,x.length);
            for (int i=0; i<n; ++i) {
                numeric += cos(omega*x[i]);
            }
            numeric = numeric/n;
            assert abs(exact-numeric) < 1/sqrt(n);
        }
    }


    void testLongRange() {
        AESPRNG rng = new AESPRNG();
        rng.seed(); // strong
        int n = 1_000_000;
        long[] x = new long[n];
        rng.nextLongs(x,0,x.length);
        long minVal = Long.MAX_VALUE;
        long maxVal = Long.MIN_VALUE;
        for (int i=0; i<n; ++i) {
            if (x[i] < minVal) minVal=x[i];
            if (x[i] > maxVal) maxVal=x[i];
        }
        assert(minVal < Long.MIN_VALUE + (1/sqrt(n))*pow(2,64));
        assert(maxVal > Long.MAX_VALUE - (1/sqrt(n))*pow(2,64));
    }

    void testNonNegativeLongRange() {
        AESPRNG rng = new AESPRNG();
        rng.seed(); // strong
        int n = 1_000_000;
        long[] x = new long[n];
        rng.nextNonNegativeLongs(x,0,x.length);
        long minVal = Long.MAX_VALUE;
        long maxVal = Long.MIN_VALUE;
        for (int i=0; i<n; ++i) {
            if (x[i] < minVal) minVal=x[i];
            if (x[i] > maxVal) maxVal=x[i];
        }
        assert(minVal >= 0);
        assert(minVal < 0 + (1/sqrt(n))*pow(2,64));
        assert(maxVal > Long.MAX_VALUE - (1/sqrt(n))*pow(2,64));
    }


    void testIntRange() {
        AESPRNG rng = new AESPRNG();
        rng.seed(); // strong
        int n = 1_000_000;
        int[] x = new int[n];
        rng.nextInts(x,0,x.length);
        int minVal = Integer.MAX_VALUE;
        int maxVal = Integer.MIN_VALUE;
        for (int i=0; i<n; ++i) {
            if (x[i] < minVal) minVal=x[i];
            if (x[i] > maxVal) maxVal=x[i];
        }
        assert(minVal < Integer.MIN_VALUE + (1/sqrt(n))*pow(2,32));
        assert(maxVal > Integer.MAX_VALUE - (1/sqrt(n))*pow(2,32));
    }

    void testNonNegativeIntRange() {
        AESPRNG rng = new AESPRNG();
        rng.seed(); // strong
        int n = 1_000_000;
        int[] x = new int[n];
        rng.nextNonNegativeInts(x,0,x.length);
        int minVal = Integer.MAX_VALUE;
        int maxVal = Integer.MIN_VALUE;
        for (int i=0; i<n; ++i) {
            if (x[i] < minVal) minVal=x[i];
            if (x[i] > maxVal) maxVal=x[i];
        }
        assert(minVal >= 0);
        assert(minVal < 0 + (1/sqrt(n))*pow(2,32));
        assert(maxVal > Integer.MAX_VALUE - (1/sqrt(n))*pow(2,32));
    }

    void testDoubleRange() {
        AESPRNG rng = new AESPRNG();
        rng.seed(); // strong
        int n = 1_000_000;
        double[] x = new double[n];
        rng.nextDoubles(x,0,x.length);
        double minVal = Double.MAX_VALUE;
        double maxVal = Double.MIN_VALUE;
        for (int i=0; i<n; ++i) {
            if (x[i] < minVal) minVal=x[i];
            if (x[i] > maxVal) maxVal=x[i];
        }
        assert(minVal >= 0);
        assert(maxVal < 1);
        assert(minVal < 0 + (1/sqrt(n)));
        assert(maxVal > 1 - (1/sqrt(n)));
    }

    void testFloatRange() {
        AESPRNG rng = new AESPRNG();
        rng.seed(); // strong
        int n = 1_000_000;
        float[] x = new float[n];
        rng.nextFloats(x,0,x.length);
        float minVal = Float.MAX_VALUE;
        float maxVal = Float.MIN_VALUE;
        for (int i=0; i<n; ++i) {
            if (x[i] < minVal) minVal=x[i];
            if (x[i] > maxVal) maxVal=x[i];
        }
        assert(minVal >= 0.0f);
        assert(maxVal < 1.0f);
        assert(minVal < 0 + (1/sqrt(n)));
        assert(maxVal > 1 - (1/sqrt(n)));
    }


    void testBooleanDistribution() {
        AESPRNG rng = new AESPRNG();
        rng.seed(); // strong
        
        int n = 1_000_000;        
        int [] bins = new int[2];
        for (int i=0; i<n; ++i) {
            if (rng.nextBoolean()) { ++bins[1]; }  else { ++bins[0]; }
        }
        assert(abs(bins[0]-n/2)<sqrt(n));
        assert(abs(bins[1]-n/2)<sqrt(n));        
    }

    void testByteDistribution() {
        AESPRNG rng = new AESPRNG();
        rng.seed(); // strong
        
        int n = 1024*1024;
        int [] bins = new int[256];
        byte[] x = new byte[n];
        rng.nextBytes(x,0,x.length);
        for (int i=0; i<n; ++i) {
            ++bins[x[i]-Byte.MIN_VALUE];
        }
        for (int i=0; i<256; ++i) {
            assert(abs(bins[i]-n/256)<sqrt(n));
        }
    }

    void testByteDistribution() {
        AESPRNG rng = new AESPRNG();
        rng.seed(); // strong
        
        int n = 1024*1024;
        int [] bins = new int[256];
        byte[] x = new byte[n];
        rng.nextBytes(x,0,x.length);
        for (int i=0; i<n; ++i) {
            ++bins[x[i]-Byte.MIN_VALUE];
        }
        for (int i=0; i<256; ++i) {
            assert(abs(bins[i]-n/256)<sqrt(n));
        }
    }
}
