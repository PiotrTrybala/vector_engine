package pl.piotrtrybala.engine.entities;

import pl.piotrtrybala.engine.auth.entities.User;

import java.util.List;

public interface UserRepository {
    User getById(int id);
    void create(String username, String password, boolean root, List<String> perms);
    void updateUsername(int id, String username);
    void updatePassword(int id, String password);
    void updatePerms(int id, List<String> perms);
    void delete(int id);
}
