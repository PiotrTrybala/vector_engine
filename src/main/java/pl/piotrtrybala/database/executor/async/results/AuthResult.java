package pl.piotrtrybala.database.executor.async.results;


import pl.piotrtrybala.database.executor.async.AsyncResult;


public class AuthResult implements AsyncResult {

    @Override
    public char[] getResult() {
        return "OK".toCharArray();
    }

//    private static final Logger LOGGER = LogManager.getLogger(AuthResult.class.getName());
//
//    private final AuthCommand command;
//
//    public AuthResult(AuthCommand command) {
//        this.command = command;
//    }
//
//    @Override
//    public char[] getResult() {
//
//        try {
//            Optional<User> user = AuthenticationManager.getInstance().authenticateUser(
//                    command.getUsername(),
//                    command.getPassword()
//            );
//            if (user.isEmpty()) {
//                return "User is not found".toCharArray();
//            }
//
//            c
//
//        } catch (Exception e) {
//            LOGGER.error("failed to authenticate user: {}", e.getMessage());
//            return "Failed to authenticate user".toCharArray();
//        }
//        return "OK".toCharArray();
//    }
}
