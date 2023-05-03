package database;

import model.Project;
import model.Task;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {
    static Database database;
    @BeforeAll
    static void setUp(){
        database = new Database();
        database.connect();
        assertDoesNotThrow(() -> database.clearAllTables());
        assertDoesNotThrow(() -> database.resetSequences());
    }

    @Test
    void assignWorker() throws SQLException {
        database.addDummyData();
        assertDoesNotThrow(()->database.assignWorkerToTask(3,6));
    }


    @Test
    void addProjectNull(){
        Project project = null;
        assertThrows(RuntimeException.class, () -> database.saveProject(project));
    }
    @Test
    void addProjectRegular(){
        Project project = new Project(1L, "Interesting", "description", LocalDate.now());
        assertDoesNotThrow(() -> database.saveProject(project));
    }

    @Test void addProjectAllNull(){
        Project project = new Project(1L, null, null, null);
        assertThrows(RuntimeException.class,() -> database.saveProject(project));
    }

    @Test void addProjectManyNull(){
        Project project = new Project(1L, "Interesting", null, null);
        assertDoesNotThrow(() -> database.saveProject(project));
    }

    @Test void updateTask(){
        Task task = new Task(19L, "Almost", "description", LocalDate.now(), 0, "HIGH", "TO DO", 8L, LocalDate.now());
        assertDoesNotThrow(() -> database.updateTask(task));
    }

    @Test void updateProject(){
        Project project = new Project(8L, "VERY Interesting", "description", LocalDate.now());
        assertDoesNotThrow(() -> database.updateProject(project));
    }

    @Test void addDummyData(){
        assertDoesNotThrow(() -> database.addDummyData());
    }

}