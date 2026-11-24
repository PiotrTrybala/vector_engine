package pl.piotrtrybala.database.executor;

import pl.piotrtrybala.database.engine.Context;
import pl.piotrtrybala.database.storage.MetadataStorage;
import pl.piotrtrybala.database.storage.RepositoryException;
import pl.piotrtrybala.database.storage.types.Collection;
import pl.piotrtrybala.database.storage.types.Embedding;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class ExecutorUtils {

    public static byte[] toBytes(String str) {
        return StandardCharsets.UTF_8.encode(str).array();
    }

    public static Collection selectCollection(Context context, String collectionName) throws ExecutorException {
        if (context.getCurrentCollection() == null) {
            try {
                Optional<Collection> collection = MetadataStorage.getInstance().getCollectionRepository().findById(
                        context.getSession().getUser().getId(),
                        collectionName
                );
                return collection.orElseThrow(() -> new ExecutorException("Could not select collection"));
            } catch (RepositoryException e) {
                throw new ExecutorException("Could not find this collection: " + collectionName);
            }
        }
        return context.getCurrentCollection();
    }

    public static HashMap<String, Object> emptyMetadata() {
        return new HashMap<>();
    }

//    List<String> keys = selectCommand.getMetadata();
//    List<HashMap<String, Object>> metadataList = new ArrayList<>();
//                for (
//    Embedding embedding : embeddings) {
//        HashMap<String, Object> metadata = new HashMap<>();
//        metadata.put("id", embedding.getId());
//        HashMap<String, Object> embeddingMetadata = embedding.getMetadata();
//        for (String key : keys) {
//            metadata.put(key, embeddingMetadata.get(key));
//        }
//        metadataList.add(metadata);
//    }
//                return new SelectResult(metadataList, null);

    public static List<HashMap<String, Object>> selectMetadataFromEmbeddings(List<Embedding> embeddings, List<String> keys, boolean withId) throws ExecutorException {
        List<HashMap<String, Object>> metadataList = new ArrayList<>();
        for (Embedding embedding : embeddings) {
            HashMap<String, Object> metadata = new HashMap<>();
            if (withId) metadata.put("id", embedding.getId());
            HashMap<String, Object> embeddingMetadata = embedding.getMetadata();
            for (String key : keys) {
                metadata.put(key, embeddingMetadata.get(key));
            }
            metadataList.add(metadata);
        }
        return metadataList;
    }

    public static List<HashMap<String, Object>> selectEmbeddingsText(List<Embedding> embeddings) {
        List<HashMap<String, Object>> embeddingsText = new ArrayList<>();
        for (Embedding embedding : embeddings) {
            HashMap<String, Object> embeddingText = new HashMap<>();
            embeddingText.put("id", embedding.getId());
            embeddingText.put("text", embedding.getContent());
            embeddingsText.add(embeddingText);
        }
        return embeddingsText;
    }
}