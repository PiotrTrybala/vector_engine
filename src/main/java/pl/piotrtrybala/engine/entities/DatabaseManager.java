package pl.piotrtrybala.engine.entities;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManager {
    private static final HikariDataSource source;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:sqlite:metadata.db");

        config.setMaximumPoolSize(10);
        config.setAutoCommit(true);

        config.addDataSourceProperty("journal_mode", "WAL");
        config.addDataSourceProperty("synchronous", "NORMAL");

//        config.setConnectionInitSql("PRAGMA busy_timeout=5000;");

        source = new HikariDataSource(config);
    }

    private DatabaseManager() {}

    public static Connection getConnection() throws SQLException {
        return source.getConnection();
    }

    public static void shutdown() {
        if (!source.isClosed()) {
            source.close();
        }
    }
}
