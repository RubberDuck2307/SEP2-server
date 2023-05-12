package model;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface Model {

    TaskList getAllTasksOfProject(Long id);

    ProjectList getAllProjectsByUserId(Integer workingNumber);

    Long saveTask(Task task);

    void unassignEmployeesFromTask(ArrayList<Integer> employeeWorkingNumbers, Long TaskID);

    void saveProject(Project project);
    void assignWorkerToTask(Integer workingNumber, Long taskID);
    void removeWorkerFromTask(Integer workingNumber, Long taskID);

    void assignEmployeeToProject(Integer workingNumber, Long projectID);
    void removeEmployeeFromProject(Integer workingNumber, Long projectID);

    void assignWorkerToManager(int managerNumber, int workerNumber);
    void removeWorkerFromManager(int managerNumber, int workerNumber);

    Employee login(UserProfile userProfile);

    Integer saveEmployee(Employee employee, String password);

    void updateProject(Project project);
    void updateTask(Task task);
    void assignEmployeesToTask(ArrayList<Integer> employeeWorkingNumbers, Long TaskID);
    EmployeeList getAllProjectManagers();
    EmployeeList getAllWorkers();
    EmployeeList getEmployeesOfTask(Long TaskId);
    EmployeeList getAllEmployeesAssignedToProject(Long projectId);
    EmployeeList getEmployeesAssignedToManager(int managerNumber);
    Employee getEmployeeByWorkingNumber(int workingNumber);
    EmployeeList getAllEmployees();
}
