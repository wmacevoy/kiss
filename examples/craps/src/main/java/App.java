import static kiss.API.*;

class App {
    Die die1 = test(new Die());
    Die die2 = test(new Die());

    void roll() {
        die1.roll();
        die2.roll();
    }
    
    String craps() // craps dice toss
    {
        roll();
        boolean same = (die1.getValue() == die2.getValue());
        int sum = die1.getValue()+die2.getValue();
        switch(sum) {
        case 2: return "Snake Eyes";
        case 3: return "Ace Deuce";
        case 4: return same ? "Hard Four" : "Easy Four";
        case 5: return "Fever Five";
        case 6: return same ? "Hard Six" : "Easy Six";
        case 7: return "Natural";
        case 8: return same ? "Hard Eight" : "Easy Eight";
        case 9: return "Nina";
        case 10: return same ? "Hard Ten" : "Easy Ten";
        case 11: return "Yo";
        case 12: return "Boxcars";
        }

        throw new Error("Impossible");
    }

    void testCraps()
    {
        String ans1 = craps();
        String ans2 = craps();
        String ans3 = craps();
        seed(1);
        assert craps() == ans1;
        assert craps() == ans2;
        assert craps() == ans3;
    }

    void run() {
        // random because of the seed() before run

        println("Roll # 1: " + craps());
        println("Roll # 2: " + craps());
        println("Roll # 3: " + craps());
    }
}
