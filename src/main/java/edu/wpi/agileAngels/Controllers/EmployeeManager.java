package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.Adb;
import edu.wpi.agileAngels.Database.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class EmployeeManager implements EmployeeDAO {
  private static EmployeeManager employeeManagerDAO = null;
  private int count;

  private HashMap<String, Employee> employeeHashMap;

  public EmployeeManager(HashMap<String, Employee> employeeHashMap, int count) {
    this.employeeHashMap = employeeHashMap;
    this.count = count;
  }

  public static EmployeeManager getInstance() {
    if (employeeManagerDAO == null) {
      HashMap<String, Employee> Employeedata = new HashMap<>();
      employeeManagerDAO = new EmployeeManager(Employeedata, 0);
    }
    return employeeManagerDAO;
  }

  /**
   * Grabs all Employees in hash.
   *
   * @return
   */
  public HashMap getAllEmployees() {
    return this.employeeHashMap;
  }

  /** Gets Employee from hash. */
  public Employee getEmployee(String username) {
    return employeeHashMap.get(username);
  }

  /**
   * Checks if the username exists in the hashmap.
   *
   * @param username
   * @return
   */
  public boolean getUsername(String username) {
    return employeeHashMap.containsKey(username);
  }

  /**
   * is being used for to check the password.
   *
   * @param username
   * @return
   */
  public String getPassword(String username) {
    return employeeHashMap.get(username).getPassword();
  }

  /** Removes Employee from hash. */
  public void removeEmployee(String username) {
    employeeHashMap.remove(username);
  }

  /** Adds Employee into hash . */
  public void addEmployee(String name, String username, String password) {
    ArrayList<Request> newERequest = new ArrayList<Request>();
    Employee newEmployee = new Employee(name, password);
    employeeHashMap.put(username, newEmployee);
  }

  /** Updates Employee's name with newName. */
  public void updateEmployeeName(String username, String newName) {
    employeeHashMap.get(username).setName(newName);
  }

  /** Updates Employee's password with newPW. */
  public void updateEmployeePassword(String username, String newPW) {
    employeeHashMap.get(username).setPassword(newPW);
  }

  public void readCSV() {
    String line = "";
    String splitBy = ",";

    try {
      BufferedReader br = new BufferedReader(new FileReader("./Employees.csv"));
      boolean OnHeader = false;
      line.split(splitBy);

      while ((line = br.readLine()) != null) {
        if (OnHeader) {
          String[] values = line.split(splitBy);

          ++this.count;
          Employee employee = new Employee(values[0], values[1]);
          this.employeeHashMap.put(values[0], employee);
          Adb.addEmployee(employee);

        } else {
          OnHeader = true;
        }
      }
    } catch (IOException var7) {
      var7.printStackTrace();
    }
  }
}
