package kiss.pi;

import kiss.pi.util.GPIO;

public class API
{
    public static final int INPUT =          GPIO.INPUT;
    public static final int INPUT_PULLDOWN = GPIO.INPUT_PULLDOWN;
    public static final int INPUT_PULLUP =   GPIO.INPUT_PULLUP;
    public static final int OUTPUT_LOW  =    GPIO.OUTPUT_LOW;
    public static final int OUTPUT_HIGH =    GPIO.OUTPUT_HIGH;

    public static final void pinMode(int pinNum, int mode) {
	GPIO.pinMode(pinNum,mode);
    }
    public static final void digitalWrite(int pinNum, boolean value) {
	GPIO.digitalWrite(pinNum,value);
    }
    public static final boolean digitalRead(int pinNum) {
	return GPIO.digitalRead(pinNum);
    }
}
