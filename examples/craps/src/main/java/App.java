import static kiss.API.*;

class App {
    String craps() // craps dice toss
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

    void testCraps()
    {
        // repeated because of the seed(1) before test
        assert craps() == "Ace Deuce";
        assert craps() == "Nina";
        assert craps() == "Nina";
    }

    void run() {
        // random because of the seed() before run

        println("Roll # 1: " + craps());
        println("Roll # 2: " + craps());
        println("Roll # 3: " + craps());
    }
}
