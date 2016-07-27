import static kiss.API.*;

public class App {

    // all test...() methods are invoked before run() is invoked
    public void testRun() {
        // create file with expected output
        outOpen("hi.out");
        println("Hello, World!");
        outClose();

        // verify that run() produces that output
        outVerify("hi.out");
        run();
        outClose();
    }

    public void run()
    {
        println("Hello, World!");
    }
}
