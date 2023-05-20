package mediator;

import model.*;
import utility.observer.listener.GeneralListener;
import utility.observer.subject.PropertyChangeHandler;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * the class is responsible for creating the local registry and exporting the remote object. It delegates the method calls to the model.
 * * @author Anna Andrlova, Alex Bolfa, Cosmin Demian, Jan Metela, Arturs Ricards Rijnieks
 * * @version 1.0 - May 2023
 */
public class Server implements RemoteModel {

    private Model model;
    private PropertyHandler propertyHandler;

    public Server(Model model) throws RemoteException, MalformedURLException {
        this.model = model;
        startRegistry();
        startServer();
        propertyHandler = new PropertyHandler(new PropertyChangeHandler<>(this));
    }


    private void startRegistry() throws RemoteException {
        try {
            Registry reg = LocateRegistry.createRegistry(1099);
            System.out.println("Registry started...");
        } catch (java.rmi.server.ExportException ex) {
            System.out.println("Registry already started?" + " Message: " + ex.getMessage());
        }
    }

    private void startServer() throws RemoteException, MalformedURLException {
        UnicastRemoteObject.exportObject(this, 0);
        Naming.rebind("Case", this);
    }


    @Override
    public IdObjectList<ForgottenPasswordNotification> getForgottenPasswordNotification() throws RemoteException {
        return model.getForgottenPasswordNotification();
    }

    @Override
    public IdObjectList<AssignedToTaskNotification> getAssignedToTaskNotification(Integer workingNumber) throws RemoteException {
        return model.getAssignedToTaskNotification(workingNumber);
    }

    @Override
    public IdObjectList<AssignedToProjectNotification> getAssignedToProjectNotification(Integer workingNumber) throws RemoteException {
        return model.getAssignedToProjectNotification(workingNumber);
    }

    @Override
    public Long saveTag(Tag tag) throws RemoteException {
        return model.saveTag(tag);
    }

    @Override
    public TagList getAllTags() {
        return model.getAllTags();
    }

    @Override
    public TagList getTagsOfTask(Long taskId) {
        return model.getTagsOfTask(taskId);
    }

    @Override
    public void addTagToTask(Long taskId, Long tagId) {
        model.addTagToTask(taskId, tagId);
    }

    @Override
    public void removeTagFromTask(Long taskId, Long tagId)
            throws RemoteException {
        model.removeTagFromTask(taskId, tagId);
    }

    @Override
    public Tag getTag(Long tagId) {
        return model.getTag(tagId);
    }


    @Override
    public void deleteTag(Long id) throws RemoteException {
        model.deleteTag(id);
    }

    @Override
    public TaskList getAllTasksOfProject(Long id) throws RemoteException {
        return model.getAllTasksOfProject(id);
    }

    @Override
    public ProjectList getAllProjectsByWorkingNumber(Integer workingNumber) throws RemoteException {
        return model.getAllProjectsByUserId(workingNumber);
    }

    @Override
    public Long saveTask(Task task) throws RemoteException {
        return model.saveTask(task);
    }

    public ProjectList getAllProjects() throws RemoteException {
        return model.getAllProjects();
    }

    @Override
    public Long saveProject(Project project) throws RemoteException {
        return model.saveProject(project);
    }

    @Override
    public EmployeeList getEmployeesAssignedToManager(int managerNumber) throws RemoteException {
        return model.getEmployeesAssignedToManager(managerNumber);
    }


    @Override
    public Employee login(UserProfile userProfile) throws RemoteException {

        return model.login(userProfile);
    }

    public void changeTaskStatus(Long taskId, String status) throws RemoteException {
        model.changeTaskStatus(taskId, status);

    }

    public Task getTask(Long projectId) throws RemoteException {
        return model.getTask(projectId);
    }

    public void unassignEmployeesFromTask(ArrayList<Integer> employeeWorkingNumbers, Long TaskID) throws RemoteException {
        model.unassignEmployeesFromTask(employeeWorkingNumbers, TaskID);
    }

    @Override
    public void dismissEmployeesFromProject(ArrayList<Integer> employeeWorkingNumbers, Long projectID) throws RemoteException {
        model.dismissEmployeesFromProject(employeeWorkingNumbers, projectID);
    }

    @Override
    public void assignEmployeesToProject(ArrayList<Integer> addedEmployees, Long id) throws RemoteException {
        model.assignEmployeesToProject(addedEmployees, id);
        model.addMultipleAssignedToProjectNotification(addedEmployees, id);
        propertyHandler.fireMultipleAssignedToProjectNotification(addedEmployees);


    }

    @Override
    public void removeWorkerFromTask(Integer workingNumber, Long taskID) throws RemoteException {
        model.removeWorkerFromTask(workingNumber, taskID);
    }


    @Override
    public Integer saveEmployee(Employee employee, String password) throws RemoteException {
        return model.saveEmployee(employee, password);
    }

    @Override
    public void deleteEmployeeByWorkingNumber(Integer workingNumber) throws RemoteException {
        model.deleteEmployeeByWorkingNumber(workingNumber);
    }


    @Override
    public void assignEmployeeToProject(Integer workingNumber,
                                        Long projectID) {
        model.assignEmployeeToProject(workingNumber, projectID);
        model.addAssignedProjectNotification(workingNumber, projectID);
        propertyHandler.fireAssignedToProjectNotification(workingNumber);
    }

    @Override
    public void removeEmployeeFromProject(Integer workingNumber,
                                          Long projectID) {
        model.removeEmployeeFromProject(workingNumber, projectID);
    }

    @Override
    public void assignWorkerToManager(int managerNumber,
                                      int workerNumber) throws RemoteException {
        model.assignWorkerToManager(managerNumber, workerNumber);
    }

    @Override
    public void removeWorkerFromManager(int managerNumber,
                                        int workerNumber) throws RemoteException {
        model.removeWorkerFromManager(managerNumber, workerNumber);
    }

    @Override
    public EmployeeList getAllProjectManagers() throws RemoteException {
        return model.getAllProjectManagers();
    }

    @Override
    public EmployeeList getAllWorkers() throws RemoteException {
        return model.getAllWorkers();
    }

    @Override
    public void updateProject(Project project) throws RemoteException {
        model.updateProject(project);
    }

    public void assignEmployeesToTask(ArrayList<Integer> employeeWorkingNumbers, Long TaskID) throws RemoteException {
        model.assignEmployeesToTask(employeeWorkingNumbers, TaskID);
        model.addMultipleAssignedToTaskNotification(employeeWorkingNumbers, TaskID);
        propertyHandler.fireMultipleAssignedToTaskNotification(employeeWorkingNumbers);
    }


    public void assignWorkerToTask(Integer workingNumber, Long taskID) throws RemoteException {

        model.assignWorkerToTask(workingNumber, taskID);
        model.addAssignedToTaskNotification(workingNumber, taskID);
        propertyHandler.fireAssignedToTaskNotification(workingNumber);
    }

    @Override
    public String hello() {
        return "hello";
    }

    @Override
    public void updateTask(Task task) throws RemoteException {
        model.updateTask(task);
    }

    public EmployeeList getAllEmployeesAssignedToProject(Long projectId) {
        return model.getAllEmployeesAssignedToProject(projectId);
    }

    @Override
    public EmployeeList getEmployeesOfTask(Long taskId) throws RemoteException {
        return model.getEmployeesOfTask(taskId);
    }

    @Override
    public Employee getEmployeeByWorkingNumber(int workingNumber) {
        return model.getEmployeeByWorkingNumber(workingNumber);
    }

    @Override
    public EmployeeList getAllEmployees() throws RemoteException {
        return model.getAllEmployees();
    }

    @Override
    public Project getProjectById(long projectId) throws RemoteException {
        return model.getProjectById(projectId);
    }

    @Override
    public TaskList getAllTasksByUserId(Integer workingNumber) throws RemoteException {
        return model.getAllTasksByUserId(workingNumber);
    }

    @Override
    public EmployeeList getAllWorkersManagersByWorkerWorkingNumber(
            Integer workingNumber) throws RemoteException {
        return model.getAllWorkersManagersByWorkerWorkingNumber(workingNumber);
    }

    public boolean addForgetPasswordNotification(Integer workingNumber) throws RemoteException {
        boolean result = model.addForgetPasswordNotification(workingNumber);
         propertyHandler.fireForgotPasswordNotification(workingNumber);
        return result;
    }

    @Override
    public void updateEmployee(Employee employee) throws RemoteException {
        model.updateEmployee(employee);
    }

    @Override
    public void changePassword(Employee employee, String password)
            throws RemoteException {
        model.changePassword(employee, password);
    }

    @Override
    public boolean addListener(GeneralListener<String, String> listener, String... propertyNames) throws RemoteException {
        return propertyHandler.addListener(listener, propertyNames);
    }

    @Override
    public boolean removeListener(GeneralListener<String, String> listener, String... propertyNames) throws RemoteException {
        return propertyHandler.addListener(listener, propertyNames);
    }
}
