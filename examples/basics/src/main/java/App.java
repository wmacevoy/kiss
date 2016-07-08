import static kiss.API.*;

public class App {
    public void hi() {
        print("What is your name? ");
        String name = readLine();
        println("Hi, " + name + "!");
    }

    public void testHi() {
        // create test input
        outOpen("hi.in");
        println("Bob");
        outClose();

        // create expected output
        outOpen("hi.out");
        println("What is your name? Bob");
        println("Hi, Bob!");
        outClose();

        // verify output for given input
        inOpen("hi.in");
        outVerify("hi.out");
        hi();
        outClose();
        inClose();
    }

    public String craps() // craps dice toss
    {
        int die1=random(1,6);
        int die2=random(1,6);

        switch(die1+die2) {
        case 2: return "Snake Eyes";
        case 3: return "Ace Deuce";
        case 4: return (die1 == die2) ? "Hard Four" : "Easy Four";
        case 5: return "Fever Five";
        case 6: return (die1 == die2) ? "Hard Six" : "Easy Six";
        case 7: return "Natural";
        case 8: return (die1 == die2) ? "Hard Eight" : "Easy Eight";
        case 9: return "Nina";
        case 10: return (die1 == die2) ? "Hard Ten" : "Easy Ten";
        case 11: return "Yo";
        case 12: return "Boxcars";
        }

        throw new Error("Impossible");
    }

    public void testCraps()
    {
        seed(1); // repeating random sequence for testability
        assert craps() == "Natural";
        assert craps() == "Easy Ten";
        assert craps() == "Nina";
    }

    public boolean isPrime(int x) {
        int n=(int) floor(sqrt(x));
        for (int i=2; i <= n; i += 2) {
            if (x % i == 0) return false;
        }
        return true;
    }

    public void testIsPrime() {
        assert isPrime(3) == true;
        assert isPrime(17) == true;
        assert isPrime(20) == false;
    }

    public void run() {
        println("All tests passed.");
    }
}
