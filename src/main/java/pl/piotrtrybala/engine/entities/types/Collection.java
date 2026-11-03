package pl.piotrtrybala.engine.entities.types;

import pl.piotrtrybala.engine.entities.CollectionRepository;

import java.sql.Timestamp;

public class Collection {

    private int id = -1;
    private int userId;
    private String name;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    private final CollectionRepository collectionRepository;

    public Collection(CollectionRepository repository) {
        this.collectionRepository = repository;
    }

    public Collection(int id, int userId, String name, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
