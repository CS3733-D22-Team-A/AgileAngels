package edu.wpi.agileAngels;

import java.util.ArrayList;
import java.util.HashMap;

public class EmployeeManager implements EmployeeDAO {

  private HashMap<Integer, Employee> employeeHashMap;

  /**
   * Grabs all Employees in hash.
   *
   * @return
   */
  public HashMap getAllEmployees() {
    return this.employeeHashMap;
  }

  /** Gets Employee from hash. */
  public Employee getEmployee(int userID) {
    return employeeHashMap.get(userID);
  }

  /** Removes Employee from hash. */
  public void removeEmployee(int userID) {
    employeeHashMap.remove(userID);
  }

  /** Adds Employee into hash . */
  public void addEmployee(String name, String username, String password, int userID) {
    ArrayList<Request> newERequest = new ArrayList<Request>();
    Employee newEmployee = new Employee(name, username, password, newERequest);
    employeeHashMap.put(userID, newEmployee);
  }

  /** Updates Employee's name with newName. */
  public void updateEmployeeName(int userID, String newName) {
    employeeHashMap.get(userID).setName(newName);
  }

  /** Updates Employee's user with newUN. */
  public void updateEmployeeUsername(int userID, String newUN) {
    employeeHashMap.get(userID).setUsername(newUN);
  }

  /** Updates Employee's password with newPW. */
  public void updateEmployeePassword(int userID, String newPW) {
    employeeHashMap.get(userID).setPassword(newPW);
  }

  /** Gets Employee's list of Requests. */
  public ArrayList<Request> getRequests(int userID) {
    return employeeHashMap.get(userID).getRequests();
  }

  /** Adds Request to Employee's list of Requests. */
  public void addRequest(int userID, Request newReq) {
    employeeHashMap.get(userID).addRequest(newReq);
  }

  /** Removes Request from Employee's list of Requests. */
  public void removeRequest(int userID, Request oldReq) {
    employeeHashMap.get(userID).removeRequest(oldReq);
  }
}
