package edu.wpi.agileAngels;

import java.util.ArrayList;
import java.util.HashMap;

public interface EmployeeDAO {
  HashMap getAllEmployees();

  Employee getEmployee(String userID);

  void addEmployee(String name, String username, String password);

  void removeEmployee(String userID);

  void updateEmployeeName(String userID, String newName);

  void updateEmployeePassword(String userID, String newPW);

  ArrayList<Request> getRequests(String userID);

  void addRequest(String userID, Request newReq);

  void removeRequest(String userID, Request oldReq);
}
