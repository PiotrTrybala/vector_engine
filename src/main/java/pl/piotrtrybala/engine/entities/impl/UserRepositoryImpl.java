package pl.piotrtrybala.engine.entities.impl;

import pl.piotrtrybala.engine.auth.entities.User;
import pl.piotrtrybala.engine.entities.UserRepository;

import java.sql.Connection;

public class UserRepositoryImpl implements UserRepository {

    private Connection connection;

    public UserRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User retrieve(int id) {
        return null;
    }

    @Override
    public User retrieve(String name) {
        return null;
    }

    @Override
    public User create() {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void delete(String name) {

    }
}
