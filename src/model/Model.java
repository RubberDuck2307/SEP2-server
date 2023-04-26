package model;

public interface Model {

    TaskList getAllTasksOfProject(Long id);

    ProjectList getAllProjectsByUserId(Integer workingNumber);
}
