package kiss.util;

import static kiss.API.*;

public class To {
    public static final boolean toBoolean(boolean x) { return x; }
    public static final boolean toBoolean(char x) { return x != 0; }
    public static final boolean toBoolean(CharSequence x) {
        if (x == null || x.length() == 0 || x.equals("false")) {
            return false;
        }
        return true;
    }
    public static final boolean toBoolean(byte x) { return x != 0; }    
    public static final boolean toBoolean(short x) { return x != 0; }
    public static final boolean toBoolean(int x) { return x != 0; }
    public static final boolean toBoolean(long x) { return x != 0; }
    public static final boolean toBoolean(float x) { return x != 0; }
    public static final boolean toBoolean(double x) { return x != 0; }

    public static final char toChar(boolean x) {
        return x ? '\u0001' : '\u0000';
    }
    public static final char toChar(char x) { return x; }
    public static final char toChar(CharSequence x) {
        return (x == null || x.length() == 0) ? 0 : x.charAt(0);
    }

    public static final char toChar(byte x) { return (char) x; }    
    public static final char toChar(short x) { return (char) x; }
    public static final char toChar(int x) { return (char) x; }
    public static final char toChar(long x) { return (char) x; }
    public static final char toChar(float x) { return (char) x; }
    public static final char toChar(double x) { return (char) x; }
        
    public static final byte toByte(boolean x) { return (byte) (x ? 1 : 0); }
    public static final byte toByte(char x) { return (byte) x; }
    public static final byte toByte(CharSequence x) {
        return (byte) toLong(x);
    }
    public static final byte toByte(byte x) { return x; }
    public static final byte toByte(short x) { return (byte) x; }    
    public static final byte toByte(int x) { return (byte) x; }
    public static final byte toByte(long x) { return (byte) x; }
    public static final byte toByte(float x) { return (byte) x; }
    public static final byte toByte(double x) { return (byte) x; }

    public static final short toShort(boolean x) { return (short) (x ? 1 : 0); }
    public static final short toShort(char x) { return (short) x; }
    public static final short toShort(CharSequence x) {
        return (short) toLong(x);
    }
    public static final short toShort(byte x) { return (short) x; }    
    public static final short toShort(short x) { return x; }
    public static final short toShort(int x) { return (short) x; }
    public static final short toShort(long x) { return (short) x; }
    public static final short toShort(float x) { return (short) x; }
    public static final short toShort(double x) { return (short) x; }

    public static final int toInt(boolean x) { return x ? 1 : 0; }
    public static final int toInt(char x) { return (int) x; }
    public static final int toInt(CharSequence x) { return (int) toLong(x); }

    public static final int toInt(byte x) { return (byte) x; }    
    public static final int toInt(short x) { return (int) x; }
    public static final int toInt(int x) { return x; }
    public static final int toInt(long x) { return (int) x; }
    public static final int toInt(float x) { return (int) x; }
    public static final int toInt(double x) { return (int) x; }

    public static final long toLong(boolean x) { return x ? 1 : 0; }
    public static final long toLong(char x) { return (long) x; }

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
    public static final long toLong(CharSequence x) {
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

    public static final long toLong(byte x) { return (byte) x; }    
    public static final long toLong(short x) { return (long) x; }
    public static final long toLong(int x) { return (long) x; }
    public static final long toLong(long x) { return x; }
    public static final long toLong(float x) { return (long) x; }
    public static final long toLong(double x) { return (long) x; }

    public static final float toFloat(boolean x) { return x ? 1 : 0; }
    public static final float toFloat(char x) { return (float) x; }
    public static final float toFloat(CharSequence x) {
        return Float.parseFloat(remove(x,'_').toString());
    }

    public static final float toFloat(byte x) { return (byte) x; }    
    public static final float toFloat(short x) { return (float) x; }
    public static final float toFloat(int x) { return (float) x; }
    public static final float toFloat(long x) { return (float) x; }
    public static final float toFloat(float x) { return x; }
    public static final float toFloat(double x) { return (float) x; }


    public static final double toDouble(boolean x) { return x ? 1 : 0; }
    public static final double toDouble(char x) { return (double) x; }
    public static final double toDouble(CharSequence x) {
        return Double.parseDouble(remove(x,'_').toString());
    }

    public static final double toDouble(byte x) { return (byte) x; }    
    public static final double toDouble(short x) { return (double) x; }
    public static final double toDouble(int x) { return (double) x; }
    public static final double toDouble(long x) { return (double) x; }
    public static final double toDouble(float x) { return (double) x; }
    public static final double toDouble(double x) { return x; }
    
    public static final String toString(boolean x) { return String.valueOf(x); }
    public static final String toString(char x) { return String.valueOf(x); }
    public static final String toString(byte x) { return String.valueOf(x); } 
    public static final String toString(short x) { return String.valueOf(x); }
    public static final String toString(int x) { return String.valueOf(x); }
    public static final String toString(String x) { return x; }
    public static final String toString(float x) { return String.valueOf(x); }
    public static final String toString(double x) { return String.valueOf(x); }
    public static final String toString(Object x) { return IO.format(x); }
}
