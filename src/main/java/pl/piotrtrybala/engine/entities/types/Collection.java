package pl.piotrtrybala.engine.entities.types;

import java.sql.Timestamp;

public class Collection {

    private int id;
    private int uid;
    private String name;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Collection() {}

    public Collection(int id, int uid, String name) {
        this.id = id;
        this.uid = uid;
        this.name = name;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
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
