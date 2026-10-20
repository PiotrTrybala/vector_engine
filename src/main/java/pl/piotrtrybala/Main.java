package pl.piotrtrybala;

import pl.piotrtrybala.engine.Engine;
import pl.piotrtrybala.engine.auth.AuthManager;
import pl.piotrtrybala.engine.auth.Group;
import pl.piotrtrybala.engine.auth.User;

public class Main {
    static void main() {
        Engine engine = Engine.getInstance();

        AuthManager authManager = engine.getAuthManager();

        Group group = authManager.createGroup("name1");
        group.addPermission("collection.create");
        group.addPermission("collection.delete");
        group.addPermission("embedding.insert");
        group.addPermission("*.*");
        group.flush();

        User user = authManager.createUser("piotrek", "password1");
        user.assignGroup(group);
        user.flush();

        engine.run();
    }
}

