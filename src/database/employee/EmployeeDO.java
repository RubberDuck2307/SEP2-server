package database.employee;

import model.Employee;
import model.EmployeeRole;

public class EmployeeDO {

    private String name;
    private String workingNumber;
    private String dob;
    private String phoneNumber;
    private String gender;
    private String email;
    private String role;

    public EmployeeDO(Employee employee) {
        if (employee.getName() == null) {
            throw new RuntimeException("Name cannot be null");
        } else {
            name = "'" + employee.getName() + "'";
        }
        if (employee.getWorkingNumber() == null) {
            workingNumber = "NULL";
        } else {
            workingNumber = employee.getWorkingNumber().toString();
        }
        if (employee.getDob() == null) {
            throw new RuntimeException("Date of birth cannot be null");
        } else {
            dob = "'" + employee.getDob() + "'";

        }
        if (employee.getPhoneNumber() == null) {
            throw new RuntimeException("Phone number cannot be null");
        } else {
            phoneNumber = "'" + employee.getPhoneNumber() + "'";
        }
        if (employee.getGender() == null) {
            throw new RuntimeException("Gender cannot be null");
        } else {
            gender = "'" + employee.getGender() + "'";
        }
        if (employee.getRole() == null) {
            throw new RuntimeException("Role cannot be null");
        } else {
            if (employee.getRole().equals(EmployeeRole.HR)) {
                role = "'HR'";
            } else if (employee.getRole().equals(EmployeeRole.WORKER)) {
                role = "'WORKER'";
            } else if (employee.getRole().equals(EmployeeRole.MAIN_MANAGER)) {
                role = "'MAIN M'";
            } else if (employee.getRole().equals(EmployeeRole.PROJECT_MANAGER)) {
                role = "'PROJECT M'";
            } else {
                throw new RuntimeException("Role is not valid");
            }
        }
        if (employee.getEmail() == null) {
            throw new RuntimeException("Email cannot be null");
        } else {
            email = "'" + employee.getEmail() + "'";
        }
    }

    public String getName() {
        return name;
    }

    public String getWorkingNumber() {
        return workingNumber;
    }

    public String getDob() {
        return dob;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }
}