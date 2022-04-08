package edu.wpi.agileAngels;

import java.util.ArrayList;
import java.util.HashMap;

public interface EmployeeDAO {
    public HashMap getAllEmployees();

    public Employee getEmployee(int userID);

    public void addEmployee(String name, String username, String password, int userID);

    public void removeEmployee(int userID);

    public void updateEmployeeName(int userID, String newName);

    public void updateEmployeeUsername(int userID, String newUN);

    public void updateEmployeePassword(int userID, String newPW);
    public ArrayList<Request> getRequests(int userID);

    public void addRequest(int userID, Request newReq);

    public void removeRequest(int userID, Request oldReq);
}
