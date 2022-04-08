package edu.wpi.agileAngels;

import java.util.ArrayList;
import java.util.HashMap;

public interface EmployeeDAO {
    HashMap getAllEmployees();

    Employee getEmployee(int userID);

    void addEmployee(String name, String username, String password, int userID);

    void removeEmployee(int userID);

    void updateEmployeeName(int userID, String newName);

    void updateEmployeeUsername(int userID, String newUN);

    void updateEmployeePassword(int userID, String newPW);

    ArrayList<Request> getRequests(int userID);

    void addRequest(int userID, Request newReq);

    void removeRequest(int userID, Request oldReq);
}
