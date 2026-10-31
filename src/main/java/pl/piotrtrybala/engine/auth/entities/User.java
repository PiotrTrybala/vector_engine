package pl.piotrtrybala.engine.auth.entities;

import pl.piotrtrybala.engine.auth.Permissions;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Random;

public class User {

    private int id;
    private String username;
    private String password;
    private boolean root;
    private Permissions permissions;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    private Group group;

    private static final Random random = new Random();
    private static final String CHARSET = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
    private static final int PASSWORD_LENGTH = 16;

    public User() {}

    public void assignGroup(Group group) {
        this.group = group;
    }

    public void flush() {



    }

    public static String generatePassword() {
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            password.append(CHARSET.charAt(random.nextInt(CHARSET.length())));
        }
        return password.toString();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public boolean isRoot() {
        return root;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public Group getGroup() {
        return group;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", root=" + root +
                ", perms='" + permissions + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    //    public void assignGroup(Group group) {
//
//    }
//
//    public void flush() {
//
//    }

}
