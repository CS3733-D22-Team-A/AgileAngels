package edu.wpi.agileAngels;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
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

  Node currentNode = null;

  Pane pane1 = new Pane();
  Pane pane2 = new Pane();
  Pane pane3 = new Pane();
  Pane paneL1 = new Pane();
  Pane paneL2 = new Pane();

  @FXML Pane mapPane;
  @FXML AnchorPane anchor;

  @FXML private TextField nodeIDField, nameField, xCoordField, yCoordField, typeField;
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
    paneL2.getChildren().add((lowerLevelTwoMap));
    mapPane.getChildren().add(paneL2);
    paneL2.setVisible(false);
    floorOne.setViewOrder(-100);
    floorTwo.setViewOrder(-100);
    floorThree.setViewOrder(-100);
    lowerLevelOne.setViewOrder(-100);
    lowerLevelTwo.setViewOrder(-100);

    nodeManager.createNodesFromDB();
  }

  public void populateNodeData(Node node) {
    System.out.println(node.getNodeID());
    nodeIDField.setText(node.getNodeID());
    nameField.setText(node.getName());
    typeField.setText(node.getNodeType());
    xCoordField.setText(Double.toString(node.getXCoord()));
    yCoordField.setText(Double.toString(node.getYCoord()));

    currentNode = node;
  }

  @FXML
  private void changeFloor() { // takes in a int or a string once it's implemented.
  }

  @FXML
  private void addNode() {
    // adds node to page.
  }

  @FXML
  private void editNode() throws IOException {
    currentNode.changeLocationXCoord(Double.parseDouble(xCoordField.getText()));
    currentNode.changeLocationYCoord(Double.parseDouble(yCoordField.getText()));
    currentNode.changeLocationName(nameField.getText());
    currentNode.changeLocationType(typeField.getText());
    currentNode.resetLocation();
    currentNode = null;
  }

  @FXML
  private void removeNode() {
    //im not sure if the below line works  we might have to reset the page

    //locationDAO.deleteLocation(currentNode.getLocation());
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
    } else if (node.getFloor().equals("2")) {
      pane2.getChildren().add(node.getButton());
    } else if (node.getFloor().equals("3")) {
      pane3.getChildren().add(node.getButton());
    } else if (node.getFloor().equals("L1")) {
      paneL1.getChildren().add(node.getButton());
    } else if (node.getFloor().equals("L2")) {
      paneL2.getChildren().add(node.getButton());
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
