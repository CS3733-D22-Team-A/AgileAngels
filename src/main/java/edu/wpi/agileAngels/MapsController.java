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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javax.swing.*;

public class MapsController extends MainController implements Initializable {

  @FXML
  private ImageView floorOneMap, floorTwoMap, floorThreeMap, lowerLevelOneMap, lowerLevelTwoMap;
  @FXML
  private Button floorOne,
      floorTwo,
      floorThree,
      lowerLevelOne,
      lowerLevelTwo,
      editButton,
      addButton,
      removeButton,
      switchToAddButton,
      switchToEditButton,
      clearButton;
  @FXML TextField getNodeIDField;

  Pane pane1 = new Pane();
  Pane pane2 = new Pane();

  private NodeManager nodeManager = NodeManager.getNodeManager();
  @FXML AnchorPane anchor;

  @FXML private TextField nodeIDField, nameField, xCoordField, yCoordField, nodeTypeField;
  private Connection connection;
  private LocationDAOImpl locationDAO;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    anchor.getChildren().add(pane1);
    anchor.getChildren().add(pane2);
    // nodeManager.createNodesFromDB();
    Location location1 =
        new Location("test1", "100", "100", "1", "TOWER", "ELV", "Elevator", "ELV1");
    Location location2 =
        new Location("test1", "100", "200", "2", "TOWER", "ELV", "Elevator", "ELV1");
    Location location3 =
        new Location("test1", "100", "300", "2", "TOWER", "ELV", "Elevator", "ELV1");

    pane1.getChildren().add(nodeManager.addNode(location1).getButton());
    pane2.getChildren().add(nodeManager.addNode(location2).getButton());
    pane2.getChildren().add(nodeManager.addNode(location3).getButton());

    // connection = DBconnection.getConnection();
    // add the locations from the location database to a list of locations and then create nodes
    // from each location
    //    Location location1 =
    //        new Location("test1", "100", "100", "1", "TOWER", "ELV", "Elevator", "ELV1");
    //    nodeManager.addNode(location1);
  }

  public void populateNodeData(Node node) {
    System.out.println(node.getNodeID());
    nodeIDField.setText(node.getNodeID());
  }

  @FXML
  private void changeFloor() { // takes in a int or a string once it's implemented.
  }

  @FXML
  private void addNode() {
    // adds node to page.
  }

  @FXML
  private void editNode() {}

  @FXML
  private void removeNode() {
    // Node.remove(NodeID) mega brain.
  }

  @FXML
  private void clearFields() {
    // I have no clue how to write this without fields yet.
  }

  /**
   * Switches between the "add a location" mode and the
   * "edit or delete a location" mode on a button press
   * @param event the button that was pressed, which is
   *              either switchToAddButton or the
   *              switchToEditButton
   */
  @FXML
  private void switchMode(ActionEvent event) {
    if (event.getSource() == switchToAddButton) {
      switchToAddButton.setVisible(false);
      switchToEditButton.setVisible(true);
      addButton.setVisible(true);
      editButton.setVisible(false);
      removeButton.setVisible(false);
    } else {
      switchToAddButton.setVisible(true);
      switchToEditButton.setVisible(false);
      addButton.setVisible(false);
      editButton.setVisible(true);
      removeButton.setVisible(true);
    }
  }

  private void displayNode(Node node) {
    // At the time of coding there's no node class so this will cause errors.
  }

  public void changeMap(ActionEvent event) {
    if (event.getSource() == floorOne) {
      pane1.setVisible(false);
      pane2.setVisible(true);
    } else if (event.getSource() == floorTwo) {
      pane1.setVisible(true);
      pane2.setVisible(false);
    }
  }
}
