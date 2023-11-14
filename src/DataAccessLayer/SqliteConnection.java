package DataAccessLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteConnection {
    // Define the path to your SQLite database file
    private static final String DATABASE_URL = "jdbc:sqlite:BugBaseStorage.db";

    public static Connection connect() {
        Connection connection;
        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
            // Establish a connection to the SQLite database
            connection = DriverManager.getConnection(DATABASE_URL);
            connection.setAutoCommit(true);
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
