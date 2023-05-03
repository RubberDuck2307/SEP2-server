package model;

import database.DatabaseConnection;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ModelManager implements Model {

    private DatabaseConnection databaseConnection;

    public ModelManager(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public TaskList getAllTasksOfProject(Long id) {

        try {

            return databaseConnection.getAllTasksOfProject(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ProjectList getAllProjectsByUserId(Integer workingNumber) {

        try {
            return databaseConnection.getAllProjectsOfEmployee(workingNumber);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveTask(Task task) {
        try {
            databaseConnection.saveTask(task);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveProject(Project project) {
        try {
            databaseConnection.saveProject(project);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void assignWorkerToTask(Integer workingNumber, Integer taskID) {
        try {
            databaseConnection.assignWorkerToTask(workingNumber, taskID);
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Employee login(UserProfile userProfile) {
       try {
           return databaseConnection.login(userProfile);
       }
       catch (SQLException e){
              throw new RuntimeException(e);
       }
    }

    @Override
    public void saveEmployee(Employee employee) {
        try {
            databaseConnection.saveEmployee(employee);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateProject(Project project) {
        try {
            databaseConnection.updateProject(project);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
