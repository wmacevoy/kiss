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
