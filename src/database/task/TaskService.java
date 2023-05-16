package database.task;

import database.SetParser;
import database.tag.TagService;
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

    private TagService tagService;

    /**
     * The constructor sets the connection to the given parameter and initializes the SetParser.
     *
     * @param conn
     */
    public TaskService(Connection conn) {
        this.conn = conn;
        setParser = new SetParser();
        tagService = new TagService(conn);
    }

    /**
     * saves the task to the database
     *
     * @param task
     * @return the generated id of the task.
     * @throws SQLException
     */

    public Long saveTask(Task task) throws SQLException {
        validateTask(task);

        String query = "INSERT INTO TASKS (project_id, name, description, status, priority, deadline, estimated_time)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setLong(1, task.getProjectId());
        statement.setString(2, task.getName());
        statement.setString(3, task.getDescription());
        statement.setString(4, task.getStatus());
        statement.setString(5, task.getPriority());
        if (task.getDeadline() != null) {
            statement.setDate(6, Date.valueOf(task.getDeadline()));
        } else {
            statement.setDate(6, null);
        }
        statement.setInt(7, task.getEstimatedTime());
        int affectedRows = statement.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating task failed, no rows affected.");
        }
        ResultSet generatedKeys = statement.getGeneratedKeys();
        Long id;
        if (generatedKeys.next()) {
            id = generatedKeys.getLong("id");
        } else {
            throw new SQLException("Creating task failed, no ID obtained.");
        }
        return id;
    }

    /**
     * updates the task in the database
     *
     * @param task
     * @throws SQLException
     */
    public void updateTask(Task task) throws SQLException {
        validateTask(task);
        if (task.getId() == null) {
            throw new RuntimeException("Id cannot be null");
        }

        String query = "UPDATE tasks SET name = ?, description = ?, status = ?, priority = ?," +
                "deadline = ?, estimated_time = ?, project_id = ? WHERE id = ?;";
        PreparedStatement st = conn.prepareStatement(query);
        st.setString(1, task.getName());
        st.setString(2, task.getDescription());
        st.setString(3, task.getStatus());
        st.setString(4, task.getPriority());
        if (task.getDeadline() != null) {
            st.setDate(5, Date.valueOf(task.getDeadline()));
        } else {
            st.setDate(5, null);
        }
        st.setInt(6, task.getEstimatedTime());
        st.setLong(7, task.getProjectId());
        st.setLong(8, task.getId());
        st.executeUpdate();
    }

    /**
     * @param projectId id of the project
     * @return list of tasks of the given project
     * @throws SQLException
     */

    public Task getTask(Long projectId) throws SQLException {
        String query = "SELECT * FROM tasks WHERE id = " + projectId + ";";
        PreparedStatement st = conn.prepareStatement(query);
        ResultSet set = st.executeQuery();
        TaskList taskList = setParser.getTasksFromSet(set);
        Task task = taskList.getTask(0);

        String workerQuery = "SELECT * FROM employees WHERE working_number in (SELECT working_number FROM worker_task WHERE task_id = " + task.getId() + ");";
        PreparedStatement workerSt = conn.prepareStatement(workerQuery);
        ResultSet workerSet = workerSt.executeQuery();
        EmployeeList employees = setParser.getAllEmployeesFromSet(workerSet);
        task.setWorkers(employees);

        task.setTags(tagService.getTagsOfTask(task.getId()));

        return task;
    }

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

            taskList.getTask(i).setTags(tagService.getTagsOfTask(taskList.getTask(i).getId()));
        }

        return taskList;
    }

    /**
     * assigns worker to the task by creating new row in the worker_task table
     *
     * @param workingNumber id of the worker
     * @param taskID        id of the task
     * @throws SQLException
     */
    public void assignWorkerToTask(Integer workingNumber, Long taskID) throws SQLException {
        String query = "INSERT INTO worker_task VALUES(" + workingNumber.toString() + ", " + taskID.toString() + ");";
        PreparedStatement st = conn.prepareStatement(query);
        st.executeUpdate();
    }

    /**
     * removes worker from the task by deleting the row in the worker_task table
     *
     * @param workingNumber id of the worker
     * @param taskID        id of the task
     * @throws SQLException
     */
    public void removeWorkerFromTask(Integer workingNumber, Long taskID) throws SQLException {
        String query = "DELETE FROM worker_task WHERE working_number = " + workingNumber.toString() + " AND task_id = " + taskID.toString() + ";";
        PreparedStatement st = conn.prepareStatement(query);
        st.executeUpdate();
    }

    /**
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
     *
     * @param employeeWorkingNumbers list of working numbers of the employees
     * @param TaskID                 id of the task
     * @throws SQLException
     */
    public void assignEmployeesToTask(ArrayList<Integer> employeeWorkingNumbers, Long TaskID) throws SQLException {
        if (employeeWorkingNumbers.size() == 0) {
            return;
        }
        String query = "INSERT INTO worker_task VALUES";
        for (int i = 0; i < employeeWorkingNumbers.size(); i++) {
            query += "(" + employeeWorkingNumbers.get(i) + ", " + TaskID + ")";
            if (i != employeeWorkingNumbers.size() - 1) {
                query += ", ";
            }
        }
        query += ";";
        ;
        PreparedStatement st = conn.prepareStatement(query);
        st.executeUpdate();
    }

    /**
     * unassigns employees from a task by deleting multiple records in the worker_task table. One for each employee working number in the list.
     *
     * @param employeeWorkingNumbers list of working numbers of the employees
     * @param TaskID                 id of the task
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

    public TaskList getAllTasksByUserId(Integer workingNumber) throws SQLException {
        String query = "SELECT * FROM tasks WHERE id in (SELECT task_id FROM worker_task WHERE working_number = " + workingNumber + " );";
        PreparedStatement st = conn.prepareStatement(query);
        ResultSet set = st.executeQuery();
        TaskList taskList = setParser.getTasksFromSet(set);
        return taskList;
    }

    public void changeTaskStatus(Long taskId, String status) throws SQLException {
        String query = "UPDATE tasks SET status = ? WHERE id = ? ;";
        PreparedStatement st = conn.prepareStatement(query);
        st.setString(1, status);
        st.setLong(2, taskId);
        st.executeUpdate();
    }

    public void addTagToTask(Long taskId, Long tagId) throws SQLException{
        String query = "INSERT INTO tag_task VALUES(?, ?);";
        PreparedStatement st = conn.prepareStatement(query);
        st.setLong(1, taskId);
        st.setLong(2, tagId);
        st.executeUpdate();
    }


    public void validateTask(Task task) {
        if (task.getName() == null || task.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Task name cannot be empty");
        }
        if (task.getProjectId() == null) {
            throw new IllegalArgumentException("Task must be assigned to a project");
        }
        if (task.getStatus()== null){
            throw new IllegalArgumentException("Task must have a status");
        }
        if (task.getPriority() == null) {
            throw new IllegalArgumentException("Task must have a priority");
        }

    }
}
