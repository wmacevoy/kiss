package kiss.util;

import static kiss.API.*;

public class As {
    public static final boolean asBoolean(boolean x) { return x; }
    public static final boolean asBoolean(char x) { return x != 0; }
    public static final boolean asBoolean(CharSequence x) {
        if (x == null || x.length() == 0 || x.equals("false")) {
            return false;
        }
        return true;
    }
    public static final boolean asBoolean(byte x) { return x != 0; }
    public static final boolean asBoolean(short x) { return x != 0; }
    public static final boolean asBoolean(int x) { return x != 0; }
    public static final boolean asBoolean(long x) { return x != 0; }
    public static final boolean asBoolean(float x) { return x != 0; }
    public static final boolean asBoolean(double x) { return x != 0; }

    public static final boolean asBoolean(Number x) {
	return !((x.longValue() == 0 && x.doubleValue() == 0));
    }

    public static final boolean asBoolean(Object x) {    
	if (x instanceof Boolean) return (boolean) x;
	if (x instanceof Character) return asBoolean((Character) x);
	if (x instanceof Number) return asBoolean((Number) x);
	if (x instanceof CharSequence) return asBoolean((CharSequence) x);
	throw new ClassCastException("cannot convert to boolean");
    }
    
    public static final char asChar(boolean x) {
        return x ? '\u0001' : '\u0000';
    }
    public static final char asChar(char x) { return x; }
    public static final char asChar(CharSequence x) {
        return (x == null || x.length() == 0) ? 0 : x.charAt(0);
    }

    public static final char asChar(byte x) { return (char) x; }
    public static final char asChar(short x) { return (char) x; }
    public static final char asChar(int x) { return (char) x; }
    public static final char asChar(long x) { return (char) x; }
    public static final char asChar(float x) { return (char) x; }
    public static final char asChar(double x) { return (char) x; }
    public static final char asChar(Number x) {
	return (char) (x.shortValue());
    }
    public static final char asChar(Object x) {
	if (x instanceof Character) return (char) x;
	if (x instanceof CharSequence) return asChar((CharSequence) x);
	if (x instanceof Number) return asChar((Number) x);
	if (x instanceof Boolean) return asChar((boolean) (Boolean) x);
	throw new ClassCastException("cannot convert to char");
    }    

    public static final byte asByte(boolean x) { return (byte) (x ? 1 : 0); }
    public static final byte asByte(char x) { return (byte) x; }
    public static final byte asByte(CharSequence x) {
        x = remove(x,'_');
        if (x != null && x.length()>=2
            && x.charAt(0)=='0' && x.charAt(1)=='x') {
            return Byte.parseByte(x.subSequence(2,x.length()).toString(),16);
        }
        if (x != null && x.length()>=2
            && x.charAt(0)=='0' && x.charAt(1)=='b') {
            return Byte.parseByte(x.subSequence(2,x.length()).toString(),2);
        }
        return Byte.parseByte(x.toString());
    }
    public static final byte asByte(byte x) { return x; }
    public static final byte asByte(short x) { return (byte) x; }
    public static final byte asByte(int x) { return (byte) x; }
    public static final byte asByte(long x) { return (byte) x; }
    public static final byte asByte(float x) { return (byte) x; }
    public static final byte asByte(double x) { return (byte) x; }
    public static final byte asByte(Number x) { return x.byteValue(); }
    public static final byte asByte(Object x) {
	if (x instanceof Number) return ((Number) x).byteValue();
	if (x instanceof Boolean) return asByte((Boolean) x);
	if (x instanceof Character) return asByte((Character) x);
	if (x instanceof CharSequence) return asByte((CharSequence) x);
	throw new ClassCastException("cannot convert to byte");
    }

    public static final short asShort(boolean x) { return (short) (x ? 1 : 0); }
    public static final short asShort(char x) { return (short) x; }
    public static final short asShort(CharSequence x) {
        x = remove(x,'_');
        if (x != null && x.length()>=2
            && x.charAt(0)=='0' && x.charAt(1)=='x') {
            return Short.parseShort(x.subSequence(2,x.length()).toString(),16);
        }
        if (x != null && x.length()>=2
            && x.charAt(0)=='0' && x.charAt(1)=='b') {
            return Short.parseShort(x.subSequence(2,x.length()).toString(),2);
        }
        return Short.parseShort(x.toString());

    }
    public static final short asShort(byte x) { return (short) x; }
    public static final short asShort(short x) { return x; }
    public static final short asShort(int x) { return (short) x; }
    public static final short asShort(long x) { return (short) x; }
    public static final short asShort(float x) { return (short) x; }
    public static final short asShort(double x) { return (short) x; }
    public static final short asShort(Number x) { return x.shortValue(); }
    public static final short asShort(Object x) {    
	if (x instanceof Number) return ((Number) x).shortValue();
	if (x instanceof Boolean) return asShort((Boolean) x);
	if (x instanceof Character) return asShort((Character) x);
	if (x instanceof CharSequence) return asShort((CharSequence) x);
	throw new ClassCastException("cannot convert to short");
    }

    public static final int asInt(boolean x) { return x ? 1 : 0; }
    public static final int asInt(char x) { return (int) x; }
    public static final int asInt(CharSequence x) {
        x = remove(x,'_');
        if (x != null && x.length()>=2
            && x.charAt(0)=='0' && x.charAt(1)=='x') {
            return Integer.parseInt(x.subSequence(2,x.length()).toString(),16);
        }
        if (x != null && x.length()>=2
            && x.charAt(0)=='0' && x.charAt(1)=='b') {
            return Integer.parseInt(x.subSequence(2,x.length()).toString(),2);
        }
        return Integer.parseInt(x.toString());
    }

    public static final int asInt(byte x) { return (int) x; }
    public static final int asInt(short x) { return (int) x; }
    public static final int asInt(int x) { return x; }
    public static final int asInt(long x) { return (int) x; }
    public static final int asInt(float x) { return (int) x; }
    public static final int asInt(double x) { return (int) x; }
    public static final int asInt(Number x) { return x.intValue(); }
    public static final int asInt(Object x) {    
	if (x instanceof Number) return ((Number) x).intValue();
	if (x instanceof Boolean) return asInt((Boolean) x);
	if (x instanceof Character) return asInt((Character) x);
	if (x instanceof CharSequence) return asInt((CharSequence) x);
	throw new ClassCastException("cannot convert to int");
    }


    public static final long asLong(boolean x) { return x ? 1 : 0; }
    public static final long asLong(char x) { return (long) x; }

    private static CharSequence remove(CharSequence x, char c) {
        if (x != null) {
            for (int i=0; i<x.length(); ++i) {
                if (x.charAt(i) == c) {
                    StringBuilder sb = new StringBuilder(x.length());
                    sb.append(x.subSequence(0,i).toString());
                    int j = ++i;
                    while (i < x.length()) {
                        while (i < x.length() && x.charAt(i) != c) { ++i; }
                        sb.append(x.subSequence(j,i).toString());
                        j=++i;
                    }
                    x=sb;
                    break;
                }
            }
        }
        return x;
    }
    public static final long asLong(CharSequence x) {
        x = remove(x,'_');
        if (x != null && x.length()>=2
            && x.charAt(0)=='0' && x.charAt(1)=='x') {
            return Long.parseLong(x.subSequence(2,x.length()).toString(),16);
        }
        if (x != null && x.length()>=2
            && x.charAt(0)=='0' && x.charAt(1)=='b') {
            return Long.parseLong(x.subSequence(2,x.length()).toString(),2);
        }
        return Long.parseLong(x.toString());
    }

    public static final long asLong(byte x) { return (long) x; }
    public static final long asLong(short x) { return (long) x; }
    public static final long asLong(int x) { return (long) x; }
    public static final long asLong(long x) { return x; }
    public static final long asLong(float x) { return (long) x; }
    public static final long asLong(double x) { return (long) x; }
    public static final long asLong(Number x) { return x.longValue(); }
    public static final long asLong(Object x) {    
	if (x instanceof Number) return ((Number) x).longValue();
	if (x instanceof Boolean) return asLong((Boolean) x);
	if (x instanceof Character) return asLong((Character) x);
	if (x instanceof CharSequence) return asLong((CharSequence) x);
	throw new ClassCastException("cannot convert to long");
    }


    public static final float asFloat(boolean x) { return x ? 1 : 0; }
    public static final float asFloat(char x) { return (float) x; }
    public static final float asFloat(CharSequence x) {
        return Float.parseFloat(remove(x,'_').toString());
    }

    public static final float asFloat(byte x) { return (float) x; }
    public static final float asFloat(short x) { return (float) x; }
    public static final float asFloat(int x) { return (float) x; }
    public static final float asFloat(long x) { return (float) x; }
    public static final float asFloat(float x) { return x; }
    public static final float asFloat(double x) { return (float) x; }
    public static final float asFloat(Number x) { return x.floatValue(); }
    public static final float asFloat(Object x) {    
	if (x instanceof Number) return ((Number) x).floatValue();
	if (x instanceof Boolean) return asFloat((Boolean) x);
	if (x instanceof Character) return asFloat((Character) x);
	if (x instanceof CharSequence) return asFloat((CharSequence) x);
	throw new ClassCastException("cannot convert to float");
    }


    public static final double asDouble(boolean x) { return x ? 1 : 0; }
    public static final double asDouble(char x) { return (double) x; }
    public static final double asDouble(CharSequence x) {
        return Double.parseDouble(remove(x,'_').toString());
    }

    public static final double asDouble(byte x) { return (double) x; }
    public static final double asDouble(short x) { return (double) x; }
    public static final double asDouble(int x) { return (double) x; }
    public static final double asDouble(long x) { return (double) x; }
    public static final double asDouble(float x) { return (double) x; }
    public static final double asDouble(double x) { return x; }
    public static final double asDouble(Number x) { return x.doubleValue(); }
    public static final double asDouble(Object x) {    
	if (x instanceof Number) return ((Number) x).doubleValue();
	if (x instanceof Boolean) return asDouble((Boolean) x);
	if (x instanceof Character) return asDouble((Character) x);
	if (x instanceof CharSequence) return asDouble((CharSequence) x);
	throw new ClassCastException("cannot convert to double");
    }

    public static final String asString(boolean x) { return String.valueOf(x); }
    public static final String asString(boolean x, CharSequence fmt) {
	return String.format(fmt.toString(),x);
    }

    public static final String asString(char x) { return String.valueOf(x); }
    public static final String asString(char x, CharSequence fmt) {
	return String.format(fmt.toString(),x);
    }
    public static final String asString(byte x) { return String.valueOf(x); }
    public static final String asString(byte x, CharSequence fmt) {
	return String.format(fmt.toString(),x);
    }

    public static final String asString(short x) { return String.valueOf(x); }
    public static final String asString(short x, CharSequence fmt) {
	return String.format(fmt.toString(),x);
    }
    public static final String asString(int x) { return String.valueOf(x); }
    public static final String asString(int x, CharSequence fmt) {
	return String.format(fmt.toString(),x);
    }

    public static final String asString(String x) { return x; }
    public static final String asString(String x, CharSequence fmt) {
	return String.format(fmt.toString(),x);
    }
    public static final String asString(float x) { return String.valueOf(x); }
    public static final String asString(float x, CharSequence fmt) {
	return String.format(fmt.toString(),x);
    }
    public static final String asString(double x) { return String.valueOf(x); }
    public static final String asString(double x, CharSequence fmt) {
	return String.format(fmt.toString(),x);
    }
    public static final String asString(Object x) { return IO.format(x); }
    public static final String asString(Object x, CharSequence fmt) {
	return String.format(fmt.toString(),x);
    }
}
