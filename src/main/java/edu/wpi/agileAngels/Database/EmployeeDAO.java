package edu.wpi.agileAngels.Database;

import java.util.HashMap;

public interface EmployeeDAO {
  HashMap getAllEmployees();

  Employee getEmployee(String userID);

  void addEmployee(String name, String password);

  void removeEmployee(String userID);

  void updateEmployeeName(String userID, String newName);

  void updateEmployeePassword(String userID, String newPW);
}
