package utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Database {
    private static final String DBNAME = "U05KKE";
    private static final String URL = "jdbc:mysql://3.227.166.251/" + DBNAME;
    private static final String USERNAME = "U05KKE";
    private static final String PASSWORD = "53688526738";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static Connection conn;

    public Database() {}

    //Connection to Database
    public static void startConnection() {
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connection is Successful.");
        } catch (ClassNotFoundException e) {
            System.out.println("SQLException: " + e.getMessage());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Disconnect from Database
    public static void disconnect() {
        try {
            conn.close();
            System.out.println("Disconnected from Database");
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    // Returns Database Connection
    public static Connection getConnection() {
        return conn;
    }
}
