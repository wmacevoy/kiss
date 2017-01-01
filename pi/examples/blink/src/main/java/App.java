import static kiss.API.*;
import static kiss.pi.API.*;

class App {
    public static final int LED_PIN = 7;

    boolean on;

    void run() {
	setup();
	while (true) {
	    loop();
	}
    }

    void setup() {
	on = false;
	pinMode(LED_PIN,OUTPUT_LOW);
    }

    void loop() {
	on = !on;
	digitalWrite(LED_PIN,on);
	pause(1.0);
    }
}
