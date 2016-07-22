public

// For single-source projects; paste in this interface, and implement
// IKiss where you want to use it.  (This is a Java anti-pattern, but
// really is a poor-man's static import, which is a fine pattern, that
// cannot be done in single source file applications).

interface IKiss {
    static java.io.Closeable outVerify(String filename) {
        return util.IO.outVerify(filename);
    }

    static java.io.Closeable outOpen(String filename) {
        return util.IO.outOpen(filename);
    }

    static java.io.Closeable outOpen(java.io.File file) {
        return util.IO.outOpen(file);
    }

    static void outClose() {
        util.IO.outClose();
    }

    static java.io.Closeable inOpen(java.io.File file) {
        return util.IO.inOpen(file);
    }

    static java.io.Closeable inOpen(String filename) {
        return util.IO.inOpen(filename);
    }

    static void inClose() {
        util.IO.inClose();
    }

    static void format(StringBuilder ans, boolean value) {
        util.IO.format(ans, value);
    }

    static void format(StringBuilder ans, byte value) {
        util.IO.format(ans, value);
    }

    static void format(StringBuilder ans, char value) {
        util.IO.format(ans, value);
    }

    static void format(StringBuilder ans, short value) {
        util.IO.format(ans, value);
    }

    static void format(StringBuilder ans, int value) {
        util.IO.format(ans, value);
    }

    static void format(StringBuilder ans, long value) {
        util.IO.format(ans, value);
    }

    static void format(StringBuilder ans, float value) {
        util.IO.format(ans, value);
    }

    static void format(StringBuilder ans, double value) {
        util.IO.format(ans, value);
    }

    static void format(StringBuilder ans, Object value) {
        util.IO.format(ans, value);
    }

    static String format(boolean value) {
        return util.IO.format(value);
    }

    static String format(byte value) {
        return util.IO.format(value);
    }

    static String format(char value) {
        return util.IO.format(value);
    }

    static String format(short value) {
        return util.IO.format(value);
    }

    static String format(int value) {
        return util.IO.format(value);
    }

    static String format(long value) {
        return util.IO.format(value);
    }

    static String format(float value) {
        return util.IO.format(value);
    }

    static String format(double value) {
        return util.IO.format(value);
    }

    static String format(Object value) {
        return util.IO.format(value);
    }

    static void print(Object value) {
        util.IO.print(value);
    }

    static void print(boolean value) {
        util.IO.print(value);
    }

    static void print(byte value) {
        util.IO.print(value);
    }

    static void print(char value) {
        util.IO.print(value);
    }

    static void print(short value) {
        util.IO.print(value);
    }

    static void print(int value) {
        util.IO.print(value);
    }

    static void print(long value) {
        util.IO.print(value);
    }

    static void print(float value) {
        util.IO.print(value);
    }

    static void print(double value) {
        util.IO.print(value);
    }

    static void println(Object value) {
        util.IO.println(value);
    }

    static void println(boolean value) {
        util.IO.println(value);
    }

    static void println(byte value) {
        util.IO.println(value);
    }

    static void println(char value) {
        util.IO.println(value);
    }

    static void println(short value) {
        util.IO.println(value);
    }

    static void println(int value) {
        util.IO.println(value);
    }

    static void println(long value) {
        util.IO.println(value);
    }

    static void println(float value) {
        util.IO.println(value);
    }

    static void println(double value) {
        util.IO.println(value);
    }

    static void print(Object... value) {
        util.IO.printva(value);
    }

    static void println(Object... value) {
        util.IO.printlnva(value);
    }
    
    static String readLine() {
        return util.IO.readLine();
    }

    static Boolean readBoolean() {
        return util.IO.readBoolean();
    }

    static Byte readByte() {
        return util.IO.readByte();
    }

    static Integer readInteger() {
        return util.IO.readInteger();
    }

    static Long readLong() {
        return util.IO.readLong();
    }

    static Float readFloat() {
        return util.IO.readFloat();
    }

    static Double readDouble() {
        return util.IO.readDouble();
    }

    static void seed() {
        util.RNG.seed();
    }

    static void seed(long value) {
        util.RNG.seed(value);
    }

    static int random(int a, int b) {
        return util.RNG.random(a, b);
    }

    static int random(int n) {
        return util.RNG.random(n);
    }

    static double random() {
        return util.RNG.random();
    }

    static void sleep(int millis) {
        util.Run.sleep(millis);
    }

    static final double E = Math.E;
    static final double PI = Math.PI;

    static double abs(double a) {
        return Math.abs(a);
    }

    static float abs(float a) {
        return Math.abs(a);
    }

    static int abs(int a) {
        return Math.abs(a);
    }

    static long abs(long a) {
        return Math.abs(a);
    }

    static double acos(double a) {
        return Math.acos(a);
    }

    static double asin(double a) {
        return Math.asin(a);
    }

    static double atan(double a) {
        return Math.atan(a);
    }

    static double atan2(double y, double x) {
        return Math.atan2(y, x);
    }

    static double cbrt(double a) {
        return Math.cbrt(a);
    }

    static double ceil(double a) {
        return Math.ceil(a);
    }

    static double copySign(double magnitude, double sign) {
        return Math.copySign(magnitude,sign);
    }

    static float copySign(float magnitude, float sign) {
        return Math.copySign(magnitude, sign);
    }

    static double cos(double a) {
        return Math.cos(a);
    }

    static double cosh(double x) {
        return Math.cosh(x);
    }

    static double exp(double a) {
        return Math.exp(a);
    }

    static double expm1(double x) {
        return Math.expm1(x);
    }

    static double floor(double a) {
        return Math.floor(a);
    }

    static int getExponent(double d) {
        return Math.getExponent(d);
    }

    static int getExponent(float f) {
        return Math.getExponent(f);
    }

    static double hypot(double x, double y) {
        return Math.hypot(x, y);
    }

    static double IEEEremainder(double f1, double f2) {
        return Math.IEEEremainder(f1,f2);
    }

    static double log(double a) {
        return Math.log(a);
    }

    static double log10(double a) {
        return Math.log10(a);
    }

    static double log1p(double x) {
        return Math.log1p(x);
    }

    static double max(double a, double b) {
        return Math.max(a, b);
    }

    static float max(float a, float b) {
        return Math.max(a, b);
    }

    static int max(int a, int b) {
        return Math.max(a, b);
    }

    static long max(long a, long b) {
        return Math.max(a, b);
    }

    static double min(double a, double b) {
        return Math.min(a, b);
    }

    static float min(float a, float b) {
        return Math.min(a, b);
    }

    static int min(int a, int b) {
        return Math.min(a, b);
    }

    static long min(long a, long b) {
        return Math.min(a, b);
    }

    static double nextAfter(double start, double direction) {
        return Math.nextAfter(start, direction);
    }

    static float nextAfter(float start, double direction) {
        return Math.nextAfter(start, direction);
    }

    static double nextUp(double d) {
        return Math.nextUp(d);
    }

    static float nextUp(float f) {
        return Math.nextUp(f);
    }

    static double pow(double a, double b) {
        return Math.pow(a, b);
    }

    // static double random() { return Math.random(); }
    static double rint(double a) {
        return Math.rint(a);
    }

    static long round(double a) {
        return Math.round(a);
    }

    static int round(float a) {
        return Math.round(a);
    }

    static double scalb(double d, int scaleFactor) {
        return Math.scalb(d, scaleFactor);
    }

    static float scalb(float f, int scaleFactor) {
        return Math.scalb(f, scaleFactor);
    }

    static double signum(double d) {
        return Math.signum(d);
    }

    static float signum(float f) {
        return Math.signum(f);
    }

    static double sin(double a) {
        return Math.sin(a);
    }

    static double sinh(double x) {
        return Math.sinh(x);
    }

    static double sqrt(double a) {
        return Math.sqrt(a);
    }

    static double tan(double a) {
        return Math.tan(a);
    }

    static double tanh(double x) {
        return Math.tanh(x);
    }

    static double toDegrees(double angrad) {
        return Math.toDegrees(angrad);
    }

    static double toRadians(double angdeg) {
        return Math.toRadians(angdeg);
    }

    static double ulp(double d) {
        return Math.ulp(d);
    }

    static float ulp(float f) {
        return Math.ulp(f);
    }


    public static class util {


        public static class IO {
            static ThreadLocal<java.util.LinkedList<java.util.Scanner>> ins = new ThreadLocal<java.util.LinkedList<java.util.Scanner>>() {
                    @Override public java.util.LinkedList<java.util.Scanner> initialValue() {
                        java.util.LinkedList<java.util.Scanner> ans = new java.util.LinkedList<java.util.Scanner>();
                        ans.add(new java.util.Scanner(System.in));
                        return ans;
                    }
                };
    
            static ThreadLocal<java.util.LinkedList<java.io.PrintStream>> outs = new ThreadLocal<java.util.LinkedList<java.io.PrintStream>>() {
                    @Override public java.util.LinkedList<java.io.PrintStream> initialValue() {
                        java.util.LinkedList<java.io.PrintStream> ans = new java.util.LinkedList<java.io.PrintStream>();
                        ans.add(System.out);
                        return ans;
                    }
                };
    
            static class OutClose implements java.io.Closeable {

		@Override
		public void close() throws java.io.IOException {
                    outClose();
		} 
    	
            };
            static OutClose OUT_CLOSE = new OutClose();
    
            static class InClose implements java.io.Closeable {

		@Override
		public void close() throws java.io.IOException {
                    inClose();
		} 
    	
            }
            static InClose IN_CLOSE = new InClose();

            public static java.io.Closeable outVerify(String filename) {
                try {
                    outs.get().addLast(new java.io.PrintStream(new VerifyOutputStream(new java.io.FileInputStream(filename))));
                } catch (java.io.IOException ex) {
                    ex.printStackTrace();
                    return null;
                }
                return OUT_CLOSE;

            }
    
            public static java.io.Closeable outOpen(String filename) {
                return outOpen(new java.io.File(filename));
            }

            public static java.io.Closeable outOpen(java.io.File file) {
                try {
                    outs.get().addLast(new java.io.PrintStream(new java.io.FileOutputStream(file)));
                } catch (java.io.IOException ex) {
                    ex.printStackTrace();
                    return null;
                }
                return OUT_CLOSE;
            }
            public static void outClose() {
                outs.get().removeLast().close();
            }

            static java.io.PrintStream out() { return outs.get().getLast(); }
            static java.util.Scanner in() { return ins.get().getLast(); }

            public static java.io.Closeable inOpen(java.io.File file) {
                try {
                    ins.get().addLast(new java.util.Scanner(file));
		} catch (java.io.FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return null;
		}
                return IN_CLOSE;
            }
    
            public static java.io.Closeable inOpen(String filename) {
                return inOpen(new java.io.File(filename));
            }

            public static void inClose() {
                ins.get().removeLast().close();
            }

            public static void format(StringBuilder ans, boolean value) {
                ans.append(Boolean.toString(value));
            }

            public static void format(StringBuilder ans, byte value) {
                ans.append(Byte.toString(value));
            }

            public static void format(StringBuilder ans, char value) {
                ans.append(Character.toString(value));
            }

            public static void format(StringBuilder ans, short value) {
                ans.append(Short.toString(value));
            }

            public static void format(StringBuilder ans, int value) {
                ans.append(Integer.toString(value));
            }

            public static void format(StringBuilder ans, long value) {
                ans.append(Long.toString(value));
            }

            public static void format(StringBuilder ans, float value) {
                ans.append(Float.toString(value));
            }

            public static void format(StringBuilder ans, double value) {
                ans.append(Double.toString(value));
            }

            // format a (possibly nested) object
            public static void format(StringBuilder ans, Object object) {
                if (object.getClass().isArray()) {
                    if (object instanceof Object[]) {
                        Object[] array = (Object[]) object;
                        ans.append("[");
                        boolean first = true;
                        for (int i = 0; i < array.length; ++i) {
                            if (first) {
                                first = false;
                            } else {
                                ans.append(",");
                            }
                            format(ans, array[i]);
                        }
                        ans.append("]");
                    } else {
                        if (object instanceof boolean[]) {
                            ans.append(java.util.Arrays.toString((boolean[]) object));
                        } else if (object instanceof byte[]) {
                            ans.append(java.util.Arrays.toString((byte[]) object));
                        } else if (object instanceof char[]) {
                            ans.append(java.util.Arrays.toString((char[]) object));
                        } else if (object instanceof short[]) {
                            ans.append(java.util.Arrays.toString((short[]) object));
                        } else if (object instanceof int[]) {
                            ans.append(java.util.Arrays.toString((int[]) object));
                        } else if (object instanceof long[]) {
                            ans.append(java.util.Arrays.toString((long[]) object));
                        } else if (object instanceof float[]) {
                            ans.append(java.util.Arrays.toString((float[]) object));
                        } else if (object instanceof double[]) {
                            ans.append(java.util.Arrays.toString((double[]) object));
                        } else {
                            throw new IllegalArgumentException("Unknown array type (should not happen)");
                        }
                    }
                } else if (object instanceof java.util.Map) {
                    java.util.Set<java.util.Map.Entry> set = ((java.util.Map) object).entrySet();
                    ans.append("[");
                    boolean first = true;
                    for (java.util.Map.Entry entry : set) {
                        if (first)
                            first = false;
                        else
                            ans.append(",");
                        format(ans, entry.getKey());
                        ans.append(":");
                        format(ans, entry.getValue());
                    }
                    ans.append("]");
                } else if (object instanceof java.util.Collection) {
                    java.util.Collection collection = (java.util.Collection) object;
                    ans.append("[");
                    boolean first = true;
                    for (Object element : collection) {
                        if (first)
                            first = false;
                        else
                            ans.append(",");
                        format(ans, element);
                    }
                    ans.append("]");
                } else {
                    ans.append(object.toString());
                }
            }

            public static String format(boolean value) {
                return (Boolean.toString(value));
            }

            public static String format(byte value) {
                return (Byte.toString(value));
            }

            public static String format(char value) {
                return (Character.toString(value));
            }

            public static String format(short value) {
                return (Short.toString(value));
            }

            public static String format(int value) {
                return (Integer.toString(value));
            }

            public static String format(long value) {
                return (Long.toString(value));
            }

            public static String format(float value) {
                return (Float.toString(value));
            }

            public static String format(double value) {
                return (Double.toString(value));
            }

            public static String format(Object object) {
                StringBuilder ans = new StringBuilder();
                format(ans, object);
                return ans.toString();
            }

            public static void print(Object value) {
                out().print(format(value));
            }

            public static void print(boolean value) {
                out().print(value);

            }

            public static void print(byte value) {
                out().print(value);
            }

            public static void print(char value) {
                out().print(value);
            }

            public static void print(short value) {
                out().print(value);
            }

            public static void print(int value) {
                out().print(value);
            }

            public static void print(long value) {
                out().print(value);
            }

            public static void print(float value) {
                out().print(value);
            }

            public static void print(double value) {
                out().print(value);
            }

            public static void println(Object value) {
                out().println(format(value));
            }

            public static void println(boolean value) {
                out().println(value);
            }

            public static void println(byte value) {
                out().println(value);
            }

            public static void println(char value) {
                out().println(value);
            }

            public static void println(short value) {
                out().println(value);
            }

            public static void println(int value) {
                out().println(value);
            }

            public static void println(long value) {
                out().println(value);
            }

            public static void println(float value) {
                out().println(value);
            }

            public static void println(double value) {
                out().println(value);
            }

            static void printva(java.io.PrintStream ps, Object args[]) {
                for (int i=0; i<args.length; ++i) {
                    if (i>0) ps.print(",");
                    ps.print(format(args[i]));
                }
            }

            public static void printva(Object[] args) {
                java.io.PrintStream ps = out();
                printva(ps,args);
            }

            public static void printlnva(Object[] args) {
                java.io.PrintStream ps = out();
                printva(ps,args);
                ps.println();
            }
    
            public static String readLine() {
                java.util.Scanner in=in();
                if (in.hasNextLine()) {
                    String value = in.nextLine();
                    if (ins.get().size() > 1 || outs.get().size() > 1) println(value);
                    return value;
                } else {
                    return null;
                }
            }

            public static Boolean readBoolean() {
                java.util.Scanner in=in();
                if (in.hasNextBoolean()) {
                    boolean value = in.nextBoolean();
                    if (ins.get().size() > 1 || outs.get().size() > 1) println(value);
                    return value;
                } else {
                    return null;
                }
            }

            public static Byte readByte() {
                java.util.Scanner in=in();
                if (in.hasNextByte()) {
                    byte value = in.nextByte();
                    if (ins.get().size() > 1 || outs.get().size() > 1) println(value);
                    return value;
                } else {
                    return null;
                }
            }

            public static Integer readInteger() {
                java.util.Scanner in=in();
                if (in.hasNextInt()) {
                    int value = in.nextInt();
                    if (ins.get().size() > 1 || outs.get().size() > 1) println(value);
                    return value;
                } else {
                    return null;
                }
            }

            public static Long readLong() {
                java.util.Scanner in=in();
                if (in.hasNextLong()) {
                    long value = in.nextLong();
                    if (ins.get().size() > 1 || outs.get().size() > 1) println(value);
                    return value;
                } else {
                    return null;
                }
            }

            public static Float readFloat() {
                java.util.Scanner in=in();
                if (in.hasNextFloat()) {
                    float value = in.nextFloat();
                    if (ins.get().size() > 1 || outs.get().size() > 1) println(value);
                    return value;
                } else {
                    return null;
                }
            }

            public static Double readDouble() {
                java.util.Scanner in=in();
                if (in.hasNextDouble()) {
                    double value = in.nextDouble();
                    if (ins.get().size() > 1 || outs.get().size() > 1) println(value);
                    return value;
                } else {
                    return null;
                }
            }
        }

        public static class RNG {
            static ThreadLocal<MersenneTwisterFast> rng = new ThreadLocal<MersenneTwisterFast>() {
                    @Override
                    protected MersenneTwisterFast initialValue() {
			return new MersenneTwisterFast();
                    }
                };

            public static void seed() {
		seed(System.currentTimeMillis());
            }

            public static void seed(long value) {
		rng.get().setSeed(value);
            }

            // return a random integer between a and b (including ends)
            // if b <= a, just returns a
            public static int random(int a, int b) {
                if (a <= b) {
                    long n = ((long) b) - ((long) a) + 1;
                    return (int)
                        ((rng.get().nextLong() & Long.MAX_VALUE) % n + a);
                } else {
                    throw new IllegalArgumentException("random(" + a + "," + b + ") does not make sense.");
                }
            }

            // return a random integer between 1..n
            public static int random(int n) {
		return rng.get().nextInt(n) + 1;
            }

            // return a random real between in [0,1)
            public static double random() {
		return rng.get().nextDouble();
            }

        }

        // https://cs.gmu.edu/~sean/research/mersenne/MersenneTwisterFast.java
        // package modified && main() removed


        /**
         * <h3>MersenneTwister and MersenneTwisterFast</h3>
         * <p>
         * <b>Version 22</b>, based on version MT199937(99/10/29) of the Mersenne
         * Twister algorithm found at
         * <a href="http://www.math.keio.ac.jp/matumoto/emt.html"> The Mersenne Twister
         * Home Page</a>, with the initialization improved using the new 2002/1/26
         * initialization algorithm By Sean Luke, October 2004.
         * 
         * <p>
         * <b>MersenneTwister</b> is a drop-in subclass replacement for
         * java.util.Random. It is properly synchronized and can be used in a
         * multithreaded environment. On modern VMs such as HotSpot, it is approximately
         * 1/3 slower than java.util.Random.
         *
         * <p>
         * <b>MersenneTwisterFast</b> is not a subclass of java.util.Random. It has the
         * same public methods as Random does, however, and it is algorithmically
         * identical to MersenneTwister. MersenneTwisterFast has hard-code inlined all
         * of its methods directly, and made all of them final (well, the ones of
         * consequence anyway). Further, these methods are <i>not</i> synchronized, so
         * the same MersenneTwisterFast instance cannot be shared by multiple threads.
         * But all this helps MersenneTwisterFast achieve well over twice the speed of
         * MersenneTwister. java.util.Random is about 1/3 slower than
         * MersenneTwisterFast.
         *
         * <h3>About the Mersenne Twister</h3>
         * <p>
         * This is a Java version of the C-program for MT19937: Integer version. The
         * MT19937 algorithm was created by Makoto Matsumoto and Takuji Nishimura, who
         * ask: "When you use this, send an email to: matumoto@math.keio.ac.jp with an
         * appropriate reference to your work". Indicate that this is a translation of
         * their algorithm into Java.
         *
         * <p>
         * <b>Reference. </b> Makato Matsumoto and Takuji Nishimura, "Mersenne Twister:
         * A 623-Dimensionally Equidistributed Uniform Pseudo-Random Number Generator",
         * <i>ACM Transactions on Modeling and. Computer Simulation,</i> Vol. 8, No. 1,
         * January 1998, pp 3--30.
         *
         * <h3>About this Version</h3>
         *
         * <p>
         * <b>Changes since V21:</b> Minor documentation HTML fixes.
         *
         * <p>
         * <b>Changes since V20:</b> Added clearGuassian(). Modified stateEquals() to be
         * synchronizd on both objects for MersenneTwister, and changed its
         * documentation. Added synchronization to both setSeed() methods, to
         * writeState(), and to readState() in MersenneTwister. Removed synchronization
         * from readObject() in MersenneTwister.
         *
         * <p>
         * <b>Changes since V19:</b> nextFloat(boolean, boolean) now returns float, not
         * double.
         *
         * <p>
         * <b>Changes since V18:</b> Removed old final declarations, which used to
         * potentially speed up the code, but no longer.
         *
         * <p>
         * <b>Changes since V17:</b> Removed vestigial references to &amp;= 0xffffffff
         * which stemmed from the original C code. The C code could not guarantee that
         * ints were 32 bit, hence the masks. The vestigial references in the Java code
         * were likely optimized out anyway.
         *
         * <p>
         * <b>Changes since V16:</b> Added nextDouble(includeZero, includeOne) and
         * nextFloat(includeZero, includeOne) to allow for half-open, fully-closed, and
         * fully-open intervals.
         *
         * <p>
         * <b>Changes Since V15:</b> Added serialVersionUID to quiet compiler warnings
         * from Sun's overly verbose compilers as of JDK 1.5.
         *
         * <p>
         * <b>Changes Since V14:</b> made strictfp, with StrictMath.log and
         * StrictMath.sqrt in nextGaussian instead of Math.log and Math.sqrt. This is
         * largely just to be safe, as it presently makes no difference in the speed,
         * correctness, or results of the algorithm.
         *
         * <p>
         * <b>Changes Since V13:</b> clone() method CloneNotSupportedException removed.
         *
         * <p>
         * <b>Changes Since V12:</b> clone() method added.
         *
         * <p>
         * <b>Changes Since V11:</b> stateEquals(...) method added. MersenneTwisterFast
         * is equal to other MersenneTwisterFasts with identical state; likewise
         * MersenneTwister is equal to other MersenneTwister with identical state. This
         * isn't equals(...) because that requires a contract of immutability to compare
         * by value.
         *
         * <p>
         * <b>Changes Since V10:</b> A documentation error suggested that setSeed(int[])
         * required an int[] array 624 long. In fact, the array can be any non-zero
         * length. The new version also checks for this fact.
         *
         * <p>
         * <b>Changes Since V9:</b> readState(stream) and writeState(stream) provided.
         *
         * <p>
         * <b>Changes Since V8:</b> setSeed(int) was only using the first 28 bits of the
         * seed; it should have been 32 bits. For small-number seeds the behavior is
         * identical.
         *
         * <p>
         * <b>Changes Since V7:</b> A documentation error in MersenneTwisterFast (but
         * not MersenneTwister) stated that nextDouble selects uniformly from the
         * full-open interval [0,1]. It does not. nextDouble's contract is identical
         * across MersenneTwisterFast, MersenneTwister, and java.util.Random, namely,
         * selection in the half-open interval [0,1). That is, 1.0 should not be
         * returned. A similar contract exists in nextFloat.
         *
         * <p>
         * <b>Changes Since V6:</b> License has changed from LGPL to BSD. New timing
         * information to compare against java.util.Random. Recent versions of HotSpot
         * have helped Random increase in speed to the point where it is faster than
         * MersenneTwister but slower than MersenneTwisterFast (which should be the
         * case, as it's a less complex algorithm but is synchronized).
         * 
         * <p>
         * <b>Changes Since V5:</b> New empty constructor made to work the same as
         * java.util.Random -- namely, it seeds based on the current time in
         * milliseconds.
         *
         * <p>
         * <b>Changes Since V4:</b> New initialization algorithms. See (see
         * <a href="http://www.math.keio.ac.jp/matumoto/MT2002/emt19937ar.html"> http://
         * www.math.keio.ac.jp/matumoto/MT2002/emt19937ar.html</a>)
         *
         * <p>
         * The MersenneTwister code is based on standard MT19937 C/C++ code by Takuji
         * Nishimura, with suggestions from Topher Cooper and Marc Rieffel, July 1997.
         * The code was originally translated into Java by Michael Lecuyer, January
         * 1999, and the original code is Copyright (c) 1999 by Michael Lecuyer.
         *
         * <h3>Java notes</h3>
         * 
         * <p>
         * This implementation implements the bug fixes made in Java 1.2's version of
         * Random, which means it can be used with earlier versions of Java. See
         * <a href=
         * "http://www.javasoft.com/products/jdk/1.2/docs/api/java/util/Random.html">
         * the JDK 1.2 java.util.Random documentation</a> for further documentation on
         * the random-number generation contracts made. Additionally, there's an
         * undocumented bug in the JDK java.util.Random.nextBytes() method, which this
         * code fixes.
         *
         * <p>
         * Just like java.util.Random, this generator accepts a long seed but doesn't
         * use all of it. java.util.Random uses 48 bits. The Mersenne Twister instead
         * uses 32 bits (int size). So it's best if your seed does not exceed the int
         * range.
         *
         * <p>
         * MersenneTwister can be used reliably on JDK version 1.1.5 or above. Earlier
         * Java versions have serious bugs in java.util.Random; only MersenneTwisterFast
         * (and not MersenneTwister nor java.util.Random) should be used with them.
         *
         * <h3>License</h3>
         *
         * Copyright (c) 2003 by Sean Luke. <br>
         * Portions copyright (c) 1993 by Michael Lecuyer. <br>
         * All rights reserved. <br>
         *
         * <p>
         * Redistribution and use in source and binary forms, with or without
         * modification, are permitted provided that the following conditions are met:
         * <ul>
         * <li>Redistributions of source code must retain the above copyright notice,
         * this list of conditions and the following disclaimer.
         * <li>Redistributions in binary form must reproduce the above copyright notice,
         * this list of conditions and the following disclaimer in the documentation
         * and/or other materials provided with the distribution.
         * <li>Neither the name of the copyright owners, their employers, nor the names
         * of its contributors may be used to endorse or promote products derived from
         * this software without specific prior written permission.
         * </ul>
         * <p>
         * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
         * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
         * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
         * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNERS OR CONTRIBUTORS BE
         * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
         * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
         * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
         * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
         * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
         * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
         * POSSIBILITY OF SUCH DAMAGE.
         *
         * @version 22
         */

        // Note: this class is hard-inlined in all of its methods. This makes some of
        // the methods well-nigh unreadable in their complexity. In fact, the Mersenne
        // Twister is fairly easy code to understand: if you're trying to get a handle
        // on the code, I strongly suggest looking at MersenneTwister.java first.
        // -- Sean

        public static strictfp class MersenneTwisterFast implements java.io.Serializable, Cloneable {
            // Serialization
            private static final long serialVersionUID = -8219700664442619525L; // locked
            // as of
            // Version
            // 15

            // Period parameters
            private static final int N = 624;
            private static final int M = 397;
            private static final int MATRIX_A = 0x9908b0df; // private static final *
            // constant vector a
            private static final int UPPER_MASK = 0x80000000; // most significant w-r
            // bits
            private static final int LOWER_MASK = 0x7fffffff; // least significant r
            // bits

            // Tempering parameters
            private static final int TEMPERING_MASK_B = 0x9d2c5680;
            private static final int TEMPERING_MASK_C = 0xefc60000;

            private int mt[]; // the array for the state vector
            private int mti; // mti==N+1 means mt[N] is not initialized
            private int mag01[];

            // a good initial seed (of int size, though stored in a long)
            // private static final long GOOD_SEED = 4357;

            private double __nextNextGaussian;
            private boolean __haveNextNextGaussian;

            /*
             * We're overriding all internal data, to my knowledge, so this should be
             * okay
             */
            public Object clone() {
		try {
                    MersenneTwisterFast f = (MersenneTwisterFast) (super.clone());
                    f.mt = (int[]) (mt.clone());
                    f.mag01 = (int[]) (mag01.clone());
                    return f;
		} catch (CloneNotSupportedException e) {
                    throw new InternalError();
		} // should never happen
            }

            /**
             * Returns true if the MersenneTwisterFast's current internal state is equal
             * to another MersenneTwisterFast. This is roughly the same as
             * equals(other), except that it compares based on value but does not
             * guarantee the contract of immutability (obviously random number
             * generators are immutable). Note that this does NOT check to see if the
             * internal gaussian storage is the same for both. You can guarantee that
             * the internal gaussian storage is the same (and so the nextGaussian()
             * methods will return the same values) by calling clearGaussian() on both
             * objects.
             */
            public boolean stateEquals(MersenneTwisterFast other) {
		if (other == this)
                    return true;
		if (other == null)
                    return false;

		if (mti != other.mti)
                    return false;
		for (int x = 0; x < mag01.length; x++)
                    if (mag01[x] != other.mag01[x])
                        return false;
		for (int x = 0; x < mt.length; x++)
                    if (mt[x] != other.mt[x])
                        return false;
		return true;
            }

            /** Reads the entire state of the MersenneTwister RNG from the stream */
            public void readState(java.io.DataInputStream stream) throws java.io.IOException {
		int len = mt.length;
		for (int x = 0; x < len; x++)
                    mt[x] = stream.readInt();

		len = mag01.length;
		for (int x = 0; x < len; x++)
                    mag01[x] = stream.readInt();

		mti = stream.readInt();
		__nextNextGaussian = stream.readDouble();
		__haveNextNextGaussian = stream.readBoolean();
            }

            /** Writes the entire state of the MersenneTwister RNG to the stream */
            public void writeState(java.io.DataOutputStream stream) throws java.io.IOException {
		int len = mt.length;
		for (int x = 0; x < len; x++)
                    stream.writeInt(mt[x]);

		len = mag01.length;
		for (int x = 0; x < len; x++)
                    stream.writeInt(mag01[x]);

		stream.writeInt(mti);
		stream.writeDouble(__nextNextGaussian);
		stream.writeBoolean(__haveNextNextGaussian);
            }

            /**
             * Constructor using the default seed.
             */
            public MersenneTwisterFast() {
		this(System.currentTimeMillis());
            }

            /**
             * Constructor using a given seed. Though you pass this seed in as a long,
             * it's best to make sure it's actually an integer.
             *
             */
            public MersenneTwisterFast(long seed) {
		setSeed(seed);
            }

            /**
             * Constructor using an array of integers as seed. Your array must have a
             * non-zero length. Only the first 624 integers in the array are used; if
             * the array is shorter than this then integers are repeatedly used in a
             * wrap-around fashion.
             */
            public MersenneTwisterFast(int[] array) {
		setSeed(array);
            }

            /**
             * Initalize the pseudo random number generator. Don't pass in a long that's
             * bigger than an int (Mersenne Twister only uses the first 32 bits for its
             * seed).
             */

            public void setSeed(long seed) {
		// Due to a bug in java.util.Random clear up to 1.2, we're
		// doing our own Gaussian variable.
		__haveNextNextGaussian = false;

		mt = new int[N];

		mag01 = new int[2];
		mag01[0] = 0x0;
		mag01[1] = MATRIX_A;

		mt[0] = (int) (seed & 0xffffffff);
		for (mti = 1; mti < N; mti++) {
                    mt[mti] = (1812433253 * (mt[mti - 1] ^ (mt[mti - 1] >>> 30)) + mti);
                    /* See Knuth TAOCP Vol2. 3rd Ed. P.106 for multiplier. */
                    /* In the previous versions, MSBs of the seed affect */
                    /* only MSBs of the array mt[]. */
                    /* 2002/01/09 modified by Makoto Matsumoto */
                    // mt[mti] &= 0xffffffff;
                    /* for >32 bit machines */
		}
            }

            /**
             * Sets the seed of the MersenneTwister using an array of integers. Your
             * array must have a non-zero length. Only the first 624 integers in the
             * array are used; if the array is shorter than this then integers are
             * repeatedly used in a wrap-around fashion.
             */

            public void setSeed(int[] array) {
		if (array.length == 0)
                    throw new IllegalArgumentException("Array length must be greater than zero");
		int i, j, k;
		setSeed(19650218);
		i = 1;
		j = 0;
		k = (N > array.length ? N : array.length);
		for (; k != 0; k--) {
                    mt[i] = (mt[i] ^ ((mt[i - 1] ^ (mt[i - 1] >>> 30)) * 1664525)) + array[j]
                        + j; /* non linear */
                    // mt[i] &= 0xffffffff; /* for WORDSIZE > 32 machines */
                    i++;
                    j++;
                    if (i >= N) {
                        mt[0] = mt[N - 1];
                        i = 1;
                    }
                    if (j >= array.length)
                        j = 0;
		}
		for (k = N - 1; k != 0; k--) {
                    mt[i] = (mt[i] ^ ((mt[i - 1] ^ (mt[i - 1] >>> 30)) * 1566083941))
                        - i; /* non linear */
                    // mt[i] &= 0xffffffff; /* for WORDSIZE > 32 machines */
                    i++;
                    if (i >= N) {
                        mt[0] = mt[N - 1];
                        i = 1;
                    }
		}
		mt[0] = 0x80000000; /* MSB is 1; assuring non-zero initial array */
            }

            public int nextInt() {
		int y;

		if (mti >= N) // generate N words at one time
                    {
			int kk;
			final int[] mt = this.mt; // locals are slightly faster
			final int[] mag01 = this.mag01; // locals are slightly faster

			for (kk = 0; kk < N - M; kk++) {
                            y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                            mt[kk] = mt[kk + M] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			for (; kk < N - 1; kk++) {
                            y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                            mt[kk] = mt[kk + (M - N)] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			y = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
			mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ mag01[y & 0x1];

			mti = 0;
                    }

		y = mt[mti++];
		y ^= y >>> 11; // TEMPERING_SHIFT_U(y)
		y ^= (y << 7) & TEMPERING_MASK_B; // TEMPERING_SHIFT_S(y)
		y ^= (y << 15) & TEMPERING_MASK_C; // TEMPERING_SHIFT_T(y)
		y ^= (y >>> 18); // TEMPERING_SHIFT_L(y)

		return y;
            }

            public short nextShort() {
		int y;

		if (mti >= N) // generate N words at one time
                    {
			int kk;
			final int[] mt = this.mt; // locals are slightly faster
			final int[] mag01 = this.mag01; // locals are slightly faster

			for (kk = 0; kk < N - M; kk++) {
                            y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                            mt[kk] = mt[kk + M] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			for (; kk < N - 1; kk++) {
                            y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                            mt[kk] = mt[kk + (M - N)] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			y = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
			mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ mag01[y & 0x1];

			mti = 0;
                    }

		y = mt[mti++];
		y ^= y >>> 11; // TEMPERING_SHIFT_U(y)
		y ^= (y << 7) & TEMPERING_MASK_B; // TEMPERING_SHIFT_S(y)
		y ^= (y << 15) & TEMPERING_MASK_C; // TEMPERING_SHIFT_T(y)
		y ^= (y >>> 18); // TEMPERING_SHIFT_L(y)

		return (short) (y >>> 16);
            }

            public char nextChar() {
		int y;

		if (mti >= N) // generate N words at one time
                    {
			int kk;
			final int[] mt = this.mt; // locals are slightly faster
			final int[] mag01 = this.mag01; // locals are slightly faster

			for (kk = 0; kk < N - M; kk++) {
                            y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                            mt[kk] = mt[kk + M] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			for (; kk < N - 1; kk++) {
                            y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                            mt[kk] = mt[kk + (M - N)] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			y = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
			mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ mag01[y & 0x1];

			mti = 0;
                    }

		y = mt[mti++];
		y ^= y >>> 11; // TEMPERING_SHIFT_U(y)
		y ^= (y << 7) & TEMPERING_MASK_B; // TEMPERING_SHIFT_S(y)
		y ^= (y << 15) & TEMPERING_MASK_C; // TEMPERING_SHIFT_T(y)
		y ^= (y >>> 18); // TEMPERING_SHIFT_L(y)

		return (char) (y >>> 16);
            }

            public boolean nextBoolean() {
		int y;

		if (mti >= N) // generate N words at one time
                    {
			int kk;
			final int[] mt = this.mt; // locals are slightly faster
			final int[] mag01 = this.mag01; // locals are slightly faster

			for (kk = 0; kk < N - M; kk++) {
                            y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                            mt[kk] = mt[kk + M] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			for (; kk < N - 1; kk++) {
                            y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                            mt[kk] = mt[kk + (M - N)] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			y = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
			mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ mag01[y & 0x1];

			mti = 0;
                    }

		y = mt[mti++];
		y ^= y >>> 11; // TEMPERING_SHIFT_U(y)
		y ^= (y << 7) & TEMPERING_MASK_B; // TEMPERING_SHIFT_S(y)
		y ^= (y << 15) & TEMPERING_MASK_C; // TEMPERING_SHIFT_T(y)
		y ^= (y >>> 18); // TEMPERING_SHIFT_L(y)

		return (boolean) ((y >>> 31) != 0);
            }

            /**
             * This generates a coin flip with a probability <tt>probability</tt> of
             * returning true, else returning false. <tt>probability</tt> must be
             * between 0.0 and 1.0, inclusive. Not as precise a random real event as
             * nextBoolean(double), but twice as fast. To explicitly use this, remember
             * you may need to cast to float first.
             */

            public boolean nextBoolean(float probability) {
		int y;

		if (probability < 0.0f || probability > 1.0f)
                    throw new IllegalArgumentException("probability must be between 0.0 and 1.0 inclusive.");
		if (probability == 0.0f)
                    return false; // fix half-open issues
		else if (probability == 1.0f)
                    return true; // fix half-open issues
		if (mti >= N) // generate N words at one time
                    {
			int kk;
			final int[] mt = this.mt; // locals are slightly faster
			final int[] mag01 = this.mag01; // locals are slightly faster

			for (kk = 0; kk < N - M; kk++) {
                            y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                            mt[kk] = mt[kk + M] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			for (; kk < N - 1; kk++) {
                            y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                            mt[kk] = mt[kk + (M - N)] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			y = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
			mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ mag01[y & 0x1];

			mti = 0;
                    }

		y = mt[mti++];
		y ^= y >>> 11; // TEMPERING_SHIFT_U(y)
		y ^= (y << 7) & TEMPERING_MASK_B; // TEMPERING_SHIFT_S(y)
		y ^= (y << 15) & TEMPERING_MASK_C; // TEMPERING_SHIFT_T(y)
		y ^= (y >>> 18); // TEMPERING_SHIFT_L(y)

		return (y >>> 8) / ((float) (1 << 24)) < probability;
            }

            /**
             * This generates a coin flip with a probability <tt>probability</tt> of
             * returning true, else returning false. <tt>probability</tt> must be
             * between 0.0 and 1.0, inclusive.
             */

            public boolean nextBoolean(double probability) {
		int y;
		int z;

		if (probability < 0.0 || probability > 1.0)
                    throw new IllegalArgumentException("probability must be between 0.0 and 1.0 inclusive.");
		if (probability == 0.0)
                    return false; // fix half-open issues
		else if (probability == 1.0)
                    return true; // fix half-open issues
		if (mti >= N) // generate N words at one time
                    {
			int kk;
			final int[] mt = this.mt; // locals are slightly faster
			final int[] mag01 = this.mag01; // locals are slightly faster

			for (kk = 0; kk < N - M; kk++) {
                            y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                            mt[kk] = mt[kk + M] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			for (; kk < N - 1; kk++) {
                            y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                            mt[kk] = mt[kk + (M - N)] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			y = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
			mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ mag01[y & 0x1];

			mti = 0;
                    }

		y = mt[mti++];
		y ^= y >>> 11; // TEMPERING_SHIFT_U(y)
		y ^= (y << 7) & TEMPERING_MASK_B; // TEMPERING_SHIFT_S(y)
		y ^= (y << 15) & TEMPERING_MASK_C; // TEMPERING_SHIFT_T(y)
		y ^= (y >>> 18); // TEMPERING_SHIFT_L(y)

		if (mti >= N) // generate N words at one time
                    {
			int kk;
			final int[] mt = this.mt; // locals are slightly faster
			final int[] mag01 = this.mag01; // locals are slightly faster

			for (kk = 0; kk < N - M; kk++) {
                            z = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                            mt[kk] = mt[kk + M] ^ (z >>> 1) ^ mag01[z & 0x1];
			}
			for (; kk < N - 1; kk++) {
                            z = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                            mt[kk] = mt[kk + (M - N)] ^ (z >>> 1) ^ mag01[z & 0x1];
			}
			z = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
			mt[N - 1] = mt[M - 1] ^ (z >>> 1) ^ mag01[z & 0x1];

			mti = 0;
                    }

		z = mt[mti++];
		z ^= z >>> 11; // TEMPERING_SHIFT_U(z)
		z ^= (z << 7) & TEMPERING_MASK_B; // TEMPERING_SHIFT_S(z)
		z ^= (z << 15) & TEMPERING_MASK_C; // TEMPERING_SHIFT_T(z)
		z ^= (z >>> 18); // TEMPERING_SHIFT_L(z)

		/* derived from nextDouble documentation in jdk 1.2 docs, see top */
		return ((((long) (y >>> 6)) << 27) + (z >>> 5)) / (double) (1L << 53) < probability;
            }

            public byte nextByte() {
		int y;

		if (mti >= N) // generate N words at one time
                    {
			int kk;
			final int[] mt = this.mt; // locals are slightly faster
			final int[] mag01 = this.mag01; // locals are slightly faster

			for (kk = 0; kk < N - M; kk++) {
                            y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                            mt[kk] = mt[kk + M] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			for (; kk < N - 1; kk++) {
                            y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                            mt[kk] = mt[kk + (M - N)] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			y = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
			mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ mag01[y & 0x1];

			mti = 0;
                    }

		y = mt[mti++];
		y ^= y >>> 11; // TEMPERING_SHIFT_U(y)
		y ^= (y << 7) & TEMPERING_MASK_B; // TEMPERING_SHIFT_S(y)
		y ^= (y << 15) & TEMPERING_MASK_C; // TEMPERING_SHIFT_T(y)
		y ^= (y >>> 18); // TEMPERING_SHIFT_L(y)

		return (byte) (y >>> 24);
            }

            public void nextBytes(byte[] bytes) {
		int y;

		for (int x = 0; x < bytes.length; x++) {
                    if (mti >= N) // generate N words at one time
			{
                            int kk;
                            final int[] mt = this.mt; // locals are slightly faster
                            final int[] mag01 = this.mag01; // locals are slightly faster

                            for (kk = 0; kk < N - M; kk++) {
                                y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                                mt[kk] = mt[kk + M] ^ (y >>> 1) ^ mag01[y & 0x1];
                            }
                            for (; kk < N - 1; kk++) {
                                y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                                mt[kk] = mt[kk + (M - N)] ^ (y >>> 1) ^ mag01[y & 0x1];
                            }
                            y = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
                            mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ mag01[y & 0x1];

                            mti = 0;
			}

                    y = mt[mti++];
                    y ^= y >>> 11; // TEMPERING_SHIFT_U(y)
                    y ^= (y << 7) & TEMPERING_MASK_B; // TEMPERING_SHIFT_S(y)
                    y ^= (y << 15) & TEMPERING_MASK_C; // TEMPERING_SHIFT_T(y)
                    y ^= (y >>> 18); // TEMPERING_SHIFT_L(y)

                    bytes[x] = (byte) (y >>> 24);
		}
            }

            /**
             * Returns a long drawn uniformly from 0 to n-1. Suffice it to say, n must
             * be greater than 0, or an IllegalArgumentException is raised.
             */

            public long nextLong() {
		int y;
		int z;

		if (mti >= N) // generate N words at one time
                    {
			int kk;
			final int[] mt = this.mt; // locals are slightly faster
			final int[] mag01 = this.mag01; // locals are slightly faster

			for (kk = 0; kk < N - M; kk++) {
                            y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                            mt[kk] = mt[kk + M] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			for (; kk < N - 1; kk++) {
                            y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                            mt[kk] = mt[kk + (M - N)] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			y = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
			mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ mag01[y & 0x1];

			mti = 0;
                    }

		y = mt[mti++];
		y ^= y >>> 11; // TEMPERING_SHIFT_U(y)
		y ^= (y << 7) & TEMPERING_MASK_B; // TEMPERING_SHIFT_S(y)
		y ^= (y << 15) & TEMPERING_MASK_C; // TEMPERING_SHIFT_T(y)
		y ^= (y >>> 18); // TEMPERING_SHIFT_L(y)

		if (mti >= N) // generate N words at one time
                    {
			int kk;
			final int[] mt = this.mt; // locals are slightly faster
			final int[] mag01 = this.mag01; // locals are slightly faster

			for (kk = 0; kk < N - M; kk++) {
                            z = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                            mt[kk] = mt[kk + M] ^ (z >>> 1) ^ mag01[z & 0x1];
			}
			for (; kk < N - 1; kk++) {
                            z = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                            mt[kk] = mt[kk + (M - N)] ^ (z >>> 1) ^ mag01[z & 0x1];
			}
			z = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
			mt[N - 1] = mt[M - 1] ^ (z >>> 1) ^ mag01[z & 0x1];

			mti = 0;
                    }

		z = mt[mti++];
		z ^= z >>> 11; // TEMPERING_SHIFT_U(z)
		z ^= (z << 7) & TEMPERING_MASK_B; // TEMPERING_SHIFT_S(z)
		z ^= (z << 15) & TEMPERING_MASK_C; // TEMPERING_SHIFT_T(z)
		z ^= (z >>> 18); // TEMPERING_SHIFT_L(z)

		return (((long) y) << 32) + (long) z;
            }

            /**
             * Returns a long drawn uniformly from 0 to n-1. Suffice it to say, n must
             * be &gt; 0, or an IllegalArgumentException is raised.
             */
            public long nextLong(long n) {
		if (n <= 0)
                    throw new IllegalArgumentException("n must be positive, got: " + n);

		long bits, val;
		do {
                    int y;
                    int z;

                    if (mti >= N) // generate N words at one time
			{
                            int kk;
                            final int[] mt = this.mt; // locals are slightly faster
                            final int[] mag01 = this.mag01; // locals are slightly faster

                            for (kk = 0; kk < N - M; kk++) {
                                y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                                mt[kk] = mt[kk + M] ^ (y >>> 1) ^ mag01[y & 0x1];
                            }
                            for (; kk < N - 1; kk++) {
                                y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                                mt[kk] = mt[kk + (M - N)] ^ (y >>> 1) ^ mag01[y & 0x1];
                            }
                            y = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
                            mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ mag01[y & 0x1];

                            mti = 0;
			}

                    y = mt[mti++];
                    y ^= y >>> 11; // TEMPERING_SHIFT_U(y)
                    y ^= (y << 7) & TEMPERING_MASK_B; // TEMPERING_SHIFT_S(y)
                    y ^= (y << 15) & TEMPERING_MASK_C; // TEMPERING_SHIFT_T(y)
                    y ^= (y >>> 18); // TEMPERING_SHIFT_L(y)

                    if (mti >= N) // generate N words at one time
			{
                            int kk;
                            final int[] mt = this.mt; // locals are slightly faster
                            final int[] mag01 = this.mag01; // locals are slightly faster

                            for (kk = 0; kk < N - M; kk++) {
                                z = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                                mt[kk] = mt[kk + M] ^ (z >>> 1) ^ mag01[z & 0x1];
                            }
                            for (; kk < N - 1; kk++) {
                                z = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                                mt[kk] = mt[kk + (M - N)] ^ (z >>> 1) ^ mag01[z & 0x1];
                            }
                            z = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
                            mt[N - 1] = mt[M - 1] ^ (z >>> 1) ^ mag01[z & 0x1];

                            mti = 0;
			}

                    z = mt[mti++];
                    z ^= z >>> 11; // TEMPERING_SHIFT_U(z)
                    z ^= (z << 7) & TEMPERING_MASK_B; // TEMPERING_SHIFT_S(z)
                    z ^= (z << 15) & TEMPERING_MASK_C; // TEMPERING_SHIFT_T(z)
                    z ^= (z >>> 18); // TEMPERING_SHIFT_L(z)

                    bits = (((((long) y) << 32) + (long) z) >>> 1);
                    val = bits % n;
		} while (bits - val + (n - 1) < 0);
		return val;
            }

            /**
             * Returns a random double in the half-open range from [0.0,1.0). Thus 0.0
             * is a valid result but 1.0 is not.
             */
            public double nextDouble() {
		int y;
		int z;

		if (mti >= N) // generate N words at one time
                    {
			int kk;
			final int[] mt = this.mt; // locals are slightly faster
			final int[] mag01 = this.mag01; // locals are slightly faster

			for (kk = 0; kk < N - M; kk++) {
                            y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                            mt[kk] = mt[kk + M] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			for (; kk < N - 1; kk++) {
                            y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                            mt[kk] = mt[kk + (M - N)] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			y = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
			mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ mag01[y & 0x1];

			mti = 0;
                    }

		y = mt[mti++];
		y ^= y >>> 11; // TEMPERING_SHIFT_U(y)
		y ^= (y << 7) & TEMPERING_MASK_B; // TEMPERING_SHIFT_S(y)
		y ^= (y << 15) & TEMPERING_MASK_C; // TEMPERING_SHIFT_T(y)
		y ^= (y >>> 18); // TEMPERING_SHIFT_L(y)

		if (mti >= N) // generate N words at one time
                    {
			int kk;
			final int[] mt = this.mt; // locals are slightly faster
			final int[] mag01 = this.mag01; // locals are slightly faster

			for (kk = 0; kk < N - M; kk++) {
                            z = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                            mt[kk] = mt[kk + M] ^ (z >>> 1) ^ mag01[z & 0x1];
			}
			for (; kk < N - 1; kk++) {
                            z = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                            mt[kk] = mt[kk + (M - N)] ^ (z >>> 1) ^ mag01[z & 0x1];
			}
			z = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
			mt[N - 1] = mt[M - 1] ^ (z >>> 1) ^ mag01[z & 0x1];

			mti = 0;
                    }

		z = mt[mti++];
		z ^= z >>> 11; // TEMPERING_SHIFT_U(z)
		z ^= (z << 7) & TEMPERING_MASK_B; // TEMPERING_SHIFT_S(z)
		z ^= (z << 15) & TEMPERING_MASK_C; // TEMPERING_SHIFT_T(z)
		z ^= (z >>> 18); // TEMPERING_SHIFT_L(z)

		/* derived from nextDouble documentation in jdk 1.2 docs, see top */
		return ((((long) (y >>> 6)) << 27) + (z >>> 5)) / (double) (1L << 53);
            }

            /**
             * Returns a double in the range from 0.0 to 1.0, possibly inclusive of 0.0
             * and 1.0 themselves. Thus:
             * 
             * <table border=0>
             * <tr>
             * <th>Expression</th>
             * <th>Interval</th>
             * </tr>
             * <tr>
             * <td>nextDouble(false, false)</td>
             * <td>(0.0, 1.0)</td>
             * </tr>
             * <tr>
             * <td>nextDouble(true, false)</td>
             * <td>[0.0, 1.0)</td>
             * </tr>
             * <tr>
             * <td>nextDouble(false, true)</td>
             * <td>(0.0, 1.0]</td>
             * </tr>
             * <tr>
             * <td>nextDouble(true, true)</td>
             * <td>[0.0, 1.0]</td>
             * </tr>
             * <caption>Table of intervals</caption>
             * </table>
             * 
             * <p>
             * This version preserves all possible random values in the double range.
             */
            public double nextDouble(boolean includeZero, boolean includeOne) {
		double d = 0.0;
		do {
                    d = nextDouble(); // grab a value, initially from half-open [0.0,
                    // 1.0)
                    if (includeOne && nextBoolean())
                        d += 1.0; // if includeOne, with 1/2 probability, push to [1.0,
                    // 2.0)
		} while ((d > 1.0) || // everything above 1.0 is always invalid
                         (!includeZero && d == 0.0)); // if we're not including zero, 0.0
                // is invalid
		return d;
            }

            /**
             * Clears the internal gaussian variable from the RNG You only need to do
             * this in the rare case that you need to guarantee that two RNGs have
             * identical internal state. Otherwise, disregard this method. See
             * stateEquals(other).
             */
            public void clearGaussian() {
		__haveNextNextGaussian = false;
            }

            public double nextGaussian() {
		if (__haveNextNextGaussian) {
                    __haveNextNextGaussian = false;
                    return __nextNextGaussian;
		} else {
                    double v1, v2, s;
                    do {
                        int y;
                        int z;
                        int a;
                        int b;

                        if (mti >= N) // generate N words at one time
                            {
                                int kk;
                                final int[] mt = this.mt; // locals are slightly faster
                                final int[] mag01 = this.mag01; // locals are slightly
                                // faster

                                for (kk = 0; kk < N - M; kk++) {
                                    y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                                    mt[kk] = mt[kk + M] ^ (y >>> 1) ^ mag01[y & 0x1];
                                }
                                for (; kk < N - 1; kk++) {
                                    y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                                    mt[kk] = mt[kk + (M - N)] ^ (y >>> 1) ^ mag01[y & 0x1];
                                }
                                y = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
                                mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ mag01[y & 0x1];

                                mti = 0;
                            }

                        y = mt[mti++];
                        y ^= y >>> 11; // TEMPERING_SHIFT_U(y)
                        y ^= (y << 7) & TEMPERING_MASK_B; // TEMPERING_SHIFT_S(y)
                        y ^= (y << 15) & TEMPERING_MASK_C; // TEMPERING_SHIFT_T(y)
                        y ^= (y >>> 18); // TEMPERING_SHIFT_L(y)

                        if (mti >= N) // generate N words at one time
                            {
                                int kk;
                                final int[] mt = this.mt; // locals are slightly faster
                                final int[] mag01 = this.mag01; // locals are slightly
                                // faster

                                for (kk = 0; kk < N - M; kk++) {
                                    z = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                                    mt[kk] = mt[kk + M] ^ (z >>> 1) ^ mag01[z & 0x1];
                                }
                                for (; kk < N - 1; kk++) {
                                    z = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                                    mt[kk] = mt[kk + (M - N)] ^ (z >>> 1) ^ mag01[z & 0x1];
                                }
                                z = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
                                mt[N - 1] = mt[M - 1] ^ (z >>> 1) ^ mag01[z & 0x1];

                                mti = 0;
                            }

                        z = mt[mti++];
                        z ^= z >>> 11; // TEMPERING_SHIFT_U(z)
                        z ^= (z << 7) & TEMPERING_MASK_B; // TEMPERING_SHIFT_S(z)
                        z ^= (z << 15) & TEMPERING_MASK_C; // TEMPERING_SHIFT_T(z)
                        z ^= (z >>> 18); // TEMPERING_SHIFT_L(z)

                        if (mti >= N) // generate N words at one time
                            {
                                int kk;
                                final int[] mt = this.mt; // locals are slightly faster
                                final int[] mag01 = this.mag01; // locals are slightly
                                // faster

                                for (kk = 0; kk < N - M; kk++) {
                                    a = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                                    mt[kk] = mt[kk + M] ^ (a >>> 1) ^ mag01[a & 0x1];
                                }
                                for (; kk < N - 1; kk++) {
                                    a = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                                    mt[kk] = mt[kk + (M - N)] ^ (a >>> 1) ^ mag01[a & 0x1];
                                }
                                a = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
                                mt[N - 1] = mt[M - 1] ^ (a >>> 1) ^ mag01[a & 0x1];

                                mti = 0;
                            }

                        a = mt[mti++];
                        a ^= a >>> 11; // TEMPERING_SHIFT_U(a)
                        a ^= (a << 7) & TEMPERING_MASK_B; // TEMPERING_SHIFT_S(a)
                        a ^= (a << 15) & TEMPERING_MASK_C; // TEMPERING_SHIFT_T(a)
                        a ^= (a >>> 18); // TEMPERING_SHIFT_L(a)

                        if (mti >= N) // generate N words at one time
                            {
                                int kk;
                                final int[] mt = this.mt; // locals are slightly faster
                                final int[] mag01 = this.mag01; // locals are slightly
                                // faster

                                for (kk = 0; kk < N - M; kk++) {
                                    b = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                                    mt[kk] = mt[kk + M] ^ (b >>> 1) ^ mag01[b & 0x1];
                                }
                                for (; kk < N - 1; kk++) {
                                    b = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                                    mt[kk] = mt[kk + (M - N)] ^ (b >>> 1) ^ mag01[b & 0x1];
                                }
                                b = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
                                mt[N - 1] = mt[M - 1] ^ (b >>> 1) ^ mag01[b & 0x1];

                                mti = 0;
                            }

                        b = mt[mti++];
                        b ^= b >>> 11; // TEMPERING_SHIFT_U(b)
                        b ^= (b << 7) & TEMPERING_MASK_B; // TEMPERING_SHIFT_S(b)
                        b ^= (b << 15) & TEMPERING_MASK_C; // TEMPERING_SHIFT_T(b)
                        b ^= (b >>> 18); // TEMPERING_SHIFT_L(b)

                        /*
                         * derived from nextDouble documentation in jdk 1.2 docs, see
                         * top
                         */
                        v1 = 2 * (((((long) (y >>> 6)) << 27) + (z >>> 5)) / (double) (1L << 53)) - 1;
                        v2 = 2 * (((((long) (a >>> 6)) << 27) + (b >>> 5)) / (double) (1L << 53)) - 1;
                        s = v1 * v1 + v2 * v2;
                    } while (s >= 1 || s == 0);
                    double multiplier = StrictMath.sqrt(-2 * StrictMath.log(s) / s);
                    __nextNextGaussian = v2 * multiplier;
                    __haveNextNextGaussian = true;
                    return v1 * multiplier;
		}
            }

            /**
             * Returns a random float in the half-open range from [0.0f,1.0f). Thus 0.0f
             * is a valid result but 1.0f is not.
             */
            public float nextFloat() {
		int y;

		if (mti >= N) // generate N words at one time
                    {
			int kk;
			final int[] mt = this.mt; // locals are slightly faster
			final int[] mag01 = this.mag01; // locals are slightly faster

			for (kk = 0; kk < N - M; kk++) {
                            y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                            mt[kk] = mt[kk + M] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			for (; kk < N - 1; kk++) {
                            y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                            mt[kk] = mt[kk + (M - N)] ^ (y >>> 1) ^ mag01[y & 0x1];
			}
			y = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
			mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ mag01[y & 0x1];

			mti = 0;
                    }

		y = mt[mti++];
		y ^= y >>> 11; // TEMPERING_SHIFT_U(y)
		y ^= (y << 7) & TEMPERING_MASK_B; // TEMPERING_SHIFT_S(y)
		y ^= (y << 15) & TEMPERING_MASK_C; // TEMPERING_SHIFT_T(y)
		y ^= (y >>> 18); // TEMPERING_SHIFT_L(y)

		return (y >>> 8) / ((float) (1 << 24));
            }

            /**
             * Returns a float in the range from 0.0f to 1.0f, possibly inclusive of
             * 0.0f and 1.0f themselves. Thus:
             * 
             * <table border=0>
             * <tr>
             * <th>Expression</th>
             * <th>Interval</th>
             * </tr>
             * <tr>
             * <td>nextFloat(false, false)</td>
             * <td>(0.0f, 1.0f)</td>
             * </tr>
             * <tr>
             * <td>nextFloat(true, false)</td>
             * <td>[0.0f, 1.0f)</td>
             * </tr>
             * <tr>
             * <td>nextFloat(false, true)</td>
             * <td>(0.0f, 1.0f]</td>
             * </tr>
             * <tr>
             * <td>nextFloat(true, true)</td>
             * <td>[0.0f, 1.0f]</td>
             * </tr>
             * <caption>Table of intervals</caption>
             * </table>
             * 
             * <p>
             * This version preserves all possible random values in the float range.
             */
            public float nextFloat(boolean includeZero, boolean includeOne) {
		float d = 0.0f;
		do {
                    d = nextFloat(); // grab a value, initially from half-open [0.0f,
                    // 1.0f)
                    if (includeOne && nextBoolean())
                        d += 1.0f; // if includeOne, with 1/2 probability, push to
                    // [1.0f, 2.0f)
		} while ((d > 1.0f) || // everything above 1.0f is always invalid
                         (!includeZero && d == 0.0f)); // if we're not including zero,
                // 0.0f is invalid
		return d;
            }

            /**
             * Returns an integer drawn uniformly from 0 to n-1. Suffice it to say, n
             * must be &gt; 0, or an IllegalArgumentException is raised.
             */
            public int nextInt(int n) {
		if (n <= 0)
                    throw new IllegalArgumentException("n must be positive, got: " + n);

		if ((n & -n) == n) // i.e., n is a power of 2
                    {
			int y;

			if (mti >= N) // generate N words at one time
                            {
				int kk;
				final int[] mt = this.mt; // locals are slightly faster
				final int[] mag01 = this.mag01; // locals are slightly faster

				for (kk = 0; kk < N - M; kk++) {
                                    y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                                    mt[kk] = mt[kk + M] ^ (y >>> 1) ^ mag01[y & 0x1];
				}
				for (; kk < N - 1; kk++) {
                                    y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                                    mt[kk] = mt[kk + (M - N)] ^ (y >>> 1) ^ mag01[y & 0x1];
				}
				y = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
				mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ mag01[y & 0x1];

				mti = 0;
                            }

			y = mt[mti++];
			y ^= y >>> 11; // TEMPERING_SHIFT_U(y)
			y ^= (y << 7) & TEMPERING_MASK_B; // TEMPERING_SHIFT_S(y)
			y ^= (y << 15) & TEMPERING_MASK_C; // TEMPERING_SHIFT_T(y)
			y ^= (y >>> 18); // TEMPERING_SHIFT_L(y)

			return (int) ((n * (long) (y >>> 1)) >> 31);
                    }

		int bits, val;
		do {
                    int y;

                    if (mti >= N) // generate N words at one time
			{
                            int kk;
                            final int[] mt = this.mt; // locals are slightly faster
                            final int[] mag01 = this.mag01; // locals are slightly faster

                            for (kk = 0; kk < N - M; kk++) {
                                y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                                mt[kk] = mt[kk + M] ^ (y >>> 1) ^ mag01[y & 0x1];
                            }
                            for (; kk < N - 1; kk++) {
                                y = (mt[kk] & UPPER_MASK) | (mt[kk + 1] & LOWER_MASK);
                                mt[kk] = mt[kk + (M - N)] ^ (y >>> 1) ^ mag01[y & 0x1];
                            }
                            y = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
                            mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ mag01[y & 0x1];

                            mti = 0;
			}

                    y = mt[mti++];
                    y ^= y >>> 11; // TEMPERING_SHIFT_U(y)
                    y ^= (y << 7) & TEMPERING_MASK_B; // TEMPERING_SHIFT_S(y)
                    y ^= (y << 15) & TEMPERING_MASK_C; // TEMPERING_SHIFT_T(y)
                    y ^= (y >>> 18); // TEMPERING_SHIFT_L(y)

                    bits = (y >>> 1);
                    val = bits % n;
		} while (bits - val + (n - 1) < 0);
		return val;
            }
        }

        public static class VerifyOutputStream extends java.io.OutputStream {
            java.io.InputStream verify;
            long at = 0L;
            long mismatch = Long.MAX_VALUE;
	
            public boolean matches() { return mismatch == Long.MAX_VALUE; }
            public long getMismatch() { return mismatch; }

            public VerifyOutputStream(java.io.InputStream _verify) {
		verify = _verify;
            }

            public void flush() throws java.io.IOException {
            };

            public void write(byte[] data, int offset, int length) throws java.io.IOException {
		if (offset != 0 || length != data.length) {
                    byte[] writedata = new byte[length];
                    System.arraycopy(data, offset, writedata, 0, length);
                    write(writedata);
		} else {
                    write(data);
		}
            }

            public void write(byte[] data) throws java.io.IOException {
		byte[] rdata = new byte[data.length];
		if (mismatch == Long.MAX_VALUE) {
                    long readBytes = verify.read(rdata);
                    if ((readBytes == data.length) && java.util.Arrays.equals(data, rdata)) {
                        at += readBytes;
                        return;
                    }
                    int i = 0;
                    for (i = 0; i < readBytes; ++i) {
                        if (data[i] != rdata[i])
                            break;
                    }
                    mismatch = at + i;
		}
            }

            public void write(int b) throws java.io.IOException {
		byte[] data = new byte[1];
		data[0] = (byte) b;
		write(data);
            }

            public void close() throws java.io.IOException {
		if (mismatch == Long.MAX_VALUE) {
                    int ch = verify.read();
                    if (ch != -1) {
                        mismatch = at;
                    }
		}
		verify.close();
		if (!matches()) {
                    throw new VerifyRuntimeException("mismatch at byte offset " + mismatch);
		}
            }
        }

        public static class VerifyRuntimeException extends RuntimeException {
            VerifyRuntimeException(String message) {
		super(message);
            }
        }


        public static class Run {
            public static void main(String[] _args) throws ClassNotFoundException, java.lang.reflect.InvocationTargetException {
                String[] args = null;

                String className = "App"; // default class is "App" in default package
                
                if (System.getenv("JAVA_APP") != null) { // env override
                    className = System.getenv("JAVA_APP");
                }
        
                if (_args.length > 1 && _args[0].equals("--app")) { 
                    className = _args[1];
                    args = new String[_args.length-2];
                    System.arraycopy(_args,2,args,0,args.length);
                } else {
                    args = new String[_args.length];
                    System.arraycopy(args,0,_args,0,args.length);
                }

                Class appClass = Class.forName(className);

                Object app = null;
                try {
                    app = appClass.getConstructor(String[].class).newInstance(new Object[] { args });
                } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | java.lang.reflect.InvocationTargetException
                         | NoSuchMethodException | SecurityException e2) {
                }
        
                if (app == null) {
                    try { // construct with default (no arg) constructor
                        app = appClass.newInstance();
                    } catch (InstantiationException | IllegalAccessException e) {
                        throw new RuntimeException("Could not construct "
                                                   + appClass.getName()
                                                   + ". Is the default or String[] constructor not public?");
                    }
                }

                kiss.util.RNG.seed(0);
                java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
        
                for (java.lang.reflect.Method method : app.getClass().getDeclaredMethods()) {
                    if (method.getName().startsWith("test") && method.getParameterTypes().length == 0) {
                        try {
                            java.util.Date started = new java.util.Date();
                            System.out.println(started+" "+method.getName()+": started");
                            method.invoke(app);

                            java.util.Date ended = new java.util.Date();
                            double elapsed =
                                (ended.getTime()-started.getTime())/1000.0;


                            System.out.println(ended+" "+method.getName()+": ended in " + df.format(elapsed) + " second(s)");
                        } catch (java.lang.IllegalAccessException | java.lang.IllegalArgumentException e) {
                            e.printStackTrace();
                        }
                    }
                }

                kiss.util.RNG.seed(java.lang.System.currentTimeMillis());

                java.lang.reflect.Method run = null;
                try {
                    run = app.getClass().getMethod("run");
                } catch (NoSuchMethodException | SecurityException e1) {
                }
        
                if (run != null) {
                    try {
                        run.invoke(app);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Make " + run + " in " + app.getClass().getName() + " public");
                    } catch (IllegalArgumentException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

            public static void sleep(long millis) {
		try {
                    Thread.sleep(millis);
		} catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
		}
            }

        }


        public static class Test implements IKiss {
            public void testRandomRange()
            {
                for (int i=0; i < 100; ++i) {
                    int die = random(1,6);
                    assert 1 <= die && die <= 6;                
                }
            }
        }

    }
}
