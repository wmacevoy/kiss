import static kiss.API.*;

enum State { off, on };

class Light {
    String location;

    Light(String _location) { location=_location; }

    void onReceiveState(Remote remote, State state) {
        println("light in " + location + " is " + state);
    }
}

class Remote extends Generator<State> {
    void on() { send(State.on); }
    void off() { send(State.off); }
}

class App {
    void testHouse() {
        Light bedroomLight = new Light("bedroom");
        Light kitchenLight = new Light("kitchen");

        Remote lightsRemote = new Remote();

        lightsRemote.addListener(bedroomLight);
        lightsRemote.addListener(kitchenLight);

        try (Close out = outExpect("light in bedroom is on",EOL,
                                   "light in kitchen is on",EOL)) {
            lightsRemote.on();
        }

        try (Close out = outExpect("light in bedroom is off",EOL,
                                   "light in kitchen is off",EOL)) {
            lightsRemote.off();
        }
    }
}
