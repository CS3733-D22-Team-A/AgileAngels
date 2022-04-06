package edu.wpi.agileAngels;

import java.sql.*;

public class Adb {
  private Connection connection = DBconnection.getConnection();
  // TODO update elements in the csv
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
      // Optimizes myDB file to get rid of it. Ask Justin or Aaron for questions.
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
      if (tableExist(connection, "RequestTable")) {
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
      } else if (!tableExist(connection, "RequestTable")) {
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
    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      return;
    }
    System.out.println("Apache Derby connection established!");
  }

  /**
   * Adds a request to the database table.
   *
   * @param request
   * @return True if successful, false if not.
   */
  public static boolean addRequest(Request request) {
    try {
      PreparedStatement preparedStatement =
          DBconnection.getConnection()
              .prepareStatement(
                  "INSERT INTO RequestTable(Name, Available, EmployeeName, Location, Type, Status, Description) VALUES(?,?,?,?,?,?,?)");
      preparedStatement.setString(1, request.getName());
      preparedStatement.setString(2, "");
      preparedStatement.setString(3, request.getEmployee());
      preparedStatement.setString(4, request.getLocation());
      preparedStatement.setString(5, request.getType());
      preparedStatement.setString(6, request.getStatus());
      preparedStatement.setString(7, request.getDescription());
      preparedStatement.execute();
      return true;
    } catch (SQLException sqlException) {
      return false;
    }
  }

  /**
   * Removes a request from the database table.
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
   * Updates availability for a request in the table.
   *
   * @param request, updateAttribute, update
   * @return True if successful, false if not.
   */
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
   * Adds a new location to the location table and hashmap.
   *
   * @param node
   * @throws SQLException
   */
  private void addLocation(String node) throws SQLException {
    // locationData = LocationDAO.getAllLocations();
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

  public static class MedicalEquip {
    private String name;

    public MedicalEquip(String name) {
      this.name = name;
    }
  }
}
