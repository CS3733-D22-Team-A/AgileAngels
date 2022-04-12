package edu.wpi.agileAngels.Controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.agileAngels.Database.Location;
import javafx.event.ActionEvent;

public class Node {

  private Location location;
  private NodeManager nodeManager;
  private JFXButton button = new JFXButton();

  private double fontSize = 10;

  public Node(Location location, NodeManager nodeManager) {
    this.location = location;
    this.nodeManager = nodeManager;

    button.setLayoutX((this.getXCoord() - 775) / 3.225);
    button.setLayoutY((this.getYCoord() - 320) / 3.232);
    button.setText(String.valueOf(location.getNodeType().charAt(0)));
    button.setStyle("-fx-font-size: 8");
    button.setOnAction(
        (ActionEvent event2) -> {
          isClicked();
        });

    // set the circle color to coordinate with the node type
    // if(this.getNodeType() == "Test") {
    button.setId("blue"); // this ID will be used in the CSS file to style the button
    // }

  }

  public void resizeButton(double scaleFactor) {
    // button.setStyle("-fx-font-size: 6");
  }

  public void resetLocation() {
    button.setLayoutX(this.getXCoord());
    button.setLayoutY(this.getYCoord());

    button.setText(String.valueOf(location.getNodeType().charAt(0)));
  }

  public void isClicked() {
    nodeManager.loadNode(this);
  }

  public Location getLocation() {
    return location;
  }

  public String getNodeID() {
    return location.getNodeID();
  }

  public String getName() {
    return location.getLongName();
  }

  public String getFloor() {
    return location.getFloor();
  }

  public Double getXCoord() {
    return location.getXCoord();
  }

  public Double getYCoord() {
    return location.getYCoord();
  }

  public String getNodeType() {
    return location.getNodeType();
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public JFXButton getButton() {
    return button;
  }

  public void changeLocationXCoord(double x) {
    location.setXCoord(x);
  }

  public void changeLocationYCoord(double y) {
    location.setYCoord(y);
  }

  public void changeLocationType(String nodeType) {
    location.setNodeType(nodeType);
  }

  public void changeLocationName(String name) {
    location.setLongName(name);
  }

  //  public void editLocation(String Name, Double XCoord, Double YCoord, int Floor) {
  //  }

  public void editLocation(Double XCoord, Double YCoord, String type) {}
}
