import static kiss.API.*;

public class App {

    // all test...() methods are invoked before run() is invoked
    public void testHi() {
        // create file with expected output
        outOpen("hi.out");
        println("Hello, World!");
        outClose();

        // verify that hi() produces that output
        outVerify("hi.out");
        hi();
        outClose();
    }

    public void hi()
    {
        println("Hello, World!");
    }

    public void run() {
        println("All tests passed.");
    }
}
