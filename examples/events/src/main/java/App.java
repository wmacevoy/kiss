import static kiss.API.*;

// A State can be on or off...
enum State { off, on };

// A Light can receive State messages...
class Light {
    String location;

    Light(String _location) { location=_location; }

    void onReceiveState(State state) {
        println("light in " + location + " is " + state);
    }
}

// A Remote extends Generator<State> to generate State messages, so
//
//   * Any Light (or other objects with an onReceiveState(...)) can
//     listen to a Remote to receive State messages.
//
//   * Remote can send(State msg) to send a message to all added
//     listeners.
//
class Remote extends Generator<State> {
    void on() { send(State.on); }
    void off() { send(State.off); }
}

class App {
    void testHouse() {
        Light bedroomLight = new Light("bedroom");
        Light kitchenLight = new Light("kitchen");

        Remote remote = new Remote();

        // connecting remote to lights...
        remote.addListener(bedroomLight);
        remote.addListener(kitchenLight);

        // and an arbitrary action (lambda function)
        remote.addListener((state)->println("sent " + state));

        try (Close out = outExpect("light in bedroom is on",EOL,
                                   "light in kitchen is on",EOL,
                                   "sent on")) {
            remote.on();
        }

        try (Close out = outExpect("light in bedroom is off",EOL,
                                   "light in kitchen is off",EOL,
                                   "sent off")) {
            remote.off();
        }
    }
}
