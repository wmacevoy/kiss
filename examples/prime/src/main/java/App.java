import static kiss.API.*;

class App {

    boolean skipSlowTests = true;
    
    void testIsPrimeSmallTrueCases() {    
        assert isPrime( 2) == true;
        assert isPrime( 3) == true;
        assert isPrime( 5) == true;
        assert isPrime( 7) == true;
    }
    void testIsPrimeSmallFalseCases() {
        assert isPrime(-5) == false;
        assert isPrime(-4) == false;
        assert isPrime(-3) == false;
        assert isPrime(-2) == false;
        assert isPrime(-1) == false;
        assert isPrime( 0) == false;
        assert isPrime( 1) == false;
        assert isPrime( 4) == false;
        assert isPrime( 6) == false;
        assert isPrime( 8) == false;
        assert isPrime( 9) == false;
        assert isPrime(10) == false;
    }

    void testIsPrimeRandomNegativeCases() {
        for (int i=0; i<100; ++i) {
            assert isPrime(random(Integer.MIN_VALUE,-1)) == false;
        }
    }

    void testIsPrimeRandomCompositeCases() {
        // make sure integer math does not overflow
        int n=(int) floor(sqrt(Integer.MAX_VALUE));
        for (int i=0; i<100; ++i) {
            assert isPrime(random(2,n)*random(2,n)) == false;
        }
    }

    void testIsPrimeLargeCases() {
        // http://compoasso.free.fr/primelistweb/page/prime/liste_online_en.php
        assert isPrime(        11) == true;
        assert isPrime(       101) == true;
        assert isPrime(      1009) == true;
        assert isPrime(     10007) == true;
        assert isPrime(    100003) == true;
        assert isPrime(   1000003) == true;
        assert isPrime(  10000019) == true;
        assert isPrime( 100000007) == true;        
        assert isPrime(1000000007) == true;
    }

    void testIsPrimeSame() {
        if (skipSlowTests) {
            println("skipSlowTests");
            return;
        }
        for (int i=0; i<100; ++i) {
            int n = random(Integer.MIN_VALUE,Integer.MAX_VALUE);
            assert isPrimeSlow(n) == isPrime(n);
        }
    }

    void testIsPrimeFast() {
        if (skipSlowTests) {
            println("skipSlowTests");
            return;
        }

        int tests = 100;
        double init  = 1;
        int minVal = 1_000_000_000;
        int maxVal = 1_999_999_999;
        double t0;

        seed(init);
        t0 = time();
        boolean tmp = false;
        for (int i = 0; i < tests; ++i) {
            int n = random(minVal,maxVal);
            if (isPrimeSlow(n)) { tmp = !tmp; }
        }
        double slowTime=time()-t0;

        seed(init);
        t0 = time();
        for (int i = 0; i < tests; ++i) {
            int n = random(minVal,maxVal);
            if (isPrime(n)) { tmp = !tmp; }
        }
        double fastTime=time()-t0;
        
        println("speedup: " + round(slowTime/fastTime) + " junk: " + tmp);

        assert fastTime < 0.1*slowTime;
    }

    boolean isPrimeSlow(int x) {
        if (x > 1) {
            for (int i=2; i<x; ++i) if (x % i == 0) return false;
            return true;
        } else {
            return false;
        }
    }
    
    boolean isPrime(int x)
    {
        if (x > 1) {
            if (x == 2) return true;
            if (x % 2 == 0) return false;
            int i=3;
            while (i*i <= x) {
                if (x % i == 0) return false;
                i = i + 2;
            }
            return true;
        } else {
            return false;
        }
    }

    public void run()
    {
        for (;;) {
            print("Number to check (0 to exit)? ");
            int n = readInteger();
            if (n == 0) break;
            if (isPrime(n)) {
                println(n + " is prime.");
            } else {
                println(n + " is not prime.");
            }
        }
    }
}
