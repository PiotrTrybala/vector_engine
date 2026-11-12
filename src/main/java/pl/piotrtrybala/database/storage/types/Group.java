package pl.piotrtrybala.database.storage.types;

import pl.piotrtrybala.database.auth.permissions.Permissions;
import pl.piotrtrybala.database.storage.repositories.GroupRepository;

import java.sql.SQLException;
import java.sql.Timestamp;

public class Group {

    private int id;
    private String name;
    private Permissions permissions;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    private GroupRepository repo;

    public Group(GroupRepository repo) {
        this.repo = repo;
        this.permissions = new Permissions();
    }

    public Group(int id, String name, Permissions permissions, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.name = name;
        this.permissions = permissions;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void addPermission(String permission) {
        this.permissions.add(permission);
    }

    public void removePermission(String permission) {
        this.permissions.remove(permission);
    }

    public void flush() throws SQLException {
        this.repo.save(this);
    }

    public void update() throws SQLException {
        this.repo.update(this);
    }

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
