package edu.wpi.agileAngels;

import edu.wpi.agileAngels.Database.*;
import java.sql.*;

// This class is the backend of the DAO method.
// The objects communicate with the DB here
// Basically, front end shouldn't directly interact adb, it should interact with DAO classes
public class Adb {
  private static LocationsTable locationsTable = null;
  private static MedicalEquipmentTable medicalEquipmentTable = null;
  private static ServiceRequestTable serviceRequestTable = null;
  private static EmployeeTable employeeTable = null;

  /**
   * Creates database tables if they do not exist already.
   *
   * @throws SQLException
   */
  public void initialize() {

    // Apache Derby and table creation
    System.out.println("-------Embedded Apache Derby Connection Testing --------");
    try {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
    } catch (ClassNotFoundException e) {
      System.out.println("Apache Derby Driver not found. Add the classpath to your module.");
      System.out.println("For IntelliJ do the following:");
      System.out.println("File | Project Structure, Modules, Dependency tab");
      System.out.println("Add by clicking on the green plus icon on the right of the window");
      System.out.println(
          "Select JARs or directories. Go to the folder where the database JAR is located");
      System.out.println("Click OK, now you can compile your program and run it.");
      e.printStackTrace();
      return;
    }

    System.out.println("Apache Derby driver registered!");

    // Create instances of all database table managers
    locationsTable = getLocationsTableInstance();
    medicalEquipmentTable = getMedicalEquipmentTableInstance();
    serviceRequestTable = getServiceRequestTableInstance();
    employeeTable = getEmployeeTableInstance();

    // Create all database tables
    locationsTable.createTable();
    medicalEquipmentTable.createTable();
    serviceRequestTable.createTable();
    employeeTable.createTable();

    // Tries to get a connection

    if (DBconnection.getConnection() == null) {
      System.out.println("Connection has failed.");
      return;
    }

    System.out.println("Apache Derby connection established!");
  }

  /**
   * Get instance of location Table
   *
   * @return a singleton of a Locations Table
   */
  public static LocationsTable getLocationsTableInstance() {
    if (locationsTable == null) {

      locationsTable = new LocationsTable();
      return locationsTable;
    }
    return locationsTable;
  }

  /**
   * Get instance of Medical Equipment Table
   *
   * @return a singleton of a MedicalEquipment Table
   */
  public static MedicalEquipmentTable getMedicalEquipmentTableInstance() {
    if (medicalEquipmentTable == null) {

      medicalEquipmentTable = new MedicalEquipmentTable();
      return medicalEquipmentTable;
    }
    return medicalEquipmentTable;
  }

  /**
   * Get instance of Service Request Table
   *
   * @return a singleton of a Service Request Table
   */
  public static ServiceRequestTable getServiceRequestTableInstance() {
    if (serviceRequestTable == null) {

      serviceRequestTable = new ServiceRequestTable();
    }
    return serviceRequestTable;
  }

  /**
   * Get instance of Employee Table
   *
   * @return a singleton of an Employee Table
   */
  public static EmployeeTable getEmployeeTableInstance() {
    if (employeeTable == null) {

      employeeTable = new EmployeeTable();
    }
    return employeeTable;
  }

  /**
   * Adds a request to the request database table.
   *
   * @param request new Request
   * @return True if successful, false if not.
   */
  public static boolean addRequest(Request request) {
    return serviceRequestTable.add(request);
  }
  //  return serviceRequestTable.add(request)

  /**
   * Removes a request from the request database table.
   *
   * @param name Request name
   * @return True if successful, false if not.
   */
  public static boolean removeRequest(String name) {
    return serviceRequestTable.delete(name);
  }

  /**
   * Updates different attributes for a request in the table.
   *
   * @param request updated Request
   * @return True if successful, false if not.
   */
  public static boolean updateRequest(Request request) {
    return serviceRequestTable.update(request);
  }

  /**
   * Adds a new location to the locations table.
   *
   * @param location Location
   * @return True if successful, false if not
   */
  public static boolean addLocation(Location location) {
    return locationsTable.add(location);
  }

  /**
   * Deletes a location from the locations table.
   *
   * @param nodeID Location id
   * @return True if successful, false if not
   */
  public static boolean removeLocation(String nodeID) {
    return locationsTable.delete(nodeID);
  }

  /**
   * Updates a location on the table with updated attributes.
   *
   * @param location updated Location
   * @return True if successful, false if not
   */
  public static boolean updateLocation(Location location) {
    return locationsTable.update(location);
  }

  /**
   * Adds one medical equipment to the medical equipment table.
   *
   * @param medicalEquip new MedicalEquip
   * @return True if successful, false if not
   */
  public static boolean addMedicalEquipment(MedicalEquip medicalEquip) {
    return medicalEquipmentTable.add(medicalEquip);
  }

  /**
   * Deletes one medical equipment from the medical equipment table
   *
   * @param ID MedicalEquip id
   * @return True if successful, false if not
   */
  public static boolean removeMedicalEquipment(String ID) {
    return medicalEquipmentTable.delete(ID);
  }

  /**
   * Updates one medical equipment in the medical equipment table
   *
   * @param medicalEquip updated MedicalEquip
   * @return True if successful, false if not
   */
  public static boolean updateMedicalEquipment(MedicalEquip medicalEquip) {
    return medicalEquipmentTable.update(medicalEquip);
  }

  /**
   * Adds a new employee to the employee table
   *
   * @param employee Employee name
   * @return True if successful, false if not
   */
  public static boolean addEmployee(Employee employee) {
    return employeeTable.add(employee);
  }

  /**
   * Removes an employee from the employee table
   *
   * @param name Employee name
   * @return True if successful, false if not
   */
  public static boolean removeEmployee(String name) {
    return employeeTable.delete(name);
  }

  /**
   * Updates an employee's information in the employee table
   *
   * @param employee updated Employee
   * @return True if successful, false if not
   */
  public static boolean updateEmployee(Employee employee) {
    return employeeTable.update(employee);
  }

  public static boolean updateRequest(Request request, String employeeName, String newName) {
    return serviceRequestTable.update(request);
  }
}
