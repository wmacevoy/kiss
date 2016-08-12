package kiss.util;

import static kiss.API.*;

class TestAs {
    void testAsBoolean() {
        assert As.asBoolean(true) == true;
        assert As.asBoolean(false) == false;
        assert As.asBoolean('\u0000') == false;
        assert As.asBoolean('\u1234') == true;
        assert As.asBoolean((String) null) == false;
        assert As.asBoolean("") == false;
        assert As.asBoolean("false") == false;
        assert As.asBoolean("anything else") == true;
        assert As.asBoolean(((short) 0)) == false;
        assert As.asBoolean(((short) 22)) == true;
        assert As.asBoolean(((int) 0)) == false;
        assert As.asBoolean(((int) 22)) == true;
        assert As.asBoolean(((long) 0)) == false;
        assert As.asBoolean(((long) 22)) == true;
        assert As.asBoolean(((float) 0)) == false;
        assert As.asBoolean(((float) 22)) == true;
        assert As.asBoolean(((double) 0)) == false;
        assert As.asBoolean(((double) 22)) == true;
    }

    void testAsChar() {
        assert As.asChar(false) == '\u0000';
        assert As.asChar(true) == '\u0001';
        assert As.asChar("words") == 'w';
        assert As.asChar("") == '\u0000';
        assert As.asChar((String) null) == '\u0000';
        assert As.asChar(PI) == (char) PI;
    }

    void testAsShort() {
        assert As.asShort(false) == 0;
        assert As.asShort(true) == 1;
        println(As.asShort("10"));
        assert As.asShort("10") == 10;
        assert As.asShort("0x10") == 16;
        assert As.asShort("0b10") == 2;
        assert As.asShort("12_345") == 12_345;
        assert As.asShort(PI) == (short) PI;
    }

    void testAsDouble() {
        assert As.asDouble("1_234.456_789") == 1_234.456_789;
    }

    void testAsString() {
        assert As.asString(false).equals("false");
        assert As.asString(true).equals("true");
        assert As.asString(0xFF).equals("255");
    }

    void testREADME() {
        assert asDouble(3)/asDouble(2) == 1.5;
        assert asInt("0xff_ff") == 0xff_ff;
        assert asString('a').equals("a");
        assert asChar("a") == 'a';
        assert asBoolean(0) == false;
        assert asBoolean(1) == true;
    }
}
