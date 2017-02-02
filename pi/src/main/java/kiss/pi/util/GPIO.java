package kiss.pi.util;

import java.io.BufferedReader;
import java.io.FileReader;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.RaspiGpioProvider;
import com.pi4j.io.gpio.RaspiPinNumberingScheme;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.io.gpio.RaspiBcmPin;
import com.pi4j.io.gpio.GpioPinDigitalMultipurpose;

public class GPIO {
    // fixed voltage (5.0,3.3,0.0) pins
    private static final int VOLT_50 = 128+50;
    private static final int VOLT_33 = 128+33;
    private static final int VOLT_00 = 128;

    // actual IO pios
    private static final int GPIO_00 = 0;
    private static final int GPIO_01 = 1;
    private static final int GPIO_02 = 2;
    private static final int GPIO_03 = 3;
    private static final int GPIO_04 = 4;
    private static final int GPIO_05 = 5;
    private static final int GPIO_06 = 6;
    private static final int GPIO_07 = 7;
    private static final int GPIO_08 = 8;
    private static final int GPIO_09 = 9;

    private static final int GPIO_10 = 10;
    private static final int GPIO_11 = 11;
    private static final int GPIO_12 = 12;
    private static final int GPIO_13 = 13;
    private static final int GPIO_14 = 14;
    private static final int GPIO_15 = 15;
    private static final int GPIO_16 = 16;
    private static final int GPIO_17 = 17;
    private static final int GPIO_18 = 18;
    private static final int GPIO_19 = 19;

    private static final int GPIO_20 = 20;
    private static final int GPIO_21 = 21;
    private static final int GPIO_22 = 22;
    private static final int GPIO_23 = 23;
    private static final int GPIO_24 = 24;
    private static final int GPIO_25 = 25;
    private static final int GPIO_26 = 26;
    private static final int GPIO_27 = 27;
    private static final int GPIO_28 = 28;
    private static final int GPIO_29 = 29;
    private static final int GPIO_30 = 30;
    private static final int GPIO_31 = 31;        

    private static final String revision;

    static {
	String _revision = null;
	try {
	    BufferedReader br = new BufferedReader(new FileReader("/proc/cpuinfo"));
	    String line;
	    while ((line = br.readLine()) != null) {
		if (line.startsWith("Revision\t:")) {
		    _revision = line.substring(10).trim();
		    if (_revision.startsWith("1000")) {
			_revision = _revision.substring(4);
		    }
		    break;
		}
	    }
	} catch (Exception e) {
	    throw new Error("Could not determine cpu revision: " + e);
	}
	if (_revision == null) {
	    throw new Error("Could not determine cpu revision.");
	}
	revision = _revision;
    }

    private static final String model;
    static {
	String _model = null;
	// http://www.raspberrypi-spy.co.uk/2012/09/checking-your-raspberry-pi-board-version/
	// http://elinux.org/RPi_HardwareHistory
	switch(revision) {
	case "Beta" : _model = "Beta"; break;
	case "0002" : _model = "Model B Revision 1.0"; break;
	case "0003" : _model = "Model B Revision 1.0 (ECN0001)"; break;
	case "0004" : _model = "Model B Revision 2.0"; break;
	case "0005" : _model = "Model B Revision 2.0"; break;
	case "0006" : _model = "Model B Revision 2.0"; break;
	case "0007" : _model = "Model A"; break;
	case "0008" : _model = "Model A"; break;
	case "0009" : _model = "Model A"; break;
	case "000d" : _model = "Model B Revision 2.0"; break;
	case "000e" : _model = "Model B Revision 2.0"; break;
	case "000f" : _model = "Model B Revision 2.0"; break;
	case "0010" : _model = "Model B+"; break;
	case "0011" : _model = "Compute Module"; break;
	case "0012" : _model = "Model A+"; break;
	case "0013" : _model = "Model B+"; break;
	case "0014" : _model = "Compute Module"; break;
	case "0015" : _model = "A+"; break;
	case "a01040" : _model = "Pi 2 Model B"; break;
	case "a01041" : _model = "Pi 2 Model B"; break;
	case "900092" : _model = "PiZero"; break;
	case "900093" : _model = "PiZero"; break;
	case "a02082" : _model = "Pi 3 Model B"; break;
	case "a22082" : _model = "Pi 3 Model B"; break;
	}
	if (_model == null) {
	    throw new Error("Could not determine pi model." + revision);
	}
	model=_model;
    }

    private static final int [] pinMap;
    static {
	int[] _pinMap = null;

	int[] pinMapBCM = new int[] {
 	     0, 1, 2, 3, 4, 5, 6, 7, 8, 9,10,11,12,13,14,15,
	    16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31 };

	int[] pinMapR1 = new int[] {
		/*  1 */    VOLT_33, VOLT_50,  /*  2 */
		/*  3 */    GPIO_00, VOLT_50,  /*  4 */
		/*  5 */    GPIO_01, VOLT_00,  /*  6 */
		/*  7 */    GPIO_04, GPIO_14,  /*  8 */
		/*  9 */    VOLT_00, GPIO_15,  /* 10 */
		/* 11 */    GPIO_17, GPIO_18,  /* 12 */
		/* 13 */    GPIO_21, VOLT_00,  /* 14 */
		/* 15 */    GPIO_22, GPIO_23,  /* 16 */
		/* 17 */    VOLT_33, GPIO_24,  /* 18 */
		/* 19 */    GPIO_10, VOLT_00,  /* 20 */
		/* 21 */    GPIO_09, GPIO_25,  /* 22 */
		/* 23 */    GPIO_11, GPIO_08,  /* 24 */
		/* 25 */    VOLT_00, GPIO_07   /* 26 */
	};

	int[] pinMapR2 = new int[] {
		/*  1 */    VOLT_33, VOLT_50,  /*  2 */
		/*  3 */    GPIO_02, VOLT_50,  /*  4 */
		/*  5 */    GPIO_03, VOLT_00,  /*  6 */
		/*  7 */    GPIO_04, GPIO_14,  /*  8 */
		/*  9 */    VOLT_00, GPIO_15,  /* 10 */
		/* 11 */    GPIO_17, GPIO_18,  /* 12 */
		/* 13 */    GPIO_27, VOLT_00,  /* 14 */
		/* 15 */    GPIO_22, GPIO_23,  /* 16 */
		/* 17 */    VOLT_33, GPIO_24,  /* 18 */
		/* 19 */    GPIO_10, VOLT_00,  /* 20 */
		/* 21 */    GPIO_09, GPIO_25,  /* 22 */
		/* 23 */    GPIO_11, GPIO_08,  /* 24 */
		/* 25 */    VOLT_00, GPIO_07   /* 26 */
	};

	// http://pinout.xyz/
	int [] pinMap40 = new int[] {
	    /*  1 */    VOLT_33, VOLT_50,  /*  2 */
	    /*  3 */    GPIO_02, VOLT_50,  /*  4 */
	    /*  5 */    GPIO_03, VOLT_00,  /*  6 */
	    /*  7 */    GPIO_04, GPIO_14,  /*  8 */
	    /*  9 */    VOLT_00, GPIO_15,  /* 10 */
	    /* 11 */    GPIO_17, GPIO_18,  /* 12 */
	    /* 13 */    GPIO_27, VOLT_00,  /* 14 */
	    /* 15 */    GPIO_22, GPIO_23,  /* 16 */
	    /* 17 */    VOLT_33, GPIO_24,  /* 18 */
	    /* 19 */    GPIO_10, VOLT_00,  /* 20 */
	    /* 21 */    GPIO_09, GPIO_25,  /* 22 */
	    /* 23 */    GPIO_11, GPIO_08,  /* 24 */
	    /* 25 */    VOLT_00, GPIO_07,  /* 26 */
	    /* 27 */    GPIO_00, GPIO_01,  /* 28 */
	    /* 29 */    GPIO_05, VOLT_00,  /* 30 */
	    /* 31 */    GPIO_06, GPIO_12,  /* 32 */
	    /* 33 */    GPIO_13, VOLT_00,  /* 34 */
	    /* 35 */    GPIO_19, GPIO_16,  /* 36 */
	    /* 37 */    GPIO_26, GPIO_20,  /* 38 */
	    /* 39 */    VOLT_00, GPIO_21   /* 40 */
	};

	if (model == null || model.equals("Compute Module")) {
	    _pinMap = pinMapBCM;
	} else if (model.equals("Beta") || model.contains("Revision 1.0")) {
	    _pinMap = pinMapR1;
	} else if (model.equals("Model A") || model.contains("Revision 2.0")) {
	    _pinMap = pinMapR2;
	} else {
	    _pinMap = pinMap40;
	}

	pinMap = _pinMap;
    }

    private static final GpioController gpio;
    static {
	GpioFactory.setDefaultProvider(new RaspiGpioProvider(RaspiPinNumberingScheme.BROADCOM_PIN_NUMBERING));
	gpio = GpioFactory.getInstance();
    }

    private static final Pin[] bcmPins = new Pin[] {
	null,
	null,
	RaspiBcmPin.GPIO_02,
	RaspiBcmPin.GPIO_03,		
	RaspiBcmPin.GPIO_04,
	RaspiBcmPin.GPIO_05,
	RaspiBcmPin.GPIO_06,
	RaspiBcmPin.GPIO_07,		
	RaspiBcmPin.GPIO_08,
	RaspiBcmPin.GPIO_09,
	RaspiBcmPin.GPIO_10,
	RaspiBcmPin.GPIO_11,
	RaspiBcmPin.GPIO_12,
	RaspiBcmPin.GPIO_13,
	RaspiBcmPin.GPIO_14,
	RaspiBcmPin.GPIO_15,		
	RaspiBcmPin.GPIO_16,
	RaspiBcmPin.GPIO_17,
	RaspiBcmPin.GPIO_18,
	RaspiBcmPin.GPIO_19,		
	RaspiBcmPin.GPIO_20,
	RaspiBcmPin.GPIO_21,
	RaspiBcmPin.GPIO_22,
	RaspiBcmPin.GPIO_23,		
	RaspiBcmPin.GPIO_24,
	RaspiBcmPin.GPIO_25,
	RaspiBcmPin.GPIO_26,
	RaspiBcmPin.GPIO_27,		
	RaspiBcmPin.GPIO_28,
	RaspiBcmPin.GPIO_29,
	RaspiBcmPin.GPIO_30,
	RaspiBcmPin.GPIO_31,		
    };


    public static final int INPUT =          0b000;
    public static final int INPUT_PULLDOWN = 0b010;
    public static final int INPUT_PULLUP =   0b011;
    public static final int OUTPUT_LOW  =    0b110;    
    public static final int OUTPUT_HIGH =    0b111;

    private static GpioPinDigitalMultipurpose[] pins
	= new GpioPinDigitalMultipurpose[40];

    public static final void pinMode(int pinNum, int mode) {
	if (pinNum < 1 || pinNum > pinMap.length
	    || pinMap[pinNum-1] >= 128 || bcmPins[pinMap[pinNum-1]] == null) {
	    throw new UnsupportedOperationException("invalid pin " + pinNum);
	}

	if (pins[pinNum-1] == null) {
	    PinMode pinMode = ((mode & 0b100) != 0) ? PinMode.DIGITAL_OUTPUT : PinMode.DIGITAL_INPUT;
	    PinPullResistance pinPullResistance = null;

	    if ((mode & 0b010) != 0) {
		if ((mode & 0b001) != 0) {
		    pinPullResistance = PinPullResistance.PULL_UP;
		} else {
		    pinPullResistance = PinPullResistance.PULL_DOWN;
		}
	    } else {
		pinPullResistance = PinPullResistance.OFF;
	    }
	    pins[pinNum-1] = gpio.provisionDigitalMultipurposePin(bcmPins[pinMap[pinNum-1]],"pin " + pinNum,pinMode,pinPullResistance);
	}
	if ((mode & 0b100) != 0) {
	    pins[pinNum-1].setMode(PinMode.DIGITAL_OUTPUT);
	    if ((mode & 0b001) != 0) {
		pins[pinNum-1].high();
	    } else {
		pins[pinNum-1].low();
	    }
	} else {
	    pins[pinNum-1].setMode(PinMode.DIGITAL_INPUT);
	}
    }

    public static final void digitalWrite(int pinNum, boolean value) {
	GpioPinDigitalMultipurpose pin = pins[pinNum-1];
	if (pin == null) {
	    pinMode(pinNum,value ? OUTPUT_HIGH : OUTPUT_LOW);
	} else {
	    if (value) {
		pin.high();
	    } else {
		pin.low();
	    }
	}
    }

    public static final boolean digitalRead(int pinNum) {
	GpioPinDigitalMultipurpose pin = pins[pinNum-1];
	if (pin == null) {
	    pinMode(pinNum,INPUT);
	    return pins[pinNum-1].getState().isHigh();
	} else {
	    return pin.getState().isHigh();
	}
    }
}
