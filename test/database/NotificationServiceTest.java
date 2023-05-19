package database;

import model.Employee;
import model.EmployeeRole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class NotificationServiceTest {
    static Database  database;

    @BeforeEach
    void setUp() {
        database = new Database();
        assertDoesNotThrow(() -> database.clearAllTables());
        assertDoesNotThrow(() -> database.resetSequences());
    }

    @AfterEach
    void tearDown() {
        assertDoesNotThrow(() -> database.clearAllTables());
        assertDoesNotThrow(() -> database.resetSequences());
        assertDoesNotThrow(() -> database.addDummyData());

    }

    @Test
    void addForgetPasswordNotification() {
        assertDoesNotThrow(()-> database.saveEmployee(new Employee("karl", LocalDate.now(), "650", "M", EmployeeRole.HR,"hhh@tg.com"),"123"));
        assertDoesNotThrow(()-> database.addForgetPasswordNotification(1000));
    }
}