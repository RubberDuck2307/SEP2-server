package database;

import database.project.ProjectService;
import database.task.TaskService;
import model.*;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.ArrayList;

public class NotificationService {
    private SetParser setParser;
    private Connection conn;
    private TaskService taskService;
    private ProjectService projectService;

    public NotificationService(Connection conn) {
        this.setParser = new SetParser();
        this.conn = conn;
        this.taskService = new TaskService(conn);
        this.projectService = new ProjectService(conn);
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
        String query = "INSERT INTO assigned_task_notification(workers_number, task_id) Values(?, ?);";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, workingNumber);
        statement.setLong(2, taskID);
        statement.executeUpdate();
    }

    public void addMultipleAssignedToTaskNotification(ArrayList<Integer> workingNumbers, Long taskID) throws SQLException {
        String query = "INSERT INTO assigned_task_notification(workers_number, task_id) values";
        for (int i = 0; i < workingNumbers.size(); i++) {
            query += "(?, ?),";
            if (i == workingNumbers.size() - 1) {
                query = query.substring(0, query.length() - 1);
                query = query + ";";
            }
        }
        PreparedStatement statement = conn.prepareStatement(query);
        int j = 1;
        for (int i = 0; i < workingNumbers.size(); i++) {
            statement.setInt(j, workingNumbers.get(i));
            statement.setLong(j + 1, taskID);
            j += 2;
        }
        statement.executeUpdate();
    }

    public void addMultipleAssignedToProjectNotification(ArrayList<Integer> workingNumbers, Long projectID) throws SQLException {
        String query = "INSERT INTO assigned_project_notification(project_manager_number, project_id) values";
        for (int i = 0; i < workingNumbers.size(); i++) {
            query += "(?, ?),";
            if (i == workingNumbers.size() - 1) {
                query = query.substring(0, query.length() - 1);
                query = query + ";";
            }
        }
        PreparedStatement statement = conn.prepareStatement(query);
        int j = 1;
        for (int i = 0; i < workingNumbers.size(); i++) {
            statement.setInt(j, workingNumbers.get(i));
            statement.setLong(j + 1, projectID);
            j += 2;
        }
        statement.executeUpdate();
    }

    public IdObjectList<ForgottenPasswordNotification> getForgottenPasswordNotification() throws SQLException {
        String query = "SELECT * FROM forgot_password_notification;";
        Statement statement = conn.createStatement();
        ResultSet set = statement.executeQuery(query);
        return setParser.getForgottenPasswordNotifications(set);
    }

    public IdObjectList<AssignedToTaskNotification> getAssignedToTaskNotification(Integer workingNumber) throws SQLException {
        String query = "SELECT * FROM assigned_task_notification WHERE workers_number = ?;";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, workingNumber);
        ResultSet set = statement.executeQuery();
        IdObjectList<AssignedToTaskNotification> notifications = setParser.getAssignedToTaskNotifications(set);
        for (int i = 0; i < notifications.size(); i++) {
            Task task = taskService.getTask(notifications.get(i).getTaskId());
            notifications.get(i).setTaskName(task.getName());
        }
        return notifications;
    }

    public IdObjectList<AssignedToProjectNotification> getAssignedToProjectNotification(Integer workingNumber) throws SQLException {
        String query = "SELECT * FROM assigned_project_notification WHERE project_manager_number = ?;";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, workingNumber);
        ResultSet set = statement.executeQuery();
        IdObjectList<AssignedToProjectNotification> notifications = setParser.getAssignedToProjectNotifications(set);
        for (int i = 0; i < notifications.size(); i++) {
            Project project = projectService.getProjectById(notifications.get(i).getProjectId());
            notifications.get(i).setProjectName(project.getName());
        }
        return notifications;
    }

    public void addAssignedProjectNotification(Integer workingNumber, Long projectID) throws SQLException {
        String query = "INSERT INTO assigned_project_notification(project_manager_number, project_id) Values(?, ?);";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, workingNumber);
        statement.setLong(2, projectID);
        statement.executeUpdate();
    }

}
