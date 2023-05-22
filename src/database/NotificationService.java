package database;

import model.AssignedToProjectNotification;
import model.AssignedToTaskNotification;
import model.ForgottenPasswordNotification;
import model.IdObjectList;

import java.sql.SQLException;
import java.util.ArrayList;

public interface NotificationService {
    boolean addForgetPasswordNotification(Integer workingNumber) throws SQLException;

    void addAssignedToTaskNotification(Integer workingNumber, Long taskID) throws SQLException;

    void addMultipleAssignedToTaskNotification(ArrayList<Integer> workingNumbers, Long taskID) throws SQLException;

    void addMultipleAssignedToProjectNotification(ArrayList<Integer> workingNumbers, Long projectID) throws SQLException;

    IdObjectList<ForgottenPasswordNotification> getForgottenPasswordNotification() throws SQLException;

    IdObjectList<AssignedToTaskNotification> getAssignedToTaskNotification(Integer workingNumber) throws SQLException;

    IdObjectList<AssignedToProjectNotification> getAssignedToProjectNotification(Integer workingNumber) throws SQLException;

    void addAssignedProjectNotification(Integer workingNumber, Long projectID) throws SQLException;
}
