package pl.piotrtrybala.engine.entities.types;

import java.sql.Timestamp;
import java.util.List;

public class User {

    private int id;
    private String username;
    private String password;
    private boolean root;
    private List<String> perms;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public User() {}

    public User(String username, String password, boolean root, List<String> perms) {
        this.username = username;
        this.password = password;
        this.root = root;
        this.perms = perms;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }

    public List<String> getPerms() {
        return perms;
    }

    public void setPerms(List<String> perms) {
        this.perms = perms;
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
