package database;

import model.*;
import org.junit.Assert;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeesTest {

    static Database database;

    @BeforeAll
    static void setUp() {
        database = new Database();
        database.connect();
        assertDoesNotThrow(() -> database.clearAllTables());
        assertDoesNotThrow(() -> database.resetSequences());
        Project project = new Project(1L, "Very Interesting", "description", LocalDate.now());
        assertDoesNotThrow(() -> database.saveProject(project));
        Task task = new Task(1L, "Task", "Description", LocalDate.now(), 1, "HIGH", "TO DO", 1L, LocalDate.now());
        assertDoesNotThrow(() -> database.saveTask(task));
    }

    @AfterAll
    static void tearDown() {
        assertDoesNotThrow(() -> database.clearAllTables());
        assertDoesNotThrow(() -> database.resetSequences());
        assertDoesNotThrow(() -> database.addDummyData());

    }

    @Test
    void addEmployeeNull() {
        Employee employee = null;
        assertThrows(RuntimeException.class, () -> database.saveEmployee(employee, "password"));
    }

    @Order(1)
    @Test
    void addEmployeeRegular() {
        Employee employee = new Employee("John Doe", LocalDate.now(), "52064", "M", EmployeeRole.HR, "john@gmail.com");
        assertDoesNotThrow(() -> database.saveEmployee(employee, "password"));
    }

    @Test
    @Order(6)
    void addEmployeeMany() {
        for (int i = 0; i < 12; i++) {


            Employee employee = new Employee("John Doe", LocalDate.now(), "52064", "M", EmployeeRole.HR, "john@gmail.com");
            assertDoesNotThrow(() -> database.saveEmployee(employee, "password"));
        }
    }

    @Order(2)
    @Test
    void loginRightCredentials() throws SQLException {
        Employee employee = new Employee("John Doe", LocalDate.now(), "52064", "M", EmployeeRole.HR, "john@gmail.com");
        int workingNumber;
        workingNumber = database.saveEmployee(employee, "password");
        UserProfile userProfile = new UserProfile(workingNumber, "password");
        assertDoesNotThrow(() -> database.login(userProfile));
    }

    @Order(3)
    @Test
    void loginWrongCredentials() {
        UserProfile userProfile = new UserProfile(1, "wrong");
        assertThrows(RuntimeException.class, () -> database.login(userProfile));
    }


    @Test
    void addEmployeeNullPassword() {
        Employee employee = new Employee("John Doe", LocalDate.now(), "52064", "M", EmployeeRole.HR, "john@gmail.com");
        assertThrows(RuntimeException.class, () -> database.saveEmployee(employee, null));
    }

    @Test
    void addEmployeeNullValues() {
        Employee employee = new Employee(null, null, null, null, null, null, null);
        assertThrows(RuntimeException.class, () -> database.saveEmployee(employee, "password"));
    }

    @Order(4)
    @Test
    void assignWorkerToTaskRegular() {
        assertDoesNotThrow(() -> database.assignWorkerToTask(1001, 1L));

    }

    @Order(5)
    @Test
    void assignWorkerToTaskNonExistingWorker() {
        assertThrows(SQLException.class, () -> database.assignWorkerToTask(245, 1L));
    }

    @Order(5)
    @Test
    void assignWorkerToTaskNonExistingTask() {
        assertThrows(SQLException.class, () -> database.assignWorkerToTask(2, 155L));
    }
    @Order(7)
    @Test
    void assignEmployeesToProject(){
        ArrayList<Integer> employees = new ArrayList<>();
        for (int i = 1000; i < 1012; i++) {
            employees.add(i);
        }
        assertDoesNotThrow(() -> database.assignEmployeesToProject(employees, 1L));
    }


}