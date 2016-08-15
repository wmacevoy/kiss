import static kiss.API.*;

class App {

    boolean isWeekend;
    boolean isSunny;

    void ifSunny() {
        // TODO: println the message "sunny!" if isSunny is true
    }


    void ifSunnyElseCold() {
        // TODO: println the message "sunny!" if isSunny is true,
        //       println the message "cold." if isSunny is false
    }

    void ifSunnyAndWeekend() {
        // TODO: print the message "going out!" if both sunny and weekend are
        //       true.  Otherwise print nothing.
    }

    void ifPlans() {
        // TODO: print the message "going out!" if both sunny and weekend
        //       are true.  If is isn't sunny, but is the weekend,
        //       print out the message "hanging out with friends".  If
        //       none of these are true, then, print out "waiting for the
        //       weekend"
        //       You should be able to do this with 3 println statements
        //
    }

    void testIfSunny() {
        String [] expects = new String[] { null, "sunny!", null, "sunny!" };
        for (int p=0; p<2; ++p) {
            for (int q=0; q<2; ++q) {
                isWeekend = asBoolean(p);
                isSunny = asBoolean(q);
                String expect = expects[2*p+q];
                if (expect != null) {
                    try (Close out = outExpect(expect)) { ifSunny(); }
                } else {
                    try (Close out = outExpect()) { ifSunny(); }
                }
            }
        }
    }

    void testIfSunnyElseCold() {
        String [] expects = new String[] {
            "cold.", "sunny!", "cold.", "sunny!"
        };
        for (int p=0; p<2; ++p) {
            for (int q=0; q<2; ++q) {
                isWeekend = asBoolean(p);
                isSunny = asBoolean(q);
                String expect = expects[2*p+q];
                if (expect != null) {
                    try (Close out = outExpect(expect)) { ifSunnyElseCold(); }
                } else {
                    try (Close out = outExpect()) { ifSunnyElseCold(); }
                }
            }
        }
    }

    void testIfSunnyAndWeekend() {
        String [] expects = new String[] {
            null, null, null, "going out!"
        };
        for (int p=0; p<2; ++p) {
            for (int q=0; q<2; ++q) {
                isWeekend = asBoolean(p);
                isSunny = asBoolean(q);
                String expect = expects[2*p+q];
                if (expect != null) {
                    try (Close out = outExpect(expect)) {
                        ifSunnyAndWeekend();
                    }
                } else {
                    try (Close out = outExpect()) {
                        ifSunnyAndWeekend();
                    }
                }
            }
        }
    }

    void testIfPlans() {
        String [] expects = new String[] {
            "waiting for the weekend",  "waiting for the weekend",
            "hanging out with friends", "going out!"
        };
        for (int p=0; p<2; ++p) {
            for (int q=0; q<2; ++q) {
                isWeekend = asBoolean(p);
                isSunny = asBoolean(q);
                if (expect != null) {
                    try (Close out = outExpect(expect)) {
                        ifPlans();
                    }
                } else {
                    try (Close out = outExpect()) {
                        ifPlans();
                    }
                }
            }
        }
    }
    
}
