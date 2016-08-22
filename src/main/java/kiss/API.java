package kiss;

import java.io.Closeable;
import java.io.File;

import java.util.Set;

import kiss.util.IO;
import kiss.util.RNG;
import kiss.util.Run;
import kiss.util.As;

/** 
 <p>The kiss library promotes good software development practices from the beginning.</p>

<p>The key principles are:
  <ul>
  <li>simple
  <li>testable
  <li>unsurpising
  </ul>
</p>


<p><strong>Simple.</strong>  Here is Hello World (specify {@see kiss.util.Run#main} as the main class):
<pre><code>
import static kiss.API.*;

class App {
  void run() {
    println("Hello World!");
  }
}
</code></pre>
</p>

<p><strong>Testable.</strong> Here is hello world, tested:
<pre><code>
import static kiss.API.*;

class App {
  void testRun() {
    outExpect("Hello World!");
    run();
    outClose();
  }
  void run() {
    println("Hello World!");
  }
}
</code></pre>
</p>

<p>A safer habit is to use try-with-resources:
<pre><code>
import static kiss.API.*;

class App {
  void testRun() {
    try (Close out=outExpect("Hello World!")) {
      run();
    }
  }
  void run() {
    println("Hello World!");
  }
}
</code></pre>
</p>

<strong>Unsurprising.</strong>  Which of the following seems easier to read or understand:

<table>
<tr>
<td>
<pre><code>
import static kiss.API.*;

class App {
  void hi() {
    print("What is your name? ");
    String name = readLine();
    println("Hello, " + name + "!");
  }

  void dice(int n) {
    int sum=0;
    for (int i=1; i&lt;=n; ++i) {
      int die = random(1,6);
      sum = sum + die;
      println("roll # " + i + " is " + die);
    }
    println("average: " + (asDouble(sum)/asDouble(n)));
  }

  void run() {
    hi();
    outOpen("dice.dat");
    dice(100);
    outClose();
  }
}
</code></pre>
</td>
<td>
<pre><code>
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class App {
  void hi() {
     Scanner scanner = new Scanner(System.in);
     System.out.print("What is your name? ");
     String name = scanner.nextLine();
     System.out.println("Hello, " + name + "!");
  }

  void dice(PrintWriter out, int n) {
    int sum = 0;
    for (int i=1; i&lt;=n; ++i) {
      int die = (int) Math.floor(6*Math.random())+1;
      sum = sum + die;
      out.println("roll # " + i + " is " + die);
    }
    out.println("average: " + ((double)(sum)/((double)n)));
  }

  public static void main(String [] args) {
      new App().run();
  }
  
  void run() {
    hi();

    try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("dice.dat")))) {
      dice(out,100);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
</code></pre>
</td>
</tr>
</table>

<p>The traditional version on the right is almost impossible to test.</p>

<p>The kiss version on the right is easy to test.  Below are two versions: the left uses nested open/close, while the version on the right uses try-with-resources.</p>

<table>
<tr>
<td>
<code><pre>
import static kiss.API.*;

class App {
  void hi() {
    print("What is your name? ");
    String name = readLine();
    println("Hello, " + name + "!");
  }
  void testHi()
  {
     inProvide("Alice");
     outExpect("What is your name? Hello, Alice!");
     hi();
     outClose();
     inClose();
  }

  void dice(int n) {
    int sum=0;
    for (int i=1; i&lt;=n; ++i) {
      int die = random(1,6);
      sum = sum + die;
      println("roll # " + i + " is " + die);
    }
    println("average: " + (asDouble(sum)/asDouble(n)));
  }

  void testDice() {
    seed(1);
    int die1 = random(1,6);
    int die2 = random(1,6);
    double average 
      = asDouble(die1+die2)/asDouble(2);

    seed(1);
    outExpect("roll # 1 is " + die1, EOL,
              "roll # 2 is " + die2, EOL,
              "average: " + average);
    dice(2);
    outClose();
  }

  void run() {
    hi();
    outOpen("dice.dat");
    dice(100);
    outClose();
  }
}
</code></pre>
</td><td>
<pre><code>
import static kiss.API.*;

class App {
  void hi() {
    print("What is your name? ");
    String name = readLine();
    println("Hello, " + name + "!");
  }
  void testHi()
  {
     try (Close in=inProvide("Alice")) {
       try (Close out=outExpect("What is your name? Hello, Alice!")) {
         hi();
       }
     }
  }

  void dice(int n) {
    int sum=0;
    for (int i=1; i&lt;=n; ++i) {
      int die = random(1,6);
      sum = sum + die;
      println("roll # " + i + " is " + die);
    }
    println("average: " + (asDouble(sum)/asDouble(n)));
  }

  void testDice() {
    seed(1);
    int die1 = random(1,6);
    int die2 = random(1,6);
    double average 
      = asDouble(die1+die2)/asDouble(2);

    seed(1);
    try (Close out = outExpect(
              "roll # 1 is " + die1, EOL,
              "roll # 2 is " + die2, EOL,
              "average: " + average)) {
      dice(2);
    }
  }

  void run() {
    hi();
    try (Close out=outOpen("dice.dat")) {
      dice(100);
    }
  }
}
</code></pre>
</tr></table>

 */
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

    /** 
        <p>Format a boolean (true or false) value for output.  This prints the characters "true" or "false".  If this is the last thing on a line, use {@see #println(boolean)} instead.</p>

<p>For example:
<blockquote><pre><code>
import static kiss.API.*;

class App {
  void run() {
    boolean value = true;
    print("value=");
    print(value);
    println(".");
  }
}
</code></pre></blockquote>
This prints <code>value=true.</code> on a single line.
</p>
    */
    public static final void print(boolean value) {
        IO.print(value);
    }

    /** 
        <p>Format a byte (-128 to 127) value for output.  This prints the decimal version of the value.  If this is the last thing on a line, use {@see #println(byte)} instead.</p>

<p>For example:
<blockquote><pre><code>
import static kiss.API.*;

class App {
  void run() {
    byte value = asByte(100);
    print("value=");
    print(value);
    println(".");
  }
}
</code></pre></blockquote>
This prints <code>value=100.</code> on a single line.
</p>
    */
    public static final void print(byte value) {
        IO.print(value);
    }

    /** 
        <p>Format a unicode code point (single letters and symbols from almost every languages) in the range 0 to 65535.  This prints the character version of the value.  This may look like a box if the character does not have a representation in the current font, or a few special formatting actions; like '\n' (code 12) often starts a new line.  If this is the last thing on a line, use {@see #println(char)} instead.</p>

<p><i>A detail beginners do not have to worry about.</i>
Modern unicode includes code points that fall outside the range 0..65535.  These will not fit onto a Java char.  This means they cannot be represented as a single java char value.  You should look at the API in {@see java.lang.String} to deal with multi-char unicode.  If you are just getting started don't worry about it; the first 65_536 codes (0..65535) that can be represented as a single java char cover the vast variety of modern languages.
</p>

<p>For example:
<blockquote><pre><code>
import static kiss.API.*;

class App {
  void run() {
    char value = 'x';
    print("value=");
    print(value);
    println(".");
  }
}
</code></pre></blockquote>
This prints <code>value=x.</code> on a single line.
</p>
    */
    public static final void print(char value) {
        IO.print(value);
    }

    /** 
        <p>Format a short (-32_768 to 32767) value for output.  This prints the decimal version of the value.  If this is the last thing on a line, use {@see #println(short)} instead.</p>

<p>For example:
<blockquote><pre><code>
import static kiss.API.*;

class App {
  void run() {
    short value = asShort(10_0000);
    print("value=");
    print(value);
    println(".");
  }
}
</code></pre></blockquote>
This prints <code>value=10000.</code> on a single line.
</p>
    */
    public static final void print(short value) {
        IO.print(value);
    }

    /** 
        <p>Print an int (-2_147_483_648 to 2_147_483_647) value.  This prints the decimal version of the value.  If this is the last thing on a line, use {@see #println(int)} instead.</p>

<p>For example:
<blockquote><pre><code>
import static kiss.API.*;

class App {
  void run() {
    int value = 10_000_000;
    print("value=");
    print(value);
    println(".");
  }
}
</code></pre></blockquote>
This prints <code>value=10000000.</code> on a single line.
</p>
    */
    public static final void print(int value) {
        IO.print(value);
    }

    /** 
        <p>Print a long (-9_223_372_036_854_775_808 to 9_223_372_036_854_775_807) value.  This prints the decimal version of the value.  If this is the last thing on a line, use {@see #println(long)} instead.</p>

<p>For example:
<blockquote><pre><code>
import static kiss.API.*;

class App {
  void run() {
    long value = 10_000_000_000L; // use L for "long literal"
    print("value=");
    print(value);
    println(".");
  }
}
</code></pre></blockquote>
This prints <code>value=10000000000.</code> on a single line.
</p>
    */
    public static final void print(long value) {
        IO.print(value);
    }

    /** 
        <p>Print a float (about seven decimal digits of precision) value.  If this is the last thing on a line, use {@see #println(float)} instead.</p>

<p>For example:
<blockquote><pre><code>
import static kiss.API.*;

class App {
  void run() {
    float value = 3.1415F; // use F for "float literal"
    print("value=");
    print(value);
    println(".");
  }
}
</code></pre></blockquote>
This prints <code>value=3.1415.</code> on a single line.
</p>
    */
    public static final void print(float value) {
        IO.print(value);
    }

    /** 
        <p>Print a double (about fifteen decimal digits of precision) value for output. If this is the last thing on a line, use {@see #println(double)} instead.</p>

<p>For example:
<blockquote><pre><code>
import static kiss.API.*;

class App {
  void run() {
    double value = PI;
    print("value=");
    print(value);
    println(".");
  }
}
</code></pre></blockquote>
This prints <code>value=3.141592653589793.</code> on a single line.
</p>
    */
    public static final void print(double value) {
        IO.print(value);
    }

    /** <p>Prints an object.  This is a lot like printing the {@see Object#toString()}, but tries to format arrays and lists into nicer strucures.  If this is the last thing on a line, use {@see println(Object)} instead. </p>
<p>For example:
<blockquote><pre><code>
import static kiss.API.*;

class App {
  void run() {
    Object value = new int[] {2,5,6};
    print("value=");
    print(value);
    println(".");
  }
}
</code></pre></blockquote>
This prints <code>value=[2,5,6].</code> on a single line.
</p>
    */
    public static final void print(Object value) {
        IO.print(value);
    }

    /** {@see print(boolean)} with a newline. */
    public static final void println(boolean value) {
        IO.println(value);
    }

    /** {@see print(byte)} with a newline. */    
    public static final void println(byte value) {
        IO.println(value);
    }

    /** {@see print(char)} with a newline. */        
    public static final void println(char value) {
        IO.println(value);
    }

    /** {@see print(short)} with a newline. */            
    public static final void println(short value) {
        IO.println(value);
    }

    /** {@see print(int)} with a newline. */                
    public static final void println(int value) {
        IO.println(value);
    }

    /** {@see print(long)} with a newline. */                    
    public static final void println(long value) {
        IO.println(value);
    }

    /** {@see print(float)} with a newline. */                        
    public static final void println(float value) {
        IO.println(value);
    }

    /** {@see print(double)} with a newline. */                            
    public static final void println(double value) {
        IO.println(value);
    }

    /** {@see print(Object)} with a newline. */                                public static final void println(Object value) {
        IO.println(value);
    }

    /** Prints all the listed items, 
        separated with a blank ' ' character */
    public static final void print(Object... value) {
        IO.printVarArgs(value);
    }

    /** Prints all the listed items, 
        separated with a blank ' ' character, follwed by a newline */
    public static final void println(Object... value) {
        IO.printlnVarArgs(value);
    }

    /** <p>Format arguments like {@see PrintStream.printf()}.  This is especially helpful for formatting numbers.</p>

    <p>For example:
<blockquote><pre><code>
import static kiss.API.*;

class App {
  void run() {
    outExpect("|0100| 3.142|");
    printf("|%04x|%6.3f|",256,PI);
    outClose();
  }
}
</code></pre></blockquote>
</p>
*/
    public static final void printf(CharSequence fmt, Object... value) {
        IO.printfVarArgs(fmt,value);
    }

    /**
       <p>Read in an end-of-line.  This is helpful in reading
       information into your program when it is broken into
       multiple lines.</p>

    <p>For example:
<blockquote><pre><code>
import static kiss.API.*;

class App {
  void run() {
    int i;
    double d;
    String s;
    inProvide(13,1.3,"thirteen", EOL,
              "next line");
    assert readInt() == 13;
    assert readDouble() == 1.3;
    assert readString().equals("thirteen");
    assert readEOL().equals(EOL);
    assert readLine().equals("next line");
    inClose();
  }
}
</code></pre></blockquote>
</p>
*/
    public static final String readEOL() {
        return IO.readEOL();
    }

    /** <p>Read from the input everything to the next end-of-line
        and return it as a string (with the end-of-line marker
        removed.</p>
    <p>For example:
<blockquote><pre><code>
import static kiss.API.*;

class App {
  void run() {
    print("What is your sport? ");
    String sport = readLine();
    println("Go " + sport + "!");
  }
}
</code></pre></blockquote>
</p>
    */
    public static final String readLine() {
        return IO.readLine();
    }

    /** <p>Read in a simple string (no white space) and return it
        as a String.</p>

<p>For example:
<blockquote><pre><code>
import static kiss.API.*;

class App {
  void run() {

//  without the inProvide/inClose, this
//  would just ask the user.

    inProvide("tango salsa cha-cha");
    String dance1 = readString();
    String dance2 = readString();
    String dance3 = readString();
    inClose();

    println("dance # 1:",dance1);
    println("dance # 2:",dance2);
    println("dance # 3:",dance3);
  }
}
</code></pre></blockquote>
</p>
    */
    public static final String readString() {
        return IO.readString();
    }

    /** 
<p>Read a boolean (true/false) value.</p>
<p>For example:
<blockquote><pre><code>
import static kiss.API.*;

class App {
  void testReadBoolean() {
    try (Close in = inProvide("true")) {
      assert readBoolean() == true;
    }
    try (Close in = inProvide("false")) {
      assert readBoolean() == false;
    }
  }
}
</code></pre></blockquote>
</p>
    */
    public static final Boolean readBoolean() {
        return IO.readBoolean();
    }

    /** 
<p>Read a boolean (true/false) value.</p>
<p>For example:
<blockquote><pre><code>
import static kiss.API.*;

class App {
  void testReadByte() {
    try (Close in = inProvide("100")) {
       assert readByte() == 100;
    }
  }
}
</code></pre></blockquote>
</p>
    */
    public static final Byte readByte() {
        return IO.readByte();
    }

    /** 
<p>Read a int (-2_147_483_648 to 2_147_483_647) value.</p>
<p>For example:
<blockquote><pre><code>
import static kiss.API.*;

class App {
  void testReadByte() {
    try (Close in = inProvide("10000000")) {
       assert readInt() == 100_000_000;
    }
  }
}
</code></pre></blockquote>
</p>
    */
    public static final Integer readInteger() {
        return IO.readInteger();
    }

    /** synonym for readInteger */
    public static final Integer readInt() {
        return IO.readInteger();
    }
    
    /** 
<p>Read a long (-9_223_372_036_854_775_808 to 9_223_372_036_854_775_807) value.</p>
<p>For example:
<blockquote><pre><code>
import static kiss.API.*;

class App {
  void testReadByte() {
    try (Close in = inProvide("10000000000")) {
       assert readLong() == 100_000_000_000L;
    }
  }
}
</code></pre></blockquote>
</p>
    */
    public static final Long readLong() {
        return IO.readLong();
    }

    /** 
<p>Read a float (about seven decimal digits of precision) value.</p>
<p>For example:
<blockquote><pre><code>
import static kiss.API.*;

class App {
  void testReadByte() {
    try (Close in = inProvide("3.14")) {
       assert readFloat() == 3.14F;
    }
  }
}
</code></pre></blockquote>
</p>
    */
    public static final Float readFloat() {
        return IO.readFloat();
    }

    /** 
<p>Read a double (about fifteen decimal digits of precision) value.</p>
<p>For example:
<blockquote><pre><code>
import static kiss.API.*;

class App {
  void testReadByte() {
    try (Close in = inProvide("3.141592653589793")) {
       assert readDouble() == PI;
    }
  }
}
</code></pre></blockquote>
</p>
    */
    public static final Double readDouble() {
        return IO.readDouble();
    }

    /** Seed the kiss random-number generator with a strong random source.

        The random number generator is automatically seeded this way before run(), so you normally don't need to explicitly use this.  But if you want real randomness in a test, they normally are seeded with seed(1). 

<p>For example:
<blockquote><pre><code>
import static kiss.API.*;

class App {
  void testRepeats() {
  // seed(1) is used before every test for repeatability
    int x1 = random(1,100_000_000);
    double y1 = random();
    seed(1);
    int x2 = random(1,100_000_000);
    double y2 = random();

    assert x1 == x2;
    assert y1 == y2;
  }

  void testReallyRandom() {
    seed();
    int x1 = random(1,100_000_000);
    double y1 = random();

    seed();
    int x2 = random(1,100_000_000);
    double y2 = random();

    // there is a tiny chance these
    // randomly are the same...
    assert x1 != x2;
    assert y1 != y2;
  }

  void run() {
    // seed() is done for complete randomness in run.
  }
}
</code></pre></blockquote>
</p>
*/
    public static final void seed() {
        RNG.seed();
    }

    /** <p>Seed the kiss random-number generator for a fixed pattern.  Small changes in the seed will have big changes in the sequence, but for real (cryptographically strong) randomness use {@see seed()} instead.</p>

<p>Seeding each test with <code>seed(1)</code> makes tests more repeatable, which helps figure out what went wrong, so this is done automatically before each test.  Before <code>run()</code> the random number generator is seeded with <code>seed()</code> so it is strongly random.</p>

<p>For example:
<blockquote><pre><code>
import static kiss.API.*;

class App {
  void testRepeats() {
  // seed(1) is used before every test for repeatability

    int x1 = random(1,100_000_000);
    double y1 = random();
    seed(1);
    int x2 = random(1,100_000_000);
    double y2 = random();

    assert x1 == x2;
    assert y1 == y2;
  }

  void testReallyRandom() {
    seed();
    int x1 = random(1,100_000_000);
    double y1 = random();

    seed();
    int x2 = random(1,100_000_000);
    double y2 = random();

    // there is a tiny chance these
    // randomly are the same...
    assert x1 != x2;
    assert y1 != y2;
  }

  void run() {
    // seed() is done for complete randomness in run.
  }
}
</code></pre></blockquote>
</p>
*/
    public static final void seed(double value) {
        RNG.seed(value);
    }

    /** Generate a psuedo-random int in some range, as in:
<blockquote><pre><code>
import static kiss.API.*;

class App {
  void testDice() {
    println("test die # 1: "+random(1,6));
    println("test coin: "+random(0,1));
  }
  void run() {
    println("run die # 1: "+random(1,6));
    println("run coin: "+random(0,1));
  }
}
</code></pre></blockquote>
The random values in from <code>run()</code> will change, but the random values in <code>testDice()</code> will stay the same.  You can create different (fixed) random sequences by calling {@see seed(double)} with different values, and different strongly random sequences by calling {@see seed()}.</p>
    */
    public static final int random(int a, int b) {
        return RNG.random(a, b);
    }

    /** Equivalent to `random(0,n-1)`.  {@see random(int,int)} */
    public static final int random(int n) {
        return RNG.random(0, n-1);
    }
    
    /** Generate a uniform psuedo-random double bigger than or equal to 0, but less than 1:
<blockquote><pre><code>
import static kiss.API.*;

class App {
  void run() {
    if (random() < 1.0/3.0) {
       println("one-third chance.");
    }
  }
}
</code></pre></blockquote>
Because the random values from <code>run()</code> will change, the results of run() will change.  Remeber you will have to <code>seed()</code> the random number generator in a test to get different random results.</p>
    */
    public static final double random() {
        return RNG.random();
    }

    /** <p>The command-line arguments the application got (except those used by the kiss.util.Run method).  If you want to use the command line arguments, you can use the APP_ARGS to get to them from within your program.</p>  

For example, for the program <code>App.java</code> in the current directory:
<blockquote><pre><code>
import static kiss.API.*;

class App {
  void run() {
    println("APP_ARGS:",APP_ARGS);
  }
}
</code></pre></blockquote>
which could be compiled with
<blockquote><pre><code>
javac -cp /path/to/kiss.jar App.java
</code></pre></blockquote>
could be run as (use a semi-colon (;) instead of a colon (:) in the classpath argument on windows systems):
<blockquote><pre><code>
java -cp .:../kiss.jar kiss.util.Run a b c
</code></pre></blockquote>
This produces the output:
<blockquote><pre><code>
APP_ARGS: [a,b,c]
</code></pre></blockquote>
    */
    public static String[] APP_ARGS; // updated by Run.main

    /** <p>The name of the class.  This is usually just "App", but
        might be something else if the "--app <app>" command line
        argument is passed to kiss.util.Run</p>

<p>For example, if the entry point in your application is <code>org.foozle.Runner</code>, as in
<blockquote><pre><code>
package org.foozle;

class Runner {
  void run() {
    println(APP_NAME + ": foozling with args:",APP_ARGS);
  }
}
</code></pre></blockquote>
as <code>Runner.java</code> in the folder <code>org/fozzle</code> of the current directory.  This can be compiled with
<blockquote><pre><code>
javac -cp /path/to/kiss.jar org/foozle/Runner.java
</code></pre></blockquote>

After compiling, this could be run as (use a semi-colon (;) instead of a colon (:) in the classpath argument on windows systems):
<blockquote><pre><code>
java -cp .:../kiss.jar kiss.util.Run --app org.foozle.Runner a b c
</code></pre></blockquote>
This produces the output:
<blockquote><pre><code>
org.foozle.Runner: foozling with args: [a,b,c]
</code></pre></blockquote>
    */
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


    /** Obscure part of <code>java.Math.*</code>, here for completeness. 
        See {@see java.lang.Math#copySign(double,double)}. */
    public static final double copySign(double magnitude, double sign) {
        return Math.copySign(magnitude,sign);
    }

    /** Obscure part of <code>java.Math.*</code>, here for completeness. 
        See {@see java.lang.Math#copySign(float,float)}. */
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

    /** Obscure part of <code>java.Math.*</code>, here for completeness. 
        See {@see java.lang.Math#expm1(double)}. */
    public static final double expm1(double x) {
        return Math.expm1(x);
    }

    /** Floor (largest integer <= a): Ex: floor(-3.5)==-4, floor(3.5)==3. */
    public static final double floor(double a) {
        return Math.floor(a);
    }

    /** Obscure part of <code>java.Math.*</code>, here for completeness. 
        See {@see java.lang.Math#getExponent(double)}. */
    public static final int getExponent(double d) {
        return Math.getExponent(d);
    }

    /** Obscure part of <code>java.Math.*</code>, here for completeness. 
        See {@see java.lang.Math#getExponent(float)}. */
    public static final int getExponent(float f) {
        return Math.getExponent(f);
    }

    /** Obscure part of <code>java.Math.*</code>, here for completeness. 
        See {@see java.lang.Math#hypot(double,double)}. */
    public static final double hypot(double x, double y) {
        return Math.hypot(x, y);
    }

    /** Obscure part of <code>java.Math.*</code>, here for completeness. 
        See {@see java.lang.Math#IEEEremainder(double,double)}. */
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

    /** Obscure part of <code>java.Math.*</code>, here for completeness. 
        See {@see java.lang.Math#log1p(double)}. */
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

    /** Obscure part of <code>java.Math.*</code>, here for completeness. 
        See {@see java.lang.Math#nextAfter(double,double)}. */
    public static final double nextAfter(double start, double direction) {
        return Math.nextAfter(start, direction);
    }

    /** Obscure part of <code>java.Math.*</code>, here for completeness. 
        See {@see java.lang.Math#nextAfter(float,double)}. */
    public static final float nextAfter(float start, double direction) {
        return Math.nextAfter(start, direction);
    }


    /** Obscure part of <code>java.Math.*</code>, here for completeness. 
        See {@see java.lang.Math#nextUp(double)}. */
    public static final double nextUp(double d) {
        return Math.nextUp(d);
    }

    /** Obscure part of <code>java.Math.*</code>, here for completeness. 
        See {@see java.lang.Math#nextUp(float)}. */
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

    /** Obscure part of <code>java.Math.*</code>, here for completeness. 
        See {@see java.lang.Math#scalb(double,int)}. */
    public static final double scalb(double d, int scaleFactor) {
        return Math.scalb(d, scaleFactor);
    }

    /** Obscure part of <code>java.Math.*</code>, here for completeness. 
        See {@see java.lang.Math#scalb(float,int)}. */
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

    /** Obscure part of <code>java.Math.*</code>, here for completeness. 
        See {@see java.lang.Math#ulp(double)}. */
    public static final double ulp(double d) {
        return Math.ulp(d);
    }

    /** Obscure part of <code>java.Math.*</code>, here for completeness. 
        See {@see java.lang.Math#ulp(float)}. */
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

    public static final boolean asBoolean(Number x) {
        return As.asBoolean(x);
    }

    public static final boolean asBoolean(Object x) {
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

    public static final char asChar(Number x) {
        return As.asChar(x);
    }

    public static final char asChar(Object x) {
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

    public static final byte asByte(Number x) {
        return As.asByte(x);
    }

    public static final byte asByte(Object x) {
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


    public static final short asShort(Number x) {
        return As.asShort(x);
    }

    public static final short asShort(Object x) {
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


    public static final int asInt(Number x) {
        return As.asInt(x);
    }

    public static final int asInt(Object x) {
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


    public static final long asLong(Number x) {
        return As.asLong(x);
    }

    public static final long asLong(Object x) {
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
    public static final float asFloat(Number x) {
        return As.asFloat(x);
    }
    public static final float asFloat(Object x) {
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
    public static final double asDouble(Number x) {
        return As.asDouble(x);
    }
    public static final double asDouble(Object x) {
        return As.asDouble(x);
    }

    /** "true" or "false" */
    public static final String asString(boolean x) {
        return As.asString(x);
    }

    /** String version of printf(fmt,x) */
    public static final String asString(boolean x, CharSequence fmt) {
        return As.asString(x,fmt);
    }
    public static final String asString(char x) {
        return As.asString(x);
    }

    /** String version of printf(fmt,x) */    
    public static final String asString(char x, CharSequence fmt) {
        return As.asString(x,fmt);
    }
    public static final String asString(byte x) {
        return As.asString(x);
    }

    /** String version of printf(fmt,x) */    
    public static final String asString(byte x, CharSequence fmt) {
        return As.asString(x,fmt);
    }
    public static final String asString(short x) {
        return As.asString(x);
    }

    /** String version of printf(fmt,x) */    
    public static final String asString(short x, CharSequence fmt) {
        return As.asString(x,fmt);
    }
    public static final String asString(int x) {
        return As.asString(x);
    }

    /** String version of printf(fmt,x) */    
    public static final String asString(int x, CharSequence fmt) {
        return As.asString(x,fmt);
    }
    public static final String asString(String x) {
        return As.asString(x);
    }

    /** String version of printf(fmt,x) */    
    public static final String asString(String x, CharSequence fmt) {
        return As.asString(x,fmt);
    }
    public static final String asString(float x) {
        return As.asString(x);
    }

    /** String version of printf(fmt,x) */    
    public static final String asString(float x, CharSequence fmt) {
        return As.asString(x,fmt);
    }
    public static final String asString(double x) {
        return As.asString(x);
    }

    /** String version of printf(fmt,x) */    
    public static final String asString(double x, CharSequence fmt) {
        return As.asString(x,fmt);
    }
    
    public static final String asString(Object x) {
        return As.asString(x);
    }

    /** String version of printf(fmt,x) */    
    public static final String asString(Object x, CharSequence fmt) {
        return As.asString(x,fmt);
    }
}
