package pl.piotrtrybala;

import pl.piotrtrybala.engine.Engine;

public class Main {
    static void main() {
        Engine engine = Engine.getInstance();
        engine.run();
    }
}

