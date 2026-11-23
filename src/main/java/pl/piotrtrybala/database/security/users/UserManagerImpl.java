package pl.piotrtrybala.database.security.users;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.piotrtrybala.database.security.UserManager;
import pl.piotrtrybala.database.storage.RepositoryException;
import pl.piotrtrybala.database.storage.impl.UserRepositoryImpl;
import pl.piotrtrybala.database.storage.repositories.UserRepository;
import pl.piotrtrybala.database.storage.types.User;

import java.util.Optional;

public class UserManagerImpl implements UserManager<User> {

    private static final Logger LOGGER = LogManager.getLogger(UserManagerImpl.class.getName());

    private final UserRepository userRepository;

    public UserManagerImpl() {
        this.userRepository = new UserRepositoryImpl();
    }

    @Override
    public Optional<User> retrieve(int id) {
        try {
            return this.userRepository.findById(id);
        } catch (RepositoryException e) {
            LOGGER.error("failed to retrieve user with id: {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> retrieve(String username) {
        try {
            return this.userRepository.findByUsername(username);
        } catch (RepositoryException e) {
            LOGGER.error("failed to retrieve user with username: {}", e.getMessage());
            return Optional.empty();
        }
    }
}
