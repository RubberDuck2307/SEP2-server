package database;

import model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * The class is used to parse the result sets from the database into the model objects.
 * @author Anna Andrlova, Alex Bolfa, Cosmin Demian, Jan Metela, Arturs Ricards Rijnieks
 * @version 1.0 - May 2023
 */
public class SetParser {
    /**
     * The method parses the result set from the database into the EmployeeList object.
     * @param set the result set from the database
     * @return EmployeeList
     * @throws SQLException
     */
    public EmployeeList getAllEmployeesFromSet(ResultSet set) throws SQLException {
        ArrayList<Employee> employees = new ArrayList<>();
        while (set.next()) {
            LocalDate dob;
            String name = set.getString("name");
            try {

                dob = set.getDate("dob").toLocalDate();
            } catch (NullPointerException e) {
                dob = null;
            }
            Integer managerNumber = set.getInt("working_number");
            String gender = set.getString("gender");
            String phoneNumber = set.getString("phone_number");
            String role = set.getString("role");
            EmployeeRole employeeRole;
            switch (role) {
                case "PROJECT M":
                    employeeRole = EmployeeRole.PROJECT_MANAGER;
                    break;
                case "WORKER":
                    employeeRole = EmployeeRole.WORKER;
                    break;
                case "HR":
                    employeeRole = EmployeeRole.HR;
                    break;
                case "MAIN M":
                    employeeRole = EmployeeRole.MAIN_MANAGER;
                    break;
                default:
                    throw new RuntimeException("Invalid role");
            }
            String email = set.getString("email");

            employees.add(new Employee(managerNumber, name, dob, phoneNumber, gender, employeeRole, email));
        }
        EmployeeList employeeList = new EmployeeList(employees);
        return employeeList;
    }

    /**
     * The method parses the result set from the database into the ProjectList object.
     * @param set the result set from the database
     * @return ProjectList
     * @throws SQLException
     */
    public ProjectList getAllProjectsFromSet(ResultSet set) throws SQLException {
        ProjectList projectList = new ProjectList();
        while (set.next()) {
            Long id = set.getLong("id");
            String name = set.getString("name");
            String description = set.getString("description");

            LocalDate deadline;
            try {
                deadline = set.getDate("deadline").toLocalDate();
            } catch (NullPointerException e) {
                deadline = null;

            }
            projectList.addProject(new Project(id, name, description, deadline));
        }
        return projectList;
    }

    /**
     * The method parses the result set from the database into the TaskList object.
     * @param set the result set from the database
     * @return TaskList
     * @throws SQLException
     */
    public TaskList getTasksFromSet(ResultSet set) throws SQLException {
        TaskList taskList = new TaskList();
        while (set.next()) {
            Long id = set.getLong("id");
            Long project_id = set.getLong("project_id");
            String name = set.getString("name");
            String description = set.getString("description");
            String status = set.getString("status");
            String priority = set.getString("priority");
            LocalDate deadline;
            try {
                deadline = set.getDate("deadline").toLocalDate();
            } catch (NullPointerException e) {
                deadline = null;
            }
            Integer estimated_time = set.getInt("estimated_time");
            taskList.addTask(new Task(id, name, description, deadline, estimated_time, priority, status, project_id));
        }
        return taskList;
    }

    public TagList getTagsFromSet(ResultSet set) throws SQLException {
        TagList tagList = new TagList();

        while (set.next()){
            Long id = set.getLong("id");
            String name = set.getString("name");
            String color = set.getString("color");
            tagList.addTag(new Tag(name, id, color));
        }
        return tagList;
    }

    public IdObjectList<ForgottenPasswordNotification> getForgottenPasswordNotifications(ResultSet set) throws SQLException{
        IdObjectList<ForgottenPasswordNotification> forgottenPasswordNotifications = new IdObjectList<>();

        while(set.next()){
            Long id = set.getLong("id");
            Integer workingNumber = set.getInt("working_number");

            forgottenPasswordNotifications.add(new ForgottenPasswordNotification(id, workingNumber));
        }
        return forgottenPasswordNotifications;
    }

    public IdObjectList<AssignedToProjectNotification> getAssignedToProjectNotifications(ResultSet set) throws SQLException{
        IdObjectList<AssignedToProjectNotification> assignedToProjectNotifications = new IdObjectList<>();

        while(set.next()){
            Long id = set.getLong("id");
            Integer workingNumber = set.getInt("project_manager_number");
            Long projectId = set.getLong("project_id");

            assignedToProjectNotifications.add(new AssignedToProjectNotification(id, workingNumber, projectId));
        }
        return assignedToProjectNotifications;
    }

    public IdObjectList<AssignedToTaskNotification> getAssignedToTaskNotifications(ResultSet set) throws SQLException{
        IdObjectList<AssignedToTaskNotification> assignedToTaskNotifications = new IdObjectList<>();

            while(set.next()){
                Long id = set.getLong("id");
                Integer workingNumber = set.getInt("workers_number");
                Long taskId = set.getLong("task_id");

                assignedToTaskNotifications.add(new AssignedToTaskNotification(id, workingNumber, taskId));
            }


    return assignedToTaskNotifications;

}}
