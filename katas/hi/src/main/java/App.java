import static kiss.API.*;

class App {
    void testKata1() {
        for (String name : new String[] { "Alice", "Bob", "World" }) {
            try (Close in = inProvide(name)) {
                try (Close out = outExpect("Hello, " + name + "!")) {
                    hi();
                }
            }
        }
    }

    void hi() {
        // TODO
    }
}
