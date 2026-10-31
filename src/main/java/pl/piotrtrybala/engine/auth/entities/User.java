package pl.piotrtrybala.engine.auth.entities;

import java.sql.Timestamp;

public class User {

    private int id;
    private String username;
    private String password;
    private boolean root;
    private String perms;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public User() {}



//    public void assignGroup(Group group) {
//
//    }
//
//    public void flush() {
//
//    }

}
