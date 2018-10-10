/*
 * Copyright 2018 wmacevoy.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kiss.util;

import java.util.ArrayList;
import kiss.API.Generator;

/**
 *
 * @author wmacevoy
 */
public class TestEvent {

    static class L {

        ArrayList<String> receivedStrings = new ArrayList<String>();

        void receive(String value) {
            receivedStrings.add(value);
        }
        ArrayList<Integer> receivedIntegers = new ArrayList<Integer>();

        void receive(int value) {
            receivedIntegers.add(value);
        }
        ArrayList<Double> receivedDoubles = new ArrayList<Double>();

        void receive(Double value) {
            receivedDoubles.add(value);
        }
    }

    static class GS extends Generator<String> {

        void emit(String value) {
            send(value);
        }
    }

    static class GI extends Generator<Integer> {

        void emit(int value) {
            send(value);
        }
    }

    static class GD extends Generator<Double> {

        void emit(Double value) {
            send(value);
        }
    }

    void testListen() {
        L el = new L();
        GS gs = new GS();
        GI gi = new GI();
        GD gd = new GD();
        gs.addListener(el);
        gi.addListener(el);
        gd.addListener(el);
        gs.emit("axe");
        gi.emit(17);
        gd.emit(3.14);

        assert el.receivedDoubles.get(0) == 3.14;
        assert el.receivedIntegers.get(0) == 17;
        assert el.receivedStrings.get(0).equals("axe");
    }
}
