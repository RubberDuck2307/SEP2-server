package database;

import model.*;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DatabaseConnection {


    ProjectList getAllProjectsOfEmployee(int workingNumber) throws SQLException;

    TaskList getAllTasksOfProject(Long projectId) throws SQLException;

    void saveTask(Task task) throws SQLException;

    void saveProject(Project project) throws SQLException;

    Employee login(UserProfile userProfile) throws SQLException;

    Integer saveEmployee(Employee employee, String password) throws SQLException;

    void updateProject(Project project) throws SQLException;

    void assignWorkerToTask(Integer workingNumber, Long taskID) throws SQLException;

    EmployeeList getAllEmployeesAssignedToProject(Long projectId) throws SQLException;
    void removeWorkerFromTask(Integer workingNumber, Long taskID) throws SQLException;
    EmployeeList getEmployeesAssignedToManager(int managerNumber) throws SQLException;
    void updateTask(Task task) throws SQLException;
    EmployeeList getEmployeesOfTask(Long TaskId) throws SQLException;
}
