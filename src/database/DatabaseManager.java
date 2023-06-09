package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The class responsible for modifying the tables in the database and adding dummy data. The class was created for testing purposes only.
 * @author Anna Andrlova, Alex Bolfa, Cosmin Demian, Jan Metela, Arturs Ricards Rijnieks
 * @version 1.0 - May 2023
 */
public class DatabaseManager {

    private Connection conn;

    /**
     * The constructor setting the connection
     * @param conn the connection to the database
     */
    public DatabaseManager(Connection conn) {
        this.conn = conn;
    }

    /**
     * adds dummy data to projects table
     * @throws SQLException
     */
    private void addDummyDataProject() throws SQLException {
        String query = "Insert into projects(name, description, deadline)" +
                "VALUES ('firstProject', 'I like potatoes', '2025-12-9')," +
                "('secondProject', 'I like bananas', '2026-12-4')," +
                "('thirdProject', 'I like apples', '2026-12-4');";
        Statement statement = conn.createStatement();
        statement.executeUpdate(query);
    }

    /**
     * adds dummy data to tasks table
     * @throws SQLException
     */
    private void addDummyDataTasks() throws SQLException {
        String query = "Insert into tasks(name, description, deadline, estimated_time, priority, status, project_id)" +
                "VALUES ('firstTask', 'I like fathers', '2025-12-9', 10, 'HIGH', 'IN PROGRESS', 1)," +
                "('secondTask', 'I like mothers', '2025-12-9', 10, 'HIGH', 'IN PROGRESS', 1)," +
                "('thirdTask', 'I like sons', '2025-12-9', 10, 'HIGH', 'IN PROGRESS', 1)," +
                "('fourthTask', 'I like daughters', '2026-12-4', 10, 'HIGH', 'IN PROGRESS', 2)," +
                "('fifthTask', 'I like brothers', '2026-12-4', 10, 'HIGH', 'IN PROGRESS', 2)," +
                "('sixthTask', 'I like sisters', '2026-12-4', 10, 'HIGH', 'IN PROGRESS', 3)," +
                "('seventhTask', 'I like grandfathers', '2026-12-4', 10, 'HIGH', 'IN PROGRESS', 3);";
        Statement statement = conn.createStatement();
        statement.executeUpdate(query);
    }

    /**
     * adds dummy data to employees table
     * @throws SQLException
     */

    private void addDummyDataEmployees() throws SQLException {
        String query = "INSERT INTO employees(name, working_number, role, gender, dob, phone_number, email)" +
                "VALUES('BOB',1,'WORKER', 'M', '1999-12-9', '123456789','Bob@gmail.com' )," +
                "('ALICE', 2, 'WORKER', 'F', '1999-12-9', '123456789', 'Alice@gmail.com')," +
                "('JOHN', 3, 'WORKER', 'M', '1999-12-9', '123456789', 'John@gmail.com')," +
                "('KARL', 4, 'PROJECT M', 'M', '1999-12-9', '123456789', 'John@gmail.com')," +
                "('JAN', 5, 'MAIN M', 'M', '1999-12-9', '123456789', 'John@gmail.com')," +
                "('ANNICKA', 6, 'HR', 'F', '2000-2-2', '222222222', 'HiMyNameIsAnna2@gmail.com');";
        Statement statement = conn.createStatement();
        statement.executeUpdate(query);
    }

    /**
     * adds dummy data to user_profiles table
     * @throws SQLException
     */

    private void addDummyDataUserProfiles() throws SQLException {
        String query = "INSERT INTO user_profiles( password, working_number)\n" +
                "VALUES ('123', 1)," +
                "('123',2)," +
                "('123',3)," +
                "('123',4),"+
                "('123',5),"+
                "('123',6);";
        Statement statement = conn.createStatement();
        statement.executeUpdate(query);
    }


    private void addDummyDataNotes() throws SQLException
    {
        String query = "INSERT INTO notes(working_number, title, note_text, creation_date)" +
                "VALUES (1, 'I like Mango', 'This should be the first employees note text No. 1', '1999-12-9')," +
                "(1, 'I like Mango', 'This should be the first employees note text No. 2', '1999-12-9')," +
                "(1, 'I like Mango', 'This should be the first employees note text No. 3', '1999-12-9')," +
                "(2, 'I like Pineapple No. 1', 'This should be the second employees note text No. 1', '1999-12-9')," +
                "(2, 'I like Pineapple No. 2', 'This should be the second employees note text No. 2', '1999-12-9')," +
                "(2, 'I like Pineapple No. 3', 'This should be the second employees note text No. 3', '1999-12-9')," +
                "(2, 'I like Pineapple No. 4', 'This should be the second employees note text No. 4', '1999-12-9')," +
                "(2, 'I like Pineapple No. 5', 'This should be the second employees note text No. 5', '1999-12-9')," +
                "(2, 'I like Pineapple No. 6', 'This should be the second employees note text No. 6', '1999-12-9')," +
                "(2, 'I like Pineapple No. 7', '{ This should be the second employees note text No. 7 } This should be the second employees note text No. 7; This should be the second employees note text No. 7; This should be the second employees note text No. 7; This should be the second employees note text No. 7 ;This should be the second employees note text No. 7; This should be the second employees note text No. 7; This should be the second employees note text No. 7; This should be the second employees note text No. 7; This should be the second employees note text No. 7', '1999-12-9')," +
                "(2, 'I like Pineapple No. 8', 'This should be the second employees note text No. 8', '1999-12-9')," +
                "(3, 'I like Papaya', 'This should be the third employees note text',  '1999-12-9')," +
                "(4, 'I like Kiwi', 'This should be the fourth employees note text', '1999-12-9')," +
                "(5, 'I like Dragon-fruit', 'This should be the fifth employees note text', '1999-12-9')," +
                "(6, 'I like Guava', 'This should be the sixth employees note text', '1999-12-9');";
        Statement statement = conn.createStatement();
        statement.executeUpdate(query);
    }


    /**
     * adds dummy data to employee_project table
     * @throws SQLException
     */

    private void addDummyDataEmployeeProject() throws SQLException {
        String query = "INSERT INTO employee_project( working_number, project_id)\n" +
                "VALUES (1, 1)," +
                "(2,1)," +
                "(3,1)," +
                "(2,2)," +
                "(3,2)," +
                "(3,3);";
        Statement statement = conn.createStatement();
        statement.executeUpdate(query);
    }

    /**
     * adds dummy data to worker_task table
     * @throws SQLException
     */

    private void addDummyDataWorkerTask() throws SQLException {
        String query = "INSERT INTO worker_task( working_number, task_id)\n" +
                "VALUES (1, 1)," +
                "(2,1)," +
                "(3,1)," +
                "(2,2)," +
                "(3,2)," +
                "(3,3);";
        Statement statement = conn.createStatement();
        statement.executeUpdate(query);
    }

    /**
     * adds dummy data to manager_worker table
     * @throws SQLException
     */

    private void addDummyDataManagerWorker() throws SQLException {
        String query = "INSERT INTO manager_worker(manager_number, worker_number)\n" +
                "VALUES (4, 2),\n" +
                "       (4, 1),\n" +
                "       (4, 3);";
        Statement statement = conn.createStatement();
        statement.executeUpdate(query);
    }

    /**
     * adds dummy data to tags table
      * @throws SQLException
     */
    private void addDummyDataTags() throws SQLException{
        String query = "Insert INTO tags(name, color)" +
                "VALUES ('tag1', '#b15583'),"+
                "('tag2', '#b15583')," +
                "('tag3', '#b15583');";
        Statement statement = conn.createStatement();
        statement.executeUpdate(query);
    }

    /**
     * The method uses other private methods to add dummy data to the database
     * @throws SQLException
     */
    public void addDummyData() throws SQLException {
        addDummyDataProject();
        addDummyDataTasks();
        addDummyDataEmployees();
        addDummyDataUserProfiles();
        addDummyDataEmployeeProject();
        addDummyDataWorkerTask();
        addDummyDataManagerWorker();
        addDummyDataNotes();

        addDummyDataTags();
    }

    /**
     * clears all tables in the database
     * @throws SQLException
     */
    public void clearAllTables() throws SQLException {
        String query = "DELETE FROM notes cascade; DELETE FROM tag_task cascade; DELETE FROM manager_worker cascade; DELETE FROM employee_project cascade; DELETE FROM worker_task cascade; DELETE FROM tasks cascade; DELETE FROM projects cascade;DELETE FROM user_profiles cascade; DELETE FROM employees cascade; Delete FROM tags cascade;";
        PreparedStatement st = conn.prepareStatement(query);
        st.executeUpdate();
    }


    /**
     * resets all sequences in the database
     * @throws SQLException
     */
    public void resetSequences() throws SQLException {
        String query = "ALTER SEQUENCE projects_id_seq RESTART WITH 1; ALTER SEQUENCE tasks_id_seq RESTART WITH 1; ALTER SEQUENCE employees_working_number_seq RESTART WITH 1000; ALTER SEQUENCE tags_id_seq RESTART WITH 1; ALTER SEQUENCE notes_id_seq RESTART WITH 1;";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.executeUpdate();
    }


}
