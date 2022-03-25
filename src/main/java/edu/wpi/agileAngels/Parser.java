package edu.wpi.agileAngels;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Parser {

  // Loop through each row
  // Loop through each item of row
  private static final String CSV_FILE_PATH = "./TowerLocations.csv";
  private String[] headers = {
    "NodeID", "xcoord", "ycoord", "floor", "building", "nodeType", "longName", "shortName"
  };
  public HashMap<String, Location> locationData = new HashMap<>();
  /**
   * Reading from CSV file and creating a database and making Location
   *
   * @param connection
   */
  public void createTable(Connection connection) { // TODO make connection a public variable

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

      for (CSVRecord csvRecord : csvParser) { // each row has a dictionary
        HashMap<String, String> data = new HashMap<>();
        String nodeID = csvRecord.get(0);

        Statement statement =
            connection.prepareStatement(
                "INSERT INTO Locations(NodeID, xcoord, ycoord, Floor, building, nodeType, longName, shortName)values(?,?,?,?,?,?,?,?)");

        // Accessing values by the names assigned to each column

        for (int i = 1; i < 9; i++) { // each item in the row
          ((PreparedStatement) statement)
              .setString(
                  i, csvRecord.get(i - 1)); // to access the first value for table it starts at 1
          if (i > 1) { // Everything that is not node
            data.put(headers[i - 1], csvRecord.get(i - 1));
          }
        }
        Location location = new Location(nodeID, data);
        locationData.put(location.getNodeID(), location);

        ((PreparedStatement) statement).execute();
      }
    } catch (SQLException | IOException e) {
      e.printStackTrace();
    }
  }
}
