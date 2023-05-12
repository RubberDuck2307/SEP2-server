package database;

import model.*;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DatabaseConnection {


    ProjectList getAllProjectsOfEmployee(int workingNumber) throws SQLException;

    TaskList getAllTasksOfProject(Long projectId) throws SQLException;

    Long saveTask(Task task) throws SQLException;

    void unassignEmployeesFromTask(ArrayList<Integer> employeeWorkingNumbers, Long TaskID) throws SQLException;

    void saveProject(Project project) throws SQLException;

    Employee login(UserProfile userProfile) throws SQLException;

    Integer saveEmployee(Employee employee, String password) throws SQLException;

    EmployeeList getAllProjectManagers() throws SQLException;
    EmployeeList getAllWorkers() throws SQLException;

    void updateProject(Project project) throws SQLException;

    void assignWorkerToTask(Integer workingNumber, Long taskID) throws SQLException;
    void removeWorkerFromTask(Integer workingNumber, Long taskID) throws SQLException;

    void assignWorkerToManager(int managerNumber, int workerNumber) throws SQLException;
    void removeWorkerFromManager(int managerNumber, int workerNumber) throws SQLException;

    void assignEmployeeToProject(Integer workingNumber, Long projectID) throws SQLException;
    void removeEmployeeFromProject(Integer workingNumber, Long projectID) throws SQLException;

    EmployeeList getAllEmployeesAssignedToProject(Long projectId) throws SQLException;
    EmployeeList getEmployeesAssignedToManager(int managerNumber) throws SQLException;
    void updateTask(Task task) throws SQLException;
    EmployeeList getEmployeesOfTask(Long TaskId) throws SQLException;
    void assignEmployeesToTask(ArrayList<Integer> employeeWorkingNumbers, Long TaskID) throws SQLException;
    Employee getEmployeeByWorkingNumber(int workingNumber) throws SQLException;
    EmployeeList getAllEmployees() throws SQLException;
    Project getProjectById(long projectId) throws SQLException;
    TaskList getAllTasksByUserId(Integer workingNumber) throws SQLException;
    EmployeeList getAllWorkersManagersByWorkerWorkingNumber(Integer workingNumber) throws SQLException;
}
