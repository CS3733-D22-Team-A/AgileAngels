package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.Database.Location;
import edu.wpi.agileAngels.Database.LocationDAOImpl;
import java.util.ArrayList;
import java.util.HashMap;

public class LocationNodeManager {

  private MapsController mapsController;
  private LocationDAOImpl locationDAO = new LocationDAOImpl();
  private HashMap<String, LocationNode> nodes = new HashMap<>();
  private int[][] typeCounts = new int[15][5];
  private HashMap<String, Integer> floorsAndTypes = new HashMap<>();

  public LocationNodeManager(MapsController mapsController) {
    this.mapsController = mapsController;

    // initialize list of location floors and types
    floorsAndTypes.put("PATI", 0);
    floorsAndTypes.put("STOR", 1);
    floorsAndTypes.put("DIRT", 2);
    floorsAndTypes.put("HALL", 3);
    floorsAndTypes.put("ELEV", 4);
    floorsAndTypes.put("REST", 5);
    floorsAndTypes.put("STAI", 6);
    floorsAndTypes.put("DEPT", 7);
    floorsAndTypes.put("LABS", 8);
    floorsAndTypes.put("INFO", 9);
    floorsAndTypes.put("CONF", 11);
    floorsAndTypes.put("EXIT", 11);
    floorsAndTypes.put("RETL", 12);
    floorsAndTypes.put("SERV", 13);
    floorsAndTypes.put("BATH", 14);
    floorsAndTypes.put("1", 0);
    floorsAndTypes.put("2", 1);
    floorsAndTypes.put("3", 2);
    floorsAndTypes.put("L1", 3);
    floorsAndTypes.put("L2", 4);

    // initialize counts for each type of location to zero
    for (int i = 0; i < 15; i++) {
      for (int j = 0; j < 5; j++) {
        typeCounts[i][j] = 0;
      }
    }
  }

  void deleteNode(String nodeID) {
    nodes.remove(nodeID);
    HashMap<String, Location> locationsHash = locationDAO.getAllLocations();
    Location location = locationsHash.get(nodeID);
    locationDAO.deleteLocation(location);
  }

  // gets all locations from the DB and creates nodes from them
  void createNodesFromDB() {
    HashMap<String, Location> locationsHash = locationDAO.getAllLocations();
    ArrayList<Location> locationsList = new ArrayList<Location>(locationsHash.values());
    for (Location location : locationsList) {
      typeCounts[floorsAndTypes.get(location.getNodeType())][
              floorsAndTypes.get(location.getFloor())] +=
          1;
      mapsController.displayLocationNode(addNode(location));
    }
  }

  Integer getTypeCount(String type, String floor) {
    typeCounts[floorsAndTypes.get(type)][floorsAndTypes.get(floor)] += 1;
    return typeCounts[floorsAndTypes.get(type)][floorsAndTypes.get(floor)];
  }

  LocationNode addNode(Location location) {
    LocationNode locationNode = new LocationNode(location, this);
    nodes.put(locationNode.getNodeID(), locationNode);

    // add the new location to the database
    locationDAO.addLocation(location);

    return locationNode;
  }

  void editNode(
      LocationNode locationNode, Double xCoord, Double yCoord, String longName, String type) {
    // edit the corresponding location in the backend
    locationDAO.updateLocationXCoord(locationNode.getLocation(), xCoord);
    locationDAO.updateLocationYCoord(locationNode.getLocation(), yCoord);
    locationDAO.updateLocationLongName(locationNode.getLocation(), longName);
    locationDAO.updateLocationType(locationNode.getLocation(), type);
  }

  // gets called on button press and gets the node data
  void loadNode(LocationNode locationNode) {
    mapsController.populateLocationNodeData(locationNode);
  }
}
