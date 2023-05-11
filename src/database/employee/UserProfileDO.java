package database.employee;

import model.UserProfile;

/**
 * The class that is used to convert attributes of an object of UserProfile class to strings so that they can be used in SQL query.
 *
 * @author Anna Andrlova, Alex Bolfa, Cosmin Demian, Jan Metela, Arturs Ricards Rijnieks
 * @version 1.0 - May 2023
 */
public class UserProfileDO {

    private String workingNumber;
    private String password;

    /**
     * The constructor takes an UserProfile object and converts all of its attributes to string attributes of this class that can be used in SQL query.
     * @param userProfile
     * @throws RuntimeException if any of the attributes of the userProfile is null.
     */
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
