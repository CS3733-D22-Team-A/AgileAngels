package edu.wpi.agileAngels;

import java.io.IOException;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

public class Node {
  private final NodeManager nodeManager = NodeManager.getNodeManager();
  private Location location;
  private Circle circle;

  public Node(Location location) {
    this.location = location;
  }

  private Circle createCircle() {
    circle = new Circle();
    circle.setOnMousePressed(
        (MouseEvent event) -> {
          if (event.isSecondaryButtonDown()) {
            isClicked();
          }
        });
    return circle;
  }

  public void isClicked() {
    try {
      nodeManager.loadNode(this);
    } catch (IOException e) {
      e.printStackTrace();
    }
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

  public String getXCoord() {
    return location.getXCoord();
  }

  public String getYCoord() {
    return location.getYCoord();
  }

  public String getNodeType() {
    return location.getNodeType();
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public Circle getCircle() {
    return circle;
  }

  public void setCircle(Circle circle) {
    this.circle = circle;
  }

  public void editLocation(String Name, Double XCoord, Double YCoord, int Floor) {}
}
