package edu.wpi.agileAngels;

import edu.wpi.agileAngels.Database.*;

import java.sql.*;
import java.util.HashMap;

// This class is the backend of the DAO method.
// The objects communicate with the DB here
// Basically, front end shouldn't directly interact adb, it should interact with DAO classes
public class Adb {
  private static LocationsTable locationsTable = null;
  private static MedicalEquipmentTable medicalEquipmentTable = null;
  private static ServiceRequestTable serviceRequestTable = null;
  private static EmployeeTable employeeTable = null;

  /**
   * Main: creates the tables if they do not exist already
   *
   * @param args
   * @throws SQLException
   */
  public void main(String[] args) throws SQLException {

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

    Statement statementLocations;
    Statement statementMedical;
    try {

      statementLocations = DBconnection.getConnection().createStatement();

      // If the table exists, the table is dropped and re-created.
      if (tableExist(DBconnection.getConnection(), "Locations")) {
        String dropLoc = "DROP TABLE Locations";
        String queryLocations =
                "CREATE TABLE Locations( "
                        + "NodeID VARCHAR(50),"
                        + "xcoord VARCHAR(50),"
                        + "ycoord VARCHAR(50),"
                        + "Floor VARCHAR(50),"
                        + "building VARCHAR(50),"
                        + "NodeType VARCHAR(50),"
                        + "longName VARCHAR(50),"
                        + "shortName VARCHAR(50))";
        statementLocations.execute(dropLoc);
        statementLocations.execute(queryLocations);
      } else if (!tableExist(DBconnection.getConnection(), "Locations")) {
        String queryLocations =
                "CREATE TABLE Locations( "
                        + "NodeID VARCHAR(50),"
                        + "xcoord VARCHAR(50),"
                        + "ycoord VARCHAR(50),"
                        + "Floor VARCHAR(50),"
                        + "building VARCHAR(50),"
                        + "NodeType VARCHAR(50),"
                        + "longName VARCHAR(50),"
                        + "shortName VARCHAR(50))";
        statementLocations.execute(queryLocations);
      }

      statementMedical = DBconnection.getConnection().createStatement();
      if (tableExist(DBconnection.getConnection(), "RequestTable")) {
        String dropRequest = "DROP TABLE RequestTable";
        String queryRequest =
                "CREATE TABLE RequestTable ( "
                        + "Name VARCHAR(50),"
                        + "Available VARCHAR(50),"
                        + "EmployeeName VARCHAR(50),"
                        + "Location VARCHAR(50),"
                        + "Type VARCHAR(50),"
                        + "Status VARCHAR(50),"
                        + "Description VARCHAR(50),"
                        + "PRIMARY KEY (Name))";
        statementMedical.execute(dropRequest);
        statementMedical.execute(queryRequest);
      } else if (!tableExist(DBconnection.getConnection(), "RequestTable")) {
        String queryRequest =
                "CREATE TABLE RequestTable ( "
                        + "Name VARCHAR(50),"
                        + "Available VARCHAR(50),"
                        + "EmployeeName VARCHAR(50),"
                        + "Location VARCHAR(50),"
                        + "Type VARCHAR(50),"
                        + "Status VARCHAR(50),"
                        + "Description VARCHAR(50),"
                        + "PRIMARY KEY (Name))";
        statementMedical.execute(queryRequest);
      }

      Statement statementEquipment = DBconnection.getConnection().createStatement();
      if (tableExist(DBconnection.getConnection(), "MedicalEquipment")) {
        String dropRequest = "DROP TABLE MedicalEquipment";
        String queryEq =
                "CREATE TABLE MedicalEquipment ( "
                        + "ID VARCHAR(50),"
                        + "Type VARCHAR(50),"
                        + "Clean VARCHAR(50),"
                        + "Location VARCHAR(50),"
                        + "PRIMARY KEY (ID))";
        statementEquipment.execute(dropRequest);
        statementEquipment.execute(queryEq);
      } else {
        String queryEq =
                "CREATE TABLE MedicalEquipment ( "
                        + "ID VARCHAR(50),"
                        + "Type VARCHAR(50),"
                        + "Clean VARCHAR(50),"
                        + "Location VARCHAR(50),"
                        + "PRIMARY KEY (ID))";
        statementEquipment.execute(queryEq);
      }

    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      return;
    }
    System.out.println("Apache Derby connection established!");

  }

  /**
   * Get instance of location Table
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
   * @return a singleton of a Medical Equipment Table
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
   * @return a singleton of a Service Request Table
   */
  public static ServiceRequestTable getServiceRequestTableInstance() {
    if (serviceRequestTable == null) {

      serviceRequestTable = new ServiceRequestTable();
      return serviceRequestTable;
    }
    return serviceRequestTable;

  }

  /**
   * Get instance of Employee Table
   * @return a singleton of a Employee Table
   */
  public static EmployeeTable getEmployeeTableInstance() {
    if (employeeTable == null) {

      employeeTable = new EmployeeTable();
      return employeeTable;
    }
    return employeeTable;

  }




  /**
   * Adds a request to the request database table.
   *
   * @param request
   * @return True if successful, false if not.
   */
  public static boolean addRequest(Request request) {
    return serviceRequestTable.add(request);
  }

  /**
   * Removes a request from the request database table.
   *
   * @param name
   * @return True if successful, false if not.
   */
  public static boolean removeRequest(String name) {
    return serviceRequestTable.delete(name);
  }

  /**
   * Updates different attributes for a request in the table.
   *
   * @param request
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
   * @param nodeID
   * @return True if successful, false if not
   */
  public static boolean deleteLocation(String nodeID) {
    return locationsTable.delete(nodeID);
  }

  /**
   * Updates a location on the table with updated attributes.
   * @param location
   * @return True if successful, false if not
   */
  public static boolean updateLocation(Location location) {
    return locationsTable.update(location);
  }

  /**
   * Adds one medical equipment to the medical equipment table.
   * @param medicalEquip
   * @return True if successful, false if not
   */
  public static boolean addMedicalEquipment(MedicalEquip medicalEquip){
    return medicalEquipmentTable.add(medicalEquip);
  }

  /**
   * Deletes one medical equipment from the medical equipment table
   * @param ID
   * @return True if successful, false if not
   */
  public static boolean removeMedicalEquipment(String ID){
    return medicalEquipmentTable.delete(ID);
  }

  /**
   * Updates one medical equipment in the medical equipment table
   * @param medicalEquip
   * @return True if successful, false if not
   */
  public static boolean updateMedicalEquipment(MedicalEquip medicalEquip){
    return medicalEquipmentTable.update(medicalEquip);
  }

  /**
   * Adds a new employee to the employee table
   * @param employee
   * @return True if successful, false if not
   */
  public static boolean addEmployee(Employee employee){
    return employeeTable.add(employee);
  }

  /**
   * Removes an employee from the employee table
   * @param name
   * @return True if successful, false if not
   */
  public static boolean removeEmployee(String name){
    return employeeTable.delete(name);
  }

  /**
   * Updates an employee's information in the employee table
   * @param employee
   * @return True if successful, false if not
   */
  public static boolean updateEmployee(Employee employee){
    return employeeTable.update(employee);
  }


  /**
   * Checks if table exists.
   *
   * @param conn  Connection
   * @param tName Name of table
   * @return True if it exists, false if not
   * @throws SQLException
   */
  private boolean tableExist(Connection conn, String tName) throws SQLException {
    boolean tExists = false;
    try {
      DatabaseMetaData metaData = conn.getMetaData();
      ResultSet rs = metaData.getTables(null, null, tName.toUpperCase(), null);
      while (rs.next()) {
        String name = rs.getString("TABLE_NAME");
        if (name != null && name.equals(tName.toUpperCase())) {
          tExists = true;
          break;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return tExists;
  }

}