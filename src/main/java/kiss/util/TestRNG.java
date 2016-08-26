package kiss.util;

import static kiss.API.*;

class TestRNG {
    void testRandomIntRange()
    {
        int min = -2;
        int max =  7;
        boolean [] hits = new boolean[max-min+1];
        for (int i=0; i < 1000; ++i) {
            int x = random(min,max);
            assert min <= x && x <= max;
            hits[x - min]=true;
        }
        for (int i=0; i<hits.length; ++i) {
            assert hits[i] == true;
        }
    }

    void testRandomUniformRange()
    {
        boolean hits [] = new boolean[10];
        for (int i=0; i < 100; ++i) {
            double x = random();
            assert 0 <= x && x < 1;
            hits[(int) Math.floor(x*hits.length)]=true;
        }
        for (int i=0; i<hits.length; ++i) {
            assert hits[i] == true;
        }
    }
    
    void testRandomRepeatability()
    {
        double [] samples = new double[100];
        seed(1);
        for (int i=0; i<samples.length; ++i) samples[i]=random();
        seed(1);
        for (int i=0; i<samples.length; ++i) assert samples[i] == random();
        seed(2);
        for (int i=0; i<samples.length; ++i) assert samples[i] != random();
    }
}
