<img align="right" src="java-kiss.png">

# KISS Java API

### Get/Use/Download

[kiss releases](https://github.com/wmacevoy/kiss/releases)

[maven complete pom] (examples/helloworld/pom.xml)
```xml
<project ...>
  ...
  <dependencies>
      <dependency>
        <groupId>com.github.wmacevoy</groupId>
        <artifactId>kiss</artifactId>
        <version>LATEST</version>
      </dependency>
      ...
  </dependencies>
  ...
</project>
```

## Why KISS Java?

KISS keeps it simple, so Java can be used to teach software development concepts.
* Simplified run, testing, input, output, math, casting, events and ciphers.
* Just one jar file with no dependencies available as release and hosted on maven central.
* [Instructor video: why kiss?](https://youtu.be/YiftHVmbxgU)
* [Student videos: getting started](https://www.youtube.com/channel/UC5Fchcau0ofytGrUbQd_J0w)

## Hello, World!
There is no need to understand packages, static methods, access modifiers, arrays, or method invocations to write simple programs.  Here is "Hello World!":
```java
import static kiss.API.*;

class App {
    void run() {
        println("Hello, World!");
    }
}
```
To see the classic message, you need the kiss JAR file in your project and use "kiss.API" as your main class.  If you use Maven (supported by most common IDE's) there are pre-built `pom.xml` files in the example projects.

## "Hello World!", Tested!!
Wouldn't it be nice to introduce testing right away?
```java
import static kiss.API.*;

class App {
    void testRun() {
        outExpect("Hello, World!");
        run();
        outClose();
    }
    void run() {
        println("Hello, World!");
    }
}
```

Any `testXXX` method is automatically called before the `run` method.  Want to write a test?  Just write the test!

Or, since you can't write much Java without a try block anyway, a better version is:
```java
import static kiss.API.*;

class App {
    void testRun() {
        try (Close out=outExpect("Hello, World!")) {
            run();
        }
    }

    void run() {
        println("Hello, World!");
    }
}
```

## Randomness a kindergartener understands

`int die=random(1,6)` is a die roll and `seed(1)` resets the pseudo-random sequence for testability.  The random-number sequence is reset with `seed(1)` before each `textXXX()` and reset to a cryptographically strong PRNG with `seed()` before invoking `run()`.  It is also about twice as fast as the normal random number generator.

## ~~Math.~~math

The java.Math static methods are effectively also imported, so you just use `sqrt`, not `Math.sqrt`.  Peppering 'Math.' in all your formulas does not make you smarter or the code easier to read.

## Obvious Output

Here is a Java program that makes a space-separated CSV file
with 100 die rolls pairs:

```java
import static kiss.API.*;

class App {
    void run() {
       outOpen("rolls.csv");
       println("i d1 d2 sum");
       for (int i=1; i<=100; ++i) {
          int d1=random(1,6);
          int d2=random(1,6);
          int sum=d1+d2;
          println(i,d1,d2,sum);
       }
       outClose();
    }
}
```

## Intuitive Input

```java
import static kiss.API.*;

class App {
    void run() {
       print("time(seconds)? ");
       double t = readDouble();
       print("gravity(meters/second^2)? ");
       double g = readDouble();
       double d =0.5*g*pow(t,2);
       println("It fell " + d + " meters.");
    }
}
```

You can get input from a file with

```java
inOpen(filename);
XX value = readXX();
...
inClose();
```

## Goodness by section

### Run

The `Run` part of the kiss API simplifies starting and controlling the execution of your application.  It contains a `main()` method to launch your application which does the following

1. Run looks for the application class.  This is `App` in the default (no declaration) package.  If you want to use some other class, just pass the `--app <classname>` argument on the command line, or specify it with the `JAVA_APP` environment variable.

2. Run constructs an instance of the application class using the default constructor.  The constructor (or anywhere else) can use APP_ARGS to access command line arguments and APP_NAME contains the class name of the application object.  After the instance is constructed (at the end of this step), APP is a global reference to the application object.

3. Run invokes all testXXX() methods in order of declaration.  The random
number generator is reset with `seed(1)` before each test to help make the
tests reproducible.  You can skip invoking tests with the `--notest` option.

4. Run invokes the run() method.  The random number generator is set to
produce a strongly random sequence before this.  You can skip invoking
run with the `--norun` option.

5. Run invokes the close method, even if there was an error in a test or run.

All the steps after the construction are optional.

### `kiss.util.Run` components of the `kiss.API`

* `test(Target target)` --- test components other than the main application.  This can be used at the point of creation, as in:
```java
MyComponent myComponent=test(new MyComponent())
```
Only the first instance of an object is tested (or use `testAlways` instead).  If you want to skip testing of an object, replace `test` with `untest`.

* `pause(double duration)` --- Pauses the application for the given number of seconds (without wasting CPU time).

* `double time()` --- Returns the seconds since midnight, January 1, 1970 UTC.  Calling this twice and taking the difference is useful for interval timing.

* `String APP_NAME` --- The name of the application class (just `"App"` by default)

* `String[] APP_ARGS` --- The command line arguments.

* `Object APP` --- Once the application object is constructed, this is a global reference to the application object.

### I/O

* `outOpen(String filename)` --- redirects print/ln() output to the specified
  file.  These can be nested and each thread is independent.  There must be a
  matching `outClose()` to close the file.  The best-practice pattern is a try-with-resources:
```java
try (Close out=outOpen("hi.txt")) {
  println("hi!");
}
```
This assures the file is closed, even in the case of an error.

* `inOpen(String filename)` --- redirects readXXX() to use this input file.  These can be nested and each thread is independent.  There must be a matching `inClose()` to close the file.  The best-practice pattern is a try-with-resources:
```java
try (Close in=inOpen("words.txt")) {
  String word = readLine();
  // do something with word.
}
```
This assures the file is closed, even in the case of an error.

* `print/ln(...)` --- like PrintStream's print/ln, but it sends the formatted strings to the current output stream (`System.out` by default) and does a nicer job with arrays and collections.  You can change the default output with `outOpen`, `outExpect`, and `outVerify`.

* `outExpect(...)` --- makes an internal stream which is used to match against future output (until `inClose()` or the end of the try block).  If there is a mismatch an error is thrown.

* `readXXX(...)` --- uses a `java.util.Scanner` on the current input stream (`System.in` by default).  You can change the default with `inOpen` and `inProvide`.

* `inProvide(...)` --- makes an internal stream which is used for future input (until `inClose()` or the end of the try block).

* `outVerify(String filename)` --- like `outExpect`, but matches against an external file for more complicated output.

### Math

All the math functions and constants are imported, with the exception of `Math.random()` and `Math.random(int n)` which are replaced by improved versions.

### RNG

A fast (faster than the standard random number generator), but cryptographically strong pseudo-random number generator is provided by default.  Calling `seed()` strongly sets the seed, while `seed(double value)` sets it to a reproducible sequence.

* `int random(int a, int b)` --- generates a uniformly random integer in the interval, including the endpoints.  If b<=a, this always returns a.

* `int random(int n)` --- same as `random(0,n-1)`.

* `double random()` --- returns a uniformly random double in the interval [0,1).

* `seed()` --- seeds the PRNG with a 128-bit strong random seed.

* `seed(double value)` --- seeds the PRNG for a reproducible sequence.

### Events

The generator/event/listener pattern is all over the JDK, because it helps solve the hard problem of hooking different objects together to solve a larger problem, even if the generators and listeners didn't know much about each other to start with.

But most tutorials on it are a secret apology: they discuss how to "listen" to events/messages but "generating" events/messages is either done badly or not at all.  With kiss, it is easy to do it right without advanced Java katas:

```java
import static kiss.API.*;

class Parrot {
    void mimic(String words) {
        println("squawk: " + words + "!");
    }
};

class Trainer extends Generator<String> {
    void speak(String words) { send(words); }
}

class App {
    void run() {
        Parrot polly = new Parrot();
        Trainer susan = new Trainer();

        susan.addListener(words -> polly.mimic(words));

        susan.speak("hello");
    }
}
```
This produces `squawk: hello!`

### As

The casting notation is hard to read and converting strings to primitives types
is a pile of API and inconsistent with the syntax of the language. So,
```java
assert asDouble(3)/asDouble(2) == 1.5;
assert asInt("0xff_ff") == 0xff_ff;
assert asString('a').equals("a");
assert asChar("a") == 'a';
assert asBoolean(0) == false;
assert asBoolean(1) == true;
```

### Crypt

Simple to use, but strong symmetric key encryption.

* String encrypt(String key, String plain) --- Encrypt message with key producing a hex encoded string (changed in v1.1 compared to previous version).

* String decrypt(String key, String secret) --- Decrypt a hex encoded string (like those made by encrypt) to recreate the message (changed in v1.1 compared to previous version).

* String sha256(String text) --- Common secure hash.

[logo]: kiss/java-kiss.png "Java Duke with Kiss"

[1] [Orignal Java Duke image on wikipedia](https://en.wikipedia.org/wiki/Java_(programming_language)#/media/File:Wave.svg)

[2] [Original public domain kiss image on pixabay](https://pixabay.com/en/lips-mouth-speak-kiss-lipstick-145758/)


