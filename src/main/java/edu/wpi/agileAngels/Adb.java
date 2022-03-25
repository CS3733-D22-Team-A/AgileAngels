package edu.wpi.agileAngels;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.*;

public class Adb {
  private HashMap<String, Location> data;
  public Connection connection = null;

  public void main(String[] args) throws IOException, InterruptedException {

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
      // String query2 = "DROP TABLE Locations";
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
      // statement.execute(query2);
      statement.execute(query);

    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      return;
    }
    System.out.println("Apache Derby connection established!");

    Parser parser = new Parser();
    parser.createTable(connection);
    data = parser.locationData; // Updates the big hashmap
    menu();
  }

  /** Menu Creation for User* */
  private void menu() {

    Scanner myObj = new Scanner(System.in); // Create a Scanner object
    System.out.println("1 - Location Information");
    System.out.println("2 - Change Floor and Type");
    System.out.println("3 - Enter Location");
    System.out.println("4 - Delete Location");
    System.out.println("5 - Save Locations to CSV File");
    System.out.println("6 - Exit Program");

    String select = myObj.nextLine();

    // TODO: make a selection

    if (select.equals("1")) {
      System.out.println("Location Information");
      // TODO call location information function

    } else if (select.equals("2")) {
      System.out.println("Change Floor and Type");
      // TODO call change floor and type function

    } else if (select.equals("3")) {
      System.out.println("Enter Location ID");
      Scanner in = new Scanner(System.in);
      String nodeID = in.next();
      try {
        addLocation(nodeID);
      } catch (SQLException sqlException) {
        System.out.println("Adding a new location unsuccessful.");
      }
      in.close();
    } else if (select.equals("4")) {
      System.out.println("Enter Location ID");
      Scanner in = new Scanner(System.in);
      String nodeID = in.next();
      try {
        deleteLocation(nodeID);
      } catch (SQLException sqlException) {
        System.out.println("Location could not be removed.");
      }
      in.close();
    } else if (select.equals("5")) {
      System.out.println("Save Locations to CSV File");
      // TODO call save locations to csv file function

    } else if (select.equals("6")) {
      System.out.println("Exit Program");
      // TODO call exit program function

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
    // Adding to the database table
    String add =
        "INSERT INTO Locations(NodeID,xcoord,ycoord,Floor,building,nodeType,longName,shortName)VALUES(?,'?','?','?','?','?','?','?')";
    PreparedStatement preparedStatement = connection.prepareStatement(add);
    preparedStatement.setString(1, node);
    preparedStatement.execute();

    // Adding to the hashmap
    data.put(node, new Location(node, new HashMap<>()));
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

    // Deleting from hashmap
    data.remove(node);
  }
}
