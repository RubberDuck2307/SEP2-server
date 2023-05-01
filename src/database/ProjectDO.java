package database;

import model.Project;

public class ProjectDO {

    String id;
    String name;
    String description;
    String deadline;

    public ProjectDO(Project project) {
        if (project.getId() == null) {
            id = "NULL";
        } else {
            id = project.getId().toString();
        }

        if (project.getName() == null) {
            throw new RuntimeException("Name cannot be null");
        } else {
            name = "'" + project.getName() + "'";
        }
        if (project.getDescription() == null) {
            description = "NULL";
        } else {
            description = "'" + project.getDescription() + "'";
        }
        if (project.getDeadline() == null) {
            deadline = "NULL";
        } else {
            deadline = "'" + project.getDeadline() + "'";
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

    public String getDeadline() {
        return deadline;
    }
}
