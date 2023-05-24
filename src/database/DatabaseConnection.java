package database;

import model.*;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface DatabaseConnection {


    void deleteTag(Long id) throws SQLException;
    IdObjectList<ForgottenPasswordNotification> getForgottenPasswordNotification() throws SQLException;
    IdObjectList<AssignedToTaskNotification> getAssignedToTaskNotification(Integer workingNumber) throws SQLException;
    IdObjectList<AssignedToProjectNotification> getAssignedToProjectNotification(Integer workingNumber) throws SQLException;
    boolean addForgetPasswordNotification(Integer workingNumber) throws SQLException;

    void addAssignedProjectNotification(Integer workingNumber, Long projectID) throws SQLException;

    void addAssignedToTaskNotification(Integer workingNumber, Long taskID) throws SQLException;

    void addMultipleAssignedToTaskNotification(ArrayList<Integer> workingNumbers, Long taskID) throws SQLException;

    void addMultipleAssignedToProjectNotification(ArrayList<Integer> workingNumbers, Long projectID) throws SQLException;
    Long saveTag(Tag tag) throws SQLException;

    TagList getAllTags() throws SQLException;

    TagList getTagsOfTask(Long taskId) throws SQLException;

    void addTagToTask(Long taskId, Long tagId) throws SQLException;

    void removeTagFromTask(Long taskId, Long tagId) throws SQLException;

    Tag getTag(Long tagId) throws SQLException;

    ProjectList getAllProjectsOfEmployee(int workingNumber) throws SQLException;

    ProjectList getAllProjects() throws SQLException;

    TaskList getAllTasksOfProject(Long projectId) throws SQLException;

    Long saveTask(Task task) throws SQLException;
    
    void deleteTaskById(Long id) throws SQLException;

    void unassignEmployeesFromTask(ArrayList<Integer> employeeWorkingNumbers, Long TaskID) throws SQLException;

    Long saveProject(Project project) throws SQLException;
    void deleteProjectById(Long id) throws SQLException;

    Employee login(UserProfile userProfile) throws SQLException;

    Integer saveEmployee(Employee employee, String password) throws SQLException;

    void deleteEmployeeByWorkingNumber(Integer workingNumber) throws SQLException;

    EmployeeList getAllProjectManagers() throws SQLException;

    EmployeeList getAllWorkers() throws SQLException;

    void updateProject(Project project) throws SQLException;

    void assignWorkerToTask(Integer workingNumber, Long taskID) throws SQLException;

    void removeWorkerFromTask(Integer workingNumber, Long taskID) throws SQLException;

    void assignWorkerToManager(int managerNumber, int workerNumber) throws SQLException;

    void removeWorkerFromManager(int managerNumber, int workerNumber) throws SQLException;

    void assignEmployeeToProject(Integer workingNumber, Long projectID) throws SQLException;

    void removeEmployeeFromProject(Integer workingNumber, Long projectID) throws SQLException;

    Task getTask(Long projectId) throws SQLException;

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

    void updateNote(Note note) throws SQLException;
    void saveNote(Note note) throws SQLException;
    NoteList getAllNotesSavedByEmployee(Integer workingNumber)  throws SQLException;


    void changeTaskStatus(Long taskId, String status) throws SQLException;

    void dismissEmployeesFromProject(ArrayList<Integer> employeeWorkingNumbers, Long projectID) throws SQLException;

    void assignEmployeesToProject(ArrayList<Integer> addedEmployees, Long id) throws SQLException;

    void changePassword(Employee employee, String password) throws SQLException;

    void updateEmployee(Employee employee) throws SQLException;
    
    
}
