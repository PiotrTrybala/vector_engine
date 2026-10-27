package pl.piotrtrybala.engine.entities;

import pl.piotrtrybala.engine.entities.types.Collection;

public interface CollectionRepository {
    Collection getById(int id);
    Collection getByName(int uid, String name);
    void create(int uid, String name);
    void delete(int id);
}
