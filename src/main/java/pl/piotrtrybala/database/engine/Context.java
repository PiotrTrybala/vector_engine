package pl.piotrtrybala.database.engine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.piotrtrybala.database.auth.session.Session;
import pl.piotrtrybala.database.storage.types.Collection;
import pl.piotrtrybala.database.storage.types.User;

import java.awt.image.WritableRaster;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Context {

    private static final Logger LOGGER = LogManager.getLogger(Context.class.getName());

    private final SocketChannel channel;
    private final Lock writeLock = new ReentrantLock();
    private final Deque<ByteBuffer> writeQueue = new ArrayDeque<>();

    private volatile SelectionKey key;
    private volatile Session session = null;
    private volatile Collection currentCollection = null;

    public Context(SocketChannel channel) {
        this.channel = channel;
    }

    public void attachKey(SelectionKey key) {
        this.key = key;
    }

    public SocketChannel getChannel() {
        return this.channel;
    }

    public boolean isAuthenticated() {
        return this.session != null;
    }

    public Session getSession() {
        return this.session;
    }

    public void createSession(User user) {
        this.session = new Session(user);
    }

    public Collection getCurrentCollection() {
        return currentCollection;
    }

    public void setCurrentCollection(Collection collection) {
        this.currentCollection = collection;
    }

    public void write(byte[] data) throws IOException {
        if (data.length == 0) {
            return;
        }

        writeLock.lock();

        try {
            writeQueue.addLast(ByteBuffer.wrap(data));
            enableWrite();
        } finally {
            writeLock.unlock();
        }
    }

    public boolean flush() throws IOException {
        writeLock.lock();
        try {
            while(!writeQueue.isEmpty()) {
                ByteBuffer buffer = writeQueue.peekFirst();
                int written = channel.write(buffer);
                if (written == 0 || buffer.hasRemaining()) {
                    enableWrite();
                    return false;
                }
                writeQueue.pollFirst();
            }
            disableWrite();
            return true;
        } finally {
            writeLock.unlock();
        }
    }

    private void enableWrite() {
        SelectionKey currentKey = this.key;
        if (currentKey != null && currentKey.isValid()) {
            int ops = currentKey.interestOps();
            if ((ops & SelectionKey.OP_WRITE) == 0) {
                currentKey.interestOps(ops | SelectionKey.OP_WRITE);
                Selector selector = currentKey.selector();
                if (selector != null) {
                    selector.wakeup();
                }
            }
        }
    }

    private void disableWrite() {
        SelectionKey currentKey = this.key;
        if (currentKey != null && currentKey.isValid()) {
            int ops = currentKey.interestOps();
            if ((ops & SelectionKey.OP_WRITE) != 0) {
                currentKey.interestOps(ops & ~SelectionKey.OP_WRITE);
            }
        }
    }

    public void close() {
        writeLock.lock();
        try {
            writeQueue.clear();
            SelectionKey currentKey = this.key;
            if (currentKey != null) {
                currentKey.cancel();
            }
            if (channel != null && channel.isOpen()) {
                channel.close();
            }
        } catch (IOException e) {
            LOGGER.error("failed to close socket channel: {}", e.getMessage());
        } finally {
            writeLock.unlock();
        }
    }

}
