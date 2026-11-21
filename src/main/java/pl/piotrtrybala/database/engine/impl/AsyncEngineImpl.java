package pl.piotrtrybala.database.engine.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.piotrtrybala.database.EntrypointConfig;
import pl.piotrtrybala.database.command.Command;
import pl.piotrtrybala.database.command.CommandException;
import pl.piotrtrybala.database.command.CommandParser;
import pl.piotrtrybala.database.command.impl.CommandParserImpl;
import pl.piotrtrybala.database.engine.Context;
import pl.piotrtrybala.database.engine.Engine;
import pl.piotrtrybala.database.engine.EngineException;
import pl.piotrtrybala.database.executor.Executor;
import pl.piotrtrybala.database.executor.blocking.ExecutorImpl;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncEngineImpl implements Engine {

    private static final Logger LOGGER = LogManager.getLogger(AsyncEngineImpl.class.getName());


    private final EntrypointConfig.EngineConfig config;
    private ExecutorService workerPool;
    private CommandParser parser;
    private Executor executor;

    public AsyncEngineImpl(EntrypointConfig.EngineConfig config) {
        this.config = config;
    }

    @Override
    public void init() throws EngineException {
        this.workerPool = Executors.newFixedThreadPool(
                config.processingWorkerPoolSize()
        );
        this.parser = new CommandParserImpl();
        this.executor = new ExecutorImpl();
    }

    @Override
    public void run() throws EngineException {

        try (Selector selector = Selector.open();
             ServerSocketChannel serverChannel = ServerSocketChannel.open()
        ) {

            serverChannel.bind(new InetSocketAddress(config.port()));
            serverChannel.configureBlocking(false);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                selector.select();

                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();

                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();

                    if (!key.isValid()) {
                        continue;
                    }

                    if (key.isAcceptable()) {
                        accept(selector, serverChannel);
                    } else if (key.isReadable()) {
                        key.interestOps(key.interestOps() & ~SelectionKey.OP_READ);
                        read(selector, key);
                    }
                }
            }

        } catch (IOException e) {
            LOGGER.error("engine io error: {}", e.getMessage());
        } finally {
            workerPool.shutdown();
        }
    }

    private void accept(Selector selector, ServerSocketChannel serverChannel) throws IOException {
        SocketChannel socketChannel = serverChannel.accept();
        socketChannel.configureBlocking(false);

        Context context = new Context(socketChannel);
        socketChannel.register(selector, SelectionKey.OP_READ, context);
        LOGGER.info("accepted new connection from {}", socketChannel.getRemoteAddress());
    }

    private void read(Selector selector, SelectionKey key) {
        Context context = (Context) key.attachment();
        context.attachKey(key);

        SocketChannel channel = context.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(config.maxQueryBufferSize());

        try {

            int bytesRead = channel.read(buffer);
            if (bytesRead == -1) {
                closeClient(context);
                return;
            }

            buffer.flip();

            List<Command> commands;
            try {
                commands = parser.parse(StandardCharsets.UTF_8.decode(buffer).array());
            } catch (CommandException e) {
                LOGGER.error("failed to parse commands: {}", e.getMessage());
//                writer.write(e.getMessage());
//                writer.flush();
            } catch (Exception e) {
                LOGGER.error("failed to execute commands: {}", e.getMessage());
//                context.write(e.getMessage().);
//                writer.flush();
            }

            buffer.clear();

            workerPool.submit(() -> {
                // implement executor logic here

                try {



                } catch(Exception e) {

                } finally {
                    try {
                        if (key.isValid()) {
                            key.selector().wakeup();
                            this.enableRead(key);
                        }
                    } catch(Exception ignored) {}
                }

            });
        } catch (IOException e) {
            LOGGER.warn("connection error with client: {}", e.getMessage());
            this.closeClient(context);
        }
    }

    private void enableRead(SelectionKey key) {
        try {
            synchronized (key) {
                if (key.isValid()) {
                    key.interestOps(key.interestOps() | SelectionKey.OP_READ);
                }
            }
        } catch (CancelledKeyException ignored) {
        }
    }

    private void closeClient(Context context) {
        try {
            LOGGER.info("closing client connection");
            context.close();
        } catch (Exception e) {
            LOGGER.error("failed to close client: {}", e.getMessage());
        }
    }
}
