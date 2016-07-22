package kiss.util;

import static kiss.API.*;

public class Test {
    public void testRandomRange()
    {
        for (int i=0; i < 100; ++i) {
            int die = random(1,6);
            assert 1 <= die && die <= 6;                
        }
    }
}
