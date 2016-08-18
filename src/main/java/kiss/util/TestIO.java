package kiss.util;

import static kiss.API.*;

import java.io.PrintStream;
import java.io.FileOutputStream;
import java.io.IOException;

class TestIO {
    void testOutExpect() {
        outExpect(1,2,3,EOL,
                  "testing",EOL,
                  "ok",EOL,
                  1.0,2.0,3.0,EOL,
                  "alpha","beta","tango");
        println(1,2,3);
        println("testing");
        println("ok");        
        println(1.0,2.0,3.0);
        println("alpha","beta","tango");
        outClose();

    }

    void testInProvide() {
        inProvide(1,2,3,EOL,
                  "testing",EOL,
                  "ok",EOL,
                  1.5,2.0,3.0,EOL,
                  "alpha","beta","tango");

        assert readInt() == 1;
        assert readInt() == 2;
        assert readInt() == 3;
        assert readEOL().equals(EOL);
        assert readLine().equals("testing");
        assert readLine().equals("ok");

        assert readDouble() == 1.5;
        assert readDouble() == 2.0;
        assert readDouble() == 3.0;
        assert readEOL().equals(EOL);

        inClose();
    }

        void testVerify() {
        try { // use java.io stuff to confidently create file content
            try (PrintStream ps
                 = new PrintStream(new FileOutputStream("test.txt"))) {
            
                ps.println("Testing 1 2 3...");
                ps.println(1);
                ps.println(PI);
            }
        } catch (IOException e) {
            print("how hard can it be? " + e);
        }

        // verify with the kiss approach
        try (Close vfy = outVerify("test.txt")) {
            println("Testing 1 2 3...");
            println(1);
            println(PI);
        }

        // verify that it also catches errors:
        boolean caught = false;
        try {
            try (Close vfy = outVerify("test.txt")) {
                println("Testing 1 2 3...");
                println(2);
                println(PI);
            }
        } catch (kiss.util.VerifyRuntimeException ae) {
            caught = true;
        }
        assert caught == true;
    }

    void testPrintf() {
	try (Close out = outExpect("Hello, World!",EOL)) {
	    printf("Hello, %s!\n","World");
	}
	try (Close out = outExpect("3.14",EOL)) {
	    printf("%1.2f\n",PI);
	}

    }
}
