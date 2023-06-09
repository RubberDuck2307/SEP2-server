package model;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface Model {

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
    
    void deleteTaskById(Long id);

    void unassignEmployeesFromTask(ArrayList<Integer> employeeWorkingNumbers, Long TaskID);

    Long saveProject(Project project);
    void deleteProjectById(Long id);

    void assignWorkerToTask(Integer workingNumber, Long taskID);

    void removeWorkerFromTask(Integer workingNumber, Long taskID);

    void assignEmployeeToProject(Integer workingNumber, Long projectID);

    void removeEmployeeFromProject(Integer workingNumber, Long projectID);

    void assignWorkerToManager(int managerNumber, int workerNumber);

    void removeWorkerFromManager(int managerNumber, int workerNumber);

    Task getTask(Long projectId);

    void addAssignedProjectNotification(Integer workingNumber, Long projectID);

    void addAssignedToTaskNotification(Integer workingNumber, Long taskID);

    boolean addForgetPasswordNotification(Integer workingNumber);

    Employee login(UserProfile userProfile);

    Integer saveEmployee(Employee employee, String password);

    void deleteEmployeeByWorkingNumber(Integer workingNumber);

    IdObjectList<ForgottenPasswordNotification> getForgottenPasswordNotification() ;
    IdObjectList<AssignedToTaskNotification> getAssignedToTaskNotification(Integer workingNumber);
    IdObjectList<AssignedToProjectNotification> getAssignedToProjectNotification(Integer workingNumber);

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

    void addMultipleAssignedToTaskNotification(ArrayList<Integer> workingNumbers, Long taskID);

    void addMultipleAssignedToProjectNotification(ArrayList<Integer> workingNumbers, Long projectID);

    void updateNote(Note note);

    void saveNote(Note note);

    NoteList getAllNotesSavedByEmployee(Integer workingNumber);
}
