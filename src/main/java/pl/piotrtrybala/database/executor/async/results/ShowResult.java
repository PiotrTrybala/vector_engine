package pl.piotrtrybala.database.executor.async.results;

import pl.piotrtrybala.database.executor.async.AsyncResult;
import pl.piotrtrybala.database.storage.types.Collection;
import pl.piotrtrybala.database.storage.types.Embedding;
import pl.piotrtrybala.database.storage.types.User;

import java.util.List;

public class ShowResult implements AsyncResult {

    private Collection collection = null;
    private User user = null;
    private List<Collection> collections = null;
    private List<Embedding> embeddings = null;

    public ShowResult(Collection collection, User user, List<Collection> collections, List<Embedding> embeddings) {
        this.collection = collection;
        this.user = user;
        this.collections = collections;
        this.embeddings = embeddings;
    }

    @Override
    public char[] getResult() {
        if (collection != null) {
            return collection.getName().toCharArray();
        }

        if (user != null) {
            return user.getUsername().toCharArray();
        }

        if (collections != null) {
            StringBuilder builder = new StringBuilder();
            for (Collection collection : collections) {
                builder.append(collection.getName()).append("\n");
            }
            return builder.toString().toCharArray();
        }

        if (embeddings != null) {
            StringBuilder builder = new StringBuilder();
            builder.append("Found ").append(embeddings.size()).append( "embeddings: n");
            for (Embedding embedding : embeddings) {
                builder.append(embedding.getId()).append(" ");
            }
            return builder.toString().toCharArray();
        }

        return "OK".toCharArray();
    }
}
