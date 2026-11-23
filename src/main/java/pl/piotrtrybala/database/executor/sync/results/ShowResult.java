package pl.piotrtrybala.database.executor.sync.results;

import pl.piotrtrybala.database.executor.Result;
import pl.piotrtrybala.database.command.types.ShowCommand;

public class ShowResult implements Result {
//    private final SessionManager sessionStorage;
    private final ShowCommand query;

    public ShowResult(ShowCommand query) {
//        this.sessionStorage = AuthenticationManager.getInstance().getStorage();
        this.query = query;
    }

    @Override
    public char[] getResult() {

//        UseTargetType useTargetType = query.getEntityType();
//        System.out.println(useTargetType.getTarget());
//
//        Session session = this.sessionStorage.retrieve(null);
//        if (session == null) {
//            return "User is not authenticated".toCharArray();
//        }
//        User user = session.getUser();
//        Collection currentCollection = session.getCurrentCollection();
//
//        if (useTargetType == UseTargetType.COLLECTION) {
//            if (currentCollection == null) {
//                return "You must select collection".toCharArray();
//            }
//
//            return currentCollection.getName().toCharArray();
//        }
//
//        if (useTargetType == UseTargetType.USER) {
//            String result = "User{id=" + user.getId() + ", username=" + user.getUsername() + "}";
//            return result.toCharArray();
//        }
//
//        if (useTargetType == UseTargetType.EMBEDDINGS) {
//
//            if (currentCollection == null) {
//                return "You must select collection to show all embeddings".toCharArray();
//            }
//            List<Embedding> embeddingList;
//            try {
//                embeddingList = MetadataStorage.getInstance().getEmbeddingRepository().findAll(currentCollection.getId());
//            } catch (RepositoryException e) {
//                System.out.println(e.getMessage());
//                return "Failed to list embeddings".toCharArray();
//            }
//
//            String message = "Found " + embeddingList.size() + " embeddings";
//            return message.toCharArray();
//        }
//
//        List<Collection> collectionList;
//        try {
//            collectionList = MetadataStorage.getInstance().getCollectionRepository().findAll(user.getId());
//        } catch (RepositoryException e) {
//            String message = "Failed to list collections: " + e.getMessage();
//            return message.toCharArray();
//        }
//        StringBuilder builder = new StringBuilder();
//
//        for (Collection collection : collectionList) {
//            builder.append(collection.getName()).append("\n");
//        }
//
//        return builder.toString().toCharArray();
        return "OK".toCharArray();
    }
}
