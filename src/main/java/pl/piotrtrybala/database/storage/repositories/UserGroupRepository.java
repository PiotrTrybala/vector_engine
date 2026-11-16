package pl.piotrtrybala.database.storage.repositories;

import pl.piotrtrybala.database.storage.RepositoryException;
import pl.piotrtrybala.database.storage.types.Group;
import pl.piotrtrybala.database.storage.types.User;

import java.sql.SQLException;
import java.util.Optional;

public interface UserGroupRepository {
    Optional<User> getUserByGroupId(int groupId) throws RepositoryException;
    Optional<Group> getGroupByUserId(int userId) throws RepositoryException;

    boolean insertUserGroup(User user) throws RepositoryException;
}
