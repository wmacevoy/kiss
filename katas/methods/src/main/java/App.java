import static kiss.API.*;

class App {
    void bib() {
        print("bib");
    }
    void bob() {
        print("bob");
    }
    void boo() {
        print("boo");
    }
    void bity() {
        print("bity");
    }
    void space() {
        print(" ");
    }
    void line() {
        println();
    }

    void bibbityBobbityBoop() {
        // TODO call methods above to pass test below.
    }

    void testBibbityBobbityBoop() {
        try (Close out = outExpect("bibbity bobbity boo",EOL)) {
            bibbityBobbityBoop();
        }
    }

    void hello(String name) {
        println("Hello " + name);
    }

    void helloWorld() {
        // TODO: call the hello() method above to pass the test below
    }

    void testHelloWorld() {
        try (Close out = outExpect("Hello World!", EOL)) {
            helloWorld();
        }
    }

    int sum(int a, int b) {
        return a+b;
    }

    int sum(int a, int b, int c, int d, int e) {
        // Write a single return expression using ONLY sum(X,Y)
        // (no explicit additions), that return the sum of
        // all five terms.
    }

    void checkRandomSum() {
        int x = random(-100,100);
        int y = random(-100,100);
        int z = random(-100,100);
        int t = random(-100,100);
        int s = random(-100,100);

        assert sum(x,y,z,t,s) == (x+y+z+t+s);
    }

    void testSum() {
        checkRandomSum();
        checkRandomSum();
        checkRandomSum();
        checkRandomSum();
        checkRandomSum();
        checkRandomSum();
        checkRandomSum();
        checkRandomSum();                
    }
}
