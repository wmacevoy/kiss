import static kiss.API.*;

class App {
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
    
    boolean isPrime(int x)
    {
        // true if x > 1 and has no integer divisors between 1 and x

        // TODO

        return true || false;
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
