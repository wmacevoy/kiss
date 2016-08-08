import static kiss.API.*;


import static kiss.API.*; // github.com/wmacevoy/kiss

// kind of information to pass around
enum EnvironmentState { Cold, Comfortable, Hot };

// something which gets this kind of information
class Fan implements Listener<EnvironmentState> {
    String name;
    boolean isOn = false;

    Fan(String _name) { name=_name; }

    // what to do when this information is sent
    @Override
    public void receive(Generator<EnvironmentState> from,
                        EnvironmentState state) {
        if (isOn) {
            if (state == EnvironmentState.Cold) {
                println(name + " fan turned off");
                isOn = false;
            }
        } else {
            if (state == EnvironmentState.Hot) {
                println(name + " fan turned on");
                isOn = true;
            }
        }
    }
}

// something else that receives environment state info
class Heater implements Listener<EnvironmentState> {
    String name;
    boolean isOn = false;
    Heater(String _name) { name = _name; }

    // how it uses it
    @Override
    public void receive(Generator<EnvironmentState> from,
                        EnvironmentState state) {
        if (isOn) {
            if (state == EnvironmentState.Hot) {
                    println(name + " heater turned off");
                    isOn = false;
                }
            } else {
                if (state == EnvironmentState.Cold) {
                    println(name + " heater turned on");
                    isOn = true;
                }
            }
        }
}


// Something that sends/generates environment state info,
// default generator helps Thermostat keep track of interested
// listeners

class Thermostat extends DefaultGenerator<EnvironmentState> {
    private double temperature; // degrees centigrade
    private double tooHot;
    private double tooCold;
    private EnvironmentState state;

    Thermostat() { // Constructor
        temperature = 25;
        tooHot = 30;
        tooCold = 20;
        state = EnvironmentState.Comfortable;
    }

    void setTemperature(double _temperature) {
        temperature = _temperature;
        adjustState();
    }

    void setTooHot(double _tooHot) {
        tooHot = _tooHot;
        adjustState();
    }

    void setTooCold(double _tooCold) {
        tooCold = _tooCold;
        adjustState();
    }

    private void adjustState() {
        EnvironmentState _state;
        if (temperature >= tooHot) {
            _state = EnvironmentState.Hot;
        } else if (temperature <= tooCold) {
            _state = EnvironmentState.Cold;
        } else {
            _state = EnvironmentState.Comfortable;
        }
        if (state != _state) {
            state=_state;
            // we found out the environment state changed,
            // so if anyone is listening, send the change
            if (listening()) send(state);
        }
    }

    boolean isHot() { return state == EnvironmentState.Hot; }
    boolean isCold() { return state == EnvironmentState.Cold; }
}


class App {
    void testHouse() {
        Fan bedroomFan = new Fan("bedroom");
        Fan garageFan = new Fan("garage");
        Heater bedroomHeater = new Heater("bedroom");
        Heater garageHeater = new Heater("garage");
        Thermostat bedroomThermostat = new Thermostat();
        Thermostat garageThermostat = new Thermostat();

        garageThermostat.setTooCold(5);
        garageThermostat.setTooHot(50);
        garageThermostat.addListener(garageFan);
        garageThermostat.addListener(garageHeater);

        bedroomThermostat.setTooCold(22);
        bedroomThermostat.setTooHot(28);
        bedroomThermostat.addListener(bedroomFan);
        bedroomThermostat.addListener(bedroomHeater);

        try (Close out = outExpect("garage heater turned on")) {
            garageThermostat.setTemperature(-10);
        }

        try (Close out = outExpect("garage fan turned on",EOL,
                                   "garage heater turned off")) {
            garageThermostat.setTemperature(100);
        }

        try (Close out = outExpect("bedroom heater turned on")) {
            bedroomThermostat.setTemperature(20);
        }

        try (Close out = outExpect("bedroom fan turned on",EOL,
                                   "bedroom heater turned off")) {
            bedroomThermostat.setTemperature(30);
        }
    }
}
