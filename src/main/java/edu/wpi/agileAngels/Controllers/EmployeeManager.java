package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.Adb;
import edu.wpi.agileAngels.Database.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
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
  public Employee getEmployee(String name) {
    return employeeHashMap.get(name);
  }

  /**
   * Checks if the name exists in the hashmap.
   *
   * @param name
   * @return
   */
  public boolean getName(String name) {
    return employeeHashMap.containsKey(name);
  }

  /**
   * is being used for to check the password.
   *
   * @param name
   * @return
   */
  public String getPassword(String name) {
    return employeeHashMap.get(name).getPassword();
  }

  /** Removes Employee from hash. */
  public void removeEmployee(String name) {
    employeeHashMap.remove(name);
  }

  /** Adds Employee into hash . */
  public void addEmployee(String name, String password, String duty, int permission) {
    ArrayList<Request> newERequest = new ArrayList<Request>();
    Employee newEmployee = new Employee(name, password, duty, permission);
    employeeHashMap.put(name, newEmployee);
    // Adb.addEmployee(newEmployee);
  }

  /** Updates Employee's name with newName. */
  public void updateEmployeeName(String name, String newName) {
    employeeHashMap.get(name).setName(newName);
  }

  /** Updates Employee's password with newPW. */
  public void updateEmployeePassword(String name, String newPW) {
    employeeHashMap.get(name).setPassword(newPW);
  }

  /**
   * Gets an employee's duty (floor number or off duty).
   *
   * @param name Employee's name
   * @return Floor number or Off Duty
   */
  public String getEmployeeFloorOnDuty(String name) {
    return employeeHashMap.get(name).getFloorOnDuty();
  }

  /**
   * Sets an employee's duty.
   *
   * @param name Employee's name
   * @param floorOnDuty Floor number or Off Duty
   */
  public void setEmployeeFloorOnDuty(String name, String floorOnDuty) {
    employeeHashMap.get(name).setFloorOnDuty(floorOnDuty);
  }

  @Override
  public String getEmployeeDepartment(String name) {
    return null;
  }

  @Override
  public void setEmployeeDepartment(String name, String department) {

  }

  @Override
  public int getEmployeePermissionLevel(String name) {
    return 0;
  }

  @Override
  public void setEmployeePermissionLevel(String name, int permissionLevel) {

  }

  @Override
  public LocalTime getEmployeeStartTime(String name) {
    return null;
  }

  @Override
  public void setEmployeeStartTime(String name, LocalTime startTime) {

  }

  @Override
  public LocalTime getEmployeeEndTime(String name) {
    return null;
  }

  @Override
  public void setEmployeeEndTime(String name, LocalTime endTime) {

  }

  @Override
  public Employee getEmployeeSupervisor(String name) {
    return null;
  }

  @Override
  public void setEmployeeSupervisor(String name, Employee supervisor) {

  }

  @Override
  public ArrayList<Employee> getEmployeeSupervisees(String name) {
    return null;
  }

  @Override
  public void setEmployeeSupervisees(String name, ArrayList<Employee> supervisees) {

  }

  @Override
  public void addEmployeeSupervisee(String name, Employee supervisee) {

  }

  @Override
  public void removeEmployeeSupervisee(String name, String superviseeName) {

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
          Employee employee =
              new Employee(values[0], values[1], values[2], Integer.parseInt(values[3]));
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
