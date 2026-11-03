package pl.piotrtrybala.engine.executor.impl.result;

import pl.piotrtrybala.engine.auth.AuthManager;
import pl.piotrtrybala.engine.executor.types.ExecutorResult;
import pl.piotrtrybala.engine.query.types.AuthQuery;

public class AuthResult implements ExecutorResult {

    private final AuthQuery query;
    private final AuthManager instance;

    public AuthResult(AuthQuery query) {
        this.query = query;
        this.instance = AuthManager.getInstance();
    }

    @Override
    public char[] getResult() {




        return new char[0];
    }
}
