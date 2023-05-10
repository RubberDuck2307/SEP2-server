package database.project;

import database.SetParser;
import model.Project;
import model.ProjectList;

import java.sql.*;
import java.util.ArrayList;

public class ProjectService {

    private Connection conn;
    private SetParser setParser;
    public ProjectService(Connection conn) {
        this.conn = conn;
        setParser = new SetParser();
    }

    public void saveProject(Project project) throws SQLException {
        ProjectDO projectDO = new ProjectDO(project);

        String query = "INSERT INTO projects (name, description, deadline) VALUES (" + projectDO.getName() + ", " + projectDO.getDescription() + ", " + projectDO.getDeadline() + ");";
        Statement statement = conn.createStatement();
        statement.executeUpdate(query);
    }

    public void updateProject(Project project) throws SQLException {
        ProjectDO projectDO = new ProjectDO(project);
        String query = "UPDATE projects SET name = " + projectDO.getName() + ", description = " + projectDO.getDescription() + ", deadline = " + projectDO.getDeadline() + " WHERE id = " + projectDO.getId() + ";";
        Statement statement = conn.createStatement();
        statement.executeUpdate(query);
    }

    public void assignEmployeeToProject(Integer workingNumber, Long projectID) throws SQLException {
        String query = "INSERT INTO employee_project VALUES(" + workingNumber.toString() + ", " + projectID.toString() + ");";
        PreparedStatement st = conn.prepareStatement(query);
        st.executeUpdate();
    }

    public void removeEmployeeFromProject(Integer workingNumber, Long projectID) throws SQLException {
        String query = "DELETE FROM employee_project WHERE working_number = " + workingNumber.toString() + " AND project_id = " + projectID.toString() + ";";
        PreparedStatement st = conn.prepareStatement(query);
        st.executeUpdate();
    }

    public ProjectList getAllProjectsOfEmployee(int workingNumber) throws SQLException {
        String query = "SELECT * FROM projects WHERE id in (SELECT id FROM employee_project WHERE working_number = " + workingNumber + " );";
        PreparedStatement st = conn.prepareStatement(query);
        ResultSet set = st.executeQuery();
        ProjectList projectList = setParser.getAllProjectsFromSet(set);
        return projectList;
    }

    public void assignEmployeesToProject(ArrayList<Integer> employeeWorkingNumbers, Long ProjectID) throws SQLException {
        String query = "INSERT INTO employee_project VALUES";
        for (int i = 0; i < employeeWorkingNumbers.size(); i++) {
            query += "(" + employeeWorkingNumbers.get(i) + ", " + ProjectID + ")";
            if (i != employeeWorkingNumbers.size() - 1) {
                query += ", ";
            }
        }
        query += ";";
        PreparedStatement st = conn.prepareStatement(query);
        st.executeUpdate();
    }

    public ProjectList getAllProjects() throws SQLException {
        String query = "SELECT * FROM projects;";
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet set = statement.executeQuery();
        ProjectList projectList = setParser.getAllProjectsFromSet(set);

        return projectList;
    }

}
