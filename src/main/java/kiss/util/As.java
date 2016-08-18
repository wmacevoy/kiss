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
        
    public static final byte asByte(boolean x) { return (byte) (x ? 1 : 0); }
    public static final byte asByte(char x) { return (byte) x; }
    public static final byte asByte(CharSequence x) {
        return (byte) asLong(x);
    }
    public static final byte asByte(byte x) { return x; }
    public static final byte asByte(short x) { return (byte) x; }    
    public static final byte asByte(int x) { return (byte) x; }
    public static final byte asByte(long x) { return (byte) x; }
    public static final byte asByte(float x) { return (byte) x; }
    public static final byte asByte(double x) { return (byte) x; }

    public static final short asShort(boolean x) { return (short) (x ? 1 : 0); }
    public static final short asShort(char x) { return (short) x; }
    public static final short asShort(CharSequence x) {
        return (short) asLong(x);
    }
    public static final short asShort(byte x) { return (short) x; }    
    public static final short asShort(short x) { return x; }
    public static final short asShort(int x) { return (short) x; }
    public static final short asShort(long x) { return (short) x; }
    public static final short asShort(float x) { return (short) x; }
    public static final short asShort(double x) { return (short) x; }

    public static final int asInt(boolean x) { return x ? 1 : 0; }
    public static final int asInt(char x) { return (int) x; }
    public static final int asInt(CharSequence x) { return (int) asLong(x); }

    public static final int asInt(byte x) { return (int) x; }    
    public static final int asInt(short x) { return (int) x; }
    public static final int asInt(int x) { return x; }
    public static final int asInt(long x) { return (int) x; }
    public static final int asInt(float x) { return (int) x; }
    public static final int asInt(double x) { return (int) x; }

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
    
    public static final String asString(boolean x) { return String.valueOf(x); }
    public static final String asString(char x) { return String.valueOf(x); }
    public static final String asString(byte x) { return String.valueOf(x); } 
    public static final String asString(short x) { return String.valueOf(x); }
    public static final String asString(int x) { return String.valueOf(x); }
    public static final String asString(String x) { return x; }
    public static final String asString(float x) { return String.valueOf(x); }
    public static final String asString(double x) { return String.valueOf(x); }
    public static final String asString(Object x) { return IO.format(x); }
}
