package pl.piotrtrybala.database.security;

public class AuthException extends RuntimeException {
    public AuthException(String message) {
        super(message);
    }
}
