package pl.piotrtrybala.engine.entities;

import pl.piotrtrybala.engine.auth.entities.User;

public interface UserRepository {

    User retrieve(int id);
    User retrieve(String name);

    User create();
    void save(User user);
    void delete(int id);
    void delete(String name);
}
