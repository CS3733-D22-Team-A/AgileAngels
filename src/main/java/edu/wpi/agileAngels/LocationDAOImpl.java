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

// includes a list od Location objects and implements the Location DAO methods
// this will get the data from the DB?
// TODO export to CSV in LocationDAOImpl
public class LocationDAOImpl implements LocationDAO {
  // List is working as a database
  private final String CSV_FILE_PATH = "./TowerLocations.csv";
  private HashMap<String, Location> data = new HashMap<>();

  public LocationDAOImpl() { // error, maybe return void? doesn't in tutorial :(
    Connection connection = DBconnection.getConnection();
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

        String nodeID = csvRecord.get(0);
        if (!nodeID.equals("nodeID")) {

          Statement statement =
              connection.prepareStatement(
                  "INSERT INTO Locations(NodeID, xcoord, ycoord, Floor, building, nodeType, longName, shortName)values(?,?,?,?,?,?,?,?)");

          // Accessing values by the names assigned to each column

          for (int i = 1; i < 9; i++) { // each item in the row
            ((PreparedStatement) statement)
                .setString(
                    i, csvRecord.get(i - 1)); // to access the first value for table it starts at 1
          }
          Location location =
              new Location(
                  csvRecord.get(0),
                  csvRecord.get(1),
                  csvRecord.get(2),
                  csvRecord.get(3),
                  csvRecord.get(4),
                  csvRecord.get(5),
                  csvRecord.get(6),
                  csvRecord.get(7));
          data.put(csvRecord.get(0), location);

          ((PreparedStatement) statement).execute();
        }
      }
    } catch (SQLException | IOException e) {
      e.printStackTrace();
    }
  }

  // retrieve list of locations from the database
  @Override
  public HashMap<String, Location> getAllLocations() {
    return data;
  }

  // @Override
  // override is in the tutorial, maybe change method name to delete?
  // use the dictionaries here instead of this method (idea)
  public void deleteLocation(Location location) {
    data.remove(location.getNodeID());
    System.out.println("Location: NodeID " + location.getNodeID() + ", deleted from the database");
  }

  // @Override override in the tutorial, different method name?
  public Location getLocation(String NodeID) {
    return data.get(NodeID);
  }

  // @Override override in the tutoral, different method name? (for all updateLocation<field_name>
  // methods)
  // TODO edit more than one at a time
  public void updateLocationType(Location location, String newLocationType) {
    location.setNodeType(newLocationType);
    System.out.println("Location: NodeID " + location.getNodeID() + ", updated in the database");
  }

  public void updateLocationFloor(Location location, String newLocationFloor) {
    location.setFloor(newLocationFloor);
    System.out.println("Location: NodeID " + location.getNodeID() + ", updated in the database");
  }

  public void updateLocationBuilding(Location location, String newLocationBuilding) {
    location.setBuilding(newLocationBuilding);
    System.out.println("Location: NodeID " + location.getNodeID() + ", updated in the database");
  }

  public void updateLocationLongName(Location location, String newLocationLongName) {
    location.setLongName(newLocationLongName);
    System.out.println("Location: NodeID " + location.getNodeID() + ", updated in the database");
  }

  public void updateLocationShortName(Location location, String newLocationShortName) {
    location.setShortName(newLocationShortName);
    System.out.println("Location: NodeID " + location.getNodeID() + ", updated in the database");
  }

  public void updateLocationXCoord(Location location, String newLocationXCoord) {
    location.setXCoord(newLocationXCoord);
    System.out.println("Location: NodeID " + location.getNodeID() + ", updated in the database");
  }

  public void updateLocationYCoord(Location location, String newLocationYCoord) {
    location.setYCoord(newLocationYCoord);
    System.out.println("Location: NodeID " + location.getNodeID() + ", updated in the database");
  }

  public void addLocation(Location location) {
    data.put(location.getNodeID(), location);
  }
}
