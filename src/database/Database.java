package database;

import model.*;

import javax.xml.crypto.Data;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

public class Database implements DatabaseConnection {

    private Connection conn;

    public Connection connect() {
        conn = null;
        try {
            conn = DriverManager.getConnection(Credentials.url, Credentials.user, Credentials.password);
            String query = "SET SCHEMA 'company';";
            PreparedStatement st = conn.prepareStatement(query);
            st.executeQuery();
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    public ProjectList getAllProjectsOfEmployee(int workingNumber) throws SQLException {
        String query = "SELECT * FROM projects WHERE id in (SELECT id FROM employee_project WHERE working_number = " + workingNumber + " );";
        PreparedStatement st = conn.prepareStatement(query);
        ResultSet set = st.executeQuery();
        ProjectList projectList = new ProjectList();
        while (set.next()) {
            Long id = set.getLong("id");

            String managerQuery = "SELECT * FROM employees WHERE working_number in (SELECT working_number FROM employee_project WHERE project_id = " + id + " ) and role = 'PROJECT_M';";
            PreparedStatement managerSt = conn.prepareStatement(managerQuery);
            ResultSet managerSet = managerSt.executeQuery();
            ArrayList<Employee> managers = getAllEmployeesFromSet(managerSet);

            String name = set.getString("name");
            String description = set.getString("description");
            LocalDate deadline = convertDate(set.getDate("deadline"));
            projectList.addProject(new Project(id, name, description, deadline, managers));
        }

        return projectList;
    }

    public TaskList getAllTasksOfProject(Long projectId) throws SQLException {
        String query = "SELECT * FROM tasks WHERE project_id = " + projectId + ";";
        PreparedStatement st = conn.prepareStatement(query);
        ResultSet set = st.executeQuery();
        TaskList taskList = getTasksFromSet(set);

        for (int i = 0; i < taskList.size(); i++){
            String workerQuery = "SELECT * FROM employees WHERE working_number in (SELECT working_number FROM employee_project WHERE project_id = " + taskList.getTask(i).getId() + " ) and role = 'PROJECT_M';";
            PreparedStatement workerSt = conn.prepareStatement(workerQuery);
            ResultSet workerSet = workerSt.executeQuery();
            ArrayList<Employee> employees = getAllEmployeesFromSet(workerSet);
            taskList.getTask(i).setWorkers(employees);
        }

        return taskList;
    }

    private LocalDate convertDate(Date date) {
        try {
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return localDate;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private TaskList getTasksFromSet(ResultSet set) throws SQLException {
        TaskList taskList = new TaskList();
        while (set.next()) {
            Long id = set.getLong("id");
            Long project_id = set.getLong("project_id");
            String name = set.getString("name");
            String description = set.getString("description");
            String status = set.getString("status");
            String priority = set.getString("priority");
            LocalDate deadline = set.getDate("deadline").toLocalDate();
            Integer estimated_time = set.getInt("estimated_time");
            LocalDate starting_date = set.getDate("starting_date").toLocalDate();
            taskList.addTask(new Task(id, name, description, deadline, estimated_time, priority, status, project_id, starting_date));
        }
        return taskList;
    }

    private ArrayList<Employee> getAllEmployeesFromSet(ResultSet set) throws SQLException{


        ArrayList<Employee> employees = new ArrayList<>();
        while (set.next()) {
            LocalDate dob;
            String name = set.getString("name");
            try {

                dob = set.getDate("dob").toLocalDate();
            }
            catch (NullPointerException e){
                dob = null;
            }
            Integer managerNumber = set.getInt("working_number");
            String gender = set.getString("gender");
            String phoneNumber = set.getString("phone_number");
            employees.add(new Employee(managerNumber, name, dob, phoneNumber, gender));
        }

        return employees;
    }
}