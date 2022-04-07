package edu.wpi.agileAngels;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MapsController extends MainController {

  @FXML
  private ImageView floorOneMap, floorTwoMap, floorThreeMap, lowerLevelOneMap, lowerLevelTwoMap;
  @FXML
  private Button floorOne,
      floorTwo,
      floorThree,
      lowerLevelOne,
      lowerLevelTwo,
      updateButton,
      addButton,
      removeButton,
      clearButton;

  private final NodeManager nodeManager = NodeManager.getNodeManager();

  @FXML private TextField nodeIDField, nameField, xCoordField, yCoordField, nodeTypeField;
  private Connection connection;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    connection = DBconnection.getConnection();

    nodeManager.addNode();
  }

  private void changeFloor() { // takes in a int or a string once it's implemented.
  }

  private void addNode(String nodeID, String name, double xCoord, double yCoord, String nodeType) {
    // adds node to page.
  }

  private void editNode(
      String nodeID, String name, double xCoord, double yCoord, String nodeType) {}

  private void removeNode(String nodeID) {
    // Node.remove(NodeID) mega brain.
  }

  private void clearFields() {
    // I have no clue how to write this without fields yet.
  }

  private void switchMode() {
    // Can't do anything till pages set up.
  }

  private void displayNode(Node node) {
    // At the time of coding there's no node class so this will cause errors.
  }

  public void changeMap(ActionEvent event) {
    // set opacities of the map images and correct node panes
  }
}
