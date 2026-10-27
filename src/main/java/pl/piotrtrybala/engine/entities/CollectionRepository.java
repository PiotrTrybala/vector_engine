package pl.piotrtrybala.engine.entities;

import java.util.Collection;

public interface CollectionRepository {
    Collection getById(int id);
    void create(int uid, String name);
    void delete(int id);
}
