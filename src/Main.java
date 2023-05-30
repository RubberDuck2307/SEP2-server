import database.Database;
import database.DatabaseConnector;
import database.ServiceFactory;
import mediator.RemoteModel;
import mediator.Server;
import model.Model;
import model.ModelManager;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws MalformedURLException, RemoteException, SQLException {

        DatabaseConnector connector = new DatabaseConnector();
        Connection connection = connector.connect();
        ServiceFactory factory = new ServiceFactory(connection);
        Database database = new Database(factory);
        Model model = new ModelManager(database);
        RemoteModel server = new Server(model);

    }
}