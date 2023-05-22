package database;

import database.project.ProjectService;
import database.task.TaskService;
import model.*;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.ArrayList;

/**
 * The class that handles database operations related to notification.
 * @author Anna Andrlova, Alex Bolfa, Cosmin Demian, Jan Metela, Arturs Ricards Rijnieks
 * @version 1.0 - May 2023
 */
public class NotificationService {
    private SetParser setParser;
    private Connection conn;
    private TaskService taskService;
    private ProjectService projectService;

    /**
     * The constructor sets the connection to the given parameter and initializes the SetParser, TaskService and ProjectService.
     * @param conn
     */
    public NotificationService(Connection conn) {
        this.setParser = new SetParser();
        this.conn = conn;
        this.taskService = new TaskService(conn);
        this.projectService = new ProjectService(conn);
    }

    /**
     * add a notification to the database that a worker with the given working number forgot his password;
     * @param workingNumber
     * @return true if the notification was added, false if the notification was not added
     * @throws SQLException
     */
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

    /**
     * add a notification to the database that a worker with the given working number was assigned to a task with the given task id;
     * @param workingNumber
     * @param taskID
     * @throws SQLException
     */
    public void addAssignedToTaskNotification(Integer workingNumber, Long taskID) throws SQLException {
        String query = "INSERT INTO assigned_task_notification(workers_number, task_id) Values(?, ?);";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, workingNumber);
        statement.setLong(2, taskID);
        statement.executeUpdate();
    }

    /**
     * add multiple notifications to the database that a workers with the given working numbers were assigned to a tasks with the given tasks id;
     * @param workingNumbers
     * @param taskID
     * @throws SQLException
     */

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

    /**
     * add multiple notification to the database that workers with the given working numbers were assigned to a project with the given project id;
     * @param workingNumbers
     * @param projectID
     * @throws SQLException
     */
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

    /**
     * gets all forgotten password notifications from the database;
     * @return
     * @throws SQLException
     */

    public IdObjectList<ForgottenPasswordNotification> getForgottenPasswordNotification() throws SQLException {
        String query = "SELECT * FROM forgot_password_notification;";
        Statement statement = conn.createStatement();
        ResultSet set = statement.executeQuery(query);
        return setParser.getForgottenPasswordNotifications(set);
    }

    /**
     * gets all assigned to task notifications from the database related to the worker with the given working number;
     * @param workingNumber
     * @return
     * @throws SQLException
     */

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

    /**
     * gets all assigned to project notifications from the database related to the worker with the given working number;
     * @param workingNumber
     * @return
     * @throws SQLException
     */

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

    /**
     * add a notification to the database that a worker with the given working number was assigned to a project with the given project id;
     * @param workingNumber
     * @param projectID
     * @throws SQLException
     */
    public void addAssignedProjectNotification(Integer workingNumber, Long projectID) throws SQLException {
        String query = "INSERT INTO assigned_project_notification(project_manager_number, project_id) Values(?, ?);";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, workingNumber);
        statement.setLong(2, projectID);
        statement.executeUpdate();
    }

}
