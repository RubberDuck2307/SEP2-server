package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnector {

    private Connection conn;


    /**
     * The method connecting to the database
     */
    public Connection connect() {
        conn = null;
        try {
            conn = DriverManager.getConnection(Credentials.url, Credentials.user, Credentials.password);
            String query = "SET SCHEMA 'company';";
            PreparedStatement st = conn.prepareStatement(query);
            st.executeQuery();
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;

    }

    /**
     * The method disconnecting from the database
     */

    public void disconnect() {
        try {
            conn.close();
            System.out.println("Disconnected from the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
