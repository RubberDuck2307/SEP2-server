package database;

import java.sql.Connection;

public class ServiceFactory {
    private EmployeeService employeeService;
    private Connection conn;
    private ProjectService projectService;
    private TaskService taskService;
    private DatabaseManager databaseManager;
    private TagService tagService;
    private NotificationService notificationService;
    private NoteService noteService;
    public ServiceFactory(Connection connection) {
        this.conn = connection;
        this.employeeService = new DefaultEmployeeService(conn);
        this.projectService = new DefaultProjectService(conn);
        this.taskService = new DefaultTaskService(conn);
        this.databaseManager = new DatabaseManager(conn);
        this.noteService = new DefaultNoteService(conn);
        this.tagService = new DefaultTagService(conn);
        this.notificationService = new DefaultNotificationService(conn);
    }


    public EmployeeService getEmployeeService() {
        return employeeService;
    }

    public Connection getConn() {
        return conn;
    }

    public ProjectService getProjectService() {
        return projectService;
    }

    public TaskService getTaskService() {
        return taskService;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public TagService getTagService() {
        return tagService;
    }

    public NotificationService getNotificationService() {
        return notificationService;
    }

    public NoteService getNoteService() {
        return noteService;
    }
}
