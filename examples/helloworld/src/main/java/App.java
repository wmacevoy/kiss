import static kiss.API.*;

class App {
    void testRun() {
        outOpen("testRun.verify");
        println("Hello, World!");
        outClose();

        outVerify("testRun.verify");
        run();
        outClose();
    }
    
    void run() {
        println("Hello, World!");
    }
}
