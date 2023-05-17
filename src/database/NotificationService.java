package database;

import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class NotificationService {
    private SetParser setParser;
    private Connection conn;

    public NotificationService(Connection conn) {
        this.setParser = new SetParser();
        this.conn = conn;
    }

    public boolean addForgetPasswordNotification(Integer workingNumber) throws SQLException {
        String query = "INSERT INTO forgot_password_notification(working_number) Values(?);";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, workingNumber);
       try {
           statement.executeUpdate();
       }
       catch (PSQLException e){
           return false;
       }
       return true;
    }

}
