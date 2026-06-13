package Databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class mysqlDBConnection {
    static String url = "jdbc:mysql://localhost:3306/eventvenuedb";
    static String user = "root";
    static String password = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
