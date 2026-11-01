# vector_engine

Queries
        Parser parser = new Parser();

//        String source = "HANDSHAKE;" +
//                "AUTH username password;" +
//                "CREATE COLLECTION test;"+
//                "USE COLLECTION test;" +
//                "INSERT INTO test TYPE text BEGIN `this is sample text for testing embedding` END;" +
//                "UPDATE embedding VALUE BEGIN `this is new value for embedding` END WHERE vector = [0.3, 0.4, 0.5] OR id = 1;" +
//                "UPDATE metadata VALUE [key:12 as int,key2:\"value2\" as text, key3:0.0 as double, key4:true as boolean] WHERE vector = [0.3, 0.4, 0.5] OR id = 1;" +
//                "SELECT METADATA [key, key2] FROM test WHERE vector=[0.3, 0.4, 0.5] OR id = 1 LIMIT 3;" + // select metadata from similar texts
//                "SELECT TEXT FROM test WHERE vector = [0.3, 0.4, 0.5] OR id = 1 LIMIT 3;"; // select similar text
//
//        List<Query> queries = null;
//        try {
//            queries = parser.parse(source.toCharArray());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//

//        Engine engine = Engine.getInstance();
//        engine.run();

//        String url = "jdbc:sqlite:metadata.db";
//        String schemaPath = "/metadata.sql";
//
//        String schema;
//        try (InputStream inputStream = Main.class.getResourceAsStream(schemaPath)) {
//            assert inputStream != null;
//            schema = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
//        } catch(IOException e) {
//            e.printStackTrace();
//            return;
//        }
//
//
//        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
//            stmt.executeUpdate(schema);
//        } catch(SQLException e) {
//            e.printStackTrace();
//        }
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