package pl.piotrtrybala.database.executor.impl.result;

import pl.piotrtrybala.database.embeddings.embedder.Embedder;
import pl.piotrtrybala.database.embeddings.embedder.EmbedderException;
import pl.piotrtrybala.database.embeddings.embedder.EmbedderManager;
import pl.piotrtrybala.database.embeddings.embedder.EmbeddingUtils;
import pl.piotrtrybala.database.executor.types.ExecutorResult;
import pl.piotrtrybala.database.query.enums.EmbeddingType;
import pl.piotrtrybala.database.query.types.InsertQuery;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class InsertResult implements ExecutorResult {

    private final InsertQuery query;

    public InsertResult(InsertQuery query) {
        this.query = query;
    }

    @Override
    public char[] getResult() {

        String collectionName = query.getCollectionName();
        EmbeddingType embeddingType = query.getType();
        byte[] data = EmbeddingUtils.toBytes(this.query.getData());

        Embedder embedder = null;
        try {
            embedder = EmbedderManager.getInstance().getEmbedder(embeddingType);
        } catch (EmbedderException e) {
            String message = "This data type is not supported: " + embeddingType.getType();
            return message.toCharArray();
        }

        float[] vector = embedder.create(data);
        try {

        }

        return "OK".toCharArray();
    }
}
