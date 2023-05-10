package database.employee;

import database.SetParser;
import model.Employee;
import model.EmployeeList;
import model.UserProfile;

import java.sql.*;

public class EmployeeService {
    private Connection conn;
    private SetParser setParser;

    public EmployeeService(Connection connection) {
        this.conn = connection;
        setParser = new SetParser();
    }

    public EmployeeList getAllProjectManagers(){
        EmployeeList employeeList = new EmployeeList();
        try {
            String query = "SELECT * FROM employees WHERE role = 'PROJECT M';";
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            employeeList = setParser.getAllEmployeesFromSet(rs);
            System.out.println(employeeList);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return employeeList;
    }

    public Integer saveEmployee(Employee employee, String password) throws SQLException {
        EmployeeDO employeeDO = new EmployeeDO(employee);
        String query = "INSERT INTO employees (name, email, phone_number, dob, gender, role) VALUES (" + employeeDO.getName() + ", " + employeeDO.getEmail() + ", " + employeeDO.getPhoneNumber() + ", " + employeeDO.getDob() + ", " + employeeDO.getGender() + ", " + employeeDO.getRole() + ");";
        PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        int affectedRows = statement.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating user failed, no rows affected.");
        }
        UserProfile userProfile;
        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            userProfile = new UserProfile(generatedKeys.getInt(2), password);
        } else {
            throw new SQLException("Creating user failed, no ID obtained.");
        }

        addUserProfile(userProfile);
        return userProfile.getWorkingNumber();
    }

    public void addUserProfile(UserProfile userProfile) throws SQLException {
        UserProfileDO userProfileDO = new UserProfileDO(userProfile);
        String query = "INSERT INTO user_profiles (working_number, password) VALUES (" + userProfileDO.getWorkingNumber() + ", " + userProfileDO.getPassword() + ");";
        Statement statement = conn.createStatement();
        statement.executeUpdate(query);
    }

    public Employee login(UserProfile userProfile) throws SQLException {
        UserProfileDO userProfileDO = new UserProfileDO(userProfile);
        String query = "SELECT * FROM user_profiles WHERE working_number = " + userProfileDO.getWorkingNumber() + " AND password = " + userProfileDO.getPassword() + ";";
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        if (!rs.next()) {
            throw new RuntimeException("Invalid working number or password");
        } else {
            query = "SELECT * FROM employees WHERE working_number = " + userProfileDO.getWorkingNumber() + ";";
            statement = conn.prepareStatement(query);
            rs = statement.executeQuery();
            System.out.println(rs);
            return setParser.getAllEmployeesFromSet(rs).get(0);
        }
    }

    public EmployeeList getEmployeesAssignedToManager(int managerNumber) throws SQLException {
        String query = "SELECT * FROM employees WHERE working_number in (SELECT working_number FROM manager_worker WHERE manager_number = " + managerNumber + ");";
        PreparedStatement st = conn.prepareStatement(query);
        ResultSet set = st.executeQuery();
        EmployeeList employeeList = setParser.getAllEmployeesFromSet(set);
        return employeeList;
    }

    public EmployeeList getEmployeesOfTask(Long TaskId) throws SQLException {
        String query = "SELECT * FROM employees WHERE working_number in (SELECT working_number FROM worker_task WHERE task_id = " + TaskId + ");";
        PreparedStatement st = conn.prepareStatement(query);
        ResultSet set = st.executeQuery();
        EmployeeList employeeList = setParser.getAllEmployeesFromSet(set);
        return employeeList;
    }

    public void assignWorkerToManager(int managerNumber, int workerNumber) throws SQLException {
        String query = "INSERT INTO worker_task VALUES(" + managerNumber + ", " + workerNumber + ");";
        PreparedStatement st = conn.prepareStatement(query);
        st.executeUpdate();
    }

    public EmployeeList getAllEmployeesAssignedToProject(Long projectId) throws SQLException {
        String query = "SELECT * FROM employees WHERE working_number in (SELECT working_number FROM employee_project WHERE project_id = " + projectId + " );";
        PreparedStatement st = conn.prepareStatement(query);
        ResultSet set = st.executeQuery();
        EmployeeList employees = setParser.getAllEmployeesFromSet(set);
        return employees;

    }


}
