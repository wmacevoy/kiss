import static kiss.API.*;

class App {
    void testEarth() {
        double t=1;
        double g=9.8;
        double d_cm = 100*g;
        inProvide(t,g);
        outExpect("time(seconds)? gravity(meters/second^2)? "
                  +"It fell 490 centimeters.");
        run();
        outClose();
        inClose();
    }

    void testMoon() {
        double t=1;
        double g=1.6;
        double d_cm = 100*g;
        inProvide(t,g);
        outExpect("time(seconds)? gravity(meters/second^2)? "
                  +"It fell 80 centimeters.");
        run();
        outClose();
        inClose();
    }
    
    void run() {
       print("time(seconds)? ");
       double t = readDouble();
       print("gravity(meters/second^2)? ");
       double g = readDouble();
       double d =0.5*g*pow(t,2);
       println("It fell " + round(d*100) + " centimeters.");
    }
}
