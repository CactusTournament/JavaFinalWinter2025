// RECOMPILING
// javac -d . -cp "lib/java-dotenv-3.1.5.jar:lib/kotlin-stdlib-1.8.22.jar:lib/postgresql-42.6.0.jar" DatabaseConnection.java
// java -cp ".:lib/java-dotenv-3.1.5.jar:lib/kotlin-stdlib-1.8.22.jar:lib/postgresql-42.6.0.jar" JavaFinalWinter2025.DatabaseConnection


package JavaFinalWinter2025;

import java.sql.*;
import io.github.cdimascio.dotenv.Dotenv;

public class DatabaseConnection {
    private static final Dotenv dotenv = Dotenv.load();

    private static final String url = dotenv.get("DB_URL");
    private static final String user = dotenv.get("DB_USER");
    private static final String password = dotenv.get("DB_PASSWORD");

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

    // Add main method to test
    public static void main(String[] args) {
        Connection conn = getcon();
        if (conn != null) {
            System.out.println("Connection successful!");
        } else {
            System.out.println("Connection failed.");
        }
    }
}
