import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

class ChangeBug1 {
    public static void main(String args[]) throws InterruptedException {
	System.out.println("<--Pi4J--> GPIO Listen Example ... started.");

	// modified because I could not figure out the pin numbering
	GpioFactory.setDefaultProvider(new RaspiGpioProvider(RaspiPinNumberingScheme.BROADCOM_PIN_NUMBERING));
	final GpioController gpio = GpioFactory.getInstance();

	
	// provision gpio pin #03 as an input pin with its internal pull down resistor enabled
	// pinMode(App.BUTTON_PIN,INPUT_PULLUP);
	//final GpioPinDigitalMultipurpose myButton = kiss.pi.util.GPIO.pin(App.BUTTON_PIN);
	final GpioPinDigitalInput myButton = gpio.provisionDigitalInputPin(RaspiBcmPin.GPIO_02, "myButton", PinPullResistance.PULL_UP);

	// set shutdown state for this input pin
	myButton.setShutdownOptions(true);

	// create and register gpio pin listener
	myButton.addListener(new GpioPinListenerDigital() {
		@Override
		public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
		    // display pin state on console
		    System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
		}
		
	    });

	System.out.println(" ... complete the GPIO #02 circuit and see the listener feedback here in the console.");

	// keep program running until user aborts (CTRL-C)
	while(true) {
	    Thread.sleep(1000);
	    System.out.println(myButton.getState());
	}

	// stop all GPIO activity/threads by shutting down the GPIO controller
	// (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
	// gpio.shutdown();   <--- implement this method call if you wish to terminate the Pi4J GPIO controller
    }
}
