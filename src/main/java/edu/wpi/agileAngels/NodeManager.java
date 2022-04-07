package edu.wpi.agileAngels;

import java.util.HashMap;
import javafx.scene.layout.Pane;

public class NodeManager {
  private static NodeManager newNodeManager;

  HashMap<String, Node> nodes = new HashMap<>();

  Pane floor1 = new Pane();

  private NodeManager() {}

  static NodeManager getNodeManager() {
    if (newNodeManager == null) newNodeManager = new NodeManager();

    return newNodeManager;
  }

  void deleteNode(String nodeID) {
    // take the Node ID and delete the node from the pane
  }

  void addNode(
      String nodeID, String name, double xCoord, double yCoord, String floor, String nodeType) {
    // create a node from location data
    // a node will include location data plus a button FXML object and color n' shit
    // add it to the correct pane
  }

  void editNode(String nodeID, String name, double xCoord, double yCoord, String nodeType) {
    // find the node by the nodeID and change its values
  }

  void loadNode(Node node) {
    // gets called on button press and gets the node data
  }
}
