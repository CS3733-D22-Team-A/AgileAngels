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

  @FXML Pane mapPane;
  @FXML AnchorPane anchor;

  @FXML private TextField nodeIDField, nameField, xCoordField, yCoordField, nodeTypeField;
  private LocationDAOImpl locationDAO;
  NodeManager nodeManager = new NodeManager(this);

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    mapPane.getChildren().add(pane1);
    pane1.getChildren().add((floorOneMap));
    pane1.setVisible(true);
    mapPane.getChildren().add(pane2);
    pane2.getChildren().add((floorTwoMap));
    pane2.setVisible(false);
    mapPane.getChildren().add(pane3);
    pane3.getChildren().add((floorThreeMap));
    pane3.setVisible(false);
    mapPane.getChildren().add(paneL1);
    paneL1.getChildren().add((lowerLevelOneMap));
    paneL1.setVisible(false);
    mapPane.getChildren().add(paneL2);
    paneL2.getChildren().add((lowerLevelTwoMap));
    paneL2.setVisible(false);
    floorOne.setViewOrder(-100);
    floorTwo.setViewOrder(-100);
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
    // pane1.getChildren().add(node.getButton());
    if (node.getFloor().equals("1")) {
      pane1.getChildren().add(node.getButton());
      System.out.println("floor1");
    } else if (node.getFloor().equals("2")) {
      pane2.getChildren().add(node.getButton());
      System.out.println("floor2");
    } else if (node.getFloor().equals("3")) {
      pane3.getChildren().add(node.getButton());
      System.out.println("floor3");
    } else if (node.getFloor().equals("L1")) {
      paneL1.getChildren().add(node.getButton());
      System.out.println("floorL1");
    } else if (node.getFloor().equals("L2")) {
      paneL2.getChildren().add(node.getButton());
      System.out.println("floorL2");
    }
  }

  public void changeMap(ActionEvent event) {
    pane1.setVisible(false);
    pane2.setVisible(false);
    pane3.setVisible(false);
    paneL1.setVisible(false);
    paneL2.setVisible(false);
    if (event.getSource() == floorOne) {
      pane1.setVisible(true);
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
