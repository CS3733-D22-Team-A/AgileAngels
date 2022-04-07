package edu.wpi.agileAngels;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;

public class Node {
  private NodeManager nodeManager = NodeManager.getNodeManager();

  private Location location;
  private JFXButton button = new JFXButton();

  public Node(Location location) {
    this.location = location;

    button.setLayoutX(this.getXCoord());
    button.setLayoutY(this.getYCoord());
    button.setText("Node");
    button.setOnAction(
        (ActionEvent event2) -> {
          isClicked();
        });

    // set the circle color to coordinate with the node type
    // if(this.getNodeType() == "Test") {
    button.setId("blue"); // this ID will be used in the CSS file to style the button
    // }

  }

  public void isClicked() {
    nodeManager.loadNode(this);
    System.out.println(this);
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
    return Double.parseDouble(location.getXCoord());
  }

  public Double getYCoord() {
    return Double.parseDouble(location.getYCoord());
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

  public void setButton(JFXButton button) {
    this.button = button;
  }

  //  public void editLocation(String Name, Double XCoord, Double YCoord, int Floor) {
  //  }

  public void editLocation(Double XCoord, Double YCoord, String type) {}
}
