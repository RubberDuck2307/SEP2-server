package database;

import database.employee.EmployeeDO;
import database.employee.EmployeeService;
import database.employee.UserProfileDO;
import database.project.ProjectDO;
import database.project.ProjectService;
import database.task.TaskDO;
import database.task.TaskService;
import model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Database implements DatabaseConnection {

    private Connection conn;
    private EmployeeService employeeService;
    private ProjectService projectService;
    private TaskService taskService;
    private DatabaseManager databaseManager;

    public Database() {
        connect();
        this.employeeService = new EmployeeService(conn);
        this.projectService = new ProjectService(conn);
        this.taskService = new TaskService(conn);
        this.databaseManager = new DatabaseManager(conn);

    }

    private void connect() {
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

    }

    public void disconnect() {
        try {
            conn.close();
            System.out.println("Disconnected from the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public EmployeeList getAllProjectManagers(){
        return employeeService.getAllProjectManagers();
    }

    public Integer saveEmployee(Employee employee, String password) throws SQLException {
        return employeeService.saveEmployee(employee, password);
    }

    public void addUserProfile(UserProfile userProfile) throws SQLException {
        employeeService.addUserProfile(userProfile);
    }

    public Employee login(UserProfile userProfile) throws SQLException {
        return employeeService.login(userProfile);
    }

    public void saveProject(Project project) throws SQLException {
        projectService.saveProject(project);
    }

    public void updateTask(Task task) throws SQLException {
        taskService.updateTask(task);
    }


    public Long saveTask(Task task) throws SQLException {
        return taskService.saveTask(task);
    }

    public void updateProject(Project project) throws SQLException {
        projectService.updateProject(project);
    }

    public ProjectList getAllProjectsOfEmployee(int workingNumber) throws SQLException {
        return projectService.getAllProjectsOfEmployee(workingNumber);
    }

    public void unassignEmployeesFromTask(ArrayList<Integer> employeeWorkingNumbers, Long TaskID) throws SQLException {
        taskService.unassignEmployeesFromTask(employeeWorkingNumbers, TaskID);
    }

    public TaskList getAllTasksOfProject(Long projectId) throws SQLException {
       return taskService.getAllTasksOfProject(projectId);
    }


    public void assignWorkerToTask(Integer workingNumber, Long taskID) throws SQLException {
        taskService.assignWorkerToTask(workingNumber, taskID);
    }

    public void removeWorkerFromTask(Integer workingNumber, Long taskID) throws SQLException {
        taskService.removeWorkerFromTask(workingNumber, taskID);
    }



    public void assignEmployeeToProject(Integer workingNumber, Long projectID) throws SQLException {
        projectService.assignEmployeeToProject(workingNumber, projectID);
    }

    public void removeEmployeeFromProject(Integer workingNumber, Long projectID) throws SQLException {
        projectService.removeEmployeeFromProject(workingNumber, projectID);
    }



    public void assignEmployeesToProject(ArrayList<Integer> employeeWorkingNumbers, Long ProjectID) throws SQLException {
        projectService.assignEmployeesToProject(employeeWorkingNumbers, ProjectID);
    }

    public TaskList getAllTasks() throws SQLException {
        return taskService.getAllTasks();
    }

    public EmployeeList getEmployeesOfTask(Long TaskId) throws SQLException {
        return employeeService.getEmployeesOfTask(TaskId);
    }

    @Override public EmployeeList getAllEmployees() throws SQLException
    {
        return employeeService.getAllEmployees();
    }

    public ProjectList getAllProjects() throws SQLException {
        return projectService.getAllProjects();
    }

    public void assignWorkerToManager(int managerNumber, int workerNumber) throws SQLException {
        employeeService.assignWorkerToManager(managerNumber, workerNumber);
    }

    public EmployeeList getEmployeesAssignedToManager(int managerNumber) throws SQLException {
        return employeeService.getEmployeesAssignedToManager(managerNumber);
    }

    public EmployeeList getAllEmployeesAssignedToProject(Long projectId) throws SQLException {
        return employeeService.getAllEmployeesAssignedToProject(projectId);
    }

    public void addDummyData() throws SQLException {
        databaseManager.addDummyData();
    }
    public void clearAllTables() throws SQLException {
        databaseManager.clearAllTables();
    }

    public void resetSequences() throws SQLException {
        databaseManager.resetSequences();
    }
    public void assignEmployeesToTask(ArrayList<Integer> employeeWorkingNumbers, Long TaskID) throws SQLException{
        taskService.assignEmployeesToTask(employeeWorkingNumbers, TaskID);
    }
}