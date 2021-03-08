package sample;
import java.sql.*;

public class Database {
    public Connection connection = null;
    public Statement stmt=null;
    public ResultSet rs=null;

    //db connection
    public Connection getConnection() {
        try {
           //JDBC Driver registration
            Class.forName("org.postgresql.Driver");

            //open connection
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "postgres", "0000");

            // statement creation
            stmt= connection.createStatement();
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return connection;
    }

}
