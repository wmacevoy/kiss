package kiss;

import java.io.Closeable;
import java.io.File;

import java.util.Set;

import kiss.util.IO;
import kiss.util.RNG;
import kiss.util.Run;
import kiss.util.As;

public class API {
    /** Close is like to make things that can be closed, i.e., have a 
        close() method, but that throw no declared exceptions. This is
        used with the outOpen() and inOpen() kiss functions so they are
        automatically closed.  */
    public static interface Close extends AutoCloseable, Closeable {
        void close(); // no exceptions
    }

    /** Objects that implement Listener<Event> can listen to events/messages
        from Objects that extend Generator<Event>.  This interface is automatically used by Generator with the addListener(event->action) or the addListener(onReceiveEvent) methods, so you usually don't use it explicitly.  See the events example for how to use events. */
    public static interface Listener < Event > {
        public void receive(Event event);
    }

    /**  Objects that extend Generator<Event> keep track of other objects
         that listen to them, and provide a send(event) method to send a
         message to all added listeners.

        See the events example for how to use events. */
    public static class Generator < Event > 
        implements java.io.Serializable, Cloneable {
        private Class<Event> type;
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

    
        /**
         *  <p>Add a listener to this generator.  It looks for a method with
         *     the signature  onReceive[Event](Event event) which is inovked
         *     when messages are sent to the listening object.
         */
        @SuppressWarnings("unchecked")
        public void addListener(Object object) {
        kiss.util.AutoListener<Event> listener =
            new kiss.util.AutoListener(type,object);
        addListener(listener);
    }
        
        /**
         *  <p>Remove a listener from this Generator.
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
    }

    /** The line terminator for this operating system.  This is handy
        for generating outExpect(...) sequences to match println()'s. */
    public static final String EOL = IO.EOL;

    /** Create an internal output stream that verifies against an
        expected sequence of output.  Object are separated by
        a single space unless it is an EOL, which expects the
        end-of-line sequence. 

        See the basics example to see how this is used. 
    */
    public static final Close outExpect(Object... args) {
        return IO.outExpectVarArgs(args);
    }
        
    /** Create an internal input stream can be used to read
        values from like inOpen or the console.

        See the basics example to see how this is used. 
    */
    public static final Close inProvide(Object... args) {
        return IO.inProvideVarArgs(args);
    }

    /** Open an external output file which is matched against
        future output.  This can be used to test that certain
        output is produced by an action.  For simple output,
        the outExpect() method is easier to use. */
    public static final Close outVerify(String filename) {
        return IO.outVerify(filename);
    }

    /** Open an output file so future print/ln statments are
        redirected to this file.  The best way to use this is
        a try-with resources like:

        try (Close out = outOpen("myfile.dat")) { ... }

        This insures the file is properly closed even if an
        error occures.
    */
    public static final Close outOpen(String filename) {
        return IO.outOpen(filename);
    }

    /** Like outOpen(String filename), but assumes you are using
        Java's File object to refer to the file. */
    public static final Close outOpen(File file) {
        return IO.outOpen(file);
    }

    /** Close an open output file.  It is better to use the try-with-resources
        notation so you don't explicitly have to call this at all. */
    public static final void outClose() {
        IO.outClose();
    }

    /** Open an input file so future readXXX() statments are
        read from file.  The best way to use this is
        a try-with resources like:

        try (Close in = inOpen("myfile.dat")) { ... }

        This insures the file is properly closed even if an
        error occures.
    */
    public static final Close inOpen(String filename) {
        return IO.inOpen(filename);
    }

    /** Like inOpen(String filename), but assumes you are using
        Java's File object to refer to the file. */
    public static final Close inOpen(File file) {
        return IO.inOpen(file);
    }


    /** Close an open input file.  It is better to use the try-with-resources
        notation so you don't explicitly have to call this at all. */
    public static final void inClose() {
        IO.inClose();
    }

    public static final void format(StringBuilder ans, boolean value) {
        IO.format(ans, value);
    }

    public static final void format(StringBuilder ans, byte value) {
        IO.format(ans, value);
    }

    public static final void format(StringBuilder ans, char value) {
        IO.format(ans, value);
    }

    public static final void format(StringBuilder ans, short value) {
        IO.format(ans, value);
    }

    public static final void format(StringBuilder ans, int value) {
        IO.format(ans, value);
    }

    public static final void format(StringBuilder ans, long value) {
        IO.format(ans, value);
    }

    public static final void format(StringBuilder ans, float value) {
        IO.format(ans, value);
    }

    public static final void format(StringBuilder ans, double value) {
        IO.format(ans, value);
    }

    public static final void format(StringBuilder ans, Object value) {
        IO.format(ans, value);
    }

    public static final String format(boolean value) {
        return IO.format(value);
    }

    public static final String format(byte value) {
        return IO.format(value);
    }

    public static final String format(char value) {
        return IO.format(value);
    }

    public static final String format(short value) {
        return IO.format(value);
    }

    public static final String format(int value) {
        return IO.format(value);
    }

    public static final String format(long value) {
        return IO.format(value);
    }

    public static final String format(float value) {
        return IO.format(value);
    }

    public static final String format(double value) {
        return IO.format(value);
    }

    public static final String format(Object value) {
        return IO.format(value);
    }

    public static final void print(Object value) {
        IO.print(value);
    }

    public static final void print(boolean value) {
        IO.print(value);
    }

    public static final void print(byte value) {
        IO.print(value);
    }

    public static final void print(char value) {
        IO.print(value);
    }

    public static final void print(short value) {
        IO.print(value);
    }

    public static final void print(int value) {
        IO.print(value);
    }

    public static final void print(long value) {
        IO.print(value);
    }

    public static final void print(float value) {
        IO.print(value);
    }

    public static final void print(double value) {
        IO.print(value);
    }

    public static final void println(Object value) {
        IO.println(value);
    }

    public static final void println(boolean value) {
        IO.println(value);
    }

    public static final void println(byte value) {
        IO.println(value);
    }

    public static final void println(char value) {
        IO.println(value);
    }

    public static final void println(short value) {
        IO.println(value);
    }

    public static final void println(int value) {
        IO.println(value);
    }

    public static final void println(long value) {
        IO.println(value);
    }

    public static final void println(float value) {
        IO.println(value);
    }

    public static final void println(double value) {
        IO.println(value);
    }

    public static final void print(Object... value) {
        IO.printVarArgs(value);
    }

    public static final void println(Object... value) {
        IO.printlnVarArgs(value);
    }

    public static final String readEOL() {
        return IO.readEOL();
    }
    
    public static final String readLine() {
        return IO.readLine();
    }

    public static final String readString() {
        return IO.readString();
    }

    public static final Boolean readBoolean() {
        return IO.readBoolean();
    }

    public static final Byte readByte() {
        return IO.readByte();
    }

    public static final Integer readInteger() {
        return IO.readInteger();
    }

    /** synonym for readInteger */
    public static final Integer readInt() {
        return IO.readInteger();
    }
    
    public static final Long readLong() {
        return IO.readLong();
    }

    public static final Float readFloat() {
        return IO.readFloat();
    }

    public static final Double readDouble() {
        return IO.readDouble();
    }

    /** seed the kiss random-number generator with a strong random source.
        This is done automatically before invoking run(). */
    public static final void seed() {
        RNG.seed();
    }

    /** seed the random-number generator for a specific sequence.  Changing the seed will change the sequence, but the same seed produces the same sequence.  The kiss random number generator is reset with seed(1.0) before invoking each testXXX to help make reproducable results. */
    public static final void seed(double value) {
        RNG.seed(value);
    }

    /** Ex: int dieRoll = random(1,6); You can change or reset the random
        sequence using the seed() or seed(value) methods. */
    public static final int random(int a, int b) {
        return RNG.random(a, b);
    }

    /** The value is uniform on the interval [0,1). */
    public static final double random() {
        return RNG.random();
    }

    /** The command-line arguments the application got (except those
        used by the kiss.util.Run method) */
    public static String[] APP_ARGS; // updated by Run.main

    /** The name of the class.  This is usually just "App", but
        might be something else if the "--app <app>" command line
        argument is passed to kiss.util.Run */
    public static String APP_NAME; // updated by Run.main

    /** This is a refrence the single main App object, just
        in case you need it somewhere else in your program. */
    public static Object APP; // updated by Run.main

    /** Test an object, invoking all the testXXX() methods in order of
        declaration.  This is only done once (if the class of the
        object hasn't already been tested), and a reference to the
        tested object is returned, so this pattern:

<pre>
        void testParts() {
           Part1 part1 = test(new Part1());
           Part2 part2 = test(new Part2());
        }
</pre>

        Will test all the parts in app.
    */
    public static final <T> T test(T object) {
        return Run.test(object);
    }

    /** Does nothing.  This makes it easy to stop/start testing an object:
        just use un/test(object) */
    public static final <T> T untest(T object) {
        return object;
    }

    /** Like test(obj), but always runs tests.  This can create
        a lot of redundant tests. */
    public static final <T> T testAlways(T object) {
        return Run.testAlways(object);
    }

    /** Does nothing.  This makes it easy to stop/start always testing
        an object: just use un/testAlways(object) */
    public static final <T> T untestAlways(T object) {
        return object;
    }

    /** Pause application for given duration in seconds. */
    public static final void pause(double duration) {
        Run.pause(duration);
    }

    /** Get time since epoch in 1970 in seconds.  This is handy for
     *  timing how long something takes:
     *  
     *<pre>
     *  double t = time();
     *  ...
     *  t = t-time(); // 
     *  println("... took " + t + " seconds");
     *</pre>
     */
    public static final double time() {
        return Run.time();
    }

    public static final double E = Math.E;
    public static final double PI = Math.PI;

    public static final double abs(double a) {
        return Math.abs(a);
    }

    public static final float abs(float a) {
        return Math.abs(a);
    }

    public static final int abs(int a) {
        return Math.abs(a);
    }

    public static final long abs(long a) {
        return Math.abs(a);
    }

    /** Ex: acos(0)==PI/2, toDegrees(acos(0))==90. */
    public static final double acos(double a) {
        return Math.acos(a);
    }

    /** Ex: asin(1.0)==PI/2.0, toDegrees(asin(1.0))==90. */
    public static final double asin(double a) {
        return Math.asin(a);
    }

    /** Ex: atan(1.0)==PI/4.0; toDegrees(atan(1.0))==45. */
    public static final double atan(double a) {
        return Math.atan(a);
    }

    /** Ex: atan2(0.0,-1.0) == PI; toDegrees(atan2(0.0,-1.0))==180. */    
    public static final double atan2(double y, double x) {
        return Math.atan2(y, x);
    }

    /** Cube root.  Ex: cbrt(1000) == 10. */
    public static final double cbrt(double a) {
        return Math.cbrt(a);
    }

    /** Ceiling (smallest integer >= a): Ex: ceil(-3.5)==-3, ceil(3.5)==4. */
    public static final double ceil(double a) {
        return Math.ceil(a);
    }


    /** Obscure part of Math, here for completeness. */
    public static final double copySign(double magnitude, double sign) {
        return Math.copySign(magnitude,sign);
    }

    /** Obscure part of Math, here for completeness. */
    public static final float copySign(float magnitude, float sign) {
        return Math.copySign(magnitude, sign);
    }

    /** Ex: cos(PI) == -1.0, cos(toRadians(180)) == -1.0 */
    public static final double cos(double a) {
        return Math.cos(a);
    }

    /** Hyperbolic cosine */
    public static final double cosh(double x) {
        return Math.cosh(x);
    }

    /** Natural exponent, exp(x)==pow(E,x). */
    public static final double exp(double a) {
        return Math.exp(a);
    }

    /** Obscure part of Math, here for completeness. */
    public static final double expm1(double x) {
        return Math.expm1(x);
    }

    /** Floor (largest integer <= a): Ex: floor(-3.5)==-4, floor(3.5)==3. */
    public static final double floor(double a) {
        return Math.floor(a);
    }

    /** Obscure part of Math, here for completeness. */    
    public static final int getExponent(double d) {
        return Math.getExponent(d);
    }

    /** Obscure part of Math, here for completeness. */
    public static final int getExponent(float f) {
        return Math.getExponent(f);
    }

    /** Obscure part of Math, here for completeness. */
    public static final double hypot(double x, double y) {
        return Math.hypot(x, y);
    }

    /** Obscure part of Math, here for completeness. */
    public static final double IEEEremainder(double f1, double f2) {
        return Math.IEEEremainder(f1,f2);
    }

    /** Natural log: Ex: log(E) == 1.0, */
    public static final double log(double a) {
        return Math.log(a);
    }

    /** Log base 10: Ex: log(1000.0) == 3.0 */
    public static final double log10(double a) {
        return Math.log10(a);
    }

    /** Obscure part of Math, here for completeness. */    
    public static final double log1p(double x) {
        return Math.log1p(x);
    }

    public static final double max(double a, double b) {
        return Math.max(a, b);
    }

    public static final float max(float a, float b) {
        return Math.max(a, b);
    }

    public static final int max(int a, int b) {
        return Math.max(a, b);
    }

    public static final long max(long a, long b) {
        return Math.max(a, b);
    }

    public static final double min(double a, double b) {
        return Math.min(a, b);
    }

    public static final float min(float a, float b) {
        return Math.min(a, b);
    }

    public static final int min(int a, int b) {
        return Math.min(a, b);
    }

    public static final long min(long a, long b) {
        return Math.min(a, b);
    }

    /** Obscure part of Math, here for completeness. */
    public static final double nextAfter(double start, double direction) {
        return Math.nextAfter(start, direction);
    }

    /** Obscure part of Math, here for completeness. */
    public static final float nextAfter(float start, double direction) {
        return Math.nextAfter(start, direction);
    }

    /** Obscure part of Math, here for completeness. */
    public static final double nextUp(double d) {
        return Math.nextUp(d);
    }

    /** Obscure part of Math, here for completeness. */
    public static final float nextUp(float f) {
        return Math.nextUp(f);
    }

    /** a raised to the power b. Ex: pow(10,3)==1000. */
    public static final double pow(double a, double b) {
        return Math.pow(a, b);
    }

    // public static final double random() { return Math.random(); }
    /** like round() but leaves result as double for very large values */
    public static final double rint(double a) {
        return Math.rint(a);
    }

    /** Nearest value */
    public static final long round(double a) {
        return Math.round(a);
    }

    /** Nearest value */    
    public static final int round(float a) {
        return Math.round(a);
    }

    /** Obscure part of Math, here for completeness. */
    public static final double scalb(double d, int scaleFactor) {
        return Math.scalb(d, scaleFactor);
    }

    /** Obscure part of Math, here for completeness. */    
    public static final float scalb(float f, int scaleFactor) {
        return Math.scalb(f, scaleFactor);
    }

    /** signum is -1,0, or 1, depending on the sign of d */
    public static final double signum(double d) {
        return Math.signum(d);
    }

    /** signum is -1,0, or 1, depending on the sign of f */    
    public static final float signum(float f) {
        return Math.signum(f);
    }

    /** Ex: sin(PI/2) == 1.0 and sin(toRadians(90)) == 1.0 */
    public static final double sin(double a) {
        return Math.sin(a);
    }

    /** Hyperbolic sine */
    public static final double sinh(double x) {
        return Math.sinh(x);
    }

    /** Square root.  Ex: sqrt(9) == 3 */
    public static final double sqrt(double a) {
        return Math.sqrt(a);
    }

    /** Tangent.  Ex: tan(PI/4)==1.0, tan(toRadians(45))==1.0 */
    public static final double tan(double a) {
        return Math.tan(a);
    }

    /** Hyperbolic tangent. */
    public static final double tanh(double x) {
        return Math.tanh(x);
    }

    /** Convert radian angle (0..2*PI) to degree angle (0..360) */
    public static final double toDegrees(double angrad) {
        return Math.toDegrees(angrad);
    }

    /** Convert degree angle (0..360) to radian angle (0..2*PI) */    
    public static final double toRadians(double angdeg) {
        return Math.toRadians(angdeg);
    }

    /** Obscure part of Math, here for completeness. */
    public static final double ulp(double d) {
        return Math.ulp(d);
    }

    /** Obscure part of Math, here for completeness. */
    public static final float ulp(float f) {
        return Math.ulp(f);
    }

    /** for completeness */
    public static final boolean asBoolean(boolean x) {
        return As.asBoolean(x);
    }

    /** Unicode codepoint 0, '\u0000' is false.  Everything else is true */
    public static final boolean asBoolean(char x) {
        return As.asBoolean(x);
    }

    /** Null, empty, or exactly "false" is false.  Everything else is true */
    public static final boolean asBoolean(CharSequence x) {
        return As.asBoolean(x);
    }

    /** x != 0 */
    public static final boolean asBoolean(byte x) {
        return As.asBoolean(x);
    }

    /** x != 0 */    
    public static final boolean asBoolean(short x) {
        return As.asBoolean(x);
    }

    /** x != 0 */    
    public static final boolean asBoolean(int x) {
        return As.asBoolean(x);
    }

    /** x != 0 */    
    public static final boolean asBoolean(long x) {
        return As.asBoolean(x);
    }

    /** x != 0 */    
    public static final boolean asBoolean(float x) {
        return As.asBoolean(x);
    }

    /** x != 0 */    
    public static final boolean asBoolean(double x) {
        return As.asBoolean(x);
    }

    /** Codepoint 0, '\u0000' for false, codepoint 1, '\u0001' for true. */
    public static final char asChar(boolean x) {
        return As.asChar(x);
    }

    /** for completeness */
    public static final char asChar(char x) {
        return As.asChar(x);
    }

    /** Codepoint 0, '\u0000' for null or empty sequence.  Otherwise first char of sequence */
    public static final char asChar(CharSequence x) {
        return As.asChar(x);
    }

    /** ((char) x) */
    public static final char asChar(byte x) {
        return As.asChar(x);
    }

    /** ((char) x) */    
    public static final char asChar(short x) {
        return As.asChar(x);
    }

    /** ((char) x) */    
    public static final char asChar(int x) {
        return As.asChar(x);
    }

    /** ((char) x) */    
    public static final char asChar(long x) {
        return As.asChar(x);
    }

    /** ((char) x) */    
    public static final char asChar(float x) {
        return As.asChar(x);
    }

    /** ((char) x) */    
    public static final char asChar(double x) {
        return As.asChar(x);
    }

    /** 0 or 1 */
    public static final byte asByte(boolean x) {
        return As.asByte(x);
    }

    /** ((byte) x) */        
    public static final byte asByte(char x) {
        return As.asByte(x);
    }

    /** Like byte literal. Ex: asByte("22")==22, asByte("0b1111_1011")==((byte) 0b1111_1011), asByte("0xFF")==((byte) 0xFF) */
    public static final byte asByte(CharSequence x) {
        return As.asByte(x);
    }

    /** ((byte) x) */            
    public static final byte asByte(byte x) {
        return As.asByte(x);
    }

    /** ((byte) x) */            
    public static final byte asByte(short x) {
        return As.asByte(x);
    }

    /** ((byte) x) */            
    public static final byte asByte(int x) {
        return As.asByte(x);
    }

    /** ((byte) x) */            
    public static final byte asByte(long x) {
        return As.asByte(x);
    }

    /** ((byte) x) */            
    public static final byte asByte(float x) {
        return As.asByte(x);
    }

    /** ((byte) x) */
    public static final byte asByte(double x) {
        return As.asByte(x);
    }

    /** 0 or 1 */
    public static final short asShort(boolean x) {
        return As.asShort(x);
    }

    /** ((short) x) */
    public static final short asShort(char x) {
        return As.asShort(x);
    }

    /** Like short literal. Ex: asShort("32_767")==((short) 32_767) */
    public static final short asShort(CharSequence x) {
        return As.asShort(x);
    }

    /** ((short) x) */    
    public static final short asShort(byte x) {
        return As.asShort(x);
    }

    /** ((short) x) */    
    public static final short asShort(short x) {
        return As.asShort(x);
    }

    /** ((short) x) */    
    public static final short asShort(int x) {
        return As.asShort(x);
    }

    /** ((short) x) */    
    public static final short asShort(long x) {
        return As.asShort(x);
    }

    /** ((short) x) */    
    public static final short asShort(float x) {
        return As.asShort(x);
    }

    /** ((short) x) */    
    public static final short asShort(double x) {
        return As.asShort(x);
    }

    /** 0 or 1 */
    public static final int asInt(boolean x) {
        return As.asInt(x);
    }

    /** ((int) x) */
    public static final int asInt(char x) {
        return As.asInt(x);
    }

    /** Like int literal. Ex: asInt("32_767")==32_767, asInt("0xffff_ffff") = 0xffff_ffff */
    public static final int asInt(CharSequence x) {
        return As.asInt(x);
    }

    /** ((int) x) */    
    public static final int asInt(byte x) {
        return As.asInt(x);
    }

    /** ((int) x) */    
    public static final int asInt(short x) {
        return As.asInt(x);
    }

    /** ((int) x) */    
    public static final int asInt(int x) {
        return As.asInt(x);
    }

    /** ((int) x) */    
    public static final int asInt(long x) {
        return As.asInt(x);
    }

    /** ((int) x) */    
    public static final int asInt(float x) {
        return As.asInt(x);
    }

    /** ((int) x) */    
    public static final int asInt(double x) {
        return As.asInt(x);
    }

    /** 0 or 1 */
    public static final long asLong(boolean x) {
        return As.asLong(x);
    }

    /** ((long) x) */
    public static final long asLong(char x) {
        return As.asLong(x);
    }

    /** Like long literal. Ex: asLong("100_000_000_000")==100_000_000_000L */
    public static final long asLong(CharSequence x) {
        return As.asLong(x);
    }

    /** ((long) x) */    
    public static final long asLong(byte x) {
        return As.asLong(x);
    }

    /** ((long) x) */    
    public static final long asLong(short x) {
        return As.asLong(x);
    }

    /** ((long) x) */    
    public static final long asLong(int x) {
        return As.asLong(x);
    }

    /** ((long) x) */    
    public static final long asLong(long x) {
        return As.asLong(x);
    }

    /** ((long) x) */    
    public static final long asLong(float x) {
        return As.asLong(x);
    }

    /** ((long) x) */    
    public static final long asLong(double x) {
        return As.asLong(x);
    }

    public static final float asFloat(boolean x) {
        return As.asFloat(x);
    }
    public static final float asFloat(char x) {
        return As.asFloat(x);
    }
    public static final float asFloat(CharSequence x) {
        return As.asFloat(x);
    }
    public static final float asFloat(byte x) {
        return As.asFloat(x);
    }
    public static final float asFloat(short x) {
        return As.asFloat(x);
    }
    public static final float asFloat(int x) {
        return As.asFloat(x);
    }
    public static final float asFloat(long x) {
        return As.asFloat(x);
    }
    public static final float asFloat(float x) {
        return As.asFloat(x);
    }
    public static final float asFloat(double x) {
        return As.asFloat(x);
    }

    public static final double asDouble(boolean x) {
        return As.asDouble(x);
    }
    public static final double asDouble(char x) {
        return As.asDouble(x);
    }
    public static final double asDouble(CharSequence x) {
        return As.asDouble(x);
    }
    public static final double asDouble(byte x) {
        return As.asDouble(x);
    }
    public static final double asDouble(short x) {
        return As.asDouble(x);
    }
    public static final double asDouble(int x) {
        return As.asDouble(x);
    }
    public static final double asDouble(long x) {
        return As.asDouble(x);
    }
    public static final double asDouble(float x) {
        return As.asDouble(x);
    }
    public static final double asDouble(double x) {
        return As.asDouble(x);
    }
    
    public static final String asString(boolean x) {
        return As.asString(x);
    }
    public static final String asString(char x) {
        return As.asString(x);
    }
    public static final String asString(byte x) {
        return As.asString(x);
    }
    public static final String asString(short x) {
        return As.asString(x);
    }
    public static final String asString(int x) {
        return As.asString(x);
    }
    public static final String asString(String x) {
        return As.asString(x);
    }
    public static final String asString(float x) {
        return As.asString(x);
    }
    public static final String asString(double x) {
        return As.asString(x);
    }
    public static final String asString(Object x) {
        return As.asString(x);
    }
}
