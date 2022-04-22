package edu.wpi.agileAngels.Database;

import java.util.HashMap;

public interface EmployeeDAO {
  HashMap getAllEmployees();

  Employee getEmployee(String userID);

  void addEmployee(String name, String password, String duty);

  void removeEmployee(String userID);

  void updateEmployeeName(String userID, String newName);

  void updateEmployeePassword(String userID, String newPW);

  String getEmployeeFloorOnDuty(String name);

  void setEmployeeFloorOnDuty(String name, String floorOnDuty);
}
