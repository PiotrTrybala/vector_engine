package pl.piotrtrybala.engine.auth.entities;

import pl.piotrtrybala.engine.auth.Permissions;

import java.sql.Connection;
import java.sql.Timestamp;

public class Group {

    private int id;
    private String name;
    private Permissions permissions;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Group() {}

    public Group(int id, String name, Permissions permissions, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.name = name;
        this.permissions = permissions;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void addPermission(String permission) {

    }

    public void flush() {}

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", permissions=" + permissions +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Permissions getPermissions() {
        return permissions;
    }

    public void setPermissions(Permissions permissions) {
        this.permissions = permissions;
    }

    public int getId() {
        return id;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
}
