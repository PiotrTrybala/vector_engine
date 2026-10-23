package pl.piotrtrybala.engine.auth;

public class Session {

    public Session() {}

    public static Session createSession() {
        return new Session();
    }

    public boolean isAuth() {
        return true;
    }

    public boolean hasPermission(User user, String permission) {
        return true;
    }


}
