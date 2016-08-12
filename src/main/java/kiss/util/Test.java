package kiss.util;

import static kiss.API.*;

public class Test {
    void testIO() { test(new TestIO()); }
    void testAs() { test(new TestAs()); }    
    void testRNG() { test(new TestRNG()); }
    void testRun() { test(new TestRun()); }
    void testAESPRNG() { test(new TestAESPRNG()); }
}
