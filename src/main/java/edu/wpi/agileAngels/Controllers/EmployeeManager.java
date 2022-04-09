package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.Database.Employee;
import edu.wpi.agileAngels.Database.Request;
import edu.wpi.agileAngels.EmployeeDAO;
import java.util.ArrayList;
import java.util.HashMap;

public class EmployeeManager implements EmployeeDAO {

  private HashMap<String, Employee> employeeHashMap;

  public EmployeeManager(HashMap<String, Employee> employeeHashMap) {
    this.employeeHashMap = employeeHashMap;
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
    Employee newEmployee = new Employee(name, password, newERequest);
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

  /** Gets Employee's list of Requests. */
  public ArrayList<Request> getRequests(String username) {
    return employeeHashMap.get(username).getRequests();
  }

  /** Adds Request to Employee's list of Requests. */
  public void addRequest(String username, Request newReq) {
    employeeHashMap.get(username).addRequest(newReq);
  }

  /** Removes Request from Employee's list of Requests. */
  public void removeRequest(String username, Request oldReq) {
    employeeHashMap.get(username).removeRequest(oldReq);
  }
}
