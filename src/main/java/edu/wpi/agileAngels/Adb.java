package edu.wpi.agileAngels;

import java.sql.*;

// This class is the backend of the DAO method.
// The objects communicate with the DB here
// Basically, front end shouldn't directly interact adb, it should interact with DAO classes
public class Adb {

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
   * Adds a request to the request database table.
   *
   * @param request
   * @return True if successful, false if not.
   */
  public static boolean addRequest(Request request) {
    try {
      PreparedStatement preparedStatement =
          DBconnection.getConnection()
              .prepareStatement(
                  "INSERT INTO RequestTable(Name, Available, EmployeeName, Location, Type, Status, Description, Attribute1, Attribute2) VALUES(?,?,?,?,?,?,?, ?,?)");
      preparedStatement.setString(1, request.getName());
      preparedStatement.setString(2, request.getAttribute1());
      preparedStatement.setString(3, request.getEmployee());
      preparedStatement.setString(4, request.getLocation());
      preparedStatement.setString(5, request.getType());
      preparedStatement.setString(6, request.getStatus());
      preparedStatement.setString(7, request.getDescription());
      preparedStatement.setString(8, request.getAttribute2());
      preparedStatement.execute();
      return true;
    } catch (SQLException sqlException) {
      return false;
    }
  }

  /**
   * Removes a request from the request database table.
   *
   * @param request
   * @return True if successful, false if not.
   */
  public static boolean removeRequest(Request request) {
    try {
      PreparedStatement preparedStatement =
          DBconnection.getConnection().prepareStatement("DELETE FROM RequestTable WHERE Name = ?");
      preparedStatement.setString(1, request.getName());
      preparedStatement.execute();
      return true;
    } catch (SQLException sqlException) {
      return false;
    }
  }

  /**
   * Allows the user to change the floor and location type.
   *
   * @param ID the node string ID to check if location is present in the map
   * @throws SQLException
   */
  private void ChangeFloorandType(String ID, String newFloor, String newLocation)
      throws SQLException {
    PreparedStatement pstmt =
        DBconnection.getConnection()
            .prepareStatement("UPDATE Locations SET floor= ?, nodeType = ? WHERE nodeID = ?");
    pstmt.setString(1, newFloor);
    pstmt.setString(2, newLocation);
    pstmt.setString(3, ID);
    pstmt.executeUpdate();
  }
  /**
   * Updates different attributes for a request in the table.
   *
   * @param request, updateAttribute, update
   * @return True if successful, false if not.
   */
  // TODO multiple updates
  public static boolean updateRequest(Request request, String updateAttribute, String update) {
    try {
      PreparedStatement preparedStatement;
      if (updateAttribute.equals("Available")) {
        preparedStatement =
            DBconnection.getConnection()
                .prepareStatement("UPDATE RequestTable SET Available = ? WHERE Name = ?");
      } else if (updateAttribute.equals("EmployeeName")) {
        preparedStatement =
            DBconnection.getConnection()
                .prepareStatement("UPDATE RequestTable SET EmployeeName = ? WHERE Name = ?");
      } else if (updateAttribute.equals("Location")) {
        preparedStatement =
            DBconnection.getConnection()
                .prepareStatement("UPDATE RequestTable SET Location = ? WHERE Name = ?");
      } else if (updateAttribute.equals("Type")) {
        preparedStatement =
            DBconnection.getConnection()
                .prepareStatement("UPDATE RequestTable SET Type = ? WHERE Name = ?");
      } else if (updateAttribute.equals("Status")) {
        preparedStatement =
            DBconnection.getConnection()
                .prepareStatement("UPDATE RequestTable SET Status = ? WHERE Name = ?");
      } else if (updateAttribute.equals("Description")) {
        preparedStatement =
            DBconnection.getConnection()
                .prepareStatement("UPDATE RequestTable SET Description = ? WHERE Name = ?");
      } else {
        return false;
      }
      preparedStatement.setString(1, update);
      preparedStatement.setString(2, request.getName());
      preparedStatement.execute();
      return true;
    } catch (SQLException sqlException) {
      return false;
    }
  }

  /**
   * Adds a new location to the locations table.
   *
   * @param nodeID
   * @throws SQLException
   */
  public static void addLocation(String nodeID) throws SQLException {
    String add =
        "INSERT INTO Locations(NodeID,xcoord,ycoord,Floor,building,nodeType,longName,shortName)VALUES(?,'?','?','?','?','?','?','?')";
    PreparedStatement preparedStatement = DBconnection.getConnection().prepareStatement(add);
    preparedStatement.setString(1, nodeID);
    preparedStatement.execute();
  }

  /**
   * Deletes a location from the locations table.
   *
   * @param nodeID
   * @throws SQLException
   */
  public static void deleteLocation(String nodeID) throws SQLException {
    PreparedStatement preparedStatement =
        DBconnection.getConnection().prepareStatement("DELETE FROM Locations WHERE NodeID = ?");
    preparedStatement.setString(1, nodeID);
    preparedStatement.execute();
  }

  /**
   * Checks if table exists.
   *
   * @param conn Connection
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

  /**
   * Adding one medical equipment to the medical equipment table.
   *
   * @param medicalEquip
   */
  public static boolean addMedicalEquipment(MedicalEquip medicalEquip) {
    try {
      PreparedStatement add =
          DBconnection.getConnection()
              .prepareStatement(
                  "INSERT INTO MedicalEquipment(ID, Type, Clean, Location) VALUES(?,?,?,?)");
      add.setString(1, medicalEquip.getID());
      add.setString(2, medicalEquip.getType());
      if (medicalEquip.isClean()) {
        add.setString(3, "Clean");
      } else {
        add.setString(3, "Dirty");
      }
      add.setString(4, medicalEquip.getLocation());
      add.execute();
      return true;
    } catch (SQLException e) {
      System.out.println("Adding unsuccessful.");
      return false;
    }
  }

  /**
   * Removes one medical equipment from the medical equipment table.
   *
   * @param MedID
   * @return True if successful, false if not.
   */
  public static boolean removeMedicalEquipment(String MedID) {
    try {
      PreparedStatement delete =
          DBconnection.getConnection()
              .prepareStatement("DELETE FROM MedicalEquipment WHERE ID = ?");
      delete.setString(1, MedID);
      delete.execute();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }
}
