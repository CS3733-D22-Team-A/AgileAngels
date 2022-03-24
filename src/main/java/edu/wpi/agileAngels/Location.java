package edu.wpi.agileAngels;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Location {
  // Makes a Location object and reads through CSV FILE and makes table
  private static final String CSV_FILE_PATH = "./TowerLocations.csv";

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
