package utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseQuery {

    private static Statement statement;


    //Create statement object
    public static void setStatement(Connection connection) throws SQLException
    {
        statement = connection.createStatement();
    }

    //Return Statement Object
    public static Statement getStatement()
    {
        return statement;
    }
}
