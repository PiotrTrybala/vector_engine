package pl.piotrtrybala.database.storage;

import pl.piotrtrybala.database.storage.impl.CollectionRepositoryImpl;
import pl.piotrtrybala.database.storage.impl.EmbeddingRepositoryImpl;
import pl.piotrtrybala.database.storage.repositories.CollectionRepository;
import pl.piotrtrybala.database.storage.repositories.EmbeddingRepository;

import java.sql.Connection;
import java.sql.SQLException;

public class MetadataStorage {

    private static MetadataStorage instance;

    private CollectionRepository collectionRepository;
    private EmbeddingRepository embeddingRepository;

    private MetadataStorage(Connection connection) {
        this.collectionRepository = new CollectionRepositoryImpl(connection);
        this.embeddingRepository = new EmbeddingRepositoryImpl(connection);
    }

    public static synchronized void initialize() {
        if (instance != null) {
            throw new IllegalStateException("MetadataStorage is already initialized");
        }
        try {
            instance = new MetadataStorage(ConnectionManager.getConnection());

        } catch (SQLException e) {
            throw new RuntimeException("could not connect to metadata: " + e.getMessage());
        }
    }

    public static synchronized MetadataStorage getInstance() {
        if (instance == null) {
            throw new RuntimeException("MetadataStorage is not initialized");
        }
        return instance;
    }

    public CollectionRepository getCollectionRepository() {
        return collectionRepository;
    }

    public EmbeddingRepository getEmbeddingRepository() {
        return embeddingRepository;
    }
}
