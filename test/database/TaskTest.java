package database;

import model.Project;
import model.Task;
import model.TaskList;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskTest {
    private static Database database;
    @BeforeAll
    static void setUp() {
        database = new Database();
        database.connect();
        assertDoesNotThrow(() -> database.clearAllTables());
        assertDoesNotThrow(() -> database.resetSequences());
        assertDoesNotThrow(() -> database.saveProject(new Project("Interesting", "description", LocalDate.now())));
    }

    @Test
    @Order(1)
    void addTaskRegular() throws SQLException {
        Task task = new Task(1L, "Interesting", "description", LocalDate.now(), 0, "HIGH", "TO DO", 1L, LocalDate.now());
        assertDoesNotThrow(() -> database.saveTask(task));
        TaskList taskList = database.getAllTasks();
        Task savedTask = taskList.getTaskById(1L);
        assertEquals(task, savedTask);
    }

    @Test
    void addTaskNull(){
        Task task = null;
        assertThrows(RuntimeException.class, () -> database.saveTask(task));
    }

    @Test
    void addTaskManyNull() {
        Task task = new Task( "Banana", null, null, 0, "HIGH", "TO DO", 1L, null);
        assertDoesNotThrow(() -> database.saveTask(task));
    }

    @Test
    void addTaskNullName(){
        Task task = new Task(1L, null, "description", null, 0, "HIGH", "TO DO", 1L, null);
        assertThrows(RuntimeException.class, () -> database.saveTask(task));
    }

    @Test
    void modifyTask() throws SQLException {
        assertDoesNotThrow(() -> database.saveProject(new Project("Interesting", "description", LocalDate.now())));
        Task task = new Task( 1L,"hello", "how", LocalDate.now(), 0, "LOW", "DONE", 2L, LocalDate.now());
        assertDoesNotThrow(() -> database.updateTask(task));
        TaskList taskList = database.getAllTasks();
        Task savedTask = taskList.getTaskById(1L);
        assertEquals(task, savedTask);
    }
}
