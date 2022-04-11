package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.Database.Location;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

// TODO: Close app button is broken when displaying floor 1

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
      switchToEditButton;
  @FXML private TextField nameField, xCoordField, yCoordField, typeField;

  @FXML Pane mapPane;
  @FXML AnchorPane anchor;
  @FXML Label floorLabel, nodeIDField;

  LocationNode currentLocationNode = null;
  RequestNode currentRequestNode = null;
  private String currentFloor = "1";

  Pane pane1 = new Pane();
  Pane pane2 = new Pane();
  Pane pane3 = new Pane();
  Pane paneL1 = new Pane();
  Pane paneL2 = new Pane();

  LocationNodeManager locationNodeManager = new LocationNodeManager(this);
  RequestNodeManager requestNodeManager = new RequestNodeManager(this);

  public MapsController() throws SQLException {}

  /**
   * Called on page load, creates panes for each map, adds the images for each map to its pane, and
   * sets their initial visibility
   *
   * @param location
   * @param resources
   */
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

    locationNodeManager.createNodesFromDB();
    requestNodeManager.createNodesFromDB();
  }

  /**
   * Populates the text fields on the page with data of a node
   *
   * @param locationNode the node whose data is populated
   */
  public void populateLocationNodeData(LocationNode locationNode) {
    System.out.println(locationNode.getNodeID());
    nodeIDField.setText(locationNode.getNodeID());
    nameField.setText(locationNode.getName());
    typeField.setText(locationNode.getNodeType());
    xCoordField.setText(Double.toString(locationNode.getXCoord()));
    yCoordField.setText(Double.toString(locationNode.getYCoord()));

    currentLocationNode = locationNode;
  }

  /**
   * Populates the text fields on the page with data of a node
   *
   * @param requestNode the node whose data is populated
   */
  public void populateRequestNodeData(RequestNode requestNode) {
    System.out.println(requestNode.getName());
    nodeIDField.setText(requestNode.getName());
    nameField.setText(requestNode.getEmployee());
    typeField.setText(requestNode.getStatus());
    xCoordField.setText(Double.toString(requestNode.getLocation().getXCoord()));
    yCoordField.setText(Double.toString(requestNode.getLocation().getYCoord()));

    currentRequestNode = requestNode;
  }

  @FXML
  private void addNode() throws IOException {

    int typeCount = (locationNodeManager.getTypeCount(typeField.getText(), currentFloor));

    String nodeID =
        "A"
            + typeField.getText()
            + String.format("%03d", typeCount)
            + ((currentFloor.length() == 1) ? ("0" + currentFloor) : (currentFloor));

    Location newLocation =
        new Location(
            nodeID,
            Double.parseDouble(xCoordField.getText()),
            Double.parseDouble(yCoordField.getText()),
            currentFloor,
            "TOWER",
            typeField.getText(),
            nameField.getText(),
            nodeID);
    displayNode(locationNodeManager.addNode(newLocation));
  }

  /**
   * Edits the parameters of currentNode
   *
   * @throws IOException NumberFormatException from parseDouble
   */
  @FXML
  private void editNode() throws IOException {
    currentLocationNode.changeLocationXCoord(Double.parseDouble(xCoordField.getText()));
    currentLocationNode.changeLocationYCoord(Double.parseDouble(yCoordField.getText()));
    currentLocationNode.changeLocationName(nameField.getText());
    currentLocationNode.changeLocationType(typeField.getText());
    currentLocationNode.resetLocation();
    currentLocationNode = null;
    locationNodeManager.editNode(currentLocationNode);
  }

  /**
   * Removes currentNode from its pane and calls NodeManager.deleteNode(), which removes the node
   * from the hashmap in NodeManager and the database
   */
  @FXML
  private void removeNode() {
    if (currentLocationNode.getFloor().equals("1")) {
      pane1.getChildren().remove(currentLocationNode.getButton());
    } else if (currentLocationNode.getFloor().equals("2")) {
      pane2.getChildren().remove(currentLocationNode.getButton());
    } else if (currentLocationNode.getFloor().equals("3")) {
      pane3.getChildren().remove(currentLocationNode.getButton());
    } else if (currentLocationNode.getFloor().equals("L1")) {
      paneL1.getChildren().remove(currentLocationNode.getButton());
    } else if (currentLocationNode.getFloor().equals("L2")) {
      paneL2.getChildren().remove(currentLocationNode.getButton());
    }
    locationNodeManager.deleteNode(currentLocationNode.getNodeID());
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

  /**
   * Adds the button for a node to the pane corresponding to its floor
   *
   * @param node the node whose button is added to a pane
   */
  public void displayNode(Node node) {
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

  /**
   * Switches between the panes for each floor when the button for each floor is pressed
   *
   * @param event one of the floor buttons
   */
  public void changeMap(ActionEvent event) {
    pane1.setVisible(false);
    pane2.setVisible(false);
    pane3.setVisible(false);
    paneL1.setVisible(false);
    paneL2.setVisible(false);
    if (event.getSource() == floorOne) {
      pane1.setVisible(true);
      currentFloor = "1";
      floorLabel.setText("Floor 1");
    } else if (event.getSource() == floorTwo) {
      pane2.setVisible(true);
      currentFloor = "2";
      floorLabel.setText("Floor 2");
    } else if (event.getSource() == floorThree) {
      pane3.setVisible(true);
      currentFloor = "3";
      floorLabel.setText("Floor 3");
    } else if (event.getSource() == lowerLevelOne) {
      paneL1.setVisible(true);
      currentFloor = "L1";
      floorLabel.setText("Lower Level 1");
    } else if (event.getSource() == lowerLevelTwo) {
      paneL2.setVisible(true);
      currentFloor = "L2";
      floorLabel.setText("Lower Level 2");
    }
  }
}
