package kiss;

import java.io.Closeable;
import java.io.File;

import kiss.util.IO;
import kiss.util.RNG;
import kiss.util.Run;

public class API {
        public static Closeable outVerify(String filename) {
		return IO.outVerify(filename);
	}

	public static Closeable outOpen(String filename) {
		return IO.outOpen(filename);
	}

	public static Closeable outOpen(File file) {
		return IO.outOpen(file);
	}

	public static void outClose() {
		IO.outClose();
	}

	public static Closeable inOpen(File file) {
		return IO.inOpen(file);
	}

	public static Closeable inOpen(String filename) {
		return IO.inOpen(filename);
	}

	public static void inClose() {
		IO.inClose();
	}

	public static void format(StringBuilder ans, boolean value) {
		IO.format(ans, value);
	}

	public static void format(StringBuilder ans, byte value) {
		IO.format(ans, value);
	}

	public static void format(StringBuilder ans, char value) {
		IO.format(ans, value);
	}

	public static void format(StringBuilder ans, short value) {
		IO.format(ans, value);
	}

	public static void format(StringBuilder ans, int value) {
		IO.format(ans, value);
	}

	public static void format(StringBuilder ans, long value) {
		IO.format(ans, value);
	}

	public static void format(StringBuilder ans, float value) {
		IO.format(ans, value);
	}

	public static void format(StringBuilder ans, double value) {
		IO.format(ans, value);
	}

	public static void format(StringBuilder ans, Object value) {
		IO.format(ans, value);
	}

	public static String format(boolean value) {
		return IO.format(value);
	}

	public static String format(byte value) {
		return IO.format(value);
	}

	public static String format(char value) {
		return IO.format(value);
	}

	public static String format(short value) {
		return IO.format(value);
	}

	public static String format(int value) {
		return IO.format(value);
	}

	public static String format(long value) {
		return IO.format(value);
	}

	public static String format(float value) {
		return IO.format(value);
	}

	public static String format(double value) {
		return IO.format(value);
	}

	public static String format(Object value) {
		return IO.format(value);
	}

	public static void print(Object value) {
		IO.print(value);
	}

	public static void print(boolean value) {
		IO.print(value);
	}

	public static void print(byte value) {
		IO.print(value);
	}

	public static void print(char value) {
		IO.print(value);
	}

	public static void print(short value) {
		IO.print(value);
	}

	public static void print(int value) {
		IO.print(value);
	}

	public static void print(long value) {
		IO.print(value);
	}

	public static void print(float value) {
		IO.print(value);
	}

	public static void print(double value) {
		IO.print(value);
	}

	public static void println(Object value) {
		IO.println(value);
	}

	public static void println(boolean value) {
		IO.println(value);
	}

	public static void println(byte value) {
		IO.println(value);
	}

	public static void println(char value) {
		IO.println(value);
	}

	public static void println(short value) {
		IO.println(value);
	}

	public static void println(int value) {
		IO.println(value);
	}

	public static void println(long value) {
		IO.println(value);
	}

	public static void println(float value) {
		IO.println(value);
	}

	public static void println(double value) {
		IO.println(value);
	}

	public static void print(Object... value) {
            IO.printva(value);
	}

	public static void println(Object... value) {
            IO.printlnva(value);
	}
    
	public static String readLine() {
		return IO.readLine();
	}

	public static Boolean readBoolean() {
		return IO.readBoolean();
	}

	public static Byte readByte() {
		return IO.readByte();
	}

	public static Integer readInteger() {
		return IO.readInteger();
	}

	public static Long readLong() {
		return IO.readLong();
	}

	public static Float readFloat() {
		return IO.readFloat();
	}

	public static Double readDouble() {
		return IO.readDouble();
	}

        public static void seed() {
            RNG.seed();
        }

        public static void seed(double value) {
            RNG.seed(value);
        }

	public static int random(int a, int b) {
		return RNG.random(a, b);
	}

	public static double random() {
		return RNG.random();
	}

        public static String[] APP_ARGS; // updated by Run.main
        public static String APP_NAME; // updated by Run.main    
        public static Object APP; // updated by Run.main

        public static void sleep(double duration) {
		Run.sleep(duration);
	}

        public static double time() {
               return Run.time();
        }

	public static final double E = Math.E;
	public static final double PI = Math.PI;

	public static double abs(double a) {
		return Math.abs(a);
	}

	public static float abs(float a) {
		return Math.abs(a);
	}

	public static int abs(int a) {
		return Math.abs(a);
	}

	public static long abs(long a) {
		return Math.abs(a);
	}

	public static double acos(double a) {
		return Math.acos(a);
	}

	public static double asin(double a) {
		return Math.asin(a);
	}

	public static double atan(double a) {
		return Math.atan(a);
	}

	public static double atan2(double y, double x) {
		return Math.atan2(y, x);
	}

	public static double cbrt(double a) {
		return Math.cbrt(a);
	}

	public static double ceil(double a) {
		return Math.ceil(a);
	}

	public static double copySign(double magnitude, double sign) {
        return Math.copySign(magnitude,sign);
    }

	public static float copySign(float magnitude, float sign) {
		return Math.copySign(magnitude, sign);
	}

	public static double cos(double a) {
		return Math.cos(a);
	}

	public static double cosh(double x) {
		return Math.cosh(x);
	}

	public static double exp(double a) {
		return Math.exp(a);
	}

	public static double expm1(double x) {
		return Math.expm1(x);
	}

	public static double floor(double a) {
		return Math.floor(a);
	}

	public static int getExponent(double d) {
		return Math.getExponent(d);
	}

	public static int getExponent(float f) {
		return Math.getExponent(f);
	}

	public static double hypot(double x, double y) {
		return Math.hypot(x, y);
	}

	public static double IEEEremainder(double f1, double f2) {
        return Math.IEEEremainder(f1,f2);
    }

	public static double log(double a) {
		return Math.log(a);
	}

	public static double log10(double a) {
		return Math.log10(a);
	}

	public static double log1p(double x) {
		return Math.log1p(x);
	}

	public static double max(double a, double b) {
		return Math.max(a, b);
	}

	public static float max(float a, float b) {
		return Math.max(a, b);
	}

	public static int max(int a, int b) {
		return Math.max(a, b);
	}

	public static long max(long a, long b) {
		return Math.max(a, b);
	}

	public static double min(double a, double b) {
		return Math.min(a, b);
	}

	public static float min(float a, float b) {
		return Math.min(a, b);
	}

	public static int min(int a, int b) {
		return Math.min(a, b);
	}

	public static long min(long a, long b) {
		return Math.min(a, b);
	}

	public static double nextAfter(double start, double direction) {
		return Math.nextAfter(start, direction);
	}

	public static float nextAfter(float start, double direction) {
		return Math.nextAfter(start, direction);
	}

	public static double nextUp(double d) {
		return Math.nextUp(d);
	}

	public static float nextUp(float f) {
		return Math.nextUp(f);
	}

	public static double pow(double a, double b) {
		return Math.pow(a, b);
	}

	// public static double random() { return Math.random(); }
	public static double rint(double a) {
		return Math.rint(a);
	}

	public static long round(double a) {
		return Math.round(a);
	}

	public static int round(float a) {
		return Math.round(a);
	}

	public static double scalb(double d, int scaleFactor) {
		return Math.scalb(d, scaleFactor);
	}

	public static float scalb(float f, int scaleFactor) {
		return Math.scalb(f, scaleFactor);
	}

	public static double signum(double d) {
		return Math.signum(d);
	}

	public static float signum(float f) {
		return Math.signum(f);
	}

	public static double sin(double a) {
		return Math.sin(a);
	}

	public static double sinh(double x) {
		return Math.sinh(x);
	}

	public static double sqrt(double a) {
		return Math.sqrt(a);
	}

	public static double tan(double a) {
		return Math.tan(a);
	}

	public static double tanh(double x) {
		return Math.tanh(x);
	}

	public static double toDegrees(double angrad) {
		return Math.toDegrees(angrad);
	}

	public static double toRadians(double angdeg) {
		return Math.toRadians(angdeg);
	}

	public static double ulp(double d) {
		return Math.ulp(d);
	}

	public static float ulp(float f) {
		return Math.ulp(f);
	}
}
