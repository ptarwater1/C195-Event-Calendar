package Model;

import utils.Database;
import utils.Logger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDatabase {
    private static User activeUser;

    public static User getActiveUser() {
        return activeUser;
    }

    //Login Attempt
    public static Boolean login(String username, String password) {
        try {
            Statement statement = Database.getConnection().createStatement();
            String query = "SELECT * FROM user WHERE userName='" + username + "' AND password='" + password + "'";
            ResultSet results = statement.executeQuery(query);
            if(results.next()) {
                activeUser = new User();
                activeUser.setUsername(results.getString("userName"));
                statement.close();
                Logger.log(username, true);
                return true;
            } else {
                Logger.log(username, false);
                return false;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
        }
    }



