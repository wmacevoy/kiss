import static kiss.API.*;

public class App {
    enum Choice { Rock, Paper, Scissors, Quit }
    
    public Choice computer;
    public Choice person;

    public void computerPick() {
        switch(random(1,3)) {
        case 1: computer = Choice.Rock; break;
        case 2: computer = Choice.Paper; break;
        case 3: computer = Choice.Scissors; break;
        }
    }

    public void personPick() {
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

    public boolean beats(Choice a, Choice b) {
        switch(a) {
        case Rock: return b == Choice.Scissors;
        case Paper: return b == Choice.Rock;
        case Scissors: return b == Choice.Paper;
        }
        throw new Error("Inconceivable!");
    }

    public void resolveWinner() {
        print(person + " vs. " + computer + ": ");
        if (beats(person,computer)) {
            println("you win!");
        } else if (beats(computer,person)) {
            println("computer wins!");
        } else {
            println("tie.");
        }
    }

    public void run() {
        for (;;) {
            computerPick();
            personPick();
            if (person == Choice.Quit) break;
            resolveWinner();
        }
        println("Bye!");
    }
}
