package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.Database.Location;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.transform.Scale;
import javax.swing.*;

// TODO: Close app button is broken when displaying floor 1

public class MapsController extends MainController implements Initializable {

  @FXML
  private ImageView floorOneMap, floorTwoMap, floorThreeMap, lowerLevelOneMap, lowerLevelTwoMap;
  @FXML private ScrollPane mapScroll;
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
      zoomIn,
      zoomOut;
  @FXML private TextField nameField, xCoordField, yCoordField, typeField;
  @FXML Pane mapPane, clickPane;
  @FXML AnchorPane anchor;
  @FXML Label floorLabel, nodeIDField;
  @FXML MenuItem pati, stor, dirt, hall, elev, rest, stai, dept, labs, info, conf, exit, retl, serv;
  @FXML MenuButton typeDropdown;

  Node currentNode = null;
  private String currentFloor = "1";

  Pane pane1 = new Pane();
  Pane pane2 = new Pane();
  Pane pane3 = new Pane();
  Pane paneL1 = new Pane();
  Pane paneL2 = new Pane();

  NodeManager nodeManager = new NodeManager(this);

  double scale = 1;
  // Map zoom
  // ZoomableMap zoomableMap = new ZoomableMap(mapScroll.getContent());
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

    nodeManager.createNodesFromDB();

    // this.ZoomableMap(mapScroll);
  }

  /**
   * Populates the text fields on the page with data of a node
   *
   * @param node the node whose data is populated
   */
  public void populateNodeData(Node node) {
    // System.out.println(node.getNodeID());
    nodeIDField.setText(node.getNodeID());
    nameField.setText(node.getName());
    typeDropdown.setText(node.getNodeType());
    xCoordField.setText(Double.toString(node.getXCoord()));
    yCoordField.setText(Double.toString(node.getYCoord()));

    currentNode = node;
  }

  @FXML
  private void addNode() throws IOException {

    int typeCount = (nodeManager.getTypeCount(typeDropdown.getText(), currentFloor));

    String nodeID =
        "A"
            + typeDropdown.getText()
            + String.format("%03d", typeCount)
            + ((currentFloor.length() == 1) ? ("0" + currentFloor) : (currentFloor));

    Location newLocation =
        new Location(
            nodeID,
            Double.parseDouble(xCoordField.getText()),
            Double.parseDouble(yCoordField.getText()),
            currentFloor,
            "TOWER",
            typeDropdown.getText(),
            nameField.getText(),
            nodeID);
    displayNode(nodeManager.addNode(newLocation));
    clearFields();
  }

  /**
   * Edits the parameters of currentNode
   *
   * @throws IOException NumberFormatException from parseDouble
   */
  @FXML
  private void editNode() throws IOException {
    currentNode.changeLocationXCoord(Double.parseDouble(xCoordField.getText()));
    currentNode.changeLocationYCoord(Double.parseDouble(yCoordField.getText()));
    currentNode.changeLocationName(nameField.getText());
    currentNode.changeLocationType(typeDropdown.getText());
    currentNode.resetLocation();
    currentNode = null;
    nodeManager.editNode(currentNode);
    clearFields();
  }

  /**
   * Removes currentNode from its pane and calls NodeManager.deleteNode(), which removes the node
   * from the hashmap in NodeManager and the database
   */
  @FXML
  private void removeNode() {
    if (currentNode.getFloor().equals("1")) {
      pane1.getChildren().remove(currentNode.getButton());
    } else if (currentNode.getFloor().equals("2")) {
      pane2.getChildren().remove(currentNode.getButton());
    } else if (currentNode.getFloor().equals("3")) {
      pane3.getChildren().remove(currentNode.getButton());
    } else if (currentNode.getFloor().equals("L1")) {
      paneL1.getChildren().remove(currentNode.getButton());
    } else if (currentNode.getFloor().equals("L2")) {
      paneL1.getChildren().remove(currentNode.getButton());
    }
    nodeManager.deleteNode(currentNode.getNodeID());
    clearFields();
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

  void clearFields() {
    nameField.clear();
    xCoordField.clear();
    yCoordField.clear();
    typeDropdown.setText("");
    nodeIDField.setText("");
  }

  public void typeMenu(ActionEvent event) {
    MenuItem button = (MenuItem) event.getSource();
    typeDropdown.setText(button.getText());
  }

  public void zoomableMap(ActionEvent event) {

    javafx.scene.Node content = mapScroll.getContent();
    Group contentGroup = new Group();
    contentGroup.getChildren().add(content);
    mapScroll.setContent(contentGroup);
    if (event.getSource() == zoomIn) {
      Scale scaleTransform = new Scale(1.05, 1.05, 0, 0);
      scale *= 1.05;
      contentGroup.getTransforms().add(scaleTransform);
      nodeManager.resieAll(0.95);

    } else if (event.getSource() == zoomOut) {
      Scale scaleTransform = new Scale(.95, .95, 0, 0);
      scale *= 0.95;
      contentGroup.getTransforms().add(scaleTransform);
      nodeManager.resieAll(1.05);
    }
  }

  public void setCoords(ActionEvent event) {
    // Pane pane = new Pane();
    // pane.setPrefSize(700, 426);
    // pane.setLayoutX(480);
    // pane.setLayoutY(200);
    // System.out.println("button pressed");
    // mapScroll.setContent(pane);
    clickPane.setDisable(false);
    clickPane.setViewOrder(-1000);
    clickPane.setStyle("-fx-background-color: rgba(0,0,0, .15)");

    clickPane.setOnMouseClicked(
        (MouseEvent click) -> {
          System.out.println(mapScroll.getVvalue());
          System.out.println(mapScroll.getHvalue());
          xCoordField.setText(String.valueOf((click.getSceneX() - 480) / scale));
          yCoordField.setText(String.valueOf((click.getSceneY() - 200) / scale));
          clickPane.setStyle("-fx-background-color: rgba(0,0,0,0)");
          System.out.println(mapPane.getWidth() * mapPane.getScaleX());
          clickPane.setDisable(true);
          try {
            editNode();
          } catch (IOException | NullPointerException e) {
          }
        });
  }
}
