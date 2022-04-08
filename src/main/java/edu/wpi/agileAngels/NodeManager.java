package edu.wpi.agileAngels;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

public class NodeManager {

  private MapsController mapsController;
  private LocationDAOImpl locationDAO;
  private Connection connection;

  public NodeManager(MapsController mapsController) {
    this.mapsController = mapsController;
  }

  void deleteNode(String nodeID) {
    // take the Node ID and delete the node from the pane
  }

  // gets all locations from the DB and creates nodes from them
  void createNodesFromDB() {
    connection = DBconnection.getConnection();
    HashMap<String, Location> locationsHash = locationDAO.getAllLocations();
    ArrayList<Location> locationsList = new ArrayList<Location>(locationsHash.values());
    for (Location location : locationsList) {
      mapsController.displayNode(addNode(location));
    }
  }

  Node addNode(Location location) {
    Node node = new Node(location, this);
    return node;
  }

  void editNode(String locationID, String name, double xCoord, double yCoord, String nodeType) {
    // find the node by the nodeID and change its values

  }

  // gets called on button press and gets the node data
  void loadNode(Node node) {
    System.out.println(node.getNodeID());
    mapsController.populateNodeData(node);
  }
}
