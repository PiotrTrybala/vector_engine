package pl.piotrtrybala.engine.auth.session;

import pl.piotrtrybala.engine.auth.entities.User;

public class Session {

    private final User user;

    public Session(User user) {
        this.user = user;
    }


}
