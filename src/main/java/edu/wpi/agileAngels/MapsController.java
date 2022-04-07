package edu.wpi.agileAngels;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class MapsController extends MainController implements Initializable {

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
  private LocationDAOImpl locationDAO;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // connection = DBconnection.getConnection();
    // add the locations from the location database to a list of locations and then create nodes
    // from each location
    //    Location location1 =
    //        new Location("test1", "100", "100", "1", "TOWER", "ELV", "Elevator", "ELV1");
    //    nodeManager.addNode(location1);
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
