# vector_engine - CHONKER

## Query language

These are queries that Chonker supports:
- HANDSHAKE [x]
- AUTH [x]
- PASSWORD [_]
- CREATE COLLECTION [x]
- USE COLLECTION [x]
- SHOW [x]
- INSERT [x]
- UPDATE [x]
- DELETE [_]

### Queries documentation

#### HANDSHAKE <i>(implemented)</i>
Used for testing connection to database

        HANDSHAKE;

#### AUTH <i>(implemented)</i>
Used for authenticating with database using credentials

        AUTH <username> <password>;

Parameters:
- username: username associated with user account
- password: password associated with user account

Result:
- <b>OK</b>: User is successfully authenticated
- <b>InvalidPassword</b>: user password is invalid
- <b>NoUserFound</b>: user with given credentials is not found

#### Password <i><b>(not implemented)</i></b>
Used for changing user's password

        PASSWORD <new_password>;

Parameters:
- new_password: New password for <u>currently signed in</u> user

Result:
- <b>OK</b>: password successfully changed
- <b>InvalidPassword</b>: invalid password was provided

Queries

#### Create Collection <i>(implemented)</i>
Used for creating new collection

Required permission: <u>collection.create</u>

        CREATE COLLECTION <name>;

Parameters:
- name: collection name to be created

Result:
- <b>OK</b>: collection is created
- <b>CollectionExists</b>: collection with this name already exists

#### Use Collection <i>(implemented)</i>
Selects collection to work with

Required permissions: <u>collection.use</u>

        USE COLLECTION <name>;

Parameters:
- <b>OK</b>: collection is now used for operations
- <b>NoCollectionFound</b>: collection with this name was not found
- <b>Unauthorized</b>: user does not have required permissions to perform this operation 

#### Show <i>(implemented)</i>
Shows different entities in database

Required permissions: <u>database.show</u>

        SHOW <COLLECTIONS | COLLECTION | USER | EMBEDDINGS>;

Parameters:
- target: target of show command, which are as follows:
  - <i>COLLECTIONS</i>: show all collections created by current user
  - <i>COLLECTION</i>: show currently chosen collection (selected by <b>USE</b> command)
  - <i>USER</i>: show currently signed in user
  - <i>EMBEDDINGS</i>: show all embeddings from current collection (selected by <b>USE</b> command)

Result:
- <b>OK</b>: shows data related to target
- <b>NoCollectionSelected</b>: in target <i>COLLECTION</i> when there is no collection selected
- <b>UserNotAuthenticated</b>: in target <i>USER</i> when user is not authenticated

#### Insert <i>(implemented)</i>
Inserts new data into collection

Required permissions: <u>collection.insert</u>

        INSERT INTO <collection_name> TYPE <content_type> BEGIN `<content>` END;

Parameters:
- collection_name: name of collection for data to be inserted in
- content_type: what type content is inserted. Supported:
  - <b>TEXT</b>: text content
- content: actual content passed to query

Result:
- <b>OK</b>: data was inserted
- <b>InsertError</b>: there was a problem with inserting new data



        UPDATE <METADATA | EMBEDDING> VALUE <BEGIN `<content>` END | [key:value as int,key:value as string, key:value as double, key:value as boolean] WHERE <expression | vector=<embedding> OR id = <value> >;




        SELECT <METADATA [key, ...] | TEXT> FROM <collection_name> WHERE vector=<embedding> OR id = [values] LIMIT <value>;

//        Connection connection = ConnectionManager.getConnection();
//
//        AuthManager manager = new AuthManager(connection);

//        Group rootGroup = manager.createGroup("root");
//        rootGroup.addPermission("*.*");
//        rootGroup.flush();

//        Group adminGroup = manager.createGroup("group1");
//        adminGroup.addPermission("collection.create");
//        adminGroup.addPermission("embedding.insert");
//        adminGroup.flush();

//        Group retrievedGroup = manager.retrieveGroup("root");
//        LOGGER.info("group: {}", retrievedGroup.toString());

//        User root = manager.createUser("root", User.generatePassword());
//        root.assignGroup(retrievedGroup);
//        root.flush();

//        User admin = manager.createUser("admin", "admin123");
//        admin.assignGroup(adminGroup);
//        admin.flush();

//        User retrievedUser = manager.retrieveUser("root");
//        retrievedUser.assignGroup(retrievedGroup);
//        retrievedUser.update();
//
//        LOGGER.info("user: {}", retrievedUser.toString());