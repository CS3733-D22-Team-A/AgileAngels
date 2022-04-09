package edu.wpi.agileAngels.Controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.agileAngels.Database.Location;
import javafx.event.ActionEvent;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;

public class Node {

  private Location location;
  private NodeManager nodeManager;
  private JFXButton button = new JFXButton();

  public Node(Location location, NodeManager nodeManager) {
    this.location = location;
    this.nodeManager = nodeManager;

    button.setLayoutX((this.getXCoord() - 800) / 5);
    button.setLayoutY((this.getYCoord() - 350) / 5);
    button.setText(String.valueOf(location.getNodeType().charAt(0)));
    button.setOnAction(
        (ActionEvent event2) -> {
          isClicked();
        });

    // set the circle color to coordinate with the node type
    // if(this.getNodeType() == "Test") {
    button.setId("blue"); // this ID will be used in the CSS file to style the button
    // }

  }

  public void resetLocation() {
    button.setLayoutX((this.getXCoord() - 800) / 5);
    button.setLayoutY((this.getYCoord() - 350) / 5);
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
