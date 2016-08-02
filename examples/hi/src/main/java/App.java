import static kiss.API.*;

class App {
    void testHiAda() {
        inProvide("Ada",EOL,42);
        outExpect("What is your name? What is a number? Hello, Ada!",EOL,
                  "I like 42 too.");
        hi();
        outClose();
        inClose();
    }

    void hi()
    {
        print("What is your name? ");
        String name = readLine();
        print("What is a number? ");
        int number = readInt(); readEOL();
        println("Hello, " + name + "!");
        println("I like " + number + " too.");
    }

    // no run() -- we just want to test Hi
}
