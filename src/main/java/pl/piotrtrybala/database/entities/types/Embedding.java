package pl.piotrtrybala.database.entities.types;

import java.sql.Timestamp;
import java.util.HashMap;

public class Embedding {

    private int id = -1;
    private int collectionId;
    private byte[] content;
    private String contentHash;
    private HashMap<String, Object> metadata;
    private String metadataHash;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Embedding(int id, int collectionId, byte[] content, String contentHash, HashMap<String, Object> metadata, String metadataHash, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.collectionId = collectionId;
        this.content = content;
        this.contentHash = contentHash;
        this.metadata = metadata;
        this.metadataHash = metadataHash;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentHash() {
        return contentHash;
    }

    public void setContentHash(String contentHash) {
        this.contentHash = contentHash;
    }

    public HashMap<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(HashMap<String, Object> metadata) {
        this.metadata = metadata;
    }

    public String getMetadataHash() {
        return metadataHash;
    }

    public void setMetadataHash(String metadataHash) {
        this.metadataHash = metadataHash;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
