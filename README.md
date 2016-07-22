# kiss java API

## kiss Java

The goal is to make the launching and testing of java programs for beginners
easy: no explainations of static methods, simplified IO, and a simple
test framework beginners can use to write and validate their code.

In order to keep it simple, I have incorporated all this into one library, `kiss`, so students do not have to download other library dependencies and have a single import statement to start using it.  Here is the hello world program:

```java
import static kiss.API.*;

public class App {
    public void run() {
        println("Hello, World!");
    }
}
```

To see the classic message, you need the (kiss-1.0-SNAPSHOT.jar kiss) library in your project and use "kiss.util.Run" as your main class.  If you use maven (supported by most common IDE's) there are pre-built `pom.xml` files in the example projects.

## Test Hello World

So it is not hard to say hello.  Now let's test it.

```java
import static kiss.API.*;

public class App {
    public void testRun() {
        // create a file with the output we expect
        outOpen("testRun.verify");
        println("Hello, World!");
        outClose();

        // call run() to check that it matches
        outVerify("testRun.verify");
        run();
        outClose();
    }

    public void run()
    {
        println("Hello, World!");
    }
}
```
Any `testXXX` method is automatically called before the `run` method.  Want to write a test?  Just write the test!

## Randomness a kindergardener understands

`int die=random(1,6)` is a die roll and `seed(1)` resets the pseudo-random sequence for testability.

## Just math

The java.Math static methods are effectively also imported, so you just use `sqrt`, not `Math.sqrt`.  Peppering 'Math.' in all your formulas does not make you smarter or the code easier to read.

## File IO sucks less.

Here is a java program that makes an CSV file with 100 die rolls pairs:

```java
import static kiss.API.*;

public class App {
    public void run() {
       outOpen("rolls.csv");
       println("i,d1,d2,sum");
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

## Rock-paper-scissors without headaches:

```java
import static kiss.API.*;

public class App {
  String computer;
  String person;
  boolean playing = true;

  void computerPick() {
    switch(random(1,3)) {
    case 1: computer = "Rock"; break;
    case 2: computer = "Paper"; break;
    case 3: computer = "Scissors"; break;
    }
    println("Computer picks: " + computer);
  }

  void personPick() {
    person = null;
    while (person == null) {
      print("Rock, Paper, Scissors, or Quit? ");
      switch(readLine()) {
      case "Rock": person = "Rock"; break;
      case "Paper": person = "Paper"; break;
      case "Scissors": person = "Scissors"; break;
      case "Quit": person = "Quit"; break;
    }
    if (person == "Quit") {
       playing = false;
       println("Person quits.");
    } else {
       println("Person picks: " + person);
    }
  }

  boolean beats(String a, String b) {
     switch(a) {
     case "Rock": return b == "Scissors";
     case "Paper": return b == "Rock";
     case "Scissors": return b == "Paper";
     }
     throw new Error("inconceivable!");
  }

  void winner() {
    if (beats(person,computer)) {
      println("person wins!");
    } else if (beats(computer,person)) {
      println("computer wins!");
    } else {
      println("tie.");
    }
  }

  void run() {
    for (;;) {
      computerPick();
      personPick();
      if (!playing) break;
      winner();
    }
  }
}






