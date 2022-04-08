package edu.wpi.agileAngels;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class NodeManager implements Initializable {

  private MapsController mapsController;
  private LocationDAOImpl locationDAO = new LocationDAOImpl();
  private HashMap<String, Node> nodes = new HashMap<>();
  private int[][] typeCounts = new int[14][5];
  private ArrayList<String> floors = new ArrayList<String>();
  private ArrayList<String> types = new ArrayList<String>();

  public NodeManager(MapsController mapsController) {
    this.mapsController = mapsController;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // initialize list of location types
    types.set(0, "PATI");
    types.set(1, "STOR");
    types.set(2, "DIRT");
    types.set(3, "HALL");
    types.set(4, "ELEV");
    types.set(5, "REST");
    types.set(6, "STAI");
    types.set(7, "DEPT");
    types.set(8, "LABS");
    types.set(9, "INFO");
    types.set(10, "CONF");
    types.set(11, "EXIT");
    types.set(12, "RETL");
    types.set(13, "SERV");

    // initialize list of floors
    floors.set(0, "1");
    floors.set(1, "2");
    floors.set(2, "3");
    floors.set(3, "L1");
    floors.set(4, "L2");

    // initialize counts for each type of location to zero
    for (int i = 0; i < 14; i++) {
      for (int j = 0; j < 5; j++) {
        typeCounts[i][j] = 0;
      }
    }
  }

  void deleteNode(String nodeID) {
    nodes.remove(nodeID);
    // locationDAO.deleteLocation(currentNode.getLocation());
    // adb.deleteLocation(location)
  }

  // gets all locations from the DB and creates nodes from them
  void createNodesFromDB() {
    HashMap<String, Location> locationsHash = locationDAO.getAllLocations();
    ArrayList<Location> locationsList = new ArrayList<Location>(locationsHash.values());
    locationsList.add(new Location("1", 0.0, 0.0, "1", "Tower", "STOR", "STOR", "s"));
    for (Location location : locationsList) {
      System.out.println(types.indexOf(location.getNodeType()));
      // typeCounts[types.indexOf(location.getNodeType())][floors.indexOf(location.getFloor())] +=
      // 1;

      mapsController.displayNode(addNode(location));
    }
  }

  Integer getTypeCount(String type, String floor) {
    // return typeCounts[types.indexOf(type)][floors.indexOf(floor)];
    return 1;
  }

  Node addNode(Location location) {
    Node node = new Node(location, this);
    nodes.put(node.getNodeID(), node);
    return node;
    // add the new location to the database
  }

  void editNode(Node node) {
    // edit the corresponding location in the backend

  }

  // gets called on button press and gets the node data
  void loadNode(Node node) {
    mapsController.populateNodeData(node);
  }
}
