package edu.wpi.agileAngels.Controllers;

import edu.wpi.agileAngels.Database.Location;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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


public class MapsController extends MainController implements Initializable {

  @FXML
  private ImageView floorTwoMap,
      floorThreeMap,
      lowerLevelOneMap,
      lowerLevelTwoMap,
      floorThreeDetailMap;
  private ImageView floorOneMap, floorTwoMap, floorThreeMap, lowerLevelOneMap, lowerLevelTwoMap;
  @FXML private ScrollPane mapScroll;
  @FXML
  private Button floorTwo,
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
  @FXML private TextField nameField, typeField, xCoordField, yCoordField;
  @FXML Pane mapPane, clickPane;
  @FXML AnchorPane anchor;
  @FXML Label floorLabel, nodeIDField;
  @FXML MenuItem pati, stor, dirt, hall, elev, rest, stai, dept, labs, info, conf, exit, retl, serv;
  @FXML MenuButton typeDropdown;

  LocationNode currentLocationNode = null;
  RequestNode currentRequestNode = null;
  private String currentFloor = "2";

  Pane pane2 = new Pane();
  Pane pane3 = new Pane();
  Pane paneL1 = new Pane();
  Pane paneL2 = new Pane();

  LocationNodeManager locationNodeManager = new LocationNodeManager(this);
  RequestNodeManager requestNodeManager = new RequestNodeManager(this);

  public MapsController() throws SQLException {}

  double scale = 1;
  double panX = 0;
  double panY = 0;

  /**
   * Called on page load, creates panes for each map, adds the images for each map to its pane, and
   * sets their initial visibility
   *
   * @param location
   * @param resources
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    mapPane.getChildren().add(pane2);
    pane2.getChildren().add((floorTwoMap));
    pane2.setVisible(true);
    mapPane.getChildren().add(pane3);
    pane3.getChildren().add((floorThreeMap));
    pane3.getChildren().add(floorThreeDetailMap);
    pane3.setVisible(false);
    mapPane.getChildren().add(paneL1);
    paneL1.getChildren().add((lowerLevelOneMap));
    paneL1.setVisible(false);
    paneL2.getChildren().add((lowerLevelTwoMap));
    mapPane.getChildren().add(paneL2);
    paneL2.setVisible(false);
    floorTwo.setViewOrder(-100);
    floorThree.setViewOrder(-100);
    lowerLevelOne.setViewOrder(-100);
    lowerLevelTwo.setViewOrder(-100);

    locationNodeManager.createNodesFromDB();
    try {
      requestNodeManager.createNodesFromDB();
    } catch (SQLException e) {
      e.printStackTrace();
    }
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
            + typeDropdown.getText()
            + String.format("%03d", typeCount)
            + ((currentFloor.length() == 1) ? ("0" + currentFloor) : (currentFloor));

    Location newLocation =
        new Location(
            nodeID,
            (Double.parseDouble(xCoordField.getText()) * 3.225) + 775,
            (Double.parseDouble(yCoordField.getText()) * 3.232) + 320,
            currentFloor,
            "TOWER",
            typeDropdown.getText(),
            nameField.getText(),
            nodeID);
    displayLocationNode(locationNodeManager.addNode(newLocation));
    clearFields();
  }

  /**
   * Edits the parameters of currentNode
   *
   * @throws IOException NumberFormatException from parseDouble
   */
  @FXML
  private void editNode() throws IOException {
    Double xCoord = Double.parseDouble(xCoordField.getText());
    Double yCoord = Double.parseDouble(yCoordField.getText());
    String name = nameField.getText();
    String type = typeField.getText();
    currentLocationNode.changeLocationXCoord(xCoord);
    currentLocationNode.changeLocationYCoord(yCoord);
    currentLocationNode.changeLocationName(name);
    currentLocationNode.changeLocationType(type);
    currentLocationNode.resetLocation();
    currentLocationNode = null;
    locationNodeManager.editNode(currentLocationNode, xCoord, yCoord, name, type);
  }

  /**
   * Removes currentNode from its pane and calls NodeManager.deleteNode(), which removes the node
   * from the hashmap in NodeManager and the database
   */
  @FXML
  private void removeNode() {
    if (currentLocationNode.getFloor().equals("2")) {
      pane2.getChildren().remove(currentLocationNode.getButton());
    } else if (currentLocationNode.getFloor().equals("3")) {
      pane3.getChildren().remove(currentLocationNode.getButton());
    } else if (currentLocationNode.getFloor().equals("L1")) {
      paneL1.getChildren().remove(currentLocationNode.getButton());
    } else if (currentLocationNode.getFloor().equals("L2")) {
      paneL2.getChildren().remove(currentLocationNode.getButton());
    }
    locationNodeManager.deleteNode(currentLocationNode.getNodeID());
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
    if (editButton.isVisible() == true) {
      addButton.setVisible(true);
      addButton.setViewOrder(-1000);
      editButton.setVisible(false);
      removeButton.setVisible(false);
      switchToAddButton.setText("Delete/Edit");
      currentNode = null;
      clearFields();
    } else {
      addButton.setVisible(false);
      addButton.setViewOrder(100);
      editButton.setVisible(true);
      removeButton.setVisible(true);
      switchToAddButton.setText("Add");
      currentNode = null;
      clearFields();
    }
  }

  /**
   * Adds the button for a location node to the pane corresponding to its floor
   *
   * @param node the node whose button is added to a pane
   */
  public void displayLocationNode(LocationNode node) {
    if (node.getFloor().equals("2")) {
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
   * Adds the button for a request node to the pane corresponding to its floor
   *
   * @param node the node whose button is added to a pane
   */
  public void displayRequestNode(RequestNode node) {
    if (node.getFloor().equals("2")) {
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
    pane2.setVisible(false);
    pane3.setVisible(false);
    paneL1.setVisible(false);
    paneL2.setVisible(false);
    if (event.getSource() == floorTwo) {
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
    xCoordField.setText("X-Coordinate:");
    yCoordField.setText("Y-Coordinate:");
    typeDropdown.setText("Node Type");
    nodeIDField.setText("Node ID:");
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
      nodeManager.resizeAll(0.95);

    } else if (event.getSource() == zoomOut && scale >= 1.05) {
      Scale scaleTransform = new Scale(.95, .95, 0, 0);
      scale *= 0.95;
      contentGroup.getTransforms().add(scaleTransform);
      nodeManager.resizeAll(1.05);
      if (mapScroll.getHvalue() > 0.8 || mapScroll.getVvalue() > 0.8) {
        mapScroll.setHvalue(0.6);
        mapScroll.setVvalue(0.6);
      }
    }
  }

  public void getPanChange() {}

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
    panX = mapScroll.getHvalue() * (mapPane.getWidth() - mapScroll.getWidth() / scale);
    panY = mapScroll.getVvalue() * (mapPane.getHeight() - mapScroll.getHeight() / scale);
    double panX2 = mapScroll.getHvalue() * 48;
    double panY2 = mapScroll.getVvalue() * 28.6;

    clickPane.setOnMouseClicked(
        (MouseEvent click) -> {
          System.out.println(mapScroll.getViewportBounds());

          if (scale == 1) {
            xCoordField.setText(String.valueOf(((click.getSceneX() - 460) / scale) + panX - 8));
            yCoordField.setText(String.valueOf(((click.getSceneY() - 60) / scale) + panY - 8));
          } else {
            xCoordField.setText(
                String.valueOf(((click.getSceneX() - 460) / scale) + panX - panX2 - 8));
            yCoordField.setText(
                String.valueOf(((click.getSceneY() - 60) / scale) + panY - panY2 - 8));
          }
          clickPane.setStyle("-fx-background-color: rgba(0,0,0,0)");
          clickPane.setDisable(true);

          try {
            editNode();
          } catch (IOException | NullPointerException e) {
          }
        });
  }
}
