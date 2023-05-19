package database;

import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
        } catch (PSQLException e) {
            return false;
        }
        return true;
    }

    public void addAssignedToTaskNotification(Integer workingNumber, Long taskID) throws SQLException {
        String query = "INSERT INTO assigned_task_notification(working_number, task_id) Values(?, ?);";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, workingNumber);
        statement.setLong(2, taskID);
        statement.executeUpdate();
    }

    public void addMultipleAssignedToTaskNotification(ArrayList<Integer> workingNumbers, Long taskID) throws SQLException {
        String query = "INSERT INTO assigned_task_notification(working_number, task_id) values";
        for (int i = 0; i < workingNumbers.size(); i++) {
            query += "(?, ?),";
            if (i == workingNumbers.size() - 1) {
                query = query.substring(0, query.length() - 1);
                query = query + ";";
            }
        }
        PreparedStatement statement = conn.prepareStatement(query);
        for (int i = 1; i < workingNumbers.size() + 1;) {
            statement.setInt(i, workingNumbers.get(i));
            statement.setLong(i + 1, taskID);
            i += 2;
        }
        statement.executeUpdate();
    }

    public void addMultipleAssignedToProjectNotification(ArrayList<Integer> workingNumbers, Long projectID) throws SQLException{
        String query = "INSERT INTO assigned_project_notification(working_number, project_id) values";
        for (int i = 0; i < workingNumbers.size(); i++) {
            query += "(?, ?),";
            if (i == workingNumbers.size() - 1) {
                query = query.substring(0, query.length() - 1);
                query = query + ";";
            }
        }
        PreparedStatement statement = conn.prepareStatement(query);
        for (int i = 1; i < workingNumbers.size() + 1; i++) {
            statement.setInt(i, workingNumbers.get(i));
            statement.setLong(i + 1, projectID);
            i += 2;
        }
        statement.executeUpdate();
    }

    public void addAssignedProjectNotification(Integer workingNumber, Long projectID) throws SQLException {
        String query = "INSERT INTO assigned_project_notification(working_number, project_id) Values(?, ?);";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, workingNumber);
        statement.setLong(2, projectID);
        statement.executeUpdate();
    }

}
