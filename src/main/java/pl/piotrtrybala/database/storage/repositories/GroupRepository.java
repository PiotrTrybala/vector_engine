package pl.piotrtrybala.database.storage.repositories;

import pl.piotrtrybala.database.auth.permissions.Permissions;
import pl.piotrtrybala.database.storage.Repository;
import pl.piotrtrybala.database.storage.RepositoryException;
import pl.piotrtrybala.database.storage.Searchable;
import pl.piotrtrybala.database.storage.types.Group;
import pl.piotrtrybala.database.storage.types.search.GroupSearchOptions;

import java.sql.SQLException;
import java.util.List;

public interface GroupRepository extends Repository<Group, Integer> { }
