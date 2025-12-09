// RECOMPILING
// javac -d . -cp "lib/java-dotenv-3.1.5.jar:lib/kotlin-stdlib-1.8.22.jar:lib/postgresql-42.6.0.jar" DatabaseConnection.java
// java -cp ".:lib/java-dotenv-3.1.5.jar:lib/kotlin-stdlib-1.8.22.jar:lib/postgresql-42.6.0.jar" JavaFinalWinter2025.DatabaseConnection


package JavaFinalWinter2025.utils;

import java.sql.*;
import io.github.cdimascio.dotenv.Dotenv;

/**
 * DatabaseConnection class to manage database connections.
 * 
 * @author: Abiodun Magret Oyedele
 * Date: 2025-12-06
 */
public class DatabaseConnection {
    private static final Dotenv dotenv = Dotenv.load();

    private static final String url = dotenv.get("DB_URL");
    private static final String user = dotenv.get("DB_USER");
    private static final String password = dotenv.get("DB_PASSWORD");

    /**
     * Get a connection to the database.
     * @return Connection object
     */
    public static Connection getcon() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
            System.out.println(connection);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Main method to test the database connection.
     * @param args Command line arguments
     */

    public static void main(String[] args) {
        Connection conn = getcon();
        if (conn != null) {
            System.out.println("Connection successful!");
        } else {
            System.out.println("Connection failed.");
        }
    }
}
