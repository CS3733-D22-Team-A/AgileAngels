package edu.wpi.agileAngels;

import java.sql.*;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.*;

public class Adb {
  private HashMap<String, Location> data;
  public Connection connection = null;
  private LocationDAOImpl DAO;

  public void main(String[] args) throws SQLException {

    // menu();
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

    Statement statement;
    try {
      // substitute your database name for myDB
      connection = DriverManager.getConnection("jdbc:derby:myDB;create=true");
      statement = connection.createStatement();

      String query =
          "CREATE TABLE Locations( "
              + "NodeID VARCHAR(50),"
              + "xcoord VARCHAR(50),"
              + "ycoord VARCHAR(50),"
              + "Floor VARCHAR(50),"
              + "building VARCHAR(50),"
              + "NodeType VARCHAR(50),"
              + "longName VARCHAR(50),"
              + "shortName VARCHAR(50))";

      statement.execute(query);

    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      return;
    }
    System.out.println("Apache Derby connection established!");

    DAO = new LocationDAOImpl(connection);
    data = DAO.getAllLocations(); // Updates the big hashmap

    menu();
  }

  /** Menu Creation for User* */
  private void menu() throws SQLException {

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
      data = DAO.getAllLocations();
      for (HashMap.Entry<String, Location> set : data.entrySet()) {
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

    } else if (select.equals("2")) {
      System.out.println("Change Floor and Type");
      System.out.println("Enter nodeID");
      // Scanner myObj = new Scanner(System.in);
      String ID = myObj.next();

      data = DAO.getAllLocations();

      if (data.get(ID) != null) {
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
        Location location = data.get(ID);
        DAO.updateLocationFloor(location, newFloor);
        DAO.updateLocationType(location, newLocation);
      }

    } else if (select.equals("3")) {
      System.out.println("Enter Location ID");
      Scanner in = new Scanner(System.in);
      String nodeID = in.next();
      data = DAO.getAllLocations();

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
    menu();
  }

  /**
   * Adds a new location to the location table and hashmap.
   *
   * @param node
   * @throws SQLException
   */
  private void addLocation(String node) throws SQLException {
    data = DAO.getAllLocations();
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
    DAO.addLocation(location);
  }

  /**
   * Deletes a location from the table and hashmap.
   *
   * @param node
   * @throws SQLException
   */
  private void deleteLocation(String node) throws SQLException {
    // Deleting from the database table
    Location location = data.get(node);
    if (location != null) {
      PreparedStatement preparedStatement =
          connection.prepareStatement("DELETE FROM Locations WHERE NodeID = ?");
      preparedStatement.setString(1, node);
      preparedStatement.execute();

      // Deleting from hashmap
      DAO.deleteLocation(location);
    } else {
      System.out.println("Location Does Not Exist");
    }
  }
}
