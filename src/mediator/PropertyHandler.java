package mediator;

import utility.observer.listener.GeneralListener;
import utility.observer.subject.PropertyChangeHandler;
import utility.observer.subject.RemoteSubject;

import java.lang.reflect.Array;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;

public class PropertyHandler implements RemoteSubject<String, String> {
    private PropertyChangeHandler<String, String> propertyChangeHandler;

    public PropertyHandler(PropertyChangeHandler<String, String> propertyChangeHandler) {
        this.propertyChangeHandler = propertyChangeHandler;
    }

    @Override
    public boolean addListener(GeneralListener<String, String> listener, String... propertyNames) throws RemoteException {
        System.out.println(Arrays.toString(propertyNames));
        return propertyChangeHandler.addListener(listener, propertyNames);

    }

    @Override
    public boolean removeListener(GeneralListener<String, String> listener, String... propertyNames) throws RemoteException {
        return propertyChangeHandler.removeListener(listener, propertyNames);
    }

    public void fireForgotPasswordNotification(Integer workingNumber){
        propertyChangeHandler.firePropertyChange("00|forgetPassword|notification", null, String.valueOf(workingNumber));
        System.out.println("PropertyHandler: fireForgotPasswordNotification");
    }

    public void fireAssignedToProjectNotification(Integer workingNumber){
        propertyChangeHandler.firePropertyChange(workingNumber + "|assignedToProject|notification", null, String.valueOf(workingNumber));
    }

    public void fireMultipleAssignedToProjectNotification(ArrayList<Integer> workingNumbers){
        for (Integer workingNumber : workingNumbers) {
            propertyChangeHandler.firePropertyChange(workingNumber + "|assignedToProject|notification", null, String.valueOf(workingNumber));
        }
    }

    public void fireAssignedToTaskNotification(Integer workingNumber){
        System.out.println("PropertyHandler: fireAssignedToTaskNotification");
        propertyChangeHandler.firePropertyChange(workingNumber + "|assignedToTask|notification", null, String.valueOf(workingNumber));
    }

    public void fireMultipleAssignedToTaskNotification(ArrayList<Integer> workingNumbers){
        for (Integer workingNumber : workingNumbers) {
            propertyChangeHandler.firePropertyChange(workingNumber + "|assignedToTask|notification", null, String.valueOf(workingNumber));
        }
    }
}
