package mediator;

import model.Model;
import model.ProjectList;
import model.TaskList;

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
        return model.getAllProjectsByUserId(workingNumber);
    }

    @Override
    public String hello() {
        return "hello";
    }
}
