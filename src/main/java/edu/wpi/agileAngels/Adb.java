package edu.wpi.agileAngels;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Adb {

  private static final String CSV_FILE_PATH = "./TowerLocations.csv";

  // Makes a Location object and reads through CSV FILE and makes table
  public static class Location {
    public void read(Connection connection) throws IOException {

      try (Reader reader = Files.newBufferedReader(Paths.get(CSV_FILE_PATH));
          CSVParser csvParser =
              new CSVParser(
                  reader,
                  CSVFormat.DEFAULT
                      .withHeader(
                          "NodeID",
                          "xcoord",
                          "ycoord",
                          "floor",
                          "building",
                          "nodeType",
                          "longName",
                          "shortName")
                      .withIgnoreHeaderCase()
                      .withTrim())) {

        for (CSVRecord csvRecord : csvParser) {
          Statement statement =
              connection.prepareStatement(
                  "INSERT INTO Locations(NodeID, xcoord, ycoord, Floor, building, nodeType, longName, shortName)values(?,?,?,?,?,?,?,?)");

          // Accessing values by the names assigned to each column

          for (int i = 1; i < 9; i++) {
            ((PreparedStatement) statement).setString(i, csvRecord.get(i - 1));
          }

        
          ((PreparedStatement) statement).execute();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public void main(String[] args) throws IOException {
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
    Connection connection = null;

    Statement statement;
    try {
      // substitute your database name for myDB
      connection = DriverManager.getConnection("jdbc:derby://localhost:1527/db");
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
    Location location = new Location();

    location.read(connection);
  }
}
