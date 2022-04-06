package edu.wpi.agileAngels;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

// TODO this needs to all be in impl or ADB (continous?)

public class exportToCSV {
  public void export(Connection connection) {

    String csvFilePath = "export.csv";

    try {

      String sql = "SELECT * FROM Locations";

      Statement statement = connection.createStatement();

      ResultSet result = statement.executeQuery(sql);

      BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvFilePath));

      // write header line containing column names
      fileWriter.write("nodeID, xcoord, ycoord, floor, building, nodeType, longName, shortName");

      while (result.next()) {
        String nodeID = result.getString("nodeID");
        String xcoord = result.getString("xcoord");
        String ycoord = result.getString("ycoord");
        String floor = result.getString("floor");
        String building = result.getString("building");
        String nodeType = result.getString("nodeType");
        String longName = result.getString("longName");
        String shortName = result.getString("shortName");

        String line =
            String.format(
                "%s,%s,%s, %s, %s, %s, %s,%s",
                nodeID, xcoord, ycoord, floor, building, nodeType, longName, shortName);

        fileWriter.newLine();
        fileWriter.write(line);
      }

      statement.close();
      fileWriter.close();

    } catch (SQLException e) {
      System.out.println("Datababse error:");
      e.printStackTrace();
    } catch (IOException e) {
      System.out.println("File IO error:");
      e.printStackTrace();
    }
  }
}
