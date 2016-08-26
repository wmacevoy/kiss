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

    void testReseedIntRange() {
        AESPRNG rng = new AESPRNG();
        rng.seed(1);
        int[] buf = new int[64*1024];
        int min = -3;
        int max =  7;
        rng.nextInts(buf,0,buf.length,min,max);
        rng.seed(1);
        for (int i=0; i<buf.length; ++i) {
            assert buf[i] == rng.nextInt(min,max);
            assert min <= buf[i];
            assert buf[i] <= max;
        }
    }

    void testReseedIntNullRange() {
        AESPRNG rng = new AESPRNG();
        rng.seed(1);
        int[] buf = new int[64*1024];
        int min = -3;
        int max = -7;
        rng.nextInts(buf,0,buf.length,min,max);
        rng.seed(1);
        for (int i=0; i<buf.length; ++i) {
            assert buf[i] == rng.nextInt(min,max);
            assert buf[i] == min;
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

    void testReseedFloatRange() {
        AESPRNG rng = new AESPRNG();
        rng.seed(1);
        float[] buf = new float[64*1024];
        float min =  (float) -PI;
        float max =  (float) sqrt(2);
        rng.nextFloats(buf,0,buf.length,min,max);
        rng.seed(1);
        for (int i=0; i<buf.length; ++i) {
            assert buf[i] == rng.nextFloat(min,max);
            assert min <= buf[i];
            assert buf[i] < max;
        }
    }

    void testReseedFloatNullRange() {
        AESPRNG rng = new AESPRNG();
        rng.seed(1);
        float[] buf = new float[64*1024];
        float min =  (float) PI;
        float max =  (float) sqrt(2);
        rng.nextFloats(buf,0,buf.length,min,max);
        rng.seed(1);
        for (int i=0; i<buf.length; ++i) {
            assert buf[i] == rng.nextFloat(min,max);
            assert buf[i] == min;
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

    void testReseedDoubleRange() {
        AESPRNG rng = new AESPRNG();
        rng.seed(1);
        double[] buf = new double[64*1024];
        double min = -PI;
        double max =  sqrt(2);
        rng.nextDoubles(buf,0,buf.length,min,max);
        rng.seed(1);
        for (int i=0; i<buf.length; ++i) {
            assert buf[i] == rng.nextDouble(min,max);
            assert min <= buf[i];
            assert buf[i] < max;
        }
    }

    void testReseedDoubleNullRange() {
        AESPRNG rng = new AESPRNG();
        rng.seed(1);
        double[] buf = new double[64*1024];
        double min =  PI;
        double max =  sqrt(2);
        rng.nextDoubles(buf,0,buf.length,min,max);
        rng.seed(1);
        for (int i=0; i<buf.length; ++i) {
            assert buf[i] == rng.nextDouble(min,max);
            assert buf[i] == min;
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
        assert abs(xbar) < 6/sqrt(n);
        println("sigma: " + sigma);
        assert abs(sigma-1) < 6/sqrt(n);
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
            assert abs(exact-numeric) < 6/sqrt(n);
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
            assert abs(exact-numeric) < 6/sqrt(n);
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
        double probabilityOfError = 1e-20;
        long delta = (long) ((-log(probabilityOfError))*pow(2,64)/n);
        assert minVal < Long.MIN_VALUE + delta;
        assert maxVal > Long.MAX_VALUE - delta;
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

        double probabilityOfError = 1e-20;
        long delta = (long) ((-log(probabilityOfError))*pow(2,63)/n);
        
        assert minVal >= 0;
        assert minVal < 0 + delta;
        assert maxVal > Long.MAX_VALUE - delta;
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

        double probabilityOfError = 1e-20;
        int delta = (int) ((-log(probabilityOfError))*pow(2,32)/n);
        
        assert minVal < Integer.MIN_VALUE + delta;
        assert maxVal > Integer.MAX_VALUE - delta;
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

        double probabilityOfError = 1e-20;
        int delta = (int) ((-log(probabilityOfError))*pow(2,31)/n);

        assert minVal >= 0;
        assert minVal < delta;
        assert maxVal > Integer.MAX_VALUE - delta;
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

        double probabilityOfError = 1e-20;
        double delta = -log(probabilityOfError)/n;

        assert minVal >= 0;
        assert maxVal < 1;

        assert minVal < delta;
        assert maxVal > 1-delta;
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

        double probabilityOfError = 1e-20;
        double delta = -log(probabilityOfError)/n;

        assert minVal >= 0;
        assert maxVal < 1;

        assert minVal < delta;
        assert maxVal > 1-delta;
    }

    private static double approxInvErf(double eps) {
        // https://en.wikipedia.org/wiki/Error_function
        // x=1-eps, for eps>0
        // x=-1-eps, for eps<0

        double b=(3*PI*(4-PI))/(8*(PI-3)); // b=1/a on Wiki
        double sigma=Math.copySign(1,eps);
        eps=abs(eps);
        double t=log(eps*(2-eps));
        double Q=(2*b)/PI+t/2;
        double y=sqrt(Q*Q-t*b)-Q;
        return Math.copySign(sqrt(y),sigma);
    }

    public static double round(double x, double digits) {
        return Math.round(x*pow(10,digits))*pow(10,-digits);
    }

    void testBooleanDistribution() {
        AESPRNG rng = new AESPRNG();
        rng.seed(); // strong
        
        int n = 1_000_000;        
        int [] bins = new int[2];
        for (int i=0; i<n; ++i) {
            if (rng.nextBoolean()) { ++bins[1]; }  else { ++bins[0]; }
        }

        double probabilityOfError = 1e-20;
        double mu=n/2.0;
        double sigma=sqrt(n/4.0);
        double delta = sqrt(2)*sigma*approxInvErf(probabilityOfError);

        assert abs(bins[0]-mu)<delta;
        assert abs(bins[1]-mu)<delta;
    }

    void testByteDistribution() {
        AESPRNG rng = new AESPRNG();
        rng.seed(); // strong
        
        int n = 1024*1024*128;
        int [] bins = new int[256];
        byte[] x = new byte[1024*1024];

        for (int i=0; i<n; i += x.length) {
            rng.nextBytes(x,0,x.length);
            for (int j=0; j<x.length; ++j) {
                ++bins[x[j]-Byte.MIN_VALUE];
            }
        }

        double probabilityOfError = 1e-20;
        double mu=n/256.0;
        double sigma=sqrt(n*(1/256.0)*(255.0/256.0));
        double delta = sqrt(2)*sigma*approxInvErf(probabilityOfError);

        for (int i=0; i<256; ++i) {
            assert abs(bins[i]-mu)<delta;
        }
    }

    void testBenchmark() {
        int n=128*1024*1024;
        int sum = 0;

        java.util.Random rng1 = new java.util.Random();
        rng1.setSeed(1);
        AESPRNG rng2 = new AESPRNG();
        rng2.seed(1);

        byte[] x = new byte[1024*1024];


        rng1.nextBytes(x);        
        double t0=time();
        for (int i=0; i<n; i += x.length) {
            rng1.nextBytes(x);
        }
        double t=time()-t0;

        rng2.nextBytes(x);
        double s0=time();
        for (int i=0; i<n; i += x.length) {
            rng2.nextBytes(x);
        }
        double s=time()-s0;

        println("time(sys rng)="+round(t,2)+"s");        
        println("time(kiss rng)="+round(s,2)+"s");
        println("kiss/sys ratio=",round(s/t,2));

        // Pi2 & Pi3 are pretty slow at AES...
        assert s/t < 5;
    }


}
