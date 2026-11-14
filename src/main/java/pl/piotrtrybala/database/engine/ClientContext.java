package pl.piotrtrybala.database.engine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.piotrtrybala.database.auth.session.Session;
import pl.piotrtrybala.database.storage.types.User;

import java.io.IOException;
import java.net.Socket;
import java.nio.channels.SocketChannel;

public class ClientContext {

    private static final Logger LOGGER = LogManager.getLogger(ClientContext.class.getName());
    private final SocketChannel channel;

    private volatile boolean authenticated = false;
    private Session session = null;

    public ClientContext(SocketChannel channel) {
        this.channel = channel;
    }

    public SocketChannel getChannel() {
        return this.channel;
    }

    public boolean isAuthenticated() {
        return this.authenticated;
    }

    public Session getSession() {
        return this.session;
    }

    public void createSession(User user) {
        this.session = new Session(user);
    }

    public void close() {
        try {
            if (channel.isOpen()) {
                channel.close();
            }
        } catch(IOException e) {
            LOGGER.error("failed to close socket channel: {}", e.getMessage());
        }
    }

}
