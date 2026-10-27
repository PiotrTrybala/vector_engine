package pl.piotrtrybala.engine.entities.types;

import java.awt.desktop.SystemEventListener;
import java.sql.Timestamp;

public class Session {
    private int id;
    private int uid;
    private String token;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Session() {}

    public Session(int uid, String token) {
        this.uid = uid;
        this.token = token;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public int getId() {
        return id;
    }

    public int getUid() {
        return uid;
    }

    public String getToken() {
        return token;
    }
}
