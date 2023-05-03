package mediator;

import model.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements RemoteModel {

    private Model model;

    public Server(Model model) throws RemoteException, MalformedURLException {
        this.model = model;
        startRegistry();
        startServer();
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
    public TaskList getAllTasksOfProject(Long id) throws RemoteException {
        return model.getAllTasksOfProject(id);
    }

    @Override
    public ProjectList getAllProjectsByWorkingNumber(Integer workingNumber) throws RemoteException {
        System.out.println("Server: " + workingNumber);
        return model.getAllProjectsByUserId(workingNumber);
    }

    @Override
    public void saveTask(Task task) throws RemoteException {
        model.saveTask(task);
    }

    @Override
    public void saveProject(Project project) throws RemoteException {
        model.saveProject(project);
    }

    @Override
    public Employee login(UserProfile userProfile) throws RemoteException {
        return model.login(userProfile);
    }

    @Override
    public void saveEmployee(Employee employee) throws RemoteException {
        model.saveEmployee(employee);
    }

    @Override
    public void updateProject(Project project) throws RemoteException {
        model.updateProject(project);
    }

    public void assignWorkerToTask(Integer workingNumber, Long taskID) throws RemoteException{

        model.assignWorkerToTask(workingNumber, taskID);
    }
    @Override
    public String hello() {
        return "hello";
    }
}
