package edu.wpi.agileAngels;

import java.sql.*;
import java.util.HashMap;
import java.util.Scanner;

public class Adb {
  private HashMap<String, Location> locationData;
  private HashMap<String, MedDevice> medData;
  public Connection connection = null;
  private LocationDAOImpl LocationDAO;
  private MedDAOImpl MedDAO;

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
      connection = DriverManager.getConnection("jdbc:derby:myDB;create=true");
      statementLocations = connection.createStatement();
      //Optimizes myDB file to get rid of it. Ask Justin or Aaron for questions.
      if (tableExist(connection, "Locations")) {
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
      } else if (!tableExist(connection, "Locations")) {
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
      statementMedical = connection.createStatement();
      if (tableExist(connection, "MedicalEquipment")) {
        String dropMed = "DROP TABLE MedicalEquipment";
        String queryMedical =
            "CREATE TABLE MedicalEquipment ( "
                + "Name VARCHAR(50),"
                + "available VARCHAR(50),"
                + "type VARCHAR(50),"
                + "location VARCHAR(50),"
                + "employee VARCHAR(50),"
                + "status VARCHAR(50),"
                + "description VARCHAR(50))";
        statementMedical.execute(dropMed);
        statementMedical.execute(queryMedical);
      } else if (!tableExist(connection, "MedicalEquipment")) {
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
      }
    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      return;
    }
    System.out.println("Apache Derby connection established!");

    // LocationDAO = new LocationDAOImpl(connection);
    // locationData = LocationDAO.getAllLocations(); // Updates the big hashmap

    // MedDAO = new MedDAOImpl(connection);
    // medData = MedDAO.getAllMedicalEquipmentRequests();

    // MedMenu();
    // Locationsmenu();
  }

  /** Medical Equipment Menu */
  private void MedMenu() {
    Scanner myObj = new Scanner(System.in); // Create a Scanner object
    System.out.println("1 - Medical Equipment Request Information");
    System.out.println("2 - Add Medical Request");
    System.out.println("3 - Delete Location");
    System.out.println("4 - Save Locations to CSV File");
    System.out.println("5 - Exit Program");

    String select = myObj.nextLine();

    if ((select.equals("1"))) {
      DisplayMedicalEquipment();
    }
    if ((select.equals("2"))) {
      System.out.println("Add Medical Equipment Database");
      Scanner in = new Scanner(System.in);
      String name = in.next();
      medData = MedDAO.getAllMedicalEquipmentRequests();
      try {
        addMedicalEquipment(name);
      } catch (SQLException sqlException) {
        System.out.println("Adding a new Medical Equipment unsuccessful.");
      }
    }
    if ((select.equals("3"))) {
      System.out.println("Enter Medical Equipment Name");
      Scanner in = new Scanner(System.in);
      String name = in.next();
      try {
        deleteMedicalEquipment(name);
      } catch (SQLException sqlException) {
        System.out.println("Medical Equipment could not be removed.");
      }
    } else if (select.equals("4")) {
      System.out.println("Save Locations to CSV File");
      MedExportToCSV medExport = new MedExportToCSV();
      medExport.export(connection);
    } else if (select.equals("5")) {
      System.out.println("Exit Program");
      return;
    } else {
      System.out.println("Wrong Input, Select From Menu");
    }
    MedMenu();
  }

  private void DisplayMedicalEquipment() {
    for (HashMap.Entry<String, MedDevice> set : medData.entrySet()) {
      System.out.println("Name " + set.getKey());
      MedDevice medDevice = set.getValue();
      System.out.println("available " + medDevice.getAvailable());
      System.out.println("Type " + medDevice.getType());
      System.out.println("Location " + medDevice.getLocation());
      System.out.println("Employee " + medDevice.getEmployee());
      System.out.println("Status " + medDevice.getStatus());
      System.out.println("Description " + medDevice.getDescription());
      System.out.println(" ");
    }
  }

  private void addMedicalEquipment(String name) throws SQLException {
    medData = MedDAO.getAllMedicalEquipmentRequests();
    // Adding to the database table
    String addMed =
        "INSERT INTO MedicalEquipment(Name, available ,type, location, employee, status, description)VALUES(?,'?','?','?','?','?','?')";
    PreparedStatement preparedStatement = connection.prepareStatement(addMed);
    preparedStatement.setString(1, name);
    preparedStatement.execute();

    // Adding to the hashmap
    String placeholder = "?";
    MedDevice medDevice =
        new MedDevice(
            name, placeholder, placeholder, placeholder, placeholder, placeholder, placeholder);

    MedDAO.addMed(medDevice);
  }

  /**
   * Deletes a Medical Equipment from the table and hashmap.
   *
   * @param name
   * @throws SQLException
   */
  private void deleteMedicalEquipment(String name) throws SQLException {
    // Deleting from the database table
    MedDevice medDevice = medData.get(name);
    if (medDevice != null) {
      PreparedStatement preparedStatement =
          connection.prepareStatement("DELETE FROM MedicalEquipment WHERE Name = ?");
      preparedStatement.setString(1, name);
      preparedStatement.execute();

      // Deleting from hashmap
      MedDAO.deleteMed(medDevice);
    } else {
      System.out.println("Medical Equipment Does Not Exist");
    }
  }

  /** Menu Creation for User* */
  private void Locationsmenu() throws SQLException {

    Scanner myObj = new Scanner(System.in); // Create a Scanner object
    System.out.println("1 - Location Information");
    System.out.println("2 - Change Floor and Type");
    System.out.println("3 - Enter Location");
    System.out.println("4 - Delete Location");
    System.out.println("5 - Save Locations to CSV File");
    System.out.println("6 - Exit Program");

    String select = myObj.nextLine();

    if (select.equals("1")) {
      System.out.println("Location Information");
      locationData = LocationDAO.getAllLocations();
      DisplayLocations();

    } else if (select.equals("2")) {
      System.out.println("Change Floor and Type");
      System.out.println("Enter nodeID");
      // Scanner myObj = new Scanner(System.in);
      String ID = myObj.next();
      locationData = LocationDAO.getAllLocations();
      ChangeFloorandType(ID, myObj);

    } else if (select.equals("3")) {
      System.out.println("Enter Location ID");
      Scanner in = new Scanner(System.in);
      String nodeID = in.next();
      locationData = LocationDAO.getAllLocations();

      try {
        addLocation(nodeID);
      } catch (SQLException sqlException) {
        System.out.println("Adding a new location unsuccessful.");
      }

    } else if (select.equals("4")) {
      System.out.println("Enter Location ID");
      Scanner in = new Scanner(System.in);
      String nodeID = in.next();
      try {
        deleteLocation(nodeID);
      } catch (SQLException sqlException) {
        System.out.println("Location could not be removed.");
      }

    } else if (select.equals("5")) {
      System.out.println("Save Locations to CSV File");
      exportToCSV export = new exportToCSV();
      export.export(connection);
    } else if (select.equals("6")) {
      System.out.println("Exit Program");
      System.exit(0);
    } else {
      System.out.println("Wrong Input, Select From Menu");
    }
    Locationsmenu();
  }

  /** Display all Locations and attributes */
  private void DisplayLocations() {
    for (HashMap.Entry<String, Location> set : locationData.entrySet()) {
      System.out.println("NodeID " + set.getKey());
      Location location = set.getValue();
      System.out.println("xcoord " + location.getXCoord());
      System.out.println("ycoord " + location.getYCoord());
      System.out.println("Floor " + location.getFloor());
      System.out.println("building " + location.getBuilding());
      System.out.println("Node Type " + location.getNodeType());
      System.out.println("Long Name " + location.getLongName());
      System.out.println("Short Name " + location.getShortName());
      System.out.println(" ");
    }
  }

  /**
   * Allows the user to change the floor and location type
   *
   * @param ID the node string ID to check if location is present in the map
   * @param myObj --> Scanner to get new floor and new location
   * @throws SQLException
   */
  private void ChangeFloorandType(String ID, Scanner myObj) throws SQLException {
    if (locationData.get(ID) != null) {
      System.out.println("Enter new Floor");
      String newFloor = myObj.next();
      System.out.println("Enter new Location");
      String newLocation = myObj.next();
      PreparedStatement pstmt =
          connection.prepareStatement(
              "UPDATE Locations SET floor= ?, nodeType = ? WHERE nodeID = ?");
      pstmt.setString(1, newFloor);
      pstmt.setString(2, newLocation);
      pstmt.setString(3, ID);
      pstmt.executeUpdate();

      // Updating java objects
      Location location = locationData.get(ID);
      LocationDAO.updateLocationFloor(location, newFloor);
      LocationDAO.updateLocationType(location, newLocation);
    } else {
      System.out.println("Cannot Find Location");
    }
  }

  /**
   * Adds a new location to the location table and hashmap.
   *
   * @param node
   * @throws SQLException
   */
  private void addLocation(String node) throws SQLException {
    locationData = LocationDAO.getAllLocations();
    // Adding to the database table
    String add =
        "INSERT INTO Locations(NodeID,xcoord,ycoord,Floor,building,nodeType,longName,shortName)VALUES(?,'?','?','?','?','?','?','?')";
    PreparedStatement preparedStatement = connection.prepareStatement(add);
    preparedStatement.setString(1, node);
    preparedStatement.execute();

    // Adding to the hashmap
    String placeholder = "?";
    Location location =
        new Location(
            node,
            placeholder,
            placeholder,
            placeholder,
            placeholder,
            placeholder,
            placeholder,
            placeholder);
    LocationDAO.addLocation(location);
  }

  /**
   * Deletes a location from the table and hashmap.
   *
   * @param node
   * @throws SQLException
   */
  private void deleteLocation(String node) throws SQLException {
    // Deleting from the database table
    Location location = locationData.get(node);
    if (location != null) {
      PreparedStatement preparedStatement =
          connection.prepareStatement("DELETE FROM Locations WHERE NodeID = ?");
      preparedStatement.setString(1, node);
      preparedStatement.execute();

      // Deleting from hashmap
      LocationDAO.deleteLocation(location);
    } else {
      System.out.println("Location Does Not Exist");
    }
  }

  //Optimizes myDB file to get rid of it. Ask Justin or Aaron for questions.
  public boolean tableExist(Connection conn, String tName) throws SQLException {
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
