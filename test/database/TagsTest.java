package database;

import model.Project;
import model.Tag;
import model.TagList;
import model.Task;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TagsTest {


    static Database database;

    @BeforeAll
    static void setUp() {
        DatabaseConnector connector = new DatabaseConnector();
        Connection connection = connector.connect();
        ServiceFactory factory = new ServiceFactory(connection);
        database = new Database(factory);
        assertDoesNotThrow(() -> database.clearAllTables());
        assertDoesNotThrow(() -> database.resetSequences());
    }

    @AfterAll
    static void tearDown() {
        assertDoesNotThrow(() -> database.clearAllTables());
        assertDoesNotThrow(() -> database.resetSequences());
        assertDoesNotThrow(() -> database.addDummyData());
    }


    @Test
    @Order(1)
    void addOneTag() throws SQLException {
        assertDoesNotThrow(() -> database.saveTag(new Tag("hello", "red")));
        Tag tag = database.getTag(1L);
        assertEquals("hello", tag.getName());
        assertEquals("red", tag.getColor());
    }

    @Test
    @Order(2)
    void addMultipleTags() throws SQLException{
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            assertDoesNotThrow(() -> database.saveTag(new Tag("hello" + finalI, "red")));
        }
    }

    @Test
    @Order(3)
    void addTagToTask() throws SQLException {
        Project project = new Project("Interesting", "description", LocalDate.now());
        database.saveProject(project);
        Task task = new Task(1L, "Interesting", "description", LocalDate.now(), 0, "HIGH", "TO DO", 1L);
        database.saveTask(task);
        assertDoesNotThrow(() -> database.addTagToTask(1L, 1L));
        task = database.getTask(1L);
        assertEquals(1, task.getTags().size());
        assert task.getTags().containsById(new Tag("hello", 1L, "red"));
    }

    @Test
    @Order(4)
    void addNullTag(){
        Tag tag = null;
        assertThrows(IllegalArgumentException.class, () -> database.saveTag(tag));
    }

    @Test
    @Order(5)
    void addTagWithNullName(){
        Tag tag = new Tag(null, null);
        assertThrows(IllegalArgumentException.class, () -> database.saveTag(tag));
    }
}
