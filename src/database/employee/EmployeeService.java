package database.employee;

import database.SetParser;
import database.task.TaskDO;
import model.Employee;
import model.EmployeeList;
import model.UserProfile;

import java.sql.*;

/**
 * The class that handles the database operations related to the employees table and user_profiles table.
 *
 * @author Anna Andrlova, Alex Bolfa, Cosmin Demian, Jan Metela, Arturs Ricards Rijnieks
 * @version 1.0 - May 2023
 */
public class EmployeeService
{

  private Connection conn;
  private SetParser setParser;

  /**
   * The constructor sets the connection to the parameter and initializes the SetParser.
   *
   * @param connection connection to the database
   */

  public EmployeeService(Connection connection)
  {
    this.conn = connection;
    setParser = new SetParser();
  }

  /**
   * @return all the projectManagers from the database.
   */
  public EmployeeList getAllProjectManagers()
  {
    EmployeeList employeeList = new EmployeeList();
    try
    {
      String query = "SELECT * FROM employees WHERE role = 'PROJECT M';";
      PreparedStatement st = conn.prepareStatement(query);
      ResultSet rs = st.executeQuery();
      employeeList = setParser.getAllEmployeesFromSet(rs);
      System.out.println(employeeList);
    }
    catch (SQLException e)
    {
      System.out.println(e.getMessage());
    }
    return employeeList;
  }

  /**
   * @return all the workers from the database.
   */
  public EmployeeList getAllWorkers()
  {
    EmployeeList employeeList = new EmployeeList();
    try
    {
      String query = "SELECT * FROM employees WHERE role = 'WORKER';";
      PreparedStatement st = conn.prepareStatement(query);
      ResultSet rs = st.executeQuery();
      employeeList = setParser.getAllEmployeesFromSet(rs);
      System.out.println(employeeList);
    }
    catch (SQLException e)
    {
      System.out.println(e.getMessage());
    }
    return employeeList;
  }

  /**
   * saves employee object and a user profile created to the database
   *
   * @param employee the employee to be stored into the database
   * @param password the password of the user profile to be stored into the database
   * @return the working number generated for the employee
   */

  public Integer saveEmployee(Employee employee, String password)
      throws SQLException
  {
    EmployeeDO employeeDO = new EmployeeDO(employee);
    String query =
        "INSERT INTO employees (name, email, phone_number, dob, gender, role) VALUES ("
            + employeeDO.getName() + ", " + employeeDO.getEmail() + ", "
            + employeeDO.getPhoneNumber() + ", " + employeeDO.getDob() + ", "
            + employeeDO.getGender() + ", " + employeeDO.getRole() + ");";
    PreparedStatement statement = conn.prepareStatement(query,
        Statement.RETURN_GENERATED_KEYS);
    int affectedRows = statement.executeUpdate();

    if (affectedRows == 0)
    {
      throw new SQLException("Creating user failed, no rows affected.");
    }
    UserProfile userProfile;
    ResultSet generatedKeys = statement.getGeneratedKeys();
    if (generatedKeys.next())
    {
      userProfile = new UserProfile(generatedKeys.getInt(2), password);
    }
    else
    {
      throw new SQLException("Creating user failed, no ID obtained.");
    }

    addUserProfile(userProfile);
    return userProfile.getWorkingNumber();
  }

  /**
   * saves UserProfile object to the database
   *
   * @param userProfile the user profile to be stored into the database
   * @throws SQLException
   */
  public void addUserProfile(UserProfile userProfile) throws SQLException
  {
    UserProfileDO userProfileDO = new UserProfileDO(userProfile);
    String query =
        "INSERT INTO user_profiles (working_number, password) VALUES ("
            + userProfileDO.getWorkingNumber() + ", "
            + userProfileDO.getPassword() + ");";
    Statement statement = conn.createStatement();
    statement.executeUpdate(query);
  }

  /**
   * checks if the given userProfile is in the database and if so returns the employee related to the userProfile
   *
   * @param userProfile the user profile to be checked
   * @return employee related to the userProfile or null if the userProfile is not in the database
   * @throws SQLException
   */

  public Employee login(UserProfile userProfile) throws SQLException
  {
    UserProfileDO userProfileDO = new UserProfileDO(userProfile);
    String query = "SELECT * FROM user_profiles WHERE working_number = "
        + userProfileDO.getWorkingNumber() + " AND password = "
        + userProfileDO.getPassword() + ";";
    PreparedStatement statement = conn.prepareStatement(query);
    ResultSet rs = statement.executeQuery();
    if (!rs.next())
    {
      System.out.println("Invalid working number or password");
      return null;
    }
    else
    {
      System.out.println("Login successful");
      query = "SELECT * FROM employees WHERE working_number = "
          + userProfileDO.getWorkingNumber() + ";";
      statement = conn.prepareStatement(query);
      rs = statement.executeQuery();
      System.out.println(rs);
      return setParser.getAllEmployeesFromSet(rs).get(0);
    }
  }

  /**
   * @param managerNumber the working number of the manager
   * @return all employees assigned to the manager
   * @throws SQLException
   */
  public EmployeeList getEmployeesAssignedToManager(int managerNumber)
      throws SQLException
  {
    String query =
        "SELECT * FROM employees WHERE working_number in (SELECT worker_number FROM manager_worker WHERE manager_number = "
            + managerNumber + ");";
    PreparedStatement st = conn.prepareStatement(query);
    ResultSet set = st.executeQuery();
    EmployeeList employeeList = setParser.getAllEmployeesFromSet(set);
    System.out.println(employeeList);
    return employeeList;
  }

  /**
   * @param TaskId the id of the task
   * @return all employees assigned to the task
   * @throws SQLException
   */
  public EmployeeList getEmployeesOfTask(Long TaskId) throws SQLException
  {
    String query =
        "SELECT * FROM employees WHERE working_number in (SELECT working_number FROM worker_task WHERE task_id = "
            + TaskId + ");";
    PreparedStatement st = conn.prepareStatement(query);
    ResultSet set = st.executeQuery();
    EmployeeList employeeList = setParser.getAllEmployeesFromSet(set);
    return employeeList;
  }

  /**
   * add a record to the manager_worker table
   *
   * @param managerNumber the working number of the manager
   * @param workerNumber  the working number of the worker
   * @throws SQLException
   */
  public void assignWorkerToManager(Integer managerNumber, Integer workerNumber)
      throws SQLException
  {
    String query =
        "INSERT INTO manager_worker VALUES(" + managerNumber.toString() + ", "
            + workerNumber.toString() + ");";
    PreparedStatement st = conn.prepareStatement(query);
    st.executeUpdate();
  }

  /**
   * remove a record from the manager_worker table
   *
   * @param managerNumber the working number of the manager
   * @param workerNumber  the working number of the worker
   * @throws SQLException
   */
  public void removeWorkerFromManager(Integer managerNumber,
      Integer workerNumber) throws SQLException
  {
    String query =
        "INSERT INTO worker_task VALUES(" + managerNumber + ", " + workerNumber
            + ");";
    query = "DELETE FROM manager_worker WHERE manager_number = "
        + managerNumber.toString() + " AND worker_number = "
        + workerNumber.toString() + ";";
    PreparedStatement st = conn.prepareStatement(query);
    st.executeUpdate();
  }

  /**
   * @param projectId the id of the project
   * @return returns all employees assigned to the project
   * @throws SQLException
   */
  public EmployeeList getAllEmployeesAssignedToProject(Long projectId)
      throws SQLException
  {
    String query =
        "SELECT * FROM employees WHERE working_number in (SELECT working_number FROM employee_project WHERE project_id = "
            + projectId + " );";
    PreparedStatement st = conn.prepareStatement(query);
    ResultSet set = st.executeQuery();
    EmployeeList employees = setParser.getAllEmployeesFromSet(set);
    return employees;

  }

  /**
   * @return all employees in the database
   * @throws SQLException
   */
  public EmployeeList getAllEmployees() throws SQLException
  {
    String query = "SELECT * FROM employees;";
    PreparedStatement st = conn.prepareStatement(query);
    ResultSet set = st.executeQuery();
    EmployeeList employees = setParser.getAllEmployeesFromSet(set);
    return employees;

  }

  /**
   * @param workingNumber the working number of the employee
   * @return the employee with the given working number
   * @throws SQLException
   */
  public Employee getEmployeeByWorkingNumber(int workingNumber)
      throws SQLException
  {
    String query =
        "SELECT * FROM employees WHERE working_number = " + workingNumber + ";";
    PreparedStatement st = conn.prepareStatement(query);
    ResultSet set = st.executeQuery();
    Employee employee = setParser.getAllEmployeesFromSet(set).get(0);
    return employee;
  }

  public EmployeeList getAllWorkersManagersByWorkerWorkingNumber(
      Integer workingNumber) throws SQLException
  {
    String query =
        "SELECT * FROM employees WHERE working_number in (SELECT manager_number FROM manager_worker WHERE worker_number = "
            + workingNumber + " );";
    PreparedStatement st = conn.prepareStatement(query);
    ResultSet set = st.executeQuery();
    EmployeeList employees = setParser.getAllEmployeesFromSet(set);
    return employees;
  }

  public void updateEmployee(Employee employee) throws SQLException
  {
    EmployeeDO employeeDO = new EmployeeDO(employee);

    if (employeeDO.getWorkingNumber() == "NULL")
    {
      throw new RuntimeException("Id cannot be null");
    }
    String query =
        "UPDATE employees SET name = " + employeeDO.getName() + ", email = "
            + employeeDO.getEmail() + ", phone_number = " + employeeDO.getPhoneNumber()
            + ", dob = " + employeeDO.getDob() + ", gender = "
            + employeeDO.getGender()+ ", role = "
            + employeeDO.getRole() + " WHERE working_number = " + employee.getWorkingNumber() + ";";
    PreparedStatement st = conn.prepareStatement(query);
    st.executeUpdate();
  }

  public void changePassword(Employee employee, String password) throws SQLException
  {
    if (employee.getWorkingNumber().toString().equals("NULL"))
    {
      throw new RuntimeException("Id cannot be null");
    }
    String query =
        "UPDATE user_profiles SET password = " + password  + " WHERE working_number = " + employee.getWorkingNumber() + ";";
    PreparedStatement st = conn.prepareStatement(query);
    st.executeUpdate();
  }
}
