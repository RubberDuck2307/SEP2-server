package database;

import model.Employee;
import model.EmployeeList;
import model.UserProfile;

import java.sql.SQLException;

public interface EmployeeService {
    EmployeeList getAllProjectManagers();

    void deleteEmployeeByWorkingNumber(Integer workingNumber) throws SQLException;

    EmployeeList getAllWorkers();

    Integer saveEmployee(Employee employee, String password)
            throws SQLException;

    void addUserProfile(UserProfile userProfile) throws SQLException;

    Employee login(UserProfile userProfile) throws SQLException;

    EmployeeList getEmployeesAssignedToManager(int managerNumber)
            throws SQLException;

    EmployeeList getEmployeesOfTask(Long TaskId) throws SQLException;

    void assignWorkerToManager(Integer managerNumber, Integer workerNumber)
            throws SQLException;

    void removeWorkerFromManager(Integer managerNumber,
                                 Integer workerNumber) throws SQLException;

    EmployeeList getAllEmployeesAssignedToProject(Long projectId)
            throws SQLException;

    EmployeeList getAllEmployees() throws SQLException;

    Employee getEmployeeByWorkingNumber(int workingNumber)
            throws SQLException;

    EmployeeList getAllWorkersManagersByWorkerWorkingNumber(
            Integer workingNumber) throws SQLException;

    void updateEmployee(Employee employee) throws SQLException;

    void changePassword(Employee employee, String password) throws SQLException;
}
