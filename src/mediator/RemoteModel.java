package mediator;

import model.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface RemoteModel extends Remote {

    TaskList getAllTasksOfProject(Long id) throws RemoteException;

    ProjectList getAllProjectsByWorkingNumber(Integer workingNumber) throws RemoteException;

    Long saveTask(Task task) throws RemoteException;

    void saveProject(Project project)throws RemoteException;

    void unassignEmployeesFromTask(ArrayList<Integer> employeeWorkingNumbers, Long TaskID) throws RemoteException;
    EmployeeList getEmployeesAssignedToManager(int managerNumber) throws RemoteException;
    Employee login(UserProfile userProfile) throws RemoteException;
    void assignWorkerToTask(Integer workingNumber, Long taskID) throws RemoteException;
    void removeWorkerFromTask(Integer workingNumber, Long taskID) throws RemoteException;
    void assignEmployeesToTask(ArrayList<Integer> employeeWorkingNumbers, Long TaskID) throws RemoteException;
    Integer saveEmployee(Employee employee, String password) throws RemoteException;

    void assignEmployeeToProject(Integer workingNumber, Long projectID) throws RemoteException;
    void removeEmployeeFromProject(Integer workingNumber, Long projectID) throws RemoteException;
    EmployeeList getAllProjectManagers() throws RemoteException;
    EmployeeList getAllEmployeesAssignedToProject(Long projectId) throws RemoteException;
    void updateProject(Project project) throws RemoteException;
    String hello() throws RemoteException;

    void updateTask(Task task) throws RemoteException;
    EmployeeList getEmployeesOfTask(Long taskId) throws RemoteException;
    EmployeeList getAllEmployees() throws RemoteException;
}
