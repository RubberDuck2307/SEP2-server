package model;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface Model
{

    Long saveTag(Tag tag);
    TagList getAllTags();
    TagList getTagsOfTask(Long taskId);
    void addTagToTask(Long taskId, Long tagId);
    void removeTagFromTask(Long taskId, Long tagId);
    Tag getTag(Long tagId);
    void deleteTag(Long id);



    TaskList getAllTasksOfProject(Long id);
    
    ProjectList getAllProjectsByUserId(Integer workingNumber);
    
    Long saveTask(Task task);
    
    void unassignEmployeesFromTask(ArrayList<Integer> employeeWorkingNumbers, Long TaskID);
    
    Long saveProject(Project project);
    
    void assignWorkerToTask(Integer workingNumber, Long taskID);
    
    void removeWorkerFromTask(Integer workingNumber, Long taskID);
    
    void assignEmployeeToProject(Integer workingNumber, Long projectID);
    
    void removeEmployeeFromProject(Integer workingNumber, Long projectID);
    
    void assignWorkerToManager(int managerNumber, int workerNumber);
    
    void removeWorkerFromManager(int managerNumber, int workerNumber);
    
    Task getTask(Long projectId);
    
    Employee login(UserProfile userProfile);

    void deleteTag(Long id);
    TagList getAllTags();
    Integer saveEmployee(Employee employee, String password);
    
    void updateProject(Project project);
    
    void updateTask(Task task);
    
    void assignEmployeesToTask(ArrayList<Integer> employeeWorkingNumbers, Long TaskID);
    
    EmployeeList getAllProjectManagers();
    
    void changeTaskStatus(Long taskId, String status);
    
    ProjectList getAllProjects();
    
    EmployeeList getAllWorkers();
    
    EmployeeList getEmployeesOfTask(Long TaskId);
    
    EmployeeList getAllEmployeesAssignedToProject(Long projectId);
    
    EmployeeList getEmployeesAssignedToManager(int managerNumber);
    
    Employee getEmployeeByWorkingNumber(int workingNumber);
    
    EmployeeList getAllEmployees();
    
    Project getProjectById(long projectId);
    
    TaskList getAllTasksByUserId(Integer workingNumber);
    
    EmployeeList getAllWorkersManagersByWorkerWorkingNumber(Integer workingNumber);
    
    void dismissEmployeesFromProject(ArrayList<Integer> employeeWorkingNumbers, Long projectID);
    
    void assignEmployeesToProject(ArrayList<Integer> addedEmployees, Long id);
    
    void updateEmployee(Employee employee);
    
    void changePassword(Employee employee, String password);
}
