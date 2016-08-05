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
}
