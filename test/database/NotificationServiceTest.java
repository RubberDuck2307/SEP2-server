package database;

import model.Employee;
import model.EmployeeRole;
import org.junit.jupiter.api.*;

import javax.xml.crypto.Data;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NotificationServiceTest {
    static Database  database;

    @BeforeAll
    static void setUp() {
        database = new Database();
        assertDoesNotThrow(() -> database.clearAllTables());
        assertDoesNotThrow(() -> database.resetSequences());
        assertDoesNotThrow(() -> database.addDummyData());
    }

    @AfterAll
    static void tearDown() {
        assertDoesNotThrow(() -> database.clearAllTables());
        assertDoesNotThrow(() -> database.resetSequences());
        assertDoesNotThrow(() -> database.addDummyData());

    }

    @Test
    @Order(1)
    void addForgetPasswordNotification() {
        assertDoesNotThrow(()-> database.saveEmployee(new Employee("karl", LocalDate.now(), "650", "M", EmployeeRole.HR,"hhh@tg.com"),"123"));
        assertDoesNotThrow(()-> database.addForgetPasswordNotification(1000));
    }

    @Test
    @Order(2)
    void addAssignedToTaskNotification() {
        assertDoesNotThrow(()-> database.addAssignedToTaskNotification(1000, 1L));
    }

    @Test
    @Order(3)
    void addMultipleAssignedToTaskNotification(){
        ArrayList<Integer> workingNumbers = new ArrayList<>();
        workingNumbers.add(3);
        workingNumbers.add(1);
        workingNumbers.add(2);
        assertDoesNotThrow(() -> database.addMultipleAssignedToTaskNotification(workingNumbers,1L));
    }

    @Test
    void  addMultipleAssignedToProjectNotification(){
        ArrayList<Integer> workingNumbers = new ArrayList<>();
        workingNumbers.add(3);
        workingNumbers.add(1);
        workingNumbers.add(2);
        assertDoesNotThrow(() -> database.addMultipleAssignedToProjectNotification(workingNumbers,1L));
    }
}