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

    void saveEmployee(Employee employee);

    void updateProject(Project project);


    ArrayList<Employee> getEmployeesAssignedToManager(int managerNumber);
}
