package DataAccessLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteConnection {
	
	private static final Connection connection = SqliteConnection.connect();
	
    // Define the path to your SQLite database file
    private static final String DATABASE_URL = "jdbc:sqlite:BugBaseStorage.db";

    private static Connection connect() {
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
    
    public static Connection getConnection() {
    	return connection;
    }
}
