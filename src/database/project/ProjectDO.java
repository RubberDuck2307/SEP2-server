package database.project;

import model.Project;

/**
 * The class that is used to convert attributes of an object of UserProfile class to strings so that they can be used in SQL query.
 * @author Anna Andrlova, Alex Bolfa, Cosmin Demian, Jan Metela, Arturs Ricards Rijnieks
 * @version 1.0 - May 2023
 */
public class ProjectDO {

    private String id;
    private String name;
    private String description;
    private String deadline;

    /**
     * takes a Project object and converts all of its attributes to string attributes of this class that can be used in SQL query.
     * @param project
     * @throws RuntimeException if any of the attributes of the project is null and the attribute cannot be null in the database.
     */
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
