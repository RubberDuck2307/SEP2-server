package database.task;

import model.Task;

/**
 * The class that is used to convert attributes of an object of Task class to strings so that they can be used in SQL query.
 * @author Anna Andrlova, Alex Bolfa, Cosmin Demian, Jan Metela, Arturs Ricards Rijnieks
 * @version 1.0 - May 2023
 */
public class TaskDO {

    String id;
    String name;
    String description;
    String status;
    String priority;
    String deadline;
    String startingDate;
    String estimatedTime;
    String projectId;

    /**
     * The constructor takes an Employee object and converts all of its attributes to string attributes of this class that can be used in SQL query.
     * @param task
     * @throws RuntimeException if any of the attributes of the task is null and the attribute cannot be null in the database.
     */

    public TaskDO(Task task) {
        if (task.getId() == null) {
            id = "NULL";
        } else {
            id = task.getId().toString();
        }

        if (task.getName() == null) {
            throw new RuntimeException("Name cannot be null");
        } else {
            name = "'" + task.getName() + "'";
        }
        if (task.getProjectId() == null) {
            throw new RuntimeException("Project id cannot be null");
        } else {
            projectId = task.getProjectId().toString();

        }
        if (task.getDescription() == null) {
            description = "NULL";
        } else {
            description = "'" + task.getDescription() + "'";
        }
        if (task.getStatus() == null) {
            throw new RuntimeException("Status cannot be null");
        } else {
            status = "'" + task.getStatus() + "'";
        }
        if (task.getPriority() == null) {
            throw new RuntimeException("Priority cannot be null");
        } else {
            priority = "'" + task.getPriority() + "'";
        }
        if (task.getDeadline() == null) {
            deadline = "NULL";
        } else {
            deadline = "'" + task.getDeadline() + "'";
        }
        if (task.getStartingDate() == null) {
            startingDate = "NULL";
        } else {
            startingDate = "'" + task.getStartingDate() + "'";
        }
        if (task.getEstimatedTime() == 0) {
            estimatedTime = "NULL";
        } else {
            estimatedTime = String.valueOf(task.getEstimatedTime());
        }

    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getPriority() {
        return priority;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setId(String id) {
        this.id = id;
    }

}
