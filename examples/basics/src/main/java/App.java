import static kiss.API.*;

class App {
    void testHi() {
        String name = "Alice";
        try (Close in = inProvide(name)) {
            try (Close out = outExpect("Hi, " + name + ".")) {
                hi();
            }
        }
    }
    void hi() {
        String name = readLine();
        println("Hi, " + name + ".");
    }
   
    void testBooleanValues() {
        assert true == true;
        assert false == false;
        assert true != false;
    }

    void testBooleanOutput() {
        try (Close out = outExpect("true")) { println(true); }
        try (Close out = outExpect("false")) { println(false); }
    }
   
    void testBooleanInput() {
        try (Close in = inProvide("true")) { assert readBoolean() == true; }
        try (Close in = inProvide("false")) { assert readBoolean() == false; }
    }
   
    void testBooleanNot() {
        assert !true == false;
        assert !false == true;
    }
   
    void testBooleanAnd() {
        assert (true && true) == true;
        assert (true && false) == false;
        assert (false && true) == false;
        assert (false && false) == false;
       
        // and short-circuit
        assert (false && (random(0,1) == 1)) == false;
        assert (false && (random(0,1) == 1)) == false;
        assert (false && (random(0,1) == 1)) == false;
        assert (false && (random(0,1) == 1)) == false;
      
        assert (false && (1/0) == 1) == false;
    }
   
    void testBooleanOr() {
        assert (true || true) == true;
        assert (true || false) == true;
        assert (false || true) == true;
        assert (false || false) == false;
       
        assert (true || (random(0,1) == 1)) == true;
        assert (true || (random(0,1) == 1)) == true;
        assert (true || (random(0,1) == 1)) == true;
        assert (true || (random(0,1) == 1)) == true;

        assert (true || (1/0) == 1) == true;
    }
   
    void testIfs() {
        boolean skyIsBlue = true;
        boolean pigsFly = false;
       
        try (Close out = outExpect()) {
            if (pigsFly) { println("surprized!"); }
        }
       
        try (Close out = outExpect("still?")) {
            if (skyIsBlue) { println("still?"); }           
        }

        try (Close out = outExpect("not today.")) {
            if (pigsFly) {
                println("surprized!");
            } else {
                println("not today.");
            }
        }

        try (Close out = outExpect("yup.")) {
            if (skyIsBlue) {
                println("yup.");
            } else {
                println("not today.");
            }
        }
       
        try (Close out = outExpect("option b")) {
            if (pigsFly) {
                println("option a");
            } else if (skyIsBlue) {
                println("option b");
            } else {
                println("option c");
            }
        }
    }
   
    void testIntRanges() {
        assert Integer.MIN_VALUE == -pow(2,31);
        assert Integer.MAX_VALUE ==  pow(2,31)-1;

        assert Integer.MIN_VALUE == -2_147_483_648;
        assert Integer.MAX_VALUE ==  2_147_483_647;
       
        assert Integer.MIN_VALUE <  -2_000_000_000;
        assert Integer.MAX_VALUE >   2_000_000_000;
       
        assert Integer.MIN_VALUE - 1 == Integer.MAX_VALUE;
        assert Integer.MAX_VALUE + 1 == Integer.MIN_VALUE;

        assert Integer.MAX_VALUE == 0x7F_FF_FF_FF;
        assert Integer.MAX_VALUE == 0b01111111_11111111_11111111_11111111;
       
        assert Integer.MIN_VALUE == 0x80_00_00_00;
        assert Integer.MIN_VALUE == 0b10000000_00000000_00000000_00000000;
    }
   
    void testIntOutput() {
        try (Close out = outExpect("1234")) { println(1_234); }
        try (Close out = outExpect("255")) { println(0xFF); }
    }
    
    void testIntInput() {   
        try (Close in = inProvide("1234")) { assert readInt() == 1_234; }
    }
   
    void testIntComparisons() {

        assert 0 == 0;
        assert 1 == 1;
        assert 0 != 1;
        assert 1 != 0;
       
        assert 1 > 0;
        assert 0 < 1;
        assert 1 >= 0;
        assert 0 >= 0;
        assert 0 <= 1;
        assert 0 <= 0;
    }
   
    void testIntArithmetic() { 
        assert 7 + 3 == 10;
        assert 7 - 3 ==  4;
        assert 7 * 3 == 21;
        assert 7 / 3 ==  2; // integer division
        assert 7 % 3 ==  1; // integer remainder

        { int x = 7; ++x;    assert x == 8; } // increment       
        { int x = 7; x += 3; assert x ==10; } // adding
        { int x = 7; x -= 3; assert x == 4; } // subtracting
        { int x = 7; x *= 3; assert x ==21; } // multipling
        { int x = 7; x /= 3; assert x == 2; } // dividing (integer)
        { int x = 7; x %= 3; assert x == 1; } // remainder
    }
   
    void testBasicLoops() {
        try (Close out = outExpect(0,EOL,1,EOL,2,EOL,3,EOL)) {
            int n = 4;
            int i = 0;
            while (i < n) {
                println(i);
                ++i;
            }
        }

        try (Close out = outExpect(3,EOL,2,EOL,1,EOL,0,EOL)) {
            int n = 4;
            int i = n-1;
            while (i >= 0) {
                println(i);
                --i;
            }
        }

        try (Close out = outExpect(0,EOL,1,EOL,2,EOL,3,EOL)) {
            int n = 4;
            for (int i = 0; i < n; ++i) {
                println(i);
            }
        }
       
        try (Close out = outExpect(3,EOL,2,EOL,1,EOL,0,EOL)) {
            int n = 4;
            for (int i = n-1; i >= 0; --i) {
                println(i);
            }
        }

        try (Close out = outExpect(0,EOL,1,EOL,3,EOL)) {
            int n = 4;
            for (int i = 0; i < n; ++i) {
                if (i == 2) { continue; }
                println(i);
            }
        }

        try (Close out = outExpect(0,EOL,1,EOL)) {
            int n = 4;
            for (int i = 0; i < n; ++i) {
                if (i == 2) { break; }
                println(i);
            }
        }
        
        try (Close out = outExpect("doe",EOL,"rae",EOL,"me",EOL)) {
            String[] notes = new String[] { "doe", "rae" , "me" };

            for (int i = 0; i<notes.length; ++i) {
                println(notes[i]);
            }
        }

        try (Close out = outExpect("doe",EOL,"rae",EOL,"me",EOL)) {
            String[] notes = new String[] { "doe", "rae" , "me" };

            for (String note : notes) {
                println(note);
            }
        }
    }
   
   
    void testDoubleValues() {
        assert Double.MIN_VALUE == 4.9E-324;
        assert Double.MAX_VALUE == 1.7976931348623157E308;
        assert PI == 3.141592653589793;
        assert E  ==  2.718281828459045;

        assert 5.3e2  == 530.0;
        assert 5.3e1  ==  53.0;
        assert 5.3e0  ==   5.3;
        assert 5.3e-1 ==   0.53;
        assert 5.3e-2 ==   0.053;
       
        assert 6.626_070_04e-34 == 
            0.000_000_000_000_000_000_000_000_000_000_000_662_607_004;
    }
   
    void testDoubleInexact() {
       
        double sum = 0.1+0.1+0.1+0.1+0.1+0.1+0.1+0.1+0.1+0.1;
        assert(sum != 1.0);
        assert(abs(sum-1.0) <= 2e-16);
    }
   
    void testDoubleDivision() {
        assert 3/2 == 1.0;
        assert 3.0/2.0 == 1.5;
    }
   
    void testDoubleCast() {
        int a = 3;
        int b = 2;
       
        assert a / b == 1;
        assert (double) (a/b) == 1.0;
        assert ((double) a)/((double) b) == 1.5;
    }
   
    void testMath() {
        assert round(3.33) == 3;
        assert round(3.67) == 4;
        assert round(-3.33) == -3;
        assert round(-3.67) == -4;
       
        assert sqrt(25.0) == 5.0;
        assert pow(5.0,2.0) == 25.0;

        assert sin(PI/2.0) == 1.0;
        assert asin(1.0) == PI/2.0;
       
        assert sin(toRadians(90.0)) == 1.0;
        assert toDegrees(asin(1.0)) == 90.0;

        assert abs(exp(1.0)-E) < 1e-15;
        assert abs(log(E)-1) < 1e-15;
       
        assert abs(pow(10.0,1.0)-10) < 1e-15;
        assert abs(log10(10.0)-1) < 1e-15;
    }
   
    void testDoubleRandom() {
        double p = 0.55;
        double q = 0.25;
        double r = 1-(p+q);

        assert (0 <= p) && (p <= 1);
        assert (0 <= q) && (q <= 1);
        assert (0 <= r) && (r <= 1);
       
        int n = 10000;
        int ps = 0;
        int qs = 0;
        int rs = 0;
       
        for (int i=0; i<n; ++i) {
            double x = random(); // [0.0,1.0)
            if (x < p) {
                ++ps;
            } else if (x < p + q) {
                ++qs;
            } else {
                ++rs;
            }
        }
       
        assert abs(ps-n*p) < 6*sqrt(n*p*(1-p));
        assert abs(qs-n*q) < 6*sqrt(n*q*(1-q));
        assert abs(rs-n*r) < 6*sqrt(n*r*(1-r));
    }

    void testStringValues() {
        String name = "Big Daddy"; // 大爸爸 in english
        String hello = "你好"; // hello in chinese

        assert name.equals("Big Daddy");
        assert hello.equals("你好");

        // use .length() to see how many codepoints (roughly)
        // are in the string

        assert name.length() == 9;
        assert hello.length() == 2;

        // use .charAt() to get to a specific letter
        assert hello.charAt(0) == '你';
        assert name.charAt(4) == 'D';

        // concatenation (gluing strings together)
        assert ("你好"+", "+"Big Daddy").equals("你好, Big Daddy");

        // substrings (taking strings apart)
        assert "Big Daddy".substring(4,7).equals("Dad");
        assert "Big Daddy".substring(4).equals("Daddy");

        // formating numbers as strings

        assert String.format("%1.4f",PI).equals("3.1416");
        assert String.format("%d",1024).equals("1024");
        assert String.format("%04x",15).equals("000f");
        assert String.format("%04x",65535).equals("ffff");

    }

    void testStringInput() {
        try (Close in = inProvide("你好")) {
            assert readLine().equals("你好");
        }
        try (Close in = inProvide("Big Daddy")) {
            assert readLine().equals("Big Daddy");
        }
    }
    
    void testStringOutput() {
        try (Close out = outExpect("你好")) { println("你好"); }
        try (Close out = outExpect("Big Daddy")) { println("Big Daddy"); }
    }

}
