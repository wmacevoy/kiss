import static kiss.API.*;

class Roll {
    Roll(int pins) { }
    int getPins() { return 0; }

    void testRollsLegal() {
        for (int testPins = 0; testPins <= 10; ++testPins) {
            Roll roll = new Roll(testPins);
            assert roll.getPins() == testPins;
        }
    }

    void testRollTooSmall() {
        boolean passed = false;
        try {
            Roll roll = new Roll(-1);
        } catch (Error e) {
            passed = true;
        }
        assert passed;
    }

    void testRollTooLarge() {
        boolean passed = false;
        try {
            Roll roll = new Roll(11);
        } catch (Error e) {
            passed = true;
        }
        assert passed;
    }
}

class Frame {
    Frame next;
    void addRoll(Roll roll) { }
    boolean isStrike() { return false; }
    boolean isSpare() { return false; }
    boolean isScoreKnown() { return false; }
    int getScore() { return 0; }
    boolean isReady() { return false; }

    void testFrameGutter() {
        Roll roll1 = new Roll(0);
        Roll roll2 = new Roll(0);
        Frame frame = new Frame();
        frame.addRoll(roll1);
        assert frame.isReady() == false;
        frame.addRoll(roll2);
        assert frame.isReady() == true;
        assert frame.isScoreKnown() == true;        
        assert frame.getScore() == 0;
        assert frame.isSpare() == false;
        assert frame.isStrike() == false;
    }

    void testFrameSimple() {
        Roll roll1 = new Roll(7);
        Roll roll2 = new Roll(1);
        Frame frame = new Frame();
        frame.addRoll(roll1);
        assert frame.isReady() == false;        
        frame.addRoll(roll2);
        assert frame.isReady() == true;
        assert frame.isScoreKnown() == true;
        assert frame.getScore() == 8;
        assert frame.isSpare() == false;
        assert frame.isStrike() == false;
    }

    void testFrameSpare() {
        Roll roll1 = new Roll(7);
        Roll roll2 = new Roll(3);
        Roll roll3 = new Roll(5);
        Frame frame = new Frame();
        frame.addRoll(roll1);
        frame.addRoll(roll2);
        frame.next = new Frame();
        frame.next.addRoll(roll3);   

        assert frame.getScore() == 15;
        assert frame.isSpare() == true;
        assert frame.isStrike() == false;
    }

    void testFrameStrike() {
        Roll roll1 = new Roll(10);
        Roll roll2 = new Roll(7);
        Roll roll3 = new Roll(3);
        Frame frame = new Frame();
        frame.next = new Frame();
        assert frame.isReady() == false;
        frame.addRoll(roll1);
        assert frame.isReady() == true;
        assert frame.isScoreKnown() == false;
        frame.next.addRoll(roll2);
        assert frame.isScoreKnown() == false;
        frame.next.addRoll(roll3);   
        assert frame.isScoreKnown() == true;
        assert frame.getScore() == 20;
        assert frame.isSpare() == false;
        assert frame.isStrike() == true;
    }
}

class Game {
    void roll(int pins) { }
    boolean isReady() { return false; }
    int getScore() { return 0; }

    void testGame0() {
        Game game = new Game();
        int[] rolls =new int[] {
            0, 0, // frame 1              
            0, 0, // frame 2              
            0, 0, // frame 3              
            0, 0, // frame 4              
            0, 0, // frame 5              
            0, 0, // frame 6              
            0, 0, // frame 7              
            0, 0, // frame 8              
            0, 0, // frame 9              
            0, 0  // frame 10 (possibly 3)
        };
        for (int pins : rolls) { game.roll(pins); }
        assert game.getScore() == 0;
    }

    void testGame1() {
        Game game = new Game();
        int[] rolls =new int[] {
            1, 1, // frame 1              
            1, 1, // frame 2              
            1, 1, // frame 3              
            1, 1, // frame 4              
            1, 1, // frame 5              
            1, 1, // frame 6              
            1, 1, // frame 7              
            1, 1, // frame 8              
            1, 1, // frame 9              
            1, 1  // frame 10 (possibly 3)
        };
        for (int pins : rolls) { game.roll(pins); }
        assert game.getScore() == 20;
    }

    void testGame2() {
        Game game = new Game();
        int[] rolls =new int[] {
            1, 1, // frame 1              2
            3, 7, // frame 2              15
            5, 1, // frame 3              6
            10,   // frame 4              15
            2, 3, // frame 5              5
            10,   // frame 6              20
            9, 1, // frame 7              20
            10,   // frame 8              12
            1, 1, // frame 9              2
            10, 10, 10  // frame 10 (possibly 3) 30
        };
        for (int pins : rolls) { game.roll(pins); }
        assert game.getScore() == 2+15+6+15+5+20+20+12+2+30;
    }

    void testGame3() {
        Game game = new Game();
        int[] rolls =new int[] {
            10, // frame 1              30
            10, // frame 2              30
            10, // frame 3              30
            10, // frame 4              30
            10, // frame 5              30
            10, // frame 6              30
            10, // frame 7              30
            10, // frame 8              30
            10, // frame 9              30
            10, 10, 10  // frame 10 (possibly 3) 30
        };
        for (int pins : rolls) { game.roll(pins); }
        assert game.getScore() == 300;
    }

    void testGame4() {
        Game game = new Game();
        int[] rolls =new int[] {
            5,5, // frame 1              15
            5,5, // frame 2              15
            5,5, // frame 3              15
            5,5, // frame 4              15
            5,5, // frame 5              15
            5,5, // frame 6              15
            5,5, // frame 7              15
            5,5, // frame 8              15
            5,5, // frame 9              15
            5,5,5  // frame 10 (possibly 3) 15
        };
        for (int pins : rolls) { game.roll(pins); }
        assert game.getScore() == 150;
    }
    
}

class App {
    void testPOST() {
        test(new Roll(1));
        test(new Frame());
        test(new Game());
    }
}
