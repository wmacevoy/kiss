import static kiss.API.*;

class App {
    enum Choice { Rock, Paper, Scissors, Quit }
    
    Choice computer;
    Choice person;

    void computerPick() {
        switch(random(1,3)) {
        case 1: computer = Choice.Rock; break;
        case 2: computer = Choice.Paper; break;
        case 3: computer = Choice.Scissors; break;
        }
    }

    void personPick() {
        person = null;
        while (person == null) {
            print("Rock, Paper, Scissors, or Quit? ");
            switch(readLine()) {
            case "Rock":     person = Choice.Rock; break;
            case "Paper":    person = Choice.Paper; break;
            case "Scissors": person = Choice.Scissors; break;
            case "Quit":     person = Choice.Quit; break;
            }
        }
    }

    boolean beats(Choice a, Choice b) {
        switch(a) {
        case Rock: return b == Choice.Scissors;
        case Paper: return b == Choice.Rock;
        case Scissors: return b == Choice.Paper;
        }
        throw new Error("Inconceivable!");
    }

    void resolveWinner() {
        print(person + " vs. " + computer + ": ");
        if (beats(person,computer)) {
            println("you win!");
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
            if (person == Choice.Quit) break;
            resolveWinner();
        }
        println("Bye!");
    }
}
