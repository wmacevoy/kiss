class TestTo {
    void testToBoolean() {
        assert toBoolean(true) == true;
        assert toBoolean(false) == false;
        assert toBoolean('\u0000') == false;
        assert toBoolean('\u1234') == true;
        assert toBoolean((String) null) == false;
        assert toBoolean("") == false;
        assert toBoolean("false") == false;
        assert toBoolean("anything else") == true;
        assert toBoolean(((short) 0)) == false;
        assert toBoolean(((short) 22)) == true;
        assert toBoolean(((int) 0)) == false;
        assert toBoolean(((int) 22)) == true;
        assert toBoolean(((long) 0)) == false;
        assert toBoolean(((long) 22)) == true;
        assert toBoolean(((float) 0)) == false;
        assert toBoolean(((float) 22)) == true;
        assert toBoolean(((double) 0)) == false;
        assert toBoolean(((double) 22)) == true;
    }

    void testToChar() {
        assert toChar(false) == '\u0000';
        assert toChar(true) == '\u0001';
        assert toChar("words") == 'w';
        assert toChar("") == '\u0000';
        assert toChar((String) null) == '\u0000';
        assert toChar(PI) == (char) PI;
    }

    void testToShort() {
        assert toShort(false) == 0;
        assert toShort(true) == 1;
        assert toShort("10") == 10;
        assert toShort("0x10") == 16;
        assert toShort("0b10") == 2;
        assert toShort("1_000_000") == 1_000_000;
        assert toShort(PI) == (short) PI;
    }

}
