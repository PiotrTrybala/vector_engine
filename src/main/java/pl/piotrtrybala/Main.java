package pl.piotrtrybala;

import pl.piotrtrybala.database.Entrypoint;

public class Main {
    static void main() {
        Entrypoint.init();
        Entrypoint.getInstance().run();
    }
}

