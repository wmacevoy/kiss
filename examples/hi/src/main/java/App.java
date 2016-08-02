import static kiss.API.*;

class App {
    void testHiAda() {
        inProvide("Ada");
        outExpect("What is your name? Hello, Ada!");
        hi();
        outClose();
        inClose();
    }

    void hi()
    {
        print("What is your name? ");
        String name = readLine();
        println("Hello, " + name + "!");
    }

    // no run() -- we just want to test Hi
}
