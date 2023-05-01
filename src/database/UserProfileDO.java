package database;

import model.UserProfile;

public class UserProfileDO {

    private String workingNumber;
    private String password;

    public UserProfileDO(UserProfile userProfile){
        if (userProfile.getWorkingNumber() == null) {
            throw new RuntimeException("Working number cannot be null");
        } else {
            workingNumber = userProfile.getWorkingNumber().toString();
        }

        if (userProfile.getPassword() == null) {
            throw new RuntimeException("Password cannot be null");
        } else {
            password = "'" + userProfile.getPassword() + "'";
        }
    }


    public String getWorkingNumber() {
        return workingNumber;
    }

    public String getPassword() {
        return password;
    }

}
