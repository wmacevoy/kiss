package kiss.util;

import static kiss.API.*;

class TestTo {
    void testToBoolean() {
        assert To.toBoolean(true) == true;
        assert To.toBoolean(false) == false;
        assert To.toBoolean('\u0000') == false;
        assert To.toBoolean('\u1234') == true;
        assert To.toBoolean((String) null) == false;
        assert To.toBoolean("") == false;
        assert To.toBoolean("false") == false;
        assert To.toBoolean("anything else") == true;
        assert To.toBoolean(((short) 0)) == false;
        assert To.toBoolean(((short) 22)) == true;
        assert To.toBoolean(((int) 0)) == false;
        assert To.toBoolean(((int) 22)) == true;
        assert To.toBoolean(((long) 0)) == false;
        assert To.toBoolean(((long) 22)) == true;
        assert To.toBoolean(((float) 0)) == false;
        assert To.toBoolean(((float) 22)) == true;
        assert To.toBoolean(((double) 0)) == false;
        assert To.toBoolean(((double) 22)) == true;
    }

    void testToChar() {
        assert To.toChar(false) == '\u0000';
        assert To.toChar(true) == '\u0001';
        assert To.toChar("words") == 'w';
        assert To.toChar("") == '\u0000';
        assert To.toChar((String) null) == '\u0000';
        assert To.toChar(PI) == (char) PI;
    }

    void testToShort() {
        assert To.toShort(false) == 0;
        assert To.toShort(true) == 1;
        println(To.toShort("10"));
        assert To.toShort("10") == 10;
        assert To.toShort("0x10") == 16;
        assert To.toShort("0b10") == 2;
        assert To.toShort("12_345") == 12_345;
        assert To.toShort(PI) == (short) PI;
    }

    void testToDouble() {
        assert To.toDouble("1_234.456_789") == 1_234.456_789;
    }

    void testToString() {
        assert To.toString(false).equals("false");
        assert To.toString(true).equals("true");
        assert To.toString(0xFF).equals("255");
    }

    void testREADME() {
        assert toDouble(3)/toDouble(2) == 1.5;
        assert toInt("0xff_ff") == 0xff_ff;
        assert To.toString('a').equals("a");
        assert toChar("a") == 'a';
        assert toBoolean(0) == false;
        assert toBoolean(1) == true;
    }
}
