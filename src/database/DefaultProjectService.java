package database;

import model.Project;
import model.ProjectList;

import java.sql.*;
import java.util.ArrayList;

/**
 * The class that handles database operations related to projects.
 * @author Anna Andrlova, Alex Bolfa, Cosmin Demian, Jan Metela, Arturs Ricards Rijnieks
 * @version 1.0 - May 2023
 */
public class DefaultProjectService implements ProjectService {

    private Connection conn;
    private SetParser setParser;

    /**
     * The constructor sets the connection to the given parameter and initializes the SetParser.
     * @param conn
     */
    public DefaultProjectService(Connection conn) {
        this.conn = conn;
        setParser = new SetParser();
    }

    /**
     * saves the project to the database
     * @param project
     * @throws SQLException
     */
    @Override
    public Long saveProject(Project project) throws SQLException {
        validateProject(project);


        String query = "INSERT INTO projects (name, description, deadline) VALUES (?,?,?);";
        PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, project.getName());
        statement.setString(2, project.getDescription());
        if (project.getDeadline() != null) {
            statement.setDate(3, Date.valueOf(project.getDeadline()));
        } else {
            statement.setDate(3, null);
        }
        int affectedRows = statement.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating project failed, no rows affected.");
        }
        ResultSet generatedKeys = statement.getGeneratedKeys();
        Long id;
        if (generatedKeys.next()) {
            id = generatedKeys.getLong("id");
        } else {
            throw new SQLException("Creating project failed, no ID obtained.");
        }
        return id;
    }

    /**
     * updates the project in the database
     * @param project
     * @throws SQLException
     */
    @Override
    public void updateProject(Project project) throws SQLException {
        validateProject(project);
        String query = "UPDATE projects SET name = ?, description = ?, deadline = ? WHERE id = ?;";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, project.getName());
        statement.setString(2, project.getDescription());
        if (project.getDeadline() != null) {
            statement.setDate(3, Date.valueOf(project.getDeadline()));
        } else {
            statement.setDate(3, null);
        }
        statement.setLong(4, project.getId());
        statement.executeUpdate();
    }


    /**
     * Deletes the project with the given id from the database
     * @param id
     * @throws SQLException
     */
    @Override
    public void deleteProjectById(Long id) throws SQLException
    {
        String query = "DELETE FROM projects where id = " + id;
        PreparedStatement st = conn.prepareStatement(query);
        st.executeUpdate();
    }

    /**
     * creates a new record in the employee_project table
     * @param workingNumber working number of employee
     * @param projectID id of the project
     * @throws SQLException
     */
    @Override
    public void assignEmployeeToProject(Integer workingNumber, Long projectID) throws SQLException {
        String query = "INSERT INTO employee_project VALUES(" + workingNumber.toString() + ", " + projectID.toString() + ");";
        PreparedStatement st = conn.prepareStatement(query);
        st.executeUpdate();
    }

    /**
     * removes the record from the employee_project table
     * @param workingNumber working number of employee
     * @param projectID id of the project
     * @throws SQLException
     */

    @Override
    public void removeEmployeeFromProject(Integer workingNumber, Long projectID) throws SQLException {
        String query = "DELETE FROM employee_project WHERE working_number = " + workingNumber.toString() + " AND project_id = " + projectID.toString() + ";";
        PreparedStatement st = conn.prepareStatement(query);
        st.executeUpdate();
    }

    /**
     * @param workingNumber working number of employee
     * @return all projects of an employee
     * @throws SQLException
     */
    @Override
    public ProjectList getAllProjectsOfEmployee(int workingNumber) throws SQLException {

        String query = "SELECT * FROM projects WHERE id in (SELECT project_id FROM employee_project WHERE working_number = " + workingNumber + " );";
        PreparedStatement st = conn.prepareStatement(query);
        ResultSet set = st.executeQuery();
        ProjectList projectList = setParser.getAllProjectsFromSet(set);
        return projectList;
    }

    /**
     * assigns employees to a project by creating multiple records in the employee_project table. One for each employee working number in the list.
     * @param employeeWorkingNumbers working numbers of employees
     * @param ProjectID id of the project
     * @throws SQLException
     */

    @Override
    public void assignEmployeesToProject(ArrayList<Integer> employeeWorkingNumbers, Long ProjectID) throws SQLException {
        if (employeeWorkingNumbers.size() == 0) {
            return;
        }
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

    /**
     * Dismisses employees with the given working numbers from the project with the given id
     * @param employeeWorkingNumbers
     * @param projectID
     * @throws SQLException
     */

    @Override
    public void dismissEmployeesFromProject(ArrayList<Integer> employeeWorkingNumbers, Long projectID) throws SQLException
    {
        String query = "DELETE FROM employee_project WHERE project_id = " + projectID + " AND working_number IN (";
        for (int i = 0; i < employeeWorkingNumbers.size(); i++) {
            query += employeeWorkingNumbers.get(i);
            if (i != employeeWorkingNumbers.size() - 1) {
                query += ", ";
            }
        }
        query += ");";
        PreparedStatement st = conn.prepareStatement(query);
        st.executeUpdate();
    }
    /**
     *
     * @return all the projects in the database
     * @throws SQLException
     */
    @Override
    public ProjectList getAllProjects() throws SQLException {
        String query = "SELECT * FROM projects;";
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet set = statement.executeQuery();
        ProjectList projectList = setParser.getAllProjectsFromSet(set);

        return projectList;
    }

    /**
     *
     * @param projectId
     * @return the project with the given id
     * @throws SQLException
     */
    @Override
    public Project getProjectById(long projectId) throws SQLException
  {
      String query = "SELECT * FROM projects;";
      PreparedStatement statement = conn.prepareStatement(query);
      ResultSet set = statement.executeQuery();
      Project project = setParser.getAllProjectsFromSet(set).getProjectByID(projectId);
      return project;
  }

    /**
     * check if the project can be stored in the database.
     * @param project
     */
  private void validateProject(Project project){
    if(project.getName() == null || project.getName().isEmpty()){
      throw new IllegalArgumentException("Project name cannot be empty");
    }
  }
  
}
