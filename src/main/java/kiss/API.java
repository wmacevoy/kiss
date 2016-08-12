package kiss;

import java.io.Closeable;
import java.io.File;

import java.util.Set;

import kiss.util.IO;
import kiss.util.RNG;
import kiss.util.Run;
import kiss.util.To;

public class API {
    public interface Close extends AutoCloseable, Closeable {
        void close(); // no exceptions
    }

    public interface Listener < Event > {
        public void receive(Event event);
    }

    public interface GeneratorInterface < Event > {
        public void addListener ( Listener < Event > listener );
        public void removeListener ( Listener < Event > listener );
    }

    public static class Generator < Event > 
        implements GeneratorInterface < Event > ,
                   java.io.Serializable, Cloneable {
        public Class<Event> type;
        {
            type=(Class<Event>)
                kiss.util.Reflect.getTypeArguments(Generator.class,
                                                   getClass()).get(0);
            assert type != null;
        }
        
        private static final Listener < ? > [] NONE =
            new Listener < ? > [ 0 ];
        
        private transient Listener < Event > [] listeners = ( Listener < Event > [] ) NONE; 
        /** Returns a duplicate of the current listeners. */
        @SuppressWarnings("unchecked")        
        public Listener < Event > [] getListeners() {
            Listener < Event > [] tmp = listeners;
            Listener < Event > [] dup = ( Listener < Event > [] ) 
                new Listener < ? > [ tmp.length ];
            
            for (int i=tmp.length-1; i>=0; --i) dup[i]=tmp[i];
            
            return dup;
        }
        
        
        private transient Object lock = new Object();
        
        
        private boolean contains(Listener < Event > listener) {
            Listener < Event > [] tmp = listeners;
            for (int i=tmp.length-1; i>=0; --i) 
                if (tmp[i] == listener) return true;
            return false;
        }
        
        /**
         *  <p>Synchronously register a listener with this Generator.
         *  <p>This has no effect if the listener is already registered or is null.
         *  <p>This only effects future (not current) send operations.
         */
        @SuppressWarnings("unchecked")        
        public void addListener ( Listener < Event > listener ) 
        {
            synchronized(lock) {
                if (contains(listener) || listener == null) return;
                
                Listener < Event > [] tmp = ( Listener < Event > [] ) 
                    new Listener < ? > [ listeners.length+1 ];
                
                for (int i=tmp.length-1; i>0; --i) tmp[i]=listeners[i-1];
                tmp[0]=listener;
                
                listeners=tmp;
            }
        }

    
        @SuppressWarnings("unchecked")
        public void addListener(Object object) {
        kiss.util.AutoListener<Event> listener =
            new kiss.util.AutoListener(type,object);
        addListener(listener);
    }
        
        /**
         *  <p>Synchronously unregister a listener from this Generator.
         *  <p>This has no effect if the listener is not registered.
         *  <p>This only effects future (not current) send operations.
         */
        @SuppressWarnings("unchecked")
        public void removeListener ( Listener < Event > listener ) 
        {
            synchronized(lock) {        
                if (!contains(listener)) return;
                
                Listener < Event > [] tmp = (Listener < Event > []) 
                    ( ( listeners.length > 1 ) ? 
                      new Listener < ? > [listeners.length-1] : NONE );
                
                for (int i=listeners.length-1,j=i; i >= 0; --i) 
                    if (listeners[i] != listener) tmp[--j]=listeners[i];
                
                listeners=tmp;
            }
        }
        
        /** Determine if any listeners are currently registered.
            This is useful to check before a send operation, since in many
            cases there are no registered listeners, an so no reason to create
            an event to send.
        */
        protected final boolean listening() { return listeners != NONE; }
        
        /** Send an event to the registered listeners.  
         *
         *   <p>The listeners receive the event in the order they were
         *      registered (with <code>addListener</code>)
         *
         *   <p>Before creating and sending an event, you should check
         *      with <code>listening()</code> to see if there are any 
         *      registered listeners.
         *
         *   <p>If the listeners add or remove themselves or other listeners
         *      from this generator as a consequence of receiving an event,
         *      these changes will only impact future (not current) send 
         *      operations.
         *
         *   <p>Send is not synchronized (and should not be).  Mutliple threads
         *      can safely send from the same Generator, provided the
         *      listeners are prepared for aynsynchronous receives.
         */
        protected final void send(Event event) {
            Listener < Event > [] tmp = listeners;
            for (int i=tmp.length-1; i>=0; --i) tmp[i].receive(event);
        }
        
        private void writeObject(java.io.ObjectOutputStream out)
            throws java.io.IOException
        {
            out.defaultWriteObject();
            Listener <Event> [] tmp = listeners;
            int len=0;
            for (int i=0; i<tmp.length; ++i) {
                if (tmp[i] instanceof java.io.Serializable) {
                    ++len;
                }
            }
            
            out.writeInt(len);
            
            for (int i=0; i<tmp.length; ++i) {
                if (tmp[i] instanceof java.io.Serializable) {
                    out.writeObject(tmp[i]);
                }
            }
        }
        
        @SuppressWarnings("unchecked")
        private void readObject(java.io.ObjectInputStream in)
            throws java.io.IOException, ClassNotFoundException
        {
            in.defaultReadObject();
            int len=in.readInt();
            
            Listener < Event > [] tmp = (Listener < Event > []) 
                ( ( len > 1 ) ? 
                  new Listener < ? > [len] : NONE );
            
            for (int i=0; i<tmp.length; ++i) {
                tmp[i]=( Listener < Event > ) in.readObject();
            }
            
            lock=new Object();
            listeners=tmp;
        }
        
        public Generator() {}
        
        /** To support Cloneable -- this is effectively a shallow copy */
        protected Generator(Generator < Event > copy) {
            listeners=copy.listeners;
        }
        
        /** Effectively, this produces a shallow copy */
        public Object clone() { return new Generator < Event > (this); }
    }
    

    public static final String EOL = IO.EOL;
    public static Close outExpect(Object... args) {
        return IO.outExpectVarArgs(args);
    }
        
    public static Close inProvide(Object... args) {
        return IO.inProvideVarArgs(args);
    }
    public static Close outVerify(String filename) {
        return IO.outVerify(filename);
    }

    public static Close outOpen(String filename) {
        return IO.outOpen(filename);
    }

    public static Close outOpen(File file) {
        return IO.outOpen(file);
    }

    public static void outClose() {
        IO.outClose();
    }

    public static Close inOpen(File file) {
        return IO.inOpen(file);
    }

    public static Close inOpen(String filename) {
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
        IO.printVarArgs(value);
    }

    public static void println(Object... value) {
        IO.printlnVarArgs(value);
    }

    public static String readEOL() {
        return IO.readEOL();
    }
    
    public static String readLine() {
        return IO.readLine();
    }

    public static String readString() {
        return IO.readString();
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

    /** synonym for readInteger */
    public static Integer readInt() {
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

    public static <T> T test(T object) {
        return Run.test(object);
    }
    public static <T> T untest(T object) {
        return object;
    }
    public static <T> T testAlways(T object) {
        return Run.testAlways(object);
    }
    public static <T> T untestAlways(T object) {
        return object;
    }

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

    public static final boolean toBoolean(boolean x) {
        return To.toBoolean(x);
    }
    public static final boolean toBoolean(char x) {
        return To.toBoolean(x);
    }
    public static final boolean toBoolean(CharSequence x) {
        return To.toBoolean(x);
    }
    public static final boolean toBoolean(byte x) {
        return To.toBoolean(x);
    }
    public static final boolean toBoolean(short x) {
        return To.toBoolean(x);
    }
    public static final boolean toBoolean(int x) {
        return To.toBoolean(x);
    }
    public static final boolean toBoolean(long x) {
        return To.toBoolean(x);
    }
    public static final boolean toBoolean(float x) {
        return To.toBoolean(x);
    }
    public static final boolean toBoolean(double x) {
        return To.toBoolean(x);
    }

    public static final char toChar(boolean x) {
        return To.toChar(x);
    }
    public static final char toChar(char x) {
        return To.toChar(x);
    }
    public static final char toChar(CharSequence x) {
        return To.toChar(x);
    }
    public static final char toChar(byte x) {
        return To.toChar(x);
    }
    public static final char toChar(short x) {
        return To.toChar(x);
    }
    public static final char toChar(int x) {
        return To.toChar(x);
    }
    public static final char toChar(long x) {
        return To.toChar(x);
    }
    public static final char toChar(float x) {
        return To.toChar(x);
    }
    public static final char toChar(double x) {
        return To.toChar(x);
    }
        
    public static final byte toByte(boolean x) {
        return To.toByte(x);
    }
    public static final byte toByte(char x) {
        return To.toByte(x);
    }
    public static final byte toByte(CharSequence x) {
        return To.toByte(x);
    }
    public static final byte toByte(byte x) {
        return To.toByte(x);
    }
    public static final byte toByte(short x) {
        return To.toByte(x);
    }
    public static final byte toByte(int x) {
        return To.toByte(x);
    }
    public static final byte toByte(long x) {
        return To.toByte(x);
    }
    public static final byte toByte(float x) {
        return To.toByte(x);
    }
    public static final byte toByte(double x) {
        return To.toByte(x);
    }

    public static final short toShort(boolean x) {
        return To.toShort(x);
    }
    public static final short toShort(char x) {
        return To.toShort(x);
    }
    public static final short toShort(CharSequence x) {
        return To.toShort(x);
    }
    public static final short toShort(byte x) {
        return To.toShort(x);
    }
    public static final short toShort(short x) {
        return To.toShort(x);
    }
    public static final short toShort(int x) {
        return To.toShort(x);
    }
    public static final short toShort(long x) {
        return To.toShort(x);
    }
    public static final short toShort(float x) {
        return To.toShort(x);
    }
    public static final short toShort(double x) {
        return To.toShort(x);
    }

    public static final int toInt(boolean x) {
        return To.toInt(x);
    }
    public static final int toInt(char x) {
        return To.toInt(x);
    }
    public static final int toInt(CharSequence x) {
        return To.toInt(x);
    }
    public static final int toInt(byte x) {
        return To.toInt(x);
    }
    public static final int toInt(short x) {
        return To.toInt(x);
    }
    public static final int toInt(int x) {
        return To.toInt(x);
    }
    public static final int toInt(long x) {
        return To.toInt(x);
    }
    public static final int toInt(float x) {
        return To.toInt(x);
    }
    public static final int toInt(double x) {
        return To.toInt(x);
    }

    public static final long toLong(boolean x) {
        return To.toLong(x);
    }
    public static final long toLong(char x) {
        return To.toLong(x);
    }
    public static final long toLong(CharSequence x) {
        return To.toLong(x);
    }
    public static final long toLong(byte x) {
        return To.toLong(x);
    }
    public static final long toLong(short x) {
        return To.toLong(x);
    }
    public static final long toLong(int x) {
        return To.toLong(x);
    }
    public static final long toLong(long x) {
        return To.toLong(x);
    }
    public static final long toLong(float x) {
        return To.toLong(x);
    }
    public static final long toLong(double x) {
        return To.toLong(x);
    }

    public static final float toFloat(boolean x) {
        return To.toFloat(x);
    }
    public static final float toFloat(char x) {
        return To.toFloat(x);
    }
    public static final float toFloat(CharSequence x) {
        return To.toFloat(x);
    }
    public static final float toFloat(byte x) {
        return To.toFloat(x);
    }
    public static final float toFloat(short x) {
        return To.toFloat(x);
    }
    public static final float toFloat(int x) {
        return To.toFloat(x);
    }
    public static final float toFloat(long x) {
        return To.toFloat(x);
    }
    public static final float toFloat(float x) {
        return To.toFloat(x);
    }
    public static final float toFloat(double x) {
        return To.toFloat(x);
    }

    public static final double toDouble(boolean x) {
        return To.toDouble(x);
    }
    public static final double toDouble(char x) {
        return To.toDouble(x);
    }
    public static final double toDouble(CharSequence x) {
        return To.toDouble(x);
    }
    public static final double toDouble(byte x) {
        return To.toDouble(x);
    }
    public static final double toDouble(short x) {
        return To.toDouble(x);
    }
    public static final double toDouble(int x) {
        return To.toDouble(x);
    }
    public static final double toDouble(long x) {
        return To.toDouble(x);
    }
    public static final double toDouble(float x) {
        return To.toDouble(x);
    }
    public static final double toDouble(double x) {
        return To.toDouble(x);
    }
    
    public static final String toString(boolean x) {
        return To.toString(x);
    }
    public static final String toString(char x) {
        return To.toString(x);
    }
    public static final String toString(byte x) {
        return To.toString(x);
    }
    public static final String toString(short x) {
        return To.toString(x);
    }
    public static final String toString(int x) {
        return To.toString(x);
    }
    public static final String toString(String x) {
        return To.toString(x);
    }
    public static final String toString(float x) {
        return To.toString(x);
    }
    public static final String toString(double x) {
        return To.toString(x);
    }
    public static final String toString(Object x) {
        return To.toString(x);
    }
}
