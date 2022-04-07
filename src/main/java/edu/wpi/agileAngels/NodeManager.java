package edu.wpi.agileAngels;

import java.util.HashMap;

public class NodeManager {
  private static NodeManager newNodeManager;

  HashMap<String, Node> nodes = new HashMap<>();

  private NodeManager() {}

  static NodeManager getNodeManager() {
    if (newNodeManager == null) {
      newNodeManager = new NodeManager();
    }

    return newNodeManager;
  }

  void deleteNode(String nodeID) {
    // take the Node ID and delete the node from the pane
  }

  void createNodesFromDB() {
    // gets all locations from the DB and creates nodes from them
    // this could get called on maps button pressed right before the page is loaded

  }

  Node addNode(Location location) {
    Node node = null;
    //    if (location.getFloor() == "1") {
    node = new Node(location);
    // floor1.getChildren().add(node.getButton());
    //    }
    return node;
  }

  void editNode(String nodeID, String name, double xCoord, double yCoord, String nodeType) {
    // find the node by the nodeID and change its values
  }

  void loadNode(Node node) {
    System.out.println(node.getNodeID());

    // gets called on button press and gets the node data
  }
}
