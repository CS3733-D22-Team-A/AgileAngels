package edu.wpi.agileAngels;

import java.net.URL;
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
  Pane pane3 = new Pane();
  Pane paneL1 = new Pane();
  Pane paneL2 = new Pane();

  @FXML AnchorPane anchor;

  @FXML private TextField nodeIDField, nameField, xCoordField, yCoordField, nodeTypeField;
  private LocationDAOImpl locationDAO;
  NodeManager nodeManager = new NodeManager(this);

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    anchor.getChildren().add(pane1);
    pane1.getChildren().add((floorOneMap));
    pane1.setVisible(true);
    anchor.getChildren().add(pane2);
    pane1.getChildren().add((floorTwoMap));
    pane2.setVisible(false);
    anchor.getChildren().add(pane3);
    pane1.getChildren().add((floorThreeMap));
    pane3.setVisible(false);
    anchor.getChildren().add(paneL1);
    pane1.getChildren().add((lowerLevelOneMap));
    paneL1.setVisible(false);
    anchor.getChildren().add(paneL2);
    pane1.getChildren().add((lowerLevelTwoMap));
    paneL2.setVisible(false);
    nodeManager.createNodesFromDB();
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
   * Switches between the "add a location" mode and the "edit or delete a location" mode on a button
   * press
   *
   * @param event the button that was pressed, which is either switchToAddButton or the
   *     switchToEditButton
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

  public void displayNode(Node node) {
    if (node.getFloor() == "1") {
      pane1.getChildren().add(node.getButton());
    } else if (node.getFloor() == "2") {
      pane2.getChildren().add(node.getButton());
    } else if (node.getFloor() == "3") {
      pane3.getChildren().add(node.getButton());
    } else if (node.getFloor() == "L1") {
      paneL1.getChildren().add(node.getButton());
    } else if (node.getFloor() == "L2") {
      paneL2.getChildren().add(node.getButton());
    }
  }

  public void changeMap(ActionEvent event) {
    if (event.getSource() == floorOne) {
      pane1.setVisible(false);
      pane2.setVisible(true);
    } else if (event.getSource() == floorTwo) {
      pane2.setVisible(true);
    } else if (event.getSource() == floorThree) {
      pane3.setVisible(true);
    } else if (event.getSource() == lowerLevelOne) {
      paneL1.setVisible(true);
    } else if (event.getSource() == lowerLevelTwo) {
      paneL2.setVisible(true);
    }
  }
}
