package database;

import model.Employee;
import model.Project;
import model.ProjectList;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Order;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProjectTest {

    static Database database;

    @BeforeAll
    static void setUp() {
        database = new Database();
        assertDoesNotThrow(() -> database.clearAllTables());
        assertDoesNotThrow(() -> database.resetSequences());
    }

    @AfterAll
    static void tearDown(){
        assertDoesNotThrow(() -> database.clearAllTables());
        assertDoesNotThrow(() -> database.resetSequences());
        assertDoesNotThrow(()-> database.addDummyData());
    }
    @Test
    @Order(1)
    void addRegularProject() throws SQLException {
        Project project = new Project("Interesting", "description", LocalDate.now());
        assertDoesNotThrow(() -> database.saveProject(project));
        ProjectList projectList = database.getAllProjects();
        Project savedProject = projectList.getProjectByID(1L);
        assertTrue(savedProject.getDescription().equals(project.getDescription()) && savedProject.getName().equals(project.getName()) && savedProject.getDeadline().equals(project.getDeadline()));
    }

    @Test
    @Order(2)
    void changeProject() throws SQLException {
        Project project = new Project(1L, "Very Interesting", "description", LocalDate.now());
        assertDoesNotThrow(() -> database.updateProject(project));
        Project savedProject = database.getAllProjects().getProjectByID(1L);
        assertTrue(savedProject.getDescription().equals(project.getDescription()) && savedProject.getName().equals(project.getName()) && savedProject.getDeadline().equals(project.getDeadline()));
    }

    @Test
    @Order(3)
    void addProjectManyNull(){
        Project project = new Project( "Interesting", null, null);
        assertDoesNotThrow(() -> database.saveProject(project));
    }

    @Test
    @Order(4)
    void addProjectAllNull(){
        Project project = new Project( null, null, null);
        assertThrows(RuntimeException.class,() -> database.saveProject(project));
    }

    @Test
    @Order(5)
    void addProjectNull(){
        Project project = null;
        assertThrows(RuntimeException.class, () -> database.saveProject(project));
    }

}
