package database;

import model.ProjectList;
import model.TaskList;

import java.sql.SQLException;

public interface DatabaseConnection {


    ProjectList getAllProjectsOfEmployee(int workingNumber) throws SQLException;

    TaskList getAllTasksOfProject(Long projectId) throws SQLException;

}
