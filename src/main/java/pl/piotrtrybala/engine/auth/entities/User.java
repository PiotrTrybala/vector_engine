package pl.piotrtrybala.engine.auth.entities;

import pl.piotrtrybala.engine.auth.Permissions;
import pl.piotrtrybala.engine.entities.UserGroupRepository;
import pl.piotrtrybala.engine.entities.UserRepository;

import java.sql.SQLException;
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
    private UserRepository repo;
    private UserGroupRepository uGroupRepo;

    private static final Random random = new Random();
    private static final String CHARSET = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
    private static final int PASSWORD_LENGTH = 16;

    public User(UserRepository repo, UserGroupRepository uGroupRepo) {
        this.repo = repo;
        this.uGroupRepo = uGroupRepo;
    }

    public User(int id, String username, String password, boolean root, Permissions permissions, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.root = root;
        this.permissions = permissions;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.group = null;
    }

    public void assignGroup(Group group) {
        this.group = group;
    }

    public void flush() throws SQLException {
        this.repo.save(this);

        if (this.group != null) {
            this.uGroupRepo.save(this);
        }
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

}
