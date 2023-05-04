package database;

import model.*;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.time.LocalDate;

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
        Task task = new Task(1L, "Task", "Description", LocalDate.now(),1,"HIGH","TO DO",1L, LocalDate.now());
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
        assertThrows(RuntimeException.class, () -> database.saveEmployee(employee));
    }

    @Order(1)
    @Test
    void addEmployeeRegular() {
        Employee employee = new Employee(1, "John Doe", LocalDate.now(), "52064", "M", EmployeeRole.HR, "john@gmail.com", new UserProfile(1, "john"));
        assertDoesNotThrow(() -> database.saveEmployee(employee));
    }

    @Order(2)
    @Test
    void loginRightCredentials() {
        UserProfile userProfile = new UserProfile(1, "john");
        assertDoesNotThrow(() -> database.login(userProfile));
    }

    @Order(3)
    @Test
    void loginWrongCredentials() {
        UserProfile userProfile = new UserProfile(1, "wrong");
        assertThrows(RuntimeException.class, () -> database.login(userProfile));
    }

    @Test
    void addEmployeeDuplicateWorkingNumber() {
        Employee employee = new Employee(1, "John Doe", LocalDate.now(), "52064", "M", EmployeeRole.HR, "john@gmail.com", new UserProfile(1, "john"));
        assertThrows(SQLException.class, () -> database.saveEmployee(employee));
    }

    @Test
    void addEmployeeNullUserProfile() {
        Employee employee = new Employee(2, "John Doe", LocalDate.now(), "52064", "M", EmployeeRole.HR, "john@gmail.com", null);
        assertThrows(NullPointerException.class, () -> database.saveEmployee(employee));
    }

    @Test
    void addEmployeeNullValues() {
        Employee employee = new Employee(2, null, null, null, null, null, null, null);
        assertThrows(RuntimeException.class, () -> database.saveEmployee(employee));
    }
    @Order(4)
    @Test void assignWorkerToTaskRegular(){
        assertDoesNotThrow(() -> database.assignWorkerToTask(1,1L));

    }
    @Order(5)
    @Test void assignWorkerToTaskNonExistingWorker(){
        assertThrows(SQLException.class, () -> database.assignWorkerToTask(245,1L));
    }
    @Order(5)
    @Test void assignWorkerToTaskNonExistingTask(){
        assertThrows(SQLException.class, () -> database.assignWorkerToTask(2,155L));
    }
}