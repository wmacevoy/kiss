import static kiss.API.*;

class App {
    void testRun() {
        try (Close out = outExpect("Hello, World!")) {
            run();
        }
    }
    
    void run() {
        println("Hello, World!");
    }
}
