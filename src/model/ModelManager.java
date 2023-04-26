package model;

import database.DatabaseConnection;

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

}
