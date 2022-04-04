package edu.wpi.agileAngels;

import java.sql.*;
import java.util.HashMap;

public class Adb {
  private HashMap<String, Location> locationData;
  private HashMap<String, MedDevice> medData;

  private LocationDAOImpl LocationDAO;

  private MedDAOImpl MedDAO;
  private Connection connection = DBconnection.getConnection();

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
      // substitute your database name for myDB

      statementLocations = connection.createStatement();

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

      statementMedical = connection.createStatement();

      String queryMedical =
          "CREATE TABLE MedicalEquipment ( "
              + "Name VARCHAR(50),"
              + "available VARCHAR(50),"
              + "type VARCHAR(50),"
              + "location VARCHAR(50),"
              + "employee VARCHAR(50),"
              + "status VARCHAR(50),"
              + "description VARCHAR(50))";
      statementMedical.execute(queryMedical);

    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      return;
    }
    System.out.println("Apache Derby connection established!");
  }

  /**
   * Adds a medical equipment request to the database table.
   *
   * @param medDevice
   * @return True if successful, false if not.
   */
  public boolean addMedicalEquipmentRequest(MedDevice medDevice) {
    try {
      PreparedStatement preparedStatement =
          DBconnection.getConnection()
              .prepareStatement(
                  "INSERT INTO MedicalEquipment(Name, available ,type, location, employee, status, description) VALUES(?,?,?,?,?,?,?)");
      preparedStatement.setString(1, medDevice.getName());
      preparedStatement.setString(2, medDevice.getAvailable());
      preparedStatement.setString(3, medDevice.getType());
      preparedStatement.setString(4, medDevice.getLocation());
      preparedStatement.setString(5, medDevice.getEmployee());
      preparedStatement.setString(6, medDevice.getStatus());
      preparedStatement.setString(7, medDevice.getDescription());
      preparedStatement.execute();
      return true;
    } catch (SQLException sqlException) {
      return false;
    }
  }

  /**
   * Removes a medical equipment request from the database table.
   *
   * @param medDevice
   * @return True if successful, false if not.
   */
  public boolean removeMedicalEquipmentRequest(MedDevice medDevice) {
    try {
      PreparedStatement preparedStatement =
          DBconnection.getConnection()
              .prepareStatement("DELETE FROM MedicalEquipment WHERE Name = ?");
      preparedStatement.setString(1, medDevice.getName());
      preparedStatement.execute();
      return true;
    } catch (SQLException sqlException) {
      return false;
    }
  }

  /**
   * Allows the user to change the floor and location type
   *
   * @param ID the node string ID to check if location is present in the map
   * @throws SQLException
   */
  private void ChangeFloorandType(String ID, String newFloor, String newLocation)
      throws SQLException {
    PreparedStatement pstmt =
        connection.prepareStatement("UPDATE Locations SET floor= ?, nodeType = ? WHERE nodeID = ?");
    pstmt.setString(1, newFloor);
    pstmt.setString(2, newLocation);
    pstmt.setString(3, ID);
    pstmt.executeUpdate();
  }
  /**
   * Updates availability for a med device in the table.
   *
   * @param medDevice (with new availability)
   * @return True if successful, false if not.
   */
  public boolean updateMedicalEquipmentRequestAvailability(MedDevice medDevice) {
    try {
      PreparedStatement preparedStatement =
          DBconnection.getConnection()
              .prepareStatement("UPDATE MedicalEquipment SET available = ? WHERE name = ?");
      preparedStatement.setString(1, medDevice.getAvailable());
      preparedStatement.setString(2, medDevice.getName());
      preparedStatement.execute();
      return true;
    } catch (SQLException sqlException) {
      return false;
    }
  }

  /**
   * Updates type for a med device in the table.
   *
   * @param medDevice (with new type)
   * @return True if successful, false if not.
   */
  public boolean updateMedicalEquipmentRequestType(MedDevice medDevice) {
    try {
      PreparedStatement preparedStatement =
          DBconnection.getConnection()
              .prepareStatement("UPDATE MedicalEquipment SET type = ? WHERE name = ?");
      preparedStatement.setString(1, medDevice.getType());
      preparedStatement.setString(2, medDevice.getName());
      preparedStatement.execute();
      return true;
    } catch (SQLException sqlException) {
      return false;
    }
  }
  /**
   * Adds a new location to the location table and hashmap.
   *
   * @param node
   * @throws SQLException
   */
  private void addLocation(String node) throws SQLException {
    // Adding to the database table
    String add =
        "INSERT INTO Locations(NodeID,xcoord,ycoord,Floor,building,nodeType,longName,shortName)VALUES(?,'?','?','?','?','?','?','?')";
    PreparedStatement preparedStatement = connection.prepareStatement(add);
    preparedStatement.setString(1, node);
    preparedStatement.execute();

    // TODO: Add this code to when you add location to hashmap
    /**
     * Adding to the hashmap String placeholder = "?"; Location location = new Location( node,
     * placeholder, placeholder, placeholder, placeholder, placeholder, placeholder, placeholder);
     * LocationDAO.addLocation(location);*
     */
  }

  /**
   * Deletes a location from the table and hashmap.
   *
   * @param node
   * @throws SQLException
   */
  private void deleteLocation(String node) throws SQLException {
    // Deleting from the database table
    PreparedStatement preparedStatement =
        connection.prepareStatement("DELETE FROM Locations WHERE NodeID = ?");
    preparedStatement.setString(1, node);
    preparedStatement.execute();
  }
  /**
   * Updates location for a med device in the table.
   *
   * @param medDevice (with new location)
   * @return True if successful, false if not.
   */
  public boolean updateMedicalEquipmentRequestLocation(MedDevice medDevice) {
    try {
      PreparedStatement preparedStatement =
          DBconnection.getConnection()
              .prepareStatement("UPDATE MedicalEquipment SET location = ? WHERE name = ?");
      preparedStatement.setString(1, medDevice.getLocation());
      preparedStatement.setString(2, medDevice.getName());
      preparedStatement.execute();
      return true;
    } catch (SQLException sqlException) {
      return false;
    }
  }

  /**
   * Updates employee for a med device in the table.
   *
   * @param medDevice (with new employee)
   * @return True if successful, false if not.
   */
  public boolean updateMedicalEquipmentRequestEmployee(MedDevice medDevice) {
    try {
      PreparedStatement preparedStatement =
          DBconnection.getConnection()
              .prepareStatement("UPDATE MedicalEquipment SET employee = ? WHERE name = ?");
      preparedStatement.setString(1, medDevice.getEmployee());
      preparedStatement.setString(2, medDevice.getName());
      preparedStatement.execute();
      return true;
    } catch (SQLException sqlException) {
      return false;
    }
  }

  /**
   * Updates status for a med device in the table.
   *
   * @param medDevice (with new status)
   * @return True if successful, false if not.
   */
  public boolean updateMedicalEquipmentRequestStatus(MedDevice medDevice) {
    try {
      PreparedStatement preparedStatement =
          DBconnection.getConnection()
              .prepareStatement("UPDATE MedicalEquipment SET status = ? WHERE name = ?");
      preparedStatement.setString(1, medDevice.getStatus());
      preparedStatement.setString(2, medDevice.getName());
      preparedStatement.execute();
      return true;
    } catch (SQLException sqlException) {
      return false;
    }
  }
}
