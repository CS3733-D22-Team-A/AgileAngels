package edu.wpi.agileAngels;

import edu.wpi.agileAngels.Database.Employee;
import java.util.HashMap;

public interface EmployeeDAO {
  HashMap getAllEmployees();

  Employee getEmployee(String userID);

  void addEmployee(String name, String username, String password);

  void removeEmployee(String userID);

  void updateEmployeeName(String userID, String newName);

  void updateEmployeePassword(String userID, String newPW);
}
