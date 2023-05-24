package database;

import model.Project;
import model.ProjectList;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProjectService {
    Long saveProject(Project project) throws SQLException;

    void updateProject(Project project) throws SQLException;

    void deleteProjectById(Long id) throws SQLException;

    void assignEmployeeToProject(Integer workingNumber, Long projectID) throws SQLException;

    void removeEmployeeFromProject(Integer workingNumber, Long projectID) throws SQLException;

    ProjectList getAllProjectsOfEmployee(int workingNumber) throws SQLException;

    void assignEmployeesToProject(ArrayList<Integer> employeeWorkingNumbers, Long ProjectID) throws SQLException;

    void dismissEmployeesFromProject(ArrayList<Integer> employeeWorkingNumbers, Long projectID) throws SQLException;

    ProjectList getAllProjects() throws SQLException;

    Project getProjectById(long projectId) throws SQLException;
}
