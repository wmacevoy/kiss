import static kiss.API.*;

class App {
    // the fixed price of a bus ride
    public static final double PRICE = 1.25;

    // money in your pocket    
    double money;

    // passes you have
    double passes;


    void testCanRideKata1() {
        money = 0.75;
        passes = 0;
        assert canRide() == false;

        money = 10.00;
        passes = 2;
        assert canRide() == true;
    }

    void testCanRideKata2() {
        money = 10.00;
        passes = 0;
        assert canRide() == true;

        money = 0.05;
        passes = 2;
        assert canRide() == true;
    }

    void testCanRideKata3() {
        money = PRICE;
        passes = 0;
        assert canRide() == true;

        money = PRICE-0.01;
        passes = 1;
        assert canRide() == true;

        money = PRICE-0.01;
        passes = 0;
        assert canRide() == false;
    }

    void testRideKata1() {
        money = 0.75;
        passes = 0;
        if (canRide()) { ride(); }
        assert money == 0.75;
        assert passes == 0;

        money = 10.00;
        passes = 2;
        if (canRide()) { ride(); }
        assert money == 10.00;
        assert passes == 1;
    }

    void testRideKata2() {
        money = 10.00;
        passes = 0;
        if (canRide()) { ride(); }
        assert money == 10.00-PRICE;
        assert passes == 0;

        money = 0.05;
        passes = 2;
        if (canRide()) { ride(); }
        assert money == 0.05;
        assert passes == 1;
    }

    void testRideKata3() {
        money = PRICE;
        passes = 0;
        if (canRide()) { ride(); }
        assert money == 0;
        assert passes == 0;

        money = PRICE-0.01;
        passes = 1;
        if (canRide()) { ride(); }
        assert money == PRICE-0.01;
        assert passes == 0;

        money = PRICE-0.01;
        passes = 0;
        if (canRide()) { ride(); }
        assert money == PRICE-0.01;
        assert passes == 0;
    }

    void testRideNinja() {
        boolean caught = false;
        
        money = PRICE-0.01;
        passes = 0;
        try {
            ride();
        } catch (Error e) {
            caught = e.getMessage().equals("get off the bus!");
        }
        assert caught == true;
        assert money == PRICE-0.01;
        assert passes == 0;
    }

    boolean canRide() {
        // canRide should be true if either
        //     you have enough money (PRICE)
        //     or have at least one pass

        // TODO
        return false;
    }

    void ride() {
        // ride should use a pass if possible
        // otherwise it should use money.
        
        // TODO
    }
}
