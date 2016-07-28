import static kiss.API.*;

class App {

    // all test...() methods are invoked before run() is invoked
    void testRun() {
        // create file with expected output
        outOpen("run.verify");
        println("Hello, World!");
        outClose();

        // verify that run() produces that output
        outVerify("run.verify");
        run();
        outClose();
    }

    void run()
    {
        println("Hello, World!");
    }
}
