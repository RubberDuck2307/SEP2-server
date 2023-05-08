package model;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface Model {

    TaskList getAllTasksOfProject(Long id);

    ProjectList getAllProjectsByUserId(Integer workingNumber);

    void saveTask(Task task);

    void saveProject(Project project);
    void assignWorkerToTask(Integer workingNumber, Long taskID);

    Employee login(UserProfile userProfile);

    Integer saveEmployee(Employee employee, String password);

    void updateProject(Project project);
    void updateTask(Task task);
    void assignEmployeesToTask(ArrayList<Integer> employeeWorkingNumbers, Long TaskID);
    EmployeeList getAllProjectManagers();
    EmployeeList getEmployeesOfTask(Long TaskId);
     EmployeeList getAllEmployeesAssignedToProject(Long projectId);
    void removeWorkerFromTask(Integer workingNumber, Long taskID);
    EmployeeList getEmployeesAssignedToManager(int managerNumber);
}
