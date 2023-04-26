import database.Database;
import mediator.Server;
import model.Model;
import model.ModelManager;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws MalformedURLException, RemoteException, SQLException {

        Database database = new Database();
        database.connect();
        System.out.println(database.getAllTasksOfProject(1L));



    }
}