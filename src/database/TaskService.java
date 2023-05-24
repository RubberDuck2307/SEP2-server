package database;

import model.Task;
import model.TaskList;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TaskService {

    Long saveTask(Task task) throws SQLException;

    void updateTask(Task task) throws SQLException;

    void deleteTaskById(Long id) throws SQLException;

    Task getTask(Long taskId) throws SQLException;

    TaskList getAllTasksOfProject(Long projectId) throws SQLException;

    void assignWorkerToTask(Integer workingNumber, Long taskID) throws SQLException;

    void removeWorkerFromTask(Integer workingNumber, Long taskID) throws SQLException;

    TaskList getAllTasks() throws SQLException;

    void assignEmployeesToTask(ArrayList<Integer> employeeWorkingNumbers, Long TaskID) throws SQLException;

    void unassignEmployeesFromTask(ArrayList<Integer> employeeWorkingNumbers, Long TaskID) throws SQLException;

    TaskList getAllTasksByUserId(Integer workingNumber) throws SQLException;

    void changeTaskStatus(Long taskId, String status) throws SQLException;

    void addTagToTask(Long taskId, Long tagId) throws SQLException;

    void removeTagFromTask(Long taskId, Long tagId) throws SQLException;
}
