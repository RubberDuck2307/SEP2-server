package database.task;

import database.SetParser;
import model.*;

import java.sql.*;
import java.util.ArrayList;

/**
 * the class that the database operations related to tasks table.
 *
 * @author Anna Andrlova, Alex Bolfa, Cosmin Demian, Jan Metela, Arturs Ricards Rijnieks
 * @version 1.0 - May 2023
 */
public class TaskService {

    private Connection conn;
    private SetParser setParser;

    /**
     * The constructor sets the connection to the given parameter and initializes the SetParser.
     * @param conn
     */
    public TaskService(Connection conn) {
        this.conn = conn;
        setParser = new SetParser();
    }

    /**
     * saves the task to the database
     * @param task
     * @return the generated id of the task.
     * @throws SQLException
     */

    public Long saveTask(Task task) throws SQLException {

        TaskDO taskDO = new TaskDO(task);

        String query = "INSERT INTO TASKS (project_id, name, description, status, priority, deadline, estimated_time, starting_date)" +
                "VALUES (" + taskDO.getProjectId() + ", " + taskDO.getName() + ", " + taskDO.getDescription() + ", " + taskDO.getStatus() + ", " + taskDO.getPriority() + ", " + taskDO.getDeadline() + ", " + taskDO.getEstimatedTime() + ", " + taskDO.getStartingDate() + ");";
        PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        int affectedRows = statement.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating user failed, no rows affected.");
        }
        ResultSet generatedKeys = statement.getGeneratedKeys();
        Long id;
        if (generatedKeys.next()) {
            id = generatedKeys.getLong("id");
        } else {
            throw new SQLException("Creating user failed, no ID obtained.");
        }
        return id;
    }

    /**
     * updates the task in the database
     * @param task
     * @throws SQLException
     */
    public void updateTask(Task task) throws SQLException {
        TaskDO taskDO = new TaskDO(task);

        if (taskDO.getId() == "NULL") {
            throw new RuntimeException("Id cannot be null");
        }

        String query = "UPDATE tasks SET name = " + taskDO.getName() + ", description = " + taskDO.getDescription() + ", status = " + taskDO.getStatus()
                + ", priority = " + taskDO.getPriority() + ", deadline = " + taskDO.getDeadline() + ", estimated_time = " + taskDO.getEstimatedTime() + ", starting_date = " + taskDO.getStartingDate() + ", project_id = " + taskDO.getProjectId() + " WHERE id = " + taskDO.getId() + ";";
        Statement statement = conn.createStatement();
        statement.executeUpdate(query);
    }

    /**
     *
     * @param projectId id of the project
     * @return list of tasks of the given project
     * @throws SQLException
     */
    public TaskList getAllTasksOfProject(Long projectId) throws SQLException {
        String query = "SELECT * FROM tasks WHERE project_id = " + projectId + ";";
        PreparedStatement st = conn.prepareStatement(query);
        ResultSet set = st.executeQuery();
        TaskList taskList = setParser.getTasksFromSet(set);

        for (int i = 0; i < taskList.size(); i++) {
            String workerQuery = "SELECT * FROM employees WHERE working_number in (SELECT working_number FROM worker_task WHERE task_id = " + taskList.getTask(i).getId() + ");";
            PreparedStatement workerSt = conn.prepareStatement(workerQuery);
            ResultSet workerSet = workerSt.executeQuery();
            EmployeeList employees = setParser.getAllEmployeesFromSet(workerSet);
            taskList.getTask(i).setWorkers(employees);
        }

        return taskList;
    }

    /**
     *  assigns worker to the task by creating new row in the worker_task table
     * @param workingNumber id of the worker
     * @param taskID id of the task
     * @throws SQLException
     */
    public void assignWorkerToTask(Integer workingNumber, Long taskID) throws SQLException {
        String query = "INSERT INTO worker_task VALUES(" + workingNumber.toString() + ", " + taskID.toString() + ");";
        PreparedStatement st = conn.prepareStatement(query);
        st.executeUpdate();
    }

    /**
     * removes worker from the task by deleting the row in the worker_task table
     * @param workingNumber id of the worker
     * @param taskID id of the task
     * @throws SQLException
     */
    public void removeWorkerFromTask(Integer workingNumber, Long taskID) throws SQLException {
        String query = "DELETE FROM worker_task WHERE working_number = " + workingNumber.toString() + " AND task_id = " + taskID.toString() + ";";
        PreparedStatement st = conn.prepareStatement(query);
        st.executeUpdate();
    }

    /**
     *
     * @return all tasks from the database
     * @throws SQLException
     */
    public TaskList getAllTasks() throws SQLException {
        String query = "SELECT * FROM tasks;";
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet set = statement.executeQuery();
        TaskList taskList = setParser.getTasksFromSet(set);
        return taskList;
    }

    /**
     * assigns employees to a task by creating multiple records in the worker_task table. One for each employee working number in the list.
     * @param employeeWorkingNumbers list of working numbers of the employees
     * @param TaskID id of the task
     * @throws SQLException
     */
    public void assignEmployeesToTask(ArrayList<Integer> employeeWorkingNumbers, Long TaskID) throws SQLException {
        String query = "INSERT INTO worker_task VALUES";
        for (int i = 0; i < employeeWorkingNumbers.size(); i++) {
            query += "(" + employeeWorkingNumbers.get(i) + ", " + TaskID + ")";
            if (i != employeeWorkingNumbers.size() - 1) {
                query += ", ";
            }
        }
        query += ";";
        System.out.println(query);
        PreparedStatement st = conn.prepareStatement(query);
        st.executeUpdate();
    }

    /**
     * unassigns employees from a task by deleting multiple records in the worker_task table. One for each employee working number in the list.
     * @param employeeWorkingNumbers list of working numbers of the employees
     * @param TaskID id of the task
     * @throws SQLException
     */
    public void unassignEmployeesFromTask(ArrayList<Integer> employeeWorkingNumbers, Long TaskID) throws SQLException {
        String query = "DELETE FROM worker_task WHERE task_id = " + TaskID + " AND working_number IN (";
        for (int i = 0; i < employeeWorkingNumbers.size(); i++) {
            query += employeeWorkingNumbers.get(i);
            if (i != employeeWorkingNumbers.size() - 1) {
                query += ", ";
            }
        }
        query += ");";
        System.out.println(query);
        PreparedStatement st = conn.prepareStatement(query);
        st.executeUpdate();
    }

  public TaskList getAllTasksByUserId(Integer workingNumber) throws SQLException
  {
      String query = "SELECT * FROM tasks WHERE id in (SELECT id FROM worker_task WHERE working_number = " + workingNumber + " );";
      PreparedStatement st = conn.prepareStatement(query);
      ResultSet set = st.executeQuery();
      TaskList taskList = setParser.getTasksFromSet(set);
      return taskList;
  }
}
