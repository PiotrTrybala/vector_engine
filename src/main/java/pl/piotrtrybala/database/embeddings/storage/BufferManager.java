package pl.piotrtrybala.database.embeddings.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.piotrtrybala.database.Entrypoint;
import pl.piotrtrybala.database.EntrypointConfig;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class BufferManager {

    public static class Vector {
        public int id;
        public float[] data;

        public Vector(int id, float[] data) {
            this.id = id;
            this.data = data;
        }

        @Override
        public String toString() {
            return "Vector{" +
                    "id=" + id +
                    ", data=" + Arrays.toString(data) +
                    '}';
        }
    }

    private static BufferManager instance;
    private static final Logger LOGGER = LogManager.getLogger(BufferManager.class.getName());

    private static final String PATH = "./vectors.bin";

    private static final EntrypointConfig.EmbedderConfig embeddingConfig = Entrypoint.getInstance().getConfig().getEmbedderConfig();
    private static final int DATA_SIZE = Integer.BYTES + (Float.BYTES * embeddingConfig.embeddingSize);
    private static final int BUFFER_SIZE = embeddingConfig.maxWriteBufferSize;
    private final ByteBuffer writeBuffer;

    private BufferManager() {
        this.writeBuffer = ByteBuffer.allocate(BUFFER_SIZE);
        this.writeBuffer.order(ByteOrder.LITTLE_ENDIAN);
    }

    public static synchronized BufferManager getInstance() {
        if (instance == null) {
            instance = new BufferManager();
        }
        return instance;
    }

    public Vector retrieve(int id) throws Exception {

        long offset = (long) (id - 1) * DATA_SIZE;

        ByteBuffer buffer = ByteBuffer.allocate(DATA_SIZE);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        try (FileChannel channel = FileChannel.open(Path.of(PATH), StandardOpenOption.READ)) {

            if (offset + DATA_SIZE > channel.size()) {
                return null;
            }

            channel.read(buffer, offset);
            buffer.flip();

            int vectorId = buffer.getInt();
            if (vectorId != id) {
                throw new Exception("inconsistent data: " + id + " != " + vectorId);
            }

            float[] data = new float[embeddingConfig.embeddingSize];
            buffer.asFloatBuffer().get(data);

            return new Vector(id, data);
        } catch (IOException e) {
            throw new Exception("failed to retrieve vector: " + e.getMessage());
        }
    }

    public void put(Vector vector) throws Exception {
        LOGGER.info("adding vector to buffer: {}", vector.id);

        if (writeBuffer.remaining() < DATA_SIZE) {
            this.flush();
            return;
        }

        writeBuffer.putInt(vector.id);
        for (float v : vector.data) {
            writeBuffer.putFloat(v);
        }
    }

    public void flush() throws Exception {
        LOGGER.info("flushing buffer...");

        if (writeBuffer.position() == 0) {
            return;
        }

        writeBuffer.flip();

        try (FileChannel channel = FileChannel.open(
                Path.of(PATH),
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,
                StandardOpenOption.APPEND
        )) {
            int result = channel.write(writeBuffer);
            LOGGER.info("writing {} bytes into file...", result);
        } catch (IOException e) {
            throw new Exception("failed to write bytes: " + e.getMessage());
        }
        writeBuffer.clear();
    }
}
