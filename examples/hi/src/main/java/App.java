import static kiss.API.*;

class App {
    void testHiAda() {
        // create a file with test input
        outOpen("hi.in");
        println("Ada");
        outClose();

        // create a file with expected output
        outOpen("hi.out");
        println("What is your name? Ada");
        println("Hello, Ada!");
        outClose();

        inOpen("hi.in"); // future input
        outVerify("hi.out"); // expected output
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
