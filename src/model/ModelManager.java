package model;

import database.DatabaseConnection;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * delegates to the database connection.
 *
 * @author Anna Andrlova, Alex Bolfa, Cosmin Demian, Jan Metela, Arturs Ricards Rijnieks
 * @version 1.0 - May 2023
 */
public class ModelManager implements Model
{
    
    private DatabaseConnection databaseConnection;
    
    public ModelManager(DatabaseConnection databaseConnection)
    {
        this.databaseConnection = databaseConnection;
    }
    public Task getTask(Long projectId)
    {
        try
        {
            return databaseConnection.getTask(projectId);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override public Long saveTag(Tag tag)
    {
        try
        {
            return databaseConnection.saveTag(tag);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override public TagList getAllTags()
    {
        try
        {
            return databaseConnection.getAllTags();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override public TagList getTagsOfTask(Long taskId)
    {
        try
        {
            return databaseConnection.getTagsOfTask(taskId);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override public void addTagToTask(Long taskId, Long tagId)
    {
        try
        {
            databaseConnection.addTagToTask(taskId, tagId);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override public void removeTagFromTask(Long taskId, Long tagId)
    {
        try
        {
            databaseConnection.removeTagFromTask(taskId, tagId);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override public Tag getTag(Long tagId)
    {
        try
        {
            return databaseConnection.getTag(tagId);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }








    public TaskList getAllTasksOfProject(Long id)
    {
        try
        {
            return databaseConnection.getAllTasksOfProject(id);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    public ProjectList getAllProjectsByUserId(Integer workingNumber)
    {
        try
        {
            return databaseConnection.getAllProjectsOfEmployee(workingNumber);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    public ProjectList getAllProjects()
    {
        try
        {
            return databaseConnection.getAllProjects();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    public EmployeeList getEmployeesOfTask(Long TaskId)
    {
        try
        {
            return databaseConnection.getEmployeesOfTask(TaskId);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void assignWorkerToTask(Integer workingNumber, Long taskID)
    {
        try
        {
            databaseConnection.assignWorkerToTask(workingNumber, taskID);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void removeWorkerFromTask(Integer workingNumber, Long taskID)
    {
        try
        {
            databaseConnection.removeWorkerFromTask(workingNumber, taskID);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public Long saveTask(Task task)
    {
        try
        {
            return databaseConnection.saveTask(task);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public void unassignEmployeesFromTask(ArrayList<Integer> employeeWorkingNumbers, Long TaskID)
    {
        try
        {
            databaseConnection.unassignEmployeesFromTask(employeeWorkingNumbers, TaskID);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public Long saveProject(Project project)
    {
        try
        {
            return databaseConnection.saveProject(project);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void assignEmployeeToProject(Integer workingNumber, Long projectID)
    {
        try
        {
            databaseConnection.assignEmployeeToProject(workingNumber, projectID);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void removeEmployeeFromProject(Integer workingNumber, Long projectID)
    {
        try
        {
            databaseConnection.removeEmployeeFromProject(workingNumber, projectID);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void assignWorkerToManager(int managerNumber, int workerNumber)
    {
        try
        {
            databaseConnection.assignWorkerToManager(managerNumber, workerNumber);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void removeWorkerFromManager(int managerNumber, int workerNumber)
    {
        try
        {
            databaseConnection.removeWorkerFromManager(managerNumber, workerNumber);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public Employee login(UserProfile userProfile)
    {
        try
        {
            return databaseConnection.login(userProfile);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    public EmployeeList getAllProjectManagers()
    {
        try
        {
            return databaseConnection.getAllProjectManagers();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public EmployeeList getAllWorkers()
    {
        try
        {
            return databaseConnection.getAllWorkers();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public Integer saveEmployee(Employee employee, String password)
    {
        try
        {
            return databaseConnection.saveEmployee(employee, password);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void updateProject(Project project)
    {
        try
        {
            databaseConnection.updateProject(project);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    public EmployeeList getEmployeesAssignedToManager(int managerNumber)
    {
        try
        {
            return databaseConnection.getEmployeesAssignedToManager(managerNumber);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public Employee getEmployeeByWorkingNumber(int workingNumber)
    {
        try
        {
            return databaseConnection.getEmployeeByWorkingNumber(workingNumber);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public EmployeeList getAllEmployees()
    {
        try
        {
            return databaseConnection.getAllEmployees();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public Project getProjectById(long projectId)
    {
        try
        {
            return databaseConnection.getProjectById(projectId);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    @Override
    public void deleteTag(Long id){
        try
        {
            databaseConnection.deleteTag(id);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    @Override
    public TaskList getAllTasksByUserId(Integer workingNumber)
    {
        try
        {
            return databaseConnection.getAllTasksByUserId(workingNumber);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public EmployeeList getAllWorkersManagersByWorkerWorkingNumber(Integer workingNumber)
    {
        try
        {
            return databaseConnection.getAllWorkersManagersByWorkerWorkingNumber(workingNumber);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    @Override
    public void dismissEmployeesFromProject(ArrayList<Integer> employeeWorkingNumbers, Long projectID)
    {
        try
        {
            databaseConnection.dismissEmployeesFromProject(employeeWorkingNumbers, projectID);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    @Override
    public void assignEmployeesToProject(ArrayList<Integer> addedEmployees, Long id)
    {
        try
        {
            databaseConnection.assignEmployeesToProject(addedEmployees, id);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    

    @Override public void changePassword(Employee employee, String password)
    {
        try {
            databaseConnection.changePassword(employee,password);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override public void updateEmployee(Employee employee)
    {
        try {
            databaseConnection.updateEmployee(employee);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void updateTask(Task task)
    {
        try
        {
            databaseConnection.updateTask(task);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void assignEmployeesToTask(ArrayList<Integer> employeeWorkingNumbers, Long TaskID)
    {
        try
        {
            databaseConnection.assignEmployeesToTask(employeeWorkingNumbers, TaskID);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    public EmployeeList getAllEmployeesAssignedToProject(Long projectId)
    {
        try
        {
            return databaseConnection.getAllEmployeesAssignedToProject(projectId);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    public void changeTaskStatus(Long taskId, String status)
    {
        try
        {
            databaseConnection.changeTaskStatus(taskId, status);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
